package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame implements MouseListener
{
    public BufferedImage MapImg,PacmanImg, FruitImg;
    public int x = -1, y = -1;
    public int buttonClicked = -1;

    public MyFrame()
    {
        initGUI();
        this.addMouseListener(this);
    }

    private void initGUI()
    {
        MenuBar menuBar = new MenuBar();
        Menu convertion_menu = new Menu("Csv/Kml");
        Menu Start_menu = new Menu("Game Menu");
        MenuItem CI1 = new MenuItem("Load Game(Csv)");
        MenuItem CI2 = new MenuItem("Save Game(Csv)");
        MenuItem CI3 = new MenuItem("Save Game(Kml)");
        MenuItem SI1 = new MenuItem("Start");
        MenuItem SI2 = new MenuItem("Restart");

        menuBar.add(convertion_menu);
        menuBar.add(Start_menu);

        convertion_menu.add(CI1);
        convertion_menu.add(CI2);
        convertion_menu.add(CI3);

        Start_menu.add(SI1);
        Start_menu.add(SI2);

        this.setMenuBar(menuBar);

        try
        {
            MapImg = ImageIO.read(new File("Ariel1.png"));
            PacmanImg = ImageIO.read(new File("pacman.png"));
            FruitImg = ImageIO.read(new File("fruit.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g)
    {
        g.drawImage(MapImg,0,0,this);

        if (x != -1 && y != -1)
        {
           if(buttonClicked == 1)
            {
                g.drawImage(PacmanImg,x,y,20,20,this);
                buttonClicked = -1;
            }
            else if (buttonClicked == 3)
            {
                g.drawImage(FruitImg,x,y,20,20,this);
                buttonClicked = -1;
            }

        }
    }


    @Override
    public void mouseClicked(MouseEvent arg)
    {
        x = arg.getX();
        y = arg.getY();
        buttonClicked = arg.getButton();
        //ImagePanel IP = new ImagePanel(x,y,buttonClicked);
        //IP.setVisible(true);
        //add(IP);
        /*JLabel ImageLabel = new JLabel(new ImageIcon(PacmanImg));
        ImageLabel.setBounds(x,y,20,20);
        ImageLabel.setVisible(true);
        add(ImageLabel);*/
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
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




}
