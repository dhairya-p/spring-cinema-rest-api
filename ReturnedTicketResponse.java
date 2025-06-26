package cinema;

public class ReturnedTicketResponse {
    private Ticket ticket;

    public ReturnedTicketResponse(Seat returnedSeat) {
        this.ticket = new Ticket(returnedSeat);
    }

    public Ticket getTicket() { return ticket; }
}