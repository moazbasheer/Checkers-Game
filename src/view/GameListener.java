package view;

import entity.pieces.Color;
import entity.pieces.Location;

public interface GameListener {
    public void onGameOver(Color winner);
    public void onMovePiece(Location location, Location destination, Location captured);
}
