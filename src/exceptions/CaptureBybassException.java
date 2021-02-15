package exceptions;

import javax.swing.*;

public class CaptureBybassException extends CheckersException {
    public CaptureBybassException(){
        JOptionPane.showMessageDialog(null,"CaptureBybassException");
    }
    public CaptureBybassException(String errMsg){
        JOptionPane.showMessageDialog(null,errMsg);
    }
}
