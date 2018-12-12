package GUI;

import Algorithms.ShortestPathAlgo;
import GIS.*;
import Geom.Point3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class MyFrame extends JFrame implements MouseListener
{
    private BufferedImage MapImg,PacmanImg, FruitImg;
    private int x = -1, y = -1 , pacmanID = 0 , fruitID = 0;
    private int buttonClicked = -1;
    private Game thisGame;
    private boolean GameHasStarted = false;
    private LinkedList<Path> ShortPath;

    private int IconSize = 20;

    public MyFrame()
    {
        initGUI();
        this.addMouseListener(this);
    }

    private void initGUI()
    {
        thisGame = new Game();
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

        thisGame.setMyMap(new Map(new Point3D( 32.106046, 35.202574) , new Point3D(32.101858,   35.212405) , "Ariel1.png"));
        CI1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    thisGame.ResetLists();
                    File file = fileChooser.getSelectedFile();
                    thisGame.GameCsvReader(file.getPath());
                    repaint();
                }
            }
        });

        CI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                thisGame.GameCsvWriter();
            }
        });
        SI1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShortPath = ShortestPathAlgo.ShortestPath(thisGame);
                GameHasStarted = true;
                repaint();
            }
        });

    }

    public void paint(Graphics g)
    {
        g.drawImage(MapImg,0,0,this.getWidth(),this.getHeight(),this);
        Iterator<Pacman> IP = thisGame.PacmanIterator();

        if (GameHasStarted)
        {
            int i = 0;
            Pixel LastPos;
            while (IP.hasNext())
            {
                if (!ShortPath.get(i).isEmpty())
                    LastPos = thisGame.getMyMap().CoordinateToPixel(ShortPath.get(i).getLast().getPosition() , this.getWidth(),this.getHeight());
                else
                    LastPos = thisGame.getMyMap().CoordinateToPixel(thisGame.getPacman_listCopy().get(i).getPosition() , this.getWidth() , this.getHeight());
                g.drawImage(PacmanImg, (int) LastPos.getX() , (int) LastPos.getY(),IconSize,IconSize,this);
                IP.next();
                i++;
            }

            LinkedList<Path> CopyPath = new LinkedList<Path>(ShortPath);
            /*LinkedList<Pacman> CopyPacman = thisGame.getPacman_listCopy();
            //Draw the first Line Between The Pacmen and rhe fruit.
            for (i = 0; i < CopyPath.size(); i++)
            {
                if(!CopyPath.get(i).isEmpty())
                {
                    Pixel c1 = thisGame.getMyMap().CoordinateToPixel(CopyPacman.get(i).getPosition() , this.getWidth() , this.getHeight());
                    Pixel c2 = thisGame.getMyMap().CoordinateToPixel(CopyPath.get(i).get(0).getPosition() , this.getWidth() , this.getHeight());
                    g.drawLine((int) c1.getX() , (int) c1.getY() , (int) c2.getX() , (int) c2.getY());
                }
            }*/


            while (!CopyPath.isEmpty())
            {
                i = 0;
                while (CopyPath.get(0).size() > i + 1)
                {
                    Pixel c1 = thisGame.getMyMap().CoordinateToPixel(CopyPath.get(0).get(i).getPosition() , this.getWidth() , this.getHeight());
                    Pixel c2 = thisGame.getMyMap().CoordinateToPixel(CopyPath.get(0).get(i + 1).getPosition() , this.getWidth() , this.getHeight());
                    g.setColor(Color.RED);
                    g.drawLine((int)c1.getX() +9 , (int)c1.getY() + 9,(int) c2.getX() + 9, (int) c2.getY()+ 9);
                    g.drawLine((int)c1.getX()+11 , (int)c1.getY() +11 ,(int) c2.getX()+11 , (int) c2.getY()+11 );
                    g.drawLine((int)c1.getX() + 10 , (int)c1.getY() +10 ,(int) c2.getX() + 10 , (int) c2.getY() +10);
                    g.fillOval((int)c1.getX() , (int)c1.getY() , IconSize , IconSize);
                    i++;
                }
                CopyPath.removeFirst();
            }

        }
        else
        {

            while (IP.hasNext())
            {
                Pacman p = IP.next();
                Pixel pos = thisGame.getMyMap().CoordinateToPixel(p.getPosition(), this.getWidth(), this.getHeight());
                g.drawImage(PacmanImg, (int) pos.getX(), (int) pos.getY(), IconSize, IconSize, this);
            }

            Iterator<Fruit> IF = thisGame.FruitIterator();
            while (IF.hasNext())
            {
                Fruit f = IF.next();
                Pixel pos = thisGame.getMyMap().CoordinateToPixel(f.getPosition(), this.getWidth(), this.getHeight());
                g.drawImage(FruitImg, (int) pos.getX(), (int) pos.getY(), IconSize, IconSize, this);
            }
        }



        if (x != -1 && y != -1)
        {
            if(buttonClicked == 1)
            {
                g.drawImage(PacmanImg,x,y,IconSize,IconSize,this);
                buttonClicked = -1;
                Point3D PixelToCoord = thisGame.getMyMap().PixelToCoordinate(new Pixel(x,y) , this.getWidth() , this.getHeight());
                thisGame.AddPacman(new Pacman(PixelToCoord , null , 1 , 1 , pacmanID));
                pacmanID++;
            }
            else if (buttonClicked == 3)
            {
                g.drawImage(FruitImg,x,y,IconSize,IconSize,this);
                buttonClicked = -1;
                Point3D PixelToCoord = thisGame.getMyMap().PixelToCoordinate(new Pixel(x,y) , this.getWidth() , this.getHeight());
                thisGame.AddFruit(new Fruit(PixelToCoord , fruitID , 1));
                fruitID++;
            }
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     *
     * @param arg the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent arg)
    {

        x = arg.getX();
        y = arg.getY();
        buttonClicked = arg.getButton();
        repaint(x,y,IconSize,IconSize);
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

    public BufferedImage getMapImg()
    {
        return MapImg;
    }

}