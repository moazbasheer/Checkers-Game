package entity.pieces;

import entity.player.PieceListener;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Location location,Color color_, PieceListener listener) {
        super(location,color_,listener);
    }
    public Pawn(int row, int col,Color color, PieceListener listener){
        super(row,col,color,listener);
    }

    @Override
    public ArrayList<Location> possibleMoves() {
        Location location = this.getLocation();
        int row = location.getRow();
        int col = location.getCol();
        ArrayList<Location> plays = new ArrayList<>();
        if(getColor() == Color.RED) {
            plays.add(new Location(row - 1, col - 1));
            plays.add(new Location(row - 1, col + 1));
            // capture moves
            plays.add(new Location(row - 2, col - 2));
            plays.add(new Location(row - 2, col + 2));
        }else{
            plays.add(new Location(row + 1, col - 1));
            plays.add(new Location(row + 1, col + 1));
            // Capture moves
            plays.add(new Location(row + 2, col - 2));
            plays.add(new Location(row + 2, col + 2));
        }
        
        return plays;
    }

    public boolean equals(Pawn obj){
        return obj.getLocation().equals(getLocation());
    }
}
