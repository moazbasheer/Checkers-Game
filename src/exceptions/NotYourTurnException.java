package exceptions;

import javax.swing.*;

public class NotYourTurnException extends CheckersException {
    public NotYourTurnException(){
        JOptionPane.showMessageDialog(null,"InvalidMoveException");
    }
    public NotYourTurnException(String errMsg){
        JOptionPane.showMessageDialog(null,errMsg);
    }
}
