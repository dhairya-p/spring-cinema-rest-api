package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    @GetMapping("/seats")
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
    @PostMapping("/purchase")
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
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of("Seat not found.")
            );
        }

        Seat seat = seatToPurchase.get();

        if (seat.purchase()) {
            // Purchase successful
            return ResponseEntity.ok(new PurchasedTicketResponse(seat));
        } else {
            // Seat was already purchased
            return ResponseEntity.badRequest().body(
                    ErrorResponse.of("The ticket has been already purchased!")
            );
        }
    }

    @DeleteMapping("/seats/purchase")
    public void cancelTicket(@RequestBody Seat seat) {
        // TODO
    }
}
