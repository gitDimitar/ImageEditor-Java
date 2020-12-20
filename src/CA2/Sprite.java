/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package CA2;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import javax.swing.JApplet;

/**
*
* @author Miteto
*/
public class Sprite extends JApplet
{
    public final static int NEW = 1;
    public final static int NORMAL = 2;
    public final static int DELETED = 3;
    private final Image img;
    private int state = Sprite.NORMAL; // can be NORMAL, NEW or DELETED used to trigger animation
    private int x = 0;  // initialise at position 0, 0
    private int y = 0;
    private int offsetX = 0;
    private int offsetY = 0;
    private int scale = 100;
    private double rotation = 0;
    private boolean visible = true;
    private String effect = "";
    private float[] convolveMatrix ;
    
    

    public Sprite(Image img)
    {
        super();

        this.img = img;
    }

    public Image getImage()
    {
        Image a = this.img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        MediaTracker tracker = new MediaTracker(this.rootPane);
        tracker.addImage(a, 1);
        try
        {
            tracker.waitForAll();
        }
        catch (InterruptedException interruptedException)
        {
            interruptedException.printStackTrace();
        }
        return a;
    }
    
//    public void setImage(BufferedImage a)
//    {
//        this.img = a;
//    }

    public void setEffect(String e)
    {
        this.effect = e;
    }
    
    public String getEffect()
    {
        return this.effect;
    }
    
    public int getX()
    {
        return (int) this.x;
    }

    public int getY()
    {
        return (int) this.y;
    }

    public int getWidth()
    {
        double sc = this.scale;
        sc /= 100;
        
        return (int)(this.img.getWidth(rootPane) * sc);
    }

    public int getHeight()
    {
        double sc = this.scale;
        sc /= 100;
        
        return (int)(this.img.getHeight(rootPane) * sc);
    }

    public int getState()
    {
        return this.state;
    }

    public void setState(int state)
    {
        this.state = state;
    }
    
    public int getScale()
    {
        return this.scale;
    }
    
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    
    public double getRotation()
    {
        return this.rotation;
    }
    
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
    
    public boolean getVisible()
    {
        return visible;
    }
    
    public void setVisible()
    {
        if(visible)
        {
            this.visible = false;
        }
        else
        {
            this.visible = true;
        }
    }

    public boolean isNormal()
    {
        return (this.state == NORMAL);
    }

    public boolean isNew()
    {
        return (this.state == NEW);
    }

    public boolean isDeleted()
    {
        return (this.state == DELETED);
    }

    public void move(int mouseX, int mouseY)  // move top left corner to position x,y
    {
        this.x = mouseX - this.offsetX;
        this.y = mouseY - this.offsetY;
    }

    public void setOffset(int mouseX, int mouseY)
    {
        this.offsetX = mouseX - this.x;
        this.offsetY = mouseY - this.y;
    }

    public boolean contains(int x, int y)
    {
        return ((x >= (int) this.x) &&
                (x <= (int) this.x + this.img.getWidth(rootPane)) &&
                (y >= (int) this.y) &&
                (y <= (int) this.y + this.img.getHeight(rootPane)));
    }
    
    public void setConvolveMatrix(float[] matrix)
    {
        this.convolveMatrix = matrix;
    }
    
    public float[] getConvolveMatrix()
    {
        return convolveMatrix;
    }
    
    
        
}
