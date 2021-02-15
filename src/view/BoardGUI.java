package view;

import engine.Board;
import engine.Game;
import entity.pieces.*;
import entity.pieces.Color;
import exceptions.CheckersException;
import exceptions.InvalidMoveException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BoardGUI {
    Game game;
    JFrame frame;
    JButton button[][];
    ImageIcon icons[][];
    int lastSelectedX;
    int lastSelectedY;

    public BoardGUI(){
        lastSelectedX = -1;
        lastSelectedY = -1;
        this.game = new Game(new GameListener() {
            @Override
            public void onGameOver(entity.pieces.Color winner) {
                JOptionPane.showMessageDialog(null, "Game Over, the " + winner + " is the winner");
                System.exit(0);
            }

            @Override
            public void onMovePiece(Location location,Location destination,Location captured){
                movePiece(location,destination,captured);
            }

        });
        setFrameOptions();
        setButtonsAndIcons();
    }

    public BufferedImage reSize(String dir) {
        BufferedImage b;
        ImageIcon icon = new ImageIcon(dir);
        Image img = icon.getImage();
        b = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.createGraphics();
        g.drawImage(img, 35, 35, 70, 65, null);
        return  b;
    }

    private void upgradeIfValid(Location location){
        if(game.getBoard().getPieceAt(location).getColor().equals(Color.RED)
                && location.getRow() == 0) {

            Piece piece = game.getBoard().getPieceAt(location);
            game.getBoard().upgradePieceAt(piece.upgrade(),location);

            icons[location.getRow()][location.getCol()] = new ImageIcon(reSize("imgs/redking.jpg"));
            button[location.getRow()][location.getCol()].setIcon(icons[location.getRow()][location.getCol()]);

        } else if(game.getBoard().getPieceAt(location).getColor().equals(Color.BLUE)
                && location.getRow() == 7) {

            Piece piece = game.getBoard().getPieceAt(location);
            game.getBoard().upgradePieceAt(piece.upgrade(),location);

            icons[location.getRow()][location.getCol()] = new ImageIcon(reSize("imgs/blueking.jpg"));
            button[location.getRow()][location.getCol()].setIcon(icons[location.getRow()][location.getCol()]);
        }
    }

    private void movePiece(Location location,Location destination,Location captured){
        // updating icons
        icons[destination.getRow()][destination.getCol()] = icons[location.getRow()][location.getCol()];
        icons[location.getRow()][location.getCol()] = null;

        // updating buttons
        button[location.getRow()][location.getCol()].setIcon(icons[location.getRow()][location.getCol()]);
        button[destination.getRow()][destination.getCol()].setIcon(icons[destination.getRow()][destination.getCol()]);

        // check for upgrading to king.
        upgradeIfValid(destination);

        // if there is a capture.
        if(captured != null) {
            icons[captured.getRow()][captured.getCol()] = null;
            button[captured.getRow()][captured.getCol()].setIcon(null);
        }
    }

    void setFrameOptions(){
        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(game.getDIMENSION(),game.getDIMENSION());
        frame.setLayout(new GridLayout(8,8));
        frame.setLocation(300,300);
    }

    void setButtonsAndIcons(){
        icons = new ImageIcon[8][8];
        button = new JButton[8][8];
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                button[i][j] = new JButton();
                if ((i + j) % 2 == 0) {
                    button[i][j].setBackground(java.awt.Color.LIGHT_GRAY);
                }
                else {
                    button[i][j].setBackground(java.awt.Color.WHITE);
                }
                // add button to the screen.
                frame.add(button[i][j]);
                // adding blue circles.
                if(i < 3 && (i+j)%2 == 0){
                    icons[i][j] = new ImageIcon(reSize("imgs/blue.png"));
                    button[i][j].setIcon(icons[i][j]);
                    pieces.add(new Pawn(i, j, entity.pieces.Color.BLUE, game.getOpponentPlayer()));
                } else if(i > 4 && (i+j)%2 == 0){ // adding red circles.
                    icons[i][j] = new ImageIcon(reSize("imgs/red.png"));
                    button[i][j].setIcon(icons[i][j]);
                    pieces.add(new Pawn(i, j, entity.pieces.Color.RED, game.getCurrentPlayer()));
                }
                button[i][j].addActionListener(new PieceSelection());
                game.setBoard(new Board(pieces,game.getDIMENSION()));
            }
        }
        game.setBoard(new Board(pieces,game.getDIMENSION()));

    }

    private class PieceSelection implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Location location = this.getPressedLocation(e);
            int selectedX = location.getRow();
            int selectedY = location.getCol();

            if(unexpectedActions(selectedX,selectedY)){
                return ;
            }

            if(lastSelectedX == -1 && lastSelectedY == -1){ // if started new action.
                lastSelectedX = selectedX;
                lastSelectedY = selectedY;
            }else {   // if it is second action.
                Piece playedPiece = game.getBoard().getPieceAt(new Location(lastSelectedX, lastSelectedY));
                game.Play(playedPiece, new Location(selectedX, selectedY));
                lastSelectedX = -1;
                lastSelectedY = -1;
            }
        }

        private boolean unexpectedActions(Integer selectedX,Integer selectedY) {
            if(lastSelectedX == -1 && lastSelectedY == -1){ // if started new action.
                if(icons[selectedX][selectedY] == null){ // if empty space.
                    return true;
                }
                return false;
            }
            try {
                // if it is second action
                if (icons[selectedX][selectedY] != null ||
                        selectedX == lastSelectedX && selectedY == lastSelectedY) { // if pressed on a piece.
                    lastSelectedX = -1;
                    lastSelectedY = -1;
                    throw new InvalidMoveException();
                }
                return false;
            }catch (CheckersException e){
                return true;
            }
        }

        Location getPressedLocation(ActionEvent e) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (e.getSource() == button[i][j]) {
                        return new Location(i,j);
                    }
                }
            }
            return null;
        }

    }
}
