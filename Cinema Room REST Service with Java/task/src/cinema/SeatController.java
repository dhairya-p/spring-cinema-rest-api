package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController {
    private final CinemaSeating cinemaRoom = new CinemaSeating(9, 9);

    @GetMapping("/seats")
    public CinemaSeating getSeats() {
        return cinemaRoom;
    }
}
