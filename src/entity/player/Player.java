package entity.player;

import engine.PlayerListener;
import entity.pieces.Color;

public class Player implements PieceListener {
    private Color color;
    private int remainingPieces;
    private PlayerListener listener;

    public Player(Color color_, PlayerListener listener){
        this.remainingPieces = 12;
        this.listener = listener;
        this.color = color_;
    }

    public Color getColor(){
        return color;
    }

    public void lost(){
        listener.onLose(this);
    }

    @Override
    public void onPieceCaptured() {
        remainingPieces --;

        if(remainingPieces == 0){
            listener.onLose(this);
        }
    }
}
