package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Class represents a seat in the cinema.
 * Each seat has row and column numbers and a price.
 * The price depends on the row number.
 * The seat is available for purchase until it is purchased.
 */
public class Seat {
    private final int row;
    private final int column;
    private final int price;
    @JsonIgnore
    private final AtomicBoolean purchased = new AtomicBoolean(false);

    /**
     * Constructs a new Seat.
     * @param row seat row
     * @param col seat column
     */
    public Seat( int row, int col) {
        this.row = row;
        this.column = col;
        this.price = (row <= 4) ? 10 : 8;
    }

    // Getters
    /**
     * Gets the row number of the seat.
     * @return row number of the seat
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column number of the seat.
     * @return column number of the seat
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the price of the seat.
     * @return price of the seat
     */
    public int getPrice() {
        return price;
    }

    /**
     * Indicates whether the seat has already been purchased.
     * @return true if the seat is purchased, false otherwise
     */
    @JsonIgnore
    public boolean isPurchased() {
        return purchased.get();
    }

    /**
     * Checks if the seat is available for purchase.
     * @return true if the seat is available for purchase, false otherwise
     */
    public boolean purchase() {
        // Atomically sets the value to true if the current value is false.
        return purchased.compareAndSet(false, true);
    }
}
