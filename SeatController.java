package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class SeatController {
    private final CinemaSeating cinemaSeating;

    @Autowired
    public SeatController(CinemaSeating cinemaSeating) {
        this.cinemaSeating = cinemaSeating;
    }

    /**
     * Handles GET requests to /seats.
     * @return A map containing total rows, columns, and a list of available seats with their prices.
     */
    @GetMapping(value = "/seats", produces = "application/json")
    public Map<String, Object> getSeats() {
        Map<String, Object> response = new HashMap<>();
        response.put("rows", cinemaSeating.getRows());
        response.put("columns", cinemaSeating.getColumns());
        response.put("seats", cinemaSeating.getAvailableSeats());
        return response;
    }

    /**
     * Handles POST requests to /seats/purchase.
     * Attempts to purchase a seat specified in the SeatRequest.
     *
     * @param seatRequest The request body containing the row and column of the desired seat.
     * @return The ResponseEntity containing either the purchased ticket details or an error message.
     */
    @PostMapping(value = "/purchase", produces = "application/json")
    public ResponseEntity<?> purchaseTicket(@RequestBody SeatRequest seatRequest) {
        int row = seatRequest.getRow();
        int col = seatRequest.getColumn();

        if (row <= 0 || row > cinemaSeating.getRows() || col <= 0 || col > cinemaSeating.getColumns()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of("The number of a row or a column is out of bounds!")
            );
        }

        Optional<Seat> seatToPurchase = cinemaSeating.getSeats().stream()
                .filter(s -> s.getRow() == row && s.getColumn() == col)
                .findFirst();

        if (seatToPurchase.isEmpty()) {
            return ResponseEntity.badRequest().body(ErrorResponse.of("Seat not found."));
        }

        Seat seat = seatToPurchase.get();
        UUID token = seat.purchase();

        if (token != null) {
            return ResponseEntity.ok(new PurchasedTicketResponse(token, seat));
        } else {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of("The ticket has been already purchased!")
            );
        }
    }

    /**
     * Handles POST requests to /seats/return.
     * Attempts to return a seat specified in the ReturnRequest.
     *
     * @param returnRequest The request body containing the token of the seat to be returned.
     * @return The ResponseEntity containing either the returned ticket details or an error message.
     */
    @PostMapping(value = "/return", produces = "application/json")
    public ResponseEntity<?> returnTicket(@RequestBody ReturnRequest returnRequest) {
        UUID token = returnRequest.getToken();

        if (token == null) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of("Wrong token!")
            );
        }

        Optional<Seat> seatToReturn = cinemaSeating.getSeats().stream()
                .filter(seat -> token.equals(seat.getToken()))
                .findFirst();

        if (seatToReturn.isPresent()) {
            Seat seat = seatToReturn.get();
            seat.makeAvailable();
            return ResponseEntity.ok(new ReturnedTicketResponse(seat));
        } else {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of("Wrong token!")
            );
        }
    }


    @GetMapping(value = "/stats", produces = "application/json")
    public ResponseEntity<?> getStats(@RequestParam(required = false) Optional<String> password) {
        final String SECRET = "super_secret";

        if (!password.map(SECRET::equals).orElse(false)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ErrorResponse.of("The password is wrong!"));
        } else {
            int totalIncome = cinemaSeating.getIncome();
            int purchasedSeats = cinemaSeating.getPurchasedSeatsCount();
            int availableSeats = cinemaSeating.getRows() * cinemaSeating.getColumns() - cinemaSeating.getPurchasedSeatsCount();
            return ResponseEntity.ok(new StatsResponse(totalIncome, availableSeats, purchasedSeats));
        }
    }

}


