package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public class Seat {
    private final int row;
    private final int column;
    private final int price;
    @JsonIgnore
    private final AtomicReference<UUID> token = new AtomicReference<>();

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = (row <= 4) ? 10 : 8;
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public int getPrice() { return price; }

    @JsonIgnore
    public UUID getToken() { return token.get();}

    @JsonIgnore
    public UUID purchase() {
        UUID newToken = UUID.randomUUID();
        if (this.token.compareAndSet(null, newToken)) {
            return newToken; // Success
        }
        return null; // Failure, already purchased
    }

    @JsonIgnore
    public boolean isPurchased() {
        return token.get() != null;
    }

    public void makeAvailable() {
        this.token.set(null);
    }
}
