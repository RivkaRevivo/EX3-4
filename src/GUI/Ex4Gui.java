
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

public class Ex4Gui extends JFrame implements MouseListener, Runnable
{
    Play play;
    BufferedImage MapImg,PacmanImg, FruitImg,OurPacmanImg , GhostImg;
    Game game;//boaz game
    Map map;
    int FruitIconSize = 20 , PacmanIconSize = 25;
    double PacmanAngle = 90;
    boolean AutmateGame = false, ManulGame = false;

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

    private void initGUI()
    {
        MyPanel panel = new MyPanel();
        add(panel);

        game = new Game("data/Ex4_OOP_example6.csv");
        play = new Play(game);
        map = MapFactory.BoazArielMap();
        play.setInitLocation( 32.1040,35.2061);
        //play.setInitLocation(40,40);

        MenuBar menuBar = new MenuBar();
        Menu Start_menu = new Menu("Game Menu");
        MenuItem SI1 = new MenuItem("Start manual game");
        MenuItem SI2 = new MenuItem("Start Automate game");
        menuBar.add(Start_menu);
        Start_menu.add(SI1);
        Start_menu.add(SI2);
        this.setMenuBar(menuBar);



        Ex4Gui temp = this;
        SI1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(temp);
                AutmateGame = false;
                ManulGame = true;
                t.start();
            }
        });

        SI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(temp);
                AutmateGame = true;
                ManulGame = false;
                t.start();
            }
        });
    }
class MyPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        g.drawImage(MapImg, 0, 0, this.getWidth(), this.getHeight(), this);
        Point3D pos, pos1;
        //draw Player
        pos = map.image2frame(map.world2frame(game.getPlayer().getLocation()), this.getWidth(), this.getHeight());
        g.drawImage(OurPacmanImg, pos.ix() + Math.round(PacmanIconSize / 2), pos.iy() + Math.round(PacmanIconSize / 2), PacmanIconSize, PacmanIconSize, this);
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
            g.drawImage(FruitImg, pos.ix() + Math.round(FruitIconSize / 2), pos.iy() + Math.round(FruitIconSize / 2), FruitIconSize, FruitIconSize, this);
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
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(!ManulGame && !AutmateGame)
        {
            LatLonAlt MouseInCords = map.frame2world(map.image2frame(new Point3D(e.getX(), e.getY()), this.getWidth(), this.getHeight()));
            play.setInitLocation(MouseInCords.x() , MouseInCords.y());
            repaint();
        }
        else
            {
            LatLonAlt MouseInCords = map.frame2world(map.image2frame(new Point3D(e.getX(), e.getY()), this.getWidth(), this.getHeight()));
            PacmanAngle = game.getPlayer().getLocation().azimuth_elevation_dist(MouseInCords)[0];
        }
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
        play.setIDs(204375455,312531031);
        play.start();
        if(ManulGame) {
            while (play.isRuning()) {
                play.rotate(PacmanAngle);
                System.out.println(play.getStatistics());
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
                LinkedList<LatLonAlt> path = Ex4Algo.Path(game.getPlayer().getLocation() , game.getTargets(), Ex4Algo.CalcGeoBox(game));
                for(int  i = 0; i < path.size(); i++)
                {
                    PacmanAngle = game.getPlayer().getLocation().azimuth_elevation_dist(path.get(i))[0];
                    while (game.getPlayer().getLocation().GPS_distance(path.get(i)) > 1) {
                        play.rotate(PacmanAngle);
                        System.out.println(play.getStatistics());
                        repaint();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}