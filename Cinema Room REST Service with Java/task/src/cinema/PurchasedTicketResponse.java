package cinema;

public class PurchasedTicketResponse {
    private int row;
    private int column;
    private int price;

    public PurchasedTicketResponse(Seat seat) {
        this.row = seat.getRow();
        this.column = seat.getColumn();
        this.price = seat.getPrice();
    }

    // --- Getters ---
    public int getRow() { return row; }
    public int getColumn() { return column; }
    public int getPrice() { return price; }
}