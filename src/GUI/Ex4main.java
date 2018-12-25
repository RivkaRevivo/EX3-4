package GUI;

import javax.swing.*;

public class Ex4main
{
    public static void main(String[] args)
    {
        Ex4Gui frame = new Ex4Gui();
        frame.setVisible(true);
        frame.setSize(frame.getMapImg().getWidth(),frame.getMapImg().getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
