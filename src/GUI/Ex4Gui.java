
package GUI;

import Robot.Play;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Ex4Gui extends JFrame implements MouseListener
{
    Play play;
    BufferedImage MapImg,PacmanImg, FruitImg;

    public Ex4Gui()
    {
        initGUI();
        this.addMouseListener(this);

        try
        {
            MapImg = ImageIO.read(new File("Ariel1.png"));
            PacmanImg = ImageIO.read(new File("pacman.png"));
            FruitImg = ImageIO.read(new File("fruit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initGUI()
    {
        play = new Play("data/Ex4_OOP_example8.csv");
        play.setInitLocation(35.206952 , 32.106535);
    }

    @Override
    public void paint(Graphics g)
    {
        g.drawImage(MapImg , 0 , 0 ,this.getWidth() , this.getHeight() , this);
        ArrayList<String> game = play.getBoard();
        for (String s : game)
        {
            if (s.charAt(0) == 'P')
            {

                //g.drawImage(PacmanImg , )
            }
        }

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    public BufferedImage getMapImg()
    {
        return MapImg;
    }
}
