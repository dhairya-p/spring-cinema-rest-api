package cinema;

import java.util.ArrayList;
import java.util.List;

public class CinemaSeating {
    private final int rows;
    private final int columns;
    private final List<Seat> seats = new ArrayList<>();

    // Constructor
    public CinemaSeating(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                seats.add(new Seat(row, column));
            }
        }
    }

    // Getters
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
