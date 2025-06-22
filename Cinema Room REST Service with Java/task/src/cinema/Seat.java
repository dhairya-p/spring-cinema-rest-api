package cinema;

public class Seat {
    private int row;
    private int column;

    public Seat() {}

    public Seat( int row, int col) {
        this.row = row;
        this.column = col;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // Setters
    public void setRows(int row) {
        this.row = row;
    }

    public void setColumns(int column) {
        this.column = column;
    }
}
