package cinema;

import java.util.UUID;

public class StatsResponse {
    private int income;
    private int available;
    private int purchased;

    public StatsResponse(int income, int available, int purchased) {
        this.income = income;
        this.available = available;
        this.purchased = purchased;
    }

   // getters
    public int getIncome() { return income; }
    public int getAvailable() { return available; }
    public int getPurchased() { return purchased; }
}
