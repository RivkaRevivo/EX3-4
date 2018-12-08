package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel
{
    public BufferedImage Image;
    private int x,y,buttonClicked;
    public ImagePanel(int x,int y,int buttonClicked)
    {
        this.x = x;
        this.y = y;
        this.buttonClicked = buttonClicked;
        setLayout(null);
        try
        {
            if (this.buttonClicked == 1)
            {
                Image = ImageIO.read(new File("pacman.png"));
            }
            else if (this.buttonClicked == 3)
            {
                Image = ImageIO.read(new File("fruit.png"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(Image,x,y,this);
    }
}
