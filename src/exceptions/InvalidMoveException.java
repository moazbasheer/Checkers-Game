package exceptions;

import javax.swing.*;

public class InvalidMoveException extends CheckersException {
    public InvalidMoveException(){
        JOptionPane.showMessageDialog(null,"InvalidMoveException");
    }
    public InvalidMoveException (String errMsg){
        JOptionPane.showMessageDialog(null,errMsg);
    }
}
