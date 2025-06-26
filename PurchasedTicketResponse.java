package cinema;

import java.util.UUID;

public class PurchasedTicketResponse {
    private UUID token;
    private Ticket ticket;

    public PurchasedTicketResponse(UUID token, Seat seat) {
        this.token = token;
        this.ticket = new Ticket(seat);
    }

    public UUID getToken() { return token; }
    public Ticket getTicket() { return ticket; }
}