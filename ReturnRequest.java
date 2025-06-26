package cinema;

import java.util.UUID;

public class ReturnRequest {
    private UUID token;
    public ReturnRequest() {}
    public UUID getToken() { return token; }
    public void setToken(UUID token) { this.token = token; }
}