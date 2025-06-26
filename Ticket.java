package cinema;

public class Ticket {
    private final int row;
    private final int column;
    private final int price;

    public Ticket(Seat seat) {
        this.row = seat.getRow();
        this.column = seat.getColumn();
        this.price = seat.getPrice();
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public int getPrice() { return price; }
}

