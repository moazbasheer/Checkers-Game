package entity.pieces;

import entity.player.PieceListener;

import java.util.ArrayList;

public class King extends Piece {
    public King(Location location, Color color, PieceListener listener) {
        super(location,color,listener);
    }
    public King(int row, int col, Color color, PieceListener listener){
        super(row,col,color,listener);
    }

    @Override
    public ArrayList<Location> possibleMoves() {
        Location location = this.getLocation();
        int row = location.getRow();
        int col = location.getCol();
        ArrayList<Location> plays = new ArrayList<>();
        // normal moves.
        plays.add(new Location(row - 1, col - 1));
        plays.add(new Location(row - 1, col + 1));
        plays.add(new Location(row + 1, col - 1));
        plays.add(new Location(row + 1, col + 1));

        // Capture moves.
        plays.add(new Location(row - 2, col - 2));
        plays.add(new Location(row - 2, col + 2));
        plays.add(new Location(row + 2, col - 2));
        plays.add(new Location(row + 2, col + 2));
        return plays;
    }

    public boolean equals(Piece obj){
        return getLocation().equals(obj.getLocation());
    }
}
