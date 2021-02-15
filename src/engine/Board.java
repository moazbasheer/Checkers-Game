package engine;

import entity.pieces.Color;
import entity.pieces.Location;
import entity.pieces.Piece;
import exceptions.InvalidMoveException;
import java.util.ArrayList;

public class Board {
    private int dimension;
    private ArrayList<Piece> pieces;
    public Board (ArrayList<Piece> pieces, int dimension){
        this.dimension = dimension;
        this.pieces = pieces;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    private int distance(Location location1 ,Location location2){
        int rows = location1.getRow() - location2.getRow();
        rows = rows * rows;
        int cols = location1.getCol() - location2.getCol();
        cols = cols * cols;
        return (int) Math.sqrt(cols + rows);
    }

    public ArrayList<Location> getCaptures(Piece piece){ // helper to get capturing moves only.
        Location location = piece.getLocation();
        ArrayList<Location> moves = piece.possibleMoves();
        ArrayList<Location> captures = new ArrayList<>();        //(row1+row2)/2
        int row1 = location.getRow(), col1 = location.getCol();

        for(int i=0;i<moves.size();i++) {
            int row2 = moves.get(i).getRow(), col2 = moves.get(i).getCol();
            Piece captured = getPieceAt(new Location((row1 + row2)/2,(col1 + col2)/2));
            if(distance(moves.get(i), location) > 1 &&
                    isValidLocation(moves.get(i)) && getPieceAt(moves.get(i)) == null
                    && getPieceAt(new Location((row1 + row2)/2,(col1 + col2)/2)) != null
                    && !piece.getColor().equals(captured.getColor())){
                captures.add(moves.get(i));
            }
        }
        return captures;
    }

    public ArrayList<Location> getNormalPlays(Piece piece){

        Location location = piece.getLocation();
        ArrayList<Location> plays = piece.possibleMoves();

        ArrayList<Location> possiblePlays = new ArrayList<>();

        for(int i=0;i<plays.size();i++){
            if(distance(plays.get(i), location) < 2 &&
                    isValidLocation(plays.get(i)) && getPieceAt(plays.get(i)) == null) {

                possiblePlays.add(plays.get(i));
            }
        }

        return possiblePlays;
    }

    private boolean isValidLocation(Location location){
        return location.getRow() >= 0 && location.getRow() < 8 &&
                location.getCol() < 8 && location.getCol() >= 0;
    }

    void capturePieceAt(Location location){
        for(int i=0;i<pieces.size();i++){
            if(pieces.get(i).getLocation().equals(location)) {
                pieces.remove(i);
                break;
            }
        }
    }

    public void upgradePieceAt(Piece piece,Location location){
        Piece king = piece.upgrade();
        for(int i=0;i<pieces.size();i++){
            if(pieces.get(i).getLocation().equals(location)) {
                pieces.remove(i);
                pieces.add(king);
                break;
            }
        }
    }

    public boolean movePiece(Piece piece, Location destination) throws InvalidMoveException{
        ArrayList<Location> moves = this.getNormalPlays(piece);

        int found = 0;
        for(int i = 0;i < moves.size();i++){

            if(moves.get(i).equals(destination)){

                return true;
            }
        }
        throw new InvalidMoveException("Invalid move, try again.");
    }

    public ArrayList<Piece> captureCapable(Color color){
        ArrayList<Piece> pieces = getPieces();
        ArrayList<Piece> captures = new ArrayList<>();

        for(int i=0;i<pieces.size();i++){
            if(pieces.get(i).getColor().equals(color)){
                if(getCaptures(pieces.get(i)).size() > 0){
                    captures.add(pieces.get(i));
                }
            }
        }
        return captures;
    }

    public boolean canMakeMove(Color color){
        for(int i=0;i<pieces.size();i++) {
            if(pieces.get(i).getColor().equals(color)) {
                if(getNormalPlays(pieces.get(i)).size() > 0 || getCaptures(pieces.get(i)).size() > 0){
                    return true;
                }
            }
        }
        return false;
    }

    public Piece getPieceAt(Location location){
        for(int i=0;i<pieces.size();i++){
            if(pieces.get(i).getLocation().equals(location)) {
                return pieces.get(i);
            }
        }
        return null;
    }
}
