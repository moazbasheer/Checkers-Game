package entity.pieces;

public class Location {
    private int row;
    private int col;
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean equals(Location obj) {
        return row == obj.row && col == obj.col;
    }
}
