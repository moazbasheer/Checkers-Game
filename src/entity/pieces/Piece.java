package entity.pieces;

import entity.player.PieceListener;

import java.util.ArrayList;

public abstract class Piece {
    private Location location;
    private Color color;
    private PieceListener listener;

    public void setLocation(Location location){
        this.location = location;
    }

    public Location getLocation(){
        return location;
    }

    public Color getColor() {
        return color;
    }

    public Piece(Location location, Color color, PieceListener listener){
        this.location = location;
        this.color = color;
        this.listener = listener;
    }

    public Piece(int row, int col, Color color, PieceListener listener){
        this.location = new Location(row,col);
        this.color = color;
        this.listener = listener;
    }

    public void captured(){
        listener.onPieceCaptured();
    }

    public King upgrade(){
        return new King(location,color,listener);
    }

    public abstract ArrayList<Location> possibleMoves();

}
