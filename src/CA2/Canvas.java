/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CA2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener , MouseMotionListener
{
        private BufferedImage image = null;
        private BufferedImage original = null ;
        private BufferedImage drawLayer = null;
        
        private ArrayList<Sprite> imageList = null;
        private Sprite copy ;
        private Color colourToDraw;
        private String mode = "MANIPULATION";
        
        private int oldX = 0;
        private int oldY = 0;
        private int x = 0;
        private int y = 0;
        private int strokeWidth = 1;
        
        private int selectedImage = -1;
        private int workLayer = -1;
        private View view;
        
        private int mouseStartX;
        private int mouseStartY;
        private double rotationDegrees;
        Graphics2D d2d;
        

        public Canvas(View view)
        {
            super();
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.view = view;           
        }
        
        public void setConvState(float[] matrix)
        {
            imageList.get(workLayer).setConvolveMatrix(matrix);
        }
        
        public void setEffect(String effect)
        {
            imageList.get(workLayer).setEffect(effect);
        }
        
        public void setCopy()
        {
            this.copy = (Sprite) this.imageList.get(workLayer);
        }
        
        public Sprite getCopy()
        {
            return this.copy;
            
        }
        
        public void paste()
        {
            this.imageList.add(copy);
            repaint();
        }
        
        public void setImage(File imageFile)
        {
            if (imageFile != null)
            {
                try
                {
                    image = ImageIO.read(imageFile);
                    original = ImageIO.read(imageFile);
                }
                catch (Exception e)
                {
                }
            }
            else // imageFile == null
            {
                // reset the image to empty
                image = null;
            }
        }
        
        public void clear()
        {
            this.image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
            this.drawLayer = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
            d2d = drawLayer.createGraphics();
            if(imageList != null)
            {
                if(!imageList.isEmpty())
                {
                    imageList.clear();
                }
            }
            repaint();
        }

        public int getSelectedLayer()
        {
            return selectedImage;
        }
        
        public void setSelectedLayer(int num)
        {
            this.selectedImage = num;
        }
        
        public int getWorkLayer()
        {
            return this.workLayer;
        }
        
        public void setWorkLayer(int num)
        {
            this.workLayer = num;
        }

        public BufferedImage getImage()
        {
            return image;
        }
        
        public BufferedImage getOriginalImage()
        {
            return original;
        }
        
        public void setDrawColour(Color colourToDraw)
        {
            this.colourToDraw = colourToDraw;
        }
        
        public void setMode(String m)
        {
            this.mode = m;
        }
        
        public String getMode()
        {
            return this.mode;
        }
        
        public void setDrawLayer(int x, int y)
        {
            drawLayer = new BufferedImage(x, y,BufferedImage.TYPE_INT_ARGB);
            image = new BufferedImage(x, y,BufferedImage.TYPE_INT_ARGB);
            d2d = drawLayer.createGraphics();
        }
        
        public void incWidth()
        {
            if(strokeWidth < 15)
            {
                strokeWidth++;
            }
        }
        
        public void decWidth()
        {
            if(strokeWidth > 0)
            {
                strokeWidth--;
            }
        }
        
        
        

        @Override
        public void mousePressed(MouseEvent e)
        {
            if(view.getDrawButton())
            {
                this.oldX = this.x = e.getX();
                this.oldY = this.y = e.getY();
            }
            else if(!view.getZoomIn() && !view.getZoomOut() && !view.getDrawButton())
            {
                if(!view.getRot())
                {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                if(imageList != null)
                {
                    for (int i = this.imageList.size() - 1; i >= 0; i--)
                    {
                        if ((this.imageList.get(i)).contains(e.getX(), e.getY()))
                        {
                            
                            this.selectedImage = i;
                            workLayer = imageList.size() -1;
                            if (e.getButton() == MouseEvent.BUTTON3)
                            {
                                this.imageList.add(((Sprite) this.imageList.get(i)));  // add a copy of the current Sprite to the front of the list
                                this.imageList.remove(i);                         // delete the Sprite from its original position
                                workLayer = imageList.size() -1;
                            }
                            this.imageList.get(i).setOffset(e.getX(), e.getY());
                            break;
                        }

                    }
                }
            }
            
            this.repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(view.getZoomIn())
            {
                Sprite currentLayer = imageList.get(workLayer);
                if (currentLayer != null && currentLayer.getScale() <= 250 )
                {
                    currentLayer.setScale(currentLayer.getScale() + 25);
                    this.ZoomIn();
                }
            }
            else if(view.getZoomOut())
            {
                Sprite currentLayer = imageList.get(workLayer);
                if (currentLayer != null && currentLayer.getScale() >= 50)
                {
                    currentLayer.setScale(currentLayer.getScale() - 25);
                    this.ZoomOut();
                }
            }
            this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(!view.getZoomIn() && !view.getZoomOut() && !view.getRot())
            {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if(this.contains(e.getX(),e.getY()) && view.getRot())
            {
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if(view.getRot())
            {
                
                int rotationStepSize = 4;
                // Set rotation amount based on the direction that the mouse is moving and 
                // where the mouse is being dragged in relation to the centre of the image
                if (Math.abs(e.getX() - this.mouseStartX) > Math.abs(e.getY() - this.mouseStartY))
                {
                    // mouse is being moved primarily along the x-axis
                    final int centerY = this.imageList.get(workLayer).getY() + (this.imageList.get(workLayer).getHeight() / 2);

                    if ((e.getX() < this.mouseStartX))  // mouse is moving to the left
                    {
                        if (this.mouseStartY < centerY)     // mouse is above the image
                        {
                            
                            this.rotationDegrees -= rotationStepSize;
                        }
                        else   // mouse is below the image
                        {
                            this.rotationDegrees += rotationStepSize;
                        }
                    }
                    else  // mouse is moving to the right
                    {
                        if (this.mouseStartY < centerY)   // mouse is to the left of the image
                        {
                            this.rotationDegrees += rotationStepSize;
                        }
                        else  // mouse is to the right of the image
                        {
                            this.rotationDegrees -= rotationStepSize;
                        }
                    }
                }
                else
                {
                    // mouse is being moved primarily along the y-axis
                    final int centerX = this.imageList.get(workLayer).getX() + (this.imageList.get(workLayer).getWidth() / 2);
                    if ((e.getY() < this.mouseStartY))  // mouse is moving to the left
                    {
                        if (this.mouseStartX < centerX)     // mouse is above the image
                        {
                            this.rotationDegrees += rotationStepSize;
                        }
                        else   // mouse is below the image
                        {
                            this.rotationDegrees -= rotationStepSize;
                        }
                    }
                    else  // mouse is moving to the right
                    {
                        if (this.mouseStartX < centerX)   // mouse is to the left of the image
                        {
                            this.rotationDegrees -= rotationStepSize;
                        }
                        else  // mouse is to the right of the image
                        {
                            this.rotationDegrees += rotationStepSize;
                        }
                    }
                }
                this.imageList.get(workLayer).setRotation(rotationDegrees);
                this.mouseStartX = e.getX();
                this.mouseStartY = e.getY();
                
            }
            else if(!view.getZoomIn() && !view.getZoomOut() && !view.getDrawButton())
            {
                if(imageList != null && !imageList.isEmpty())
                {
                    if ((this.imageList.get(this.selectedImage)).contains(e.getX(), e.getY()))
                    {
                        (this.imageList.get(this.selectedImage)).move(e.getX(), e.getY());
                        
                    }
                }
            }
            else if(view.getDrawButton() && this.mode.equals("DRAW"))
            {
               
                this.x = e.getX();
                this.y = e.getY();
                drawing();

            }
            this.repaint();
            
        }

        public void paintList(ArrayList imageList)
        {
            this.imageList = imageList;
            this.repaint();
        }
        
        public void ZoomIn()
        {
            this.setPreferredSize(new Dimension((int)(this.getWidth()* 1.25), (int)(this.getHeight() *1.25)));
        }
        
        public void ZoomOut()
        {
            this.setPreferredSize(new Dimension((int)(this.getWidth()* 0.75), (int)(this.getHeight() * 0.75)));
        }
        
        public void drawing()
        {
            
            d2d.setColor(this.colourToDraw);
            d2d.setStroke(new BasicStroke(strokeWidth,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));     
            d2d.drawLine(this.oldX, this.oldY, this.x, this.y);
            
            repaint();
        }
        
        public void mirrorImage() throws InterruptedException
        {
            //super.paintComponent(g);

            // place the original file image into buffered image
            BufferedImage originalBufferedImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D originalBufferedG = originalBufferedImg.createGraphics();
            
            
            Sprite v = imageList.get(0);
            int vHeight = v.getHeight();
            int vWeight = v.getWidth();
            
            
            originalBufferedG.drawImage(v.getImage(), 0, 0, getWidth(), getHeight(), this);

            final AffineTransform affineTransform = new AffineTransform();
            affineTransform.translate(0, getHeight());  // need to translate because we are scaling y by a factor of -1
            
            affineTransform.scale(1.0, -1.0);

            final AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);
            final BufferedImage invertedBufferedImg = affineTransformOp.filter(originalBufferedImg, null);
            
            
            

            // draw the original image and the inverted image
            //g.drawImage(this.canvas.getImage(), 0, 0, getWidth(), getHeight() / 2, this);
            //g.drawImage(invertedBufferedImg, 0, getHeight() / 2, getWidth(), getHeight() / 2, this);
            
            //imageList.add(new Sprite(invertedBufferedImg));
            
            
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(invertedBufferedImg, 1);
            tracker.waitForAll();
            
            invertedBufferedImg.getScaledInstance(vWeight, vHeight, 0);
            
            this.imageList.add(new Sprite(invertedBufferedImg.getScaledInstance(vWeight, vHeight, 0))); // the identity of the added image. This information is used by deleteImages
            this.setSelectedLayer(imageList.size() -1);
            this.setWorkLayer(imageList.size() -1);
            
            
        }
        
        public BufferedImage convImage(int i)
        {
            final BufferedImage srcBufferedImg = new BufferedImage(imageList.get(i).getWidth(),imageList.get(i).getHeight(), BufferedImage.TYPE_INT_RGB);
            final Graphics2D SrcG = srcBufferedImg.createGraphics();
            SrcG.drawImage(imageList.get(i).getImage(),0,0,this);

            // Do the convolution
            Kernel kernel = new Kernel(3, 3,this.imageList.get(i).getConvolveMatrix());
            final ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            final BufferedImage destBufferedImg = convolve.filter(srcBufferedImg, null);
            
            return destBufferedImg;
        }
        
        private BufferedImage colorEffect(String effect)
        {
            BufferedImage b = new BufferedImage(imageList.get(workLayer).getImage().getWidth(null),imageList.get(workLayer).getImage().getWidth(null),BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = b.createGraphics();
            g2d.drawImage(imageList.get(workLayer).getImage(), 0,0, this);
            g2d.dispose();
                for (int x = 0; x <  b.getWidth(); x++)
                {
                    for (int y = 0; y < b.getHeight(); y++)
                    {
                        int pixelColour;
                        int alpha;
                        int red;
                        int green;
                        int blue;
                        // get the pixel's colour as an integer

                        pixelColour = b.getRGB(x, y);

                        // extract the alpha, red, green and blue colour data from the pixel's RGB colour
                        alpha = (pixelColour >> 24) & 0xff;
                        red = (pixelColour >> 16) & 0xff;
                        green = (pixelColour >> 8) & 0xff;
                        blue = (pixelColour) & 0xff;

                        // manipulate the pixel by writting back only the blue component of the RGB colour
                        if(effect.equals("greyscale"))
                        {
                           int greyscale = (red + green + blue) / 3;
                           b.setRGB(x, y, new Color(greyscale, greyscale,greyscale, alpha).getRGB()); 
                        }
                        else if (effect.equals("sepia"))
                        {
                            int sred = (int) ((red * 0.393) + (green * 0.769) + (blue * 0.189));                            
                            int sgreen = (int) ((red * 0.349) + (green * 0.686) + (blue * 0.168));
                            int sblue = (int) ((red * 0.272) + (green * 0.534) + (blue * 0.131));
                            
                            if (sred > 255) 
                            {
                                sred = 255;
                            }
                            if (sgreen > 255) 
                            {
                                sgreen = 255;
                            }
                            if (sblue > 255) 
                            {
                                sblue = 255;
                            }
                            
                            
                            b.setRGB(x, y, new Color(sred , sgreen, sblue, alpha).getRGB());
                        }
                        else if(effect.equals("invert"))
                        {
                            b.setRGB(x, y, new Color(255 - red , 255 - green , 255 - blue, alpha).getRGB());
                        }
                        else if(effect.equals("posterise"))
                        {
                            int pred = (int) (red - red%64);
                            int pgreen = (int) (green - green % 64);
                            int pblue = (int) (blue - blue % 64);
                            b.setRGB(x, y, new Color(pred, pgreen, pblue, alpha).getRGB());
                        }
                        else if(effect.equals(""))
                        {
                            b.setRGB(x, y, new Color(red, green, blue, alpha).getRGB());
                        }
                    }
                }

                return b;
                
        }
        
        public BufferedImage rotate(int i, Graphics g)
        {
            final BufferedImage srcBufferedImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D SrcG = srcBufferedImg.createGraphics();
            SrcG.drawImage(this.imageList.get(i).getImage(), 0, 0, this.imageList.get(i).getWidth(), this.imageList.get(i).getHeight(), this);
            final int centreX = this.imageList.get(i).getWidth() / 2;
            final int centreY = this.imageList.get(i).getHeight() / 2;
            final AffineTransform affineTransform = new AffineTransform();
            // transforms are applied in reverse order
            affineTransform.translate(this.imageList.get(i).getX(), this.imageList.get(i).getY());               // location on the display to paint the image

            // To rotate about the centre of an image, we must:
            // 1) translate the centre of the image to the origin
            // 2) rotate
            // 3) translate the centre of the image back to its original position
            // Remember that the transforms are applied in reverse order
            affineTransform.translate(centreX, centreY);
            affineTransform.rotate(Math.toRadians(this.imageList.get(i).getRotation()));
            affineTransform.translate(-centreX, -centreY);

            // make a new AffineTransformOp filter
            final AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);

            // apply the AffineTransformOp filter
            final BufferedImage destBufferedImg = affineTransformOp.filter(srcBufferedImg, null);
            
            return destBufferedImg;
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            BufferedImage b = null;
            Graphics2D g2d = (Graphics2D) g;
            g2d = image.createGraphics();
            
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            
            
            if (this.imageList != null)
            {   
                // draw the sprites
                for (int i = 0; i < this.imageList.size(); i++)
                {
                    int xPos = imageList.get(i).getX() + (imageList.get(i).getWidth() / 2);
                    int yPos = imageList.get(i).getY() + (imageList.get(i).getHeight() / 2);
                    
                    System.out.println(imageList.size());
                    
                    if(this.imageList.get(i).getConvolveMatrix() != null && this.imageList.get(i).getEffect().equals(""))
                    {
                        b = convImage(i);
                        
                        g2d.rotate(Math.toRadians(imageList.get(i).getRotation()), xPos, yPos);//layer.getRotation()));
                        g2d.drawImage(b, imageList.get(i).getX(), imageList.get(i).getY(), this);
                        g2d.rotate(Math.toRadians(imageList.get(i).getRotation() * -1), xPos, yPos);
                    }
                    else if(!this.imageList.get(i).getEffect().equals(""))
                    {
                        b = colorEffect(imageList.get(i).getEffect());
                        
                        g2d.rotate(Math.toRadians(imageList.get(i).getRotation()), xPos, yPos);//layer.getRotation()));
                        g2d.drawImage(b, imageList.get(i).getX(), imageList.get(i).getY(), this);
                        g2d.rotate(Math.toRadians(imageList.get(i).getRotation() * -1), xPos, yPos);
                    }
                    else
                    {
                        g2d.rotate(Math.toRadians(imageList.get(i).getRotation()), xPos, yPos);//layer.getRotation()));
                        g2d.drawImage(imageList.get(i).getImage(), imageList.get(i).getX(), imageList.get(i).getY(), this);
                        g2d.rotate(Math.toRadians(imageList.get(i).getRotation() * -1), xPos, yPos);
                    }
                    
                }
                
                
            }
            g2d.drawImage(drawLayer, 0, 0, this);
            g.drawImage(image, 0, 0, this);
                  
     
            this.oldX = this.x;
            this.oldY = this.y;
        }
     
    }