package engine;

import entity.pieces.Color;
import entity.pieces.Location;
import entity.pieces.Piece;
import entity.player.Player;
import exceptions.CaptureBybassException;
import exceptions.CheckersException;
import exceptions.InvalidMoveException;
import exceptions.NotYourTurnException;
import view.GameListener;

import java.util.ArrayList;

public class Game implements PlayerListener {
    private Player currentPlayer;
    private Player opponentPlayer;
    private Board board;
    private GameListener listener;
    private final int DIMENSION;

    public Game(GameListener listener){
        DIMENSION = 700;
        this.listener = listener;
        currentPlayer = new Player(entity.pieces.Color.RED,this);
        opponentPlayer = new Player(entity.pieces.Color.BLUE, this);
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public int getDIMENSION(){
        return DIMENSION;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard(){
        return board;
    }

    public ArrayList<Piece> getPieces(){
        return board.getPieces();
    }

    private boolean didCapture(Piece piece,Location destination){
        ArrayList<Location> captures = board.getCaptures(piece);
        for(int i = 0;i < captures.size();i++){
            if(captures.get(i).equals(destination)){
                return true;
            }
        }
        return false;
    }

    public void Play(Piece piece, Location destination) {
        try {
            Location location = piece.getLocation();

            if(!(currentPlayer.getColor().equals(piece.getColor()))){
                throw new NotYourTurnException("This is not your turn.");
            }

            if(board.captureCapable(currentPlayer.getColor()).size() > 0){
                if(!didCapture(piece,destination)){ // if his player can capture but he did't capture.
                    throw new CaptureBybassException("Invalid move,\n you should capture.");
                }else{ // if captured.
                    // get location and destination.
                    int row1 = piece.getLocation().getRow();
                    int col1 = piece.getLocation().getCol();
                    int row2 = destination.getRow();
                    int col2 = destination.getCol();

                    // capturing now....
                    Piece capturedPiece = board.getPieceAt(new Location((row1 + row2)/2,(col1 + col2)/2));
                    capturedPiece.captured(); // rem--.
                    board.capturePieceAt(new Location((row1 + row2)/2,(col1 + col2)/2));
                    piece.setLocation(destination);
                    listener.onMovePiece(location, destination, new Location((row1 + row2) / 2,(col1 + col2) / 2));

                    // if there is no other capture turns.
                    if(board.captureCapable(currentPlayer.getColor()).size() == 0) {
                        endTurn();
                    }
                    return ;
                }
            }else if (board.movePiece(piece, destination)) {
                piece.setLocation(destination);
                listener.onMovePiece(location, destination, null);
                endTurn();
            }
        }catch(CheckersException e){
            return;
        }
    }

    private void endTurn(){
        Player temp = opponentPlayer;
        opponentPlayer = currentPlayer;
        currentPlayer = temp;
        if(!board.canMakeMove(currentPlayer.getColor())) {
            currentPlayer.lost();
        }
    }

    @Override
    public void onLose(Player player) {
        if(player.getColor() == Color.RED) {
            listener.onGameOver(Color.BLUE);
        }else{
            listener.onGameOver(Color.RED);
        }
    }

}
