
package GUI;

import Algorithms.Ex4Algo;
import Coords.LatLonAlt;
import Coords.Map;
import Factory.MapFactory;
import Geom.Point3D;
import Robot.Game;
import Robot.Play;

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
import java.util.LinkedList;

/**
 * @author Shai kaikov and sela goldenberg
 * The Gui function used in Ex4
 * For The Assignment the best Score we were able to get on Manuel game for Example 8 was 30.1
 *
 */

public class Ex4Gui extends JFrame implements MouseListener, Runnable
{
    Play play;
    BufferedImage MapImg,PacmanImg, FruitImg,OurPacmanImg , GhostImg;
    Game game;
    Map map;
    int FruitIconSize = 20 , PacmanIconSize = 25;
    double PacmanAngle = 90;
    boolean AutmateGame = false, ManulGame = false;

    /**
     * The constructor of the class the initialize all of the images and call the initGUI function for more initialization
     */
    public Ex4Gui()
    {
        initGUI();
        this.addMouseListener(this);

        try
        {
            MapImg = ImageIO.read(new File("Ariel1.png"));
            PacmanImg = ImageIO.read(new File("pacman.png"));
            FruitImg = ImageIO.read(new File("fruit.png"));
            OurPacmanImg = ImageIO.read(new File("Pacman_magenta.png"));
            GhostImg = ImageIO.read(new File("RedGhost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This Function initialize the game ,map ,play and also create The menu for the gui and add action listener to all the button in the menu
     */
    private void initGUI()
    {
        MyPanel panel = new MyPanel();
        add(panel);

        game = new Game("data/Ex4_OOP_example1.csv");
        play = new Play(game);
        map = MapFactory.BoazArielMap();
        play.setInitLocation( 32.1040,35.2061);
        //play.setInitLocation(40,40);

        MenuBar menuBar = new MenuBar();
        Menu Start_menu = new Menu("Game Menu");
        Menu Load_menu = new Menu("Load Game");
        MenuItem SI1 = new MenuItem("Start manual game");
        MenuItem SI2 = new MenuItem("Start Automate game");
        MenuItem SI3 = new MenuItem("Load csv");
        menuBar.add(Start_menu);
        menuBar.add(Load_menu);
        Start_menu.add(SI1);
        Start_menu.add(SI2);
        Load_menu.add(SI3);
        this.setMenuBar(menuBar);



        Ex4Gui temp = this; // a temp of this gui so we can used it inside the menu Action listener

        //Start Manual game
        SI1.addActionListener(new ActionListener() {
            /**
             * Create a new Thread and start the Manual game
             * @param e the event needed to be listened
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(temp);
                AutmateGame = false;
                ManulGame = true;
                t.start();
            }
        });

        //start Automate game
        SI2.addActionListener(new ActionListener() {
            /**
             * Create a new Thread and start the Automate game
             * @param e the event needed to be listened
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(temp);
                AutmateGame = true;
                ManulGame = false;
                t.start();
            }
        });

        //Load a csv game
        SI3.addActionListener(new ActionListener() {
            /**
             * Load a new csv game
             * @param e the event needed to be listened
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("data");
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    game = new Game(file.getPath());
                    play = new Play(game);
                    play.setInitLocation( 32.1040,35.2061);
                    ManulGame = false;
                    AutmateGame = false;
                    repaint();

                }
            }
        });
    }

    /**
     * A panel we add to the our frame in order to make the images in the gui to be more clear
     */
    class MyPanel extends JPanel {
        /**
         * Paint the entire game in its position. get called every time one of the game Character move
         * @param g the graphic needed to paint
         */
    @Override
    public void paint(Graphics g) {
        g.drawImage(MapImg, 0, 0, this.getWidth(), this.getHeight(), this);
        Point3D pos, pos1;
        //draw Player
        pos = map.image2frame(map.world2frame(game.getPlayer().getLocation()), this.getWidth(), this.getHeight());
        g.drawImage(OurPacmanImg, pos.ix(), pos.iy(), PacmanIconSize, PacmanIconSize, this);
        //draw Pacmans
        for (int i = 0; i < game.sizeR(); i++) {
            //pos = map.CoordinateToPixel(game.getPackman(i).getLocation() ,this.getWidth() , this.getHeight());
            pos = map.image2frame(map.world2frame(game.getPackman(i).getLocation()), this.getWidth(), this.getHeight());
            g.drawImage(PacmanImg, pos.ix(), pos.iy(), PacmanIconSize, PacmanIconSize, this);
        }
        //draw Ghosts
        for (int i = 0; i < game.sizeG(); i++) {
            //pos = map.CoordinateToPixel(game.getGhosts(i).getLocation() , this.getWidth() , this.getHeight());
            pos = map.image2frame(map.world2frame(game.getGhosts(i).getLocation()), this.getWidth(), this.getHeight());
            g.drawImage(GhostImg, pos.ix(), pos.iy(), FruitIconSize, FruitIconSize, this);
        }
        //draw Fruits
        for (int i = 0; i < game.sizeT(); i++) {
            //pos = map.CoordinateToPixel(game.getTarget(i).getLocation() , this.getWidth() , this.getHeight());
            pos = map.image2frame(map.world2frame(game.getTarget(i).getLocation()), this.getWidth(), this.getHeight());
            g.drawImage(FruitImg, pos.ix(), pos.iy(), FruitIconSize, FruitIconSize, this);
        }
        //draw Boxes
        for (int i = 0; i < game.sizeB(); i++) {
            //pos = map.CoordinateToPixel(game.getBox(i).getMin() , this.getWidth() , this.getHeight());
            pos = map.image2frame(map.world2frame(game.getBox(i).getMin()), this.getWidth(), this.getHeight());
            //pos1 = map.CoordinateToPixel(game.getBox(i).getMax() , this.getWidth() , this.getHeight());
            pos1 = map.image2frame(map.world2frame(game.getBox(i).getMax()), this.getWidth(), this.getHeight());
            int width = pos1.ix() - pos.ix();
            int height = pos.iy() - pos1.iy();
            g.fillRect(pos.ix(), pos1.iy(), width, height);
        }
    }
}

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * used in order to set the initial position of the player and in the manual game in order to move the player
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(!ManulGame && !AutmateGame)
        {
            LatLonAlt MouseInCords = map.frame2world(map.image2frame(new Point3D(e.getX(), e.getY()), this.getWidth(), this.getHeight()));
            play.setInitLocation(MouseInCords.lat() + 0.0002 , MouseInCords.lon() + 0.0002);
            repaint();

        }
        else
            {
                if(ManulGame) {
                    LatLonAlt MouseInCords = map.frame2world(map.image2frame(new Point3D(e.getX(), e.getY()), this.getWidth(), this.getHeight()));
                    PacmanAngle = game.getPlayer().getLocation().azimuth_elevation_dist(MouseInCords)[0];
                }
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * only here because we need the MouseListener interface for mouseClicked
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * only here because we need the MouseListener interface for mouseClicked
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * only here because we need the MouseListener interface for mouseClicked
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * only here because we need the MouseListener interface for mouseClicked
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Return The Image of our map
     * @return The Map Image
     */
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
     * this run used in two scenarios the Automate game and in the Manual game
     * in both cases the user can choose where to put the player using the mouse and we run until the game is over (Time run out or all the fruits has been eaten),
     * in the Manual game the user can control the player using the mouse to tell the player where to go
     * in the Automate game the player move on is on calculating every time where is best to go
     *
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        play.setIDs(204375455,312531031,308019819);
        play.start();
        if(ManulGame) {
            while (play.isRuning()) {
                play.rotate(PacmanAngle);//change the angle
                System.out.println(play.getStatistics()); //print the game statistics
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(AutmateGame)
        {
            while (play.isRuning())
            {
                //Calculate the path to the closest fruit accounting to the boxes
                LinkedList<LatLonAlt> path = Ex4Algo.Path(game.getPlayer().getLocation() , game.getTargets(), Ex4Algo.CalcGeoBox(game));
                for(int  i = 0; i < path.size(); i++) // the pacman move through the path
                {
                    PacmanAngle = game.getPlayer().getLocation().azimuth_elevation_dist(path.get(i))[0];
                    //the pacman move to the next point in this path until he reached there or until the fruit in the end of the path got eaten or until the game stop running
                    while (game.getPlayer().getLocation().GPS_distance(path.get(i)) > 1 && Ex4Algo.FruitExsit(path.getLast() , game.getTargets()) && play.isRuning()) {
                        play.rotate(PacmanAngle);
                        System.out.println(play.getStatistics());
                        repaint();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!Ex4Algo.FruitExsit(path.getLast() , game.getTargets())) // check if the fruit in the end of the line has not been eaten yet
                    {
                        play.rotate(PacmanAngle);
                        System.out.println(play.getStatistics());
                        break;
                    }
                }
            }
        }

        database.sql.PrintFromDatabase(); // After the game finish we print how did we do compare to other game we played and compare to other game played by the class
    }
}