package cinema;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The CinemaSeating class represents the seating arrangement in a cinema room.
 * It holds the number of rows and columns, and manages a list of Seat objects.
 *
 * It provides mechanisms to initialize the seating layout and manipulate seat data.
 */
@Component
public class CinemaSeating {
    private final int rows = 9;
    private final int columns = 9;
    // Synchronized list for thread safety on the collection
    private final List<Seat> seats = Collections.synchronizedList(new ArrayList<>());

    /**
     * The constructor for the CinemaSeating class.
     * It initializes the seats with the given 9 rows and 9 columns.
     * Each seat is represented by a Seat object.
     */
    public CinemaSeating() {
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                seats.add(new Seat(row, column));
            }
        }
    }

    /**
     * Returns the number of rows in the cinema seating arrangement.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the cinema seating arrangement.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns a list of all seats in the cinema.
     *
     * @return A list of all seats in the cinema.
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Returns a list of all seats that have not yet been purchased.
     *
     * @return A list of all seats that have not yet been purchased.
     */
    public List<Seat> getAvailableSeats() {
        return seats.stream()
                .filter(seat -> !seat.isPurchased())
                .collect(Collectors.toList());
    }

    public int getPurchasedSeatsCount() {
        return (int)seats.stream()
                .filter(seat -> seat.isPurchased())
                .count();
    }

    public int getIncome() {
        return (int)seats.stream()
                .filter(seat -> seat.isPurchased())
                .mapToInt(seat -> seat.getPrice())
                .sum();
    }
}
