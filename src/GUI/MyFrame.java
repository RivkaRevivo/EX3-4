package GUI;

import Algorithms.ShortestPathAlgo;
import Factory.MapFactory;
import File_format.Path2Kml;
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

public class MyFrame extends JFrame implements MouseListener, Runnable
{
    private BufferedImage MapImg,PacmanImg, FruitImg;
    private int x = -1, y = -1 , pacmanID = 0 , fruitID = 0;
    private int buttonClicked = -1;
    private Game thisGame,GameCopy;
    private boolean GameHasStarted = false;
    private LinkedList<Path> ShortPath = null ,PathsCopy = null;

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

        thisGame.setMyMap(MapFactory.ArielMap());

        //Load Game From Csv
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


        //Save Game To csv
        CI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                thisGame.GameCsvWriter();
            }
        });

        //Save Paths To Kml
        CI3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PathsCopy != null)
                    Path2Kml.Paths2kml(PathsCopy);
            }
        });

        MyFrame temp = this;
        //Start Game
        SI1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShortPath = ShortestPathAlgo.ShortestPath(thisGame);
                GameHasStarted = true;
                PathsCopy = ShortestPathAlgo.GetCopyPaths( ShortPath);
                System.out.println("Game Time in second:" + ShortestPathAlgo.GetGameTime(ShortPath,thisGame));
                Thread t = new Thread(temp);
                t.start();
            }
        });

        //Restart Game
        SI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameHasStarted = false;
                thisGame = GameCopy;
                repaint();
            }
        });



    }

    public void paint(Graphics g)
    {
        g.drawImage(MapImg,0,0,this.getWidth(),this.getHeight(),this);
        Iterator<Pacman> IP;

       /* if (GameHasStarted)
        {

            IP = thisGame.PacmanIterator();

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

            GameHasStarted = false;
        }*/
        /*else
            {*/

                IP = thisGame.PacmanIterator();
                while (IP.hasNext())
                {
                    Pacman p = IP.next();
                    Pixel pos = thisGame.getMyMap().CoordinateToPixel(p.getPosition(), this.getWidth(), this.getHeight());
                    g.drawImage(PacmanImg, (int) pos.getX(), (int) pos.getY(), IconSize, IconSize, this);

                    //System.out.println("Pacman " + p.getId() + " New Position: x: " + p.getPosition().x() + " y: " + p.getPosition().y());
                    //System.out.println("Pacman " + p.getId() + " New Pixels: x: " + pos.getX() + " y: " + pos.getY());

                }

                Iterator<Fruit> IF = thisGame.FruitIterator();
                while (IF.hasNext())
                {
                Fruit f = IF.next();
                Pixel pos = thisGame.getMyMap().CoordinateToPixel(f.getPosition(), this.getWidth(), this.getHeight());
                g.drawImage(FruitImg, (int) pos.getX(), (int) pos.getY(), IconSize, IconSize, this);
                }


            if (x != -1 && y != -1)
            {
                if (buttonClicked == 1)
                {
                    g.drawImage(PacmanImg, x, y, IconSize, IconSize, this);
                    buttonClicked = -1;
                    Point3D PixelToCoord = thisGame.getMyMap().PixelToCoordinate(new Pixel(x, y), this.getWidth(), this.getHeight());
                    thisGame.AddPacman(new Pacman(PixelToCoord, null, 1, 1, pacmanID));
                    pacmanID++;
                    System.out.println("Pacman " + (pacmanID - 1) + " Original Position: x: " + PixelToCoord.x() + " y: " + PixelToCoord.y());
                    System.out.println("Pacman " + (pacmanID - 1) + " Original Pixels: x: " + x + " y: " + y);
                }
                else if (buttonClicked == 3)
                {

                    g.drawImage(FruitImg, x, y, IconSize, IconSize, this);
                    buttonClicked = -1;
                    Point3D PixelToCoord = thisGame.getMyMap().PixelToCoordinate(new Pixel(x, y), this.getWidth(), this.getHeight());
                    thisGame.AddFruit(new Fruit(PixelToCoord, fruitID, 1));
                    fruitID++;
                }
            }
        /*}*/
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

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        if(GameHasStarted)
        {
            double speed;
            boolean finishRun = true;
            int x = 0,y = 0;
            Point3D CurrnetPacmanPos , CurrnetFruitPos;
            GameCopy = new Game(thisGame);
            do
            {
                finishRun = true;
                for (int i = 0; i < thisGame.GetPacmanListSize(); i++) {
                    if (!ShortPath.get(i).isEmpty()) {
                        finishRun = false;
                        CurrnetPacmanPos = thisGame.getPacman_listCopy().get(i).getPosition();
                        CurrnetFruitPos = ShortPath.get(i).getFirst().getPosition();
                        speed = thisGame.getPacman_listCopy().get(i).getSpeed();


                        Pixel CurrnetPacmanPosPixel = thisGame.getMyMap().CoordinateToPixel(CurrnetPacmanPos, this.getWidth(), this.getHeight());
                        Pixel CurrnetFruitPosPixel = thisGame.getMyMap().CoordinateToPixel(CurrnetFruitPos, this.getWidth(), this.getHeight());

                        //System.out.println("Fruit Pixel:  x:" + CurrnetFruitPosPixel.getX() + " y:" + CurrnetFruitPosPixel.getY());


                        if (CurrnetPacmanPosPixel.getX() == CurrnetFruitPosPixel.getX())
                            x = 0;
                        else if (CurrnetPacmanPosPixel.getX() < CurrnetFruitPosPixel.getX())
                            x = 1;
                        else x = -1;
                        if (CurrnetPacmanPosPixel.getY() == CurrnetFruitPosPixel.getY())
                            y = 0;
                        else if (CurrnetPacmanPosPixel.getY() < CurrnetFruitPosPixel.getY())
                            y = 1;
                        else y = -1;
                        int new_x = (int)(CurrnetPacmanPosPixel.getX() + (x));
                        int new_y = (int)(CurrnetPacmanPosPixel.getY() + (y));
                        CurrnetPacmanPosPixel.setX(new_x);
                        CurrnetPacmanPosPixel.setY(new_y);

                        Point3D NewPosition = thisGame.getMyMap().PixelToCoordinate(CurrnetPacmanPosPixel, this.getWidth(), this.getHeight());

                        thisGame.getPacman_listOriginal().get(i).setPosition(NewPosition);

                        if (CurrnetPacmanPosPixel.equals(CurrnetFruitPosPixel)) {
                            thisGame.RemoveFruitByID(ShortPath.get(i).getFirst().getId());
                            ShortPath.get(i).removeFirst();
                        }

                        //System.out.println("Change To New Position");
                    }
                }
                repaint();
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }while (!finishRun);
        }
    }
}