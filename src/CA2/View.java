/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CA2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class View extends JPanel implements ActionListener, MouseListener, MouseWheelListener, KeyListener
    {     
        private ArrayList<Sprite> imageList = new ArrayList<>();
        private final Convolve c = new Convolve();

        private final JMenuBar menuBar = new JMenuBar();

        private JMenu menuFile = new JMenu();
        private JMenu menuOptions = new JMenu();
        private JMenu imgEffects = new JMenu();
        private JMenu translate = new JMenu();
        private JMenu frames = new JMenu();
        private JMenu colorEffects = new JMenu();

        private JMenuItem menuItemOpen;
        private JMenuItem menuItemSave;
        private JMenuItem menuItemRemoveLayer;
        private JMenuItem menuCopy;
        private JMenuItem menuPaste;

        private JMenuItem NormalEffect;
        private JMenuItem SharpenSffect;
        private JMenuItem BlurEffect;
        private JMenuItem EdgeDetectionEffect;
        private JMenuItem LightenEffect;
        private JMenuItem DarkenEffect;
        private JMenuItem embossEffect;

        private JMenuItem Normal;
        private JMenuItem greyscale;
        private JMenuItem sepia;
        private JMenuItem invert;
        private JMenuItem posterise;

        private JMenuItem mirrorImage;

        private JMenuItem frame1;
        private JMenuItem frame2;
        private JMenuItem frame3;
        private JMenuItem frame4;
        private JMenuItem frame5;
        private JMenuItem frame6;
        private JMenuItem frame7;
        private JMenuItem frame8;
         
        JPanel controlPanel;
        JPanel sidePanel ;
        JPanel colorPanel ;
        JPanel redPanel;
        JPanel bluePanel;
        JPanel greenPanel;
        JPanel yellowPanel ;
        JPanel purplePanel;
        JPanel blackPanel;
        JPanel pinkPanel;
        JPanel orangePanel;
        JPanel lightbluePanel;
        JPanel whitePanel;
        
        
        private String selectedImage;

        private JToggleButton zoomIn;
        private JToggleButton zoomOut;
        private JToggleButton rotate;
        private JToggleButton drawButton;
        private JButton clearButton;
        private JButton incBrushSize;
        private JButton decBrushSize;
         
         
         
         private final JFileChooser fileChooser = new JFileChooser();
         private final Canvas canvas = new Canvas(this);       

        public View()
        {
            super();

            // initialise the fileChooser
            this.fileChooser.setAcceptAllFileFilterUsed(false);
            this.fileChooser.addChoosableFileFilter(new AllFileFilter());
            this.fileChooser.addChoosableFileFilter(new JpegFileFilter());
            this.fileChooser.addChoosableFileFilter(new GifFileFilter());
            this.fileChooser.addChoosableFileFilter(new PngFileFilter());
            
            
            final JScrollPane scrollPane = new JScrollPane(canvas);
            scrollPane.setLayout(new ScrollPaneLayout());
            scrollPane.add(canvas);//add the canvas panel to the scrollPane
            scrollPane.setViewportView(canvas);//set the viewport to the canvas also
            //scrollPane.setPreferredSize(new Dimension(250, 200));//set preferred size
            //canvas.setPreferredSize(new Dimension(500, 300));//set canvas size bigger ...this is what I was missing..
            int x = canvas.getWidth();
            int y = canvas.getHeight();
            
            Toolkit tk = Toolkit.getDefaultToolkit();  
            int xSize = ((int) tk.getScreenSize().getWidth()) - 100;  
            int ySize = ((int) tk.getScreenSize().getHeight()) - 120;
            
            scrollPane.setPreferredSize(new Dimension(x,y));//set preferred size
            canvas.setPreferredSize(new Dimension(xSize, ySize));//set canvas size bigger ...this is what I was missing..
            canvas.setDrawLayer(xSize, ySize);            
//            zIn = new ImageIcon("icons/Zoom+.jpg");
//            zOut = new ImageIcon("Zoom -");

            menuFile = new JMenu("File");
            menuBar.add(menuFile);
            
            menuOptions = new JMenu("Image Options");
            menuBar.add(menuOptions);
            
            menuItemOpen = new JMenuItem("Open Image");
            menuItemOpen.addActionListener(this);
            menuFile.add(menuItemOpen);
            
            menuItemSave = new JMenuItem("Save Image");          
            menuItemSave.addActionListener(this);
            menuFile.add(menuItemSave);
            
            menuCopy = new JMenuItem("Copy");          
            menuCopy.addActionListener(this);
            menuFile.add(menuCopy);
            
            menuPaste = new JMenuItem("Paste");          
            menuPaste.addActionListener(this);
            menuFile.add(menuPaste);  
            
            menuItemRemoveLayer = new JMenuItem("Remove Top Layer");          
            menuItemRemoveLayer.addActionListener(this);
            menuFile.add(menuItemRemoveLayer);
            
            
     
            //a submenu
            
            imgEffects = new JMenu("Effects");
            
            NormalEffect = new JMenuItem("Normal");
            NormalEffect.addActionListener(this);
            imgEffects.add(NormalEffect);
            
            SharpenSffect = new JMenuItem("Sharpen");
            SharpenSffect.addActionListener(this);
            imgEffects.add(SharpenSffect);
            
            BlurEffect = new JMenuItem("Blur");
            BlurEffect.addActionListener(this);
            imgEffects.add(BlurEffect);
            
            EdgeDetectionEffect = new JMenuItem("Edge Detection");
            EdgeDetectionEffect.addActionListener(this);
            imgEffects.add(EdgeDetectionEffect);
            
            LightenEffect = new JMenuItem("Lighten");
            LightenEffect.addActionListener(this);
            imgEffects.add(LightenEffect);
            
            DarkenEffect = new JMenuItem("Darken");
            DarkenEffect.addActionListener(this);
            imgEffects.add(DarkenEffect);
            
            embossEffect = new JMenuItem("Emboss");
            embossEffect.addActionListener(this);
            imgEffects.add(embossEffect);
            
            //
            
            colorEffects = new JMenu("Colour Effects");
            menuBar.add(colorEffects);
            
            Normal = new JMenuItem("Normal");
            Normal.addActionListener(this);
            colorEffects.add(Normal);
            
            greyscale = new JMenuItem("Black & White");
            greyscale.addActionListener(this);
            colorEffects.add(greyscale);
            
            sepia = new JMenuItem("Sepia");
            sepia.addActionListener(this);
            colorEffects.add(sepia);
            
            invert = new JMenuItem("Inverted");
            invert.addActionListener(this);
            colorEffects.add(invert);
            
            posterise = new JMenuItem("Posterise");
            posterise.addActionListener(this);
            colorEffects.add(posterise);
            
            
            frames = new JMenu("Add Frames");
       
            
            frame1 = new JMenuItem("Frame 1");
            frame1.addActionListener(this);
            frames.add(frame1);
            
            frame2 = new JMenuItem("Frame 2");
            frame2.addActionListener(this);
            frames.add(frame2);
            
            frame3 = new JMenuItem("Frame 3");
            frame3.addActionListener(this);
            frames.add(frame3);
            
            frame4 = new JMenuItem("Frame 4");
            frame4.addActionListener(this);
            frames.add(frame4);
            
            frame5 = new JMenuItem("Frame 5");
            frame5.addActionListener(this);
            frames.add(frame5);
            
            frame6 = new JMenuItem("Frame 6");
            frame6.addActionListener(this);
            frames.add(frame6);
            
            frame7 = new JMenuItem("Frame 7");
            frame7.addActionListener(this);
            frames.add(frame7);
            
            frame8 = new JMenuItem("Frame 8");
            frame8.addActionListener(this);
            frames.add(frame8);
            
            translate = new JMenu("Translate");
            menuBar.add(translate);

            mirrorImage = new JMenuItem("Mirror Image");
            mirrorImage.addActionListener(this);
            translate.add(mirrorImage);
            
            
            menuOptions.add(imgEffects);
            menuOptions.add(frames);
            
            // control panel
            controlPanel = new JPanel();
            sidePanel = new JPanel();
            colorPanel = new JPanel();
            redPanel = new JPanel();
            bluePanel = new JPanel();
            greenPanel = new JPanel();
            yellowPanel = new JPanel();
            purplePanel = new JPanel();
            blackPanel= new JPanel();
            pinkPanel= new JPanel();
            orangePanel= new JPanel();
            lightbluePanel= new JPanel();
            whitePanel= new JPanel();
            
            
            sidePanel.setLayout(new GridBagLayout());
            sidePanel.setPreferredSize(new Dimension(80,getHeight())); 
            sidePanel.setMinimumSize(new Dimension(80,getHeight())); 
            sidePanel.setMaximumSize(new Dimension(80,getHeight()));
            
            ImageIcon rIcon = new ImageIcon(getClass().getResource("rotate_2.png"));
            ImageIcon draw = new ImageIcon(getClass().getResource("draw.png"));
            ImageIcon zIn = new ImageIcon(getClass().getResource("zoom-in.png"));
            ImageIcon zOut = new ImageIcon(getClass().getResource("zoom-out.png"));
            ImageIcon plus = new ImageIcon(getClass().getResource("+.png"));
            ImageIcon minus = new ImageIcon(getClass().getResource("-.png"));
            
            zoomIn = new JToggleButton(zIn);
            zoomIn.setPreferredSize(new Dimension(40,40));
            zoomIn.setMinimumSize(new Dimension(40,40));
            zoomIn.setMaximumSize(new Dimension(40,40));
            zoomIn.addActionListener(this);

            zoomOut = new JToggleButton(zOut);
            zoomOut.setPreferredSize(new Dimension(40,40));
            zoomOut.setMinimumSize(new Dimension(40,40));
            zoomOut.setMaximumSize(new Dimension(40,40));
            zoomOut.addActionListener(this);

            
            rotate = new JToggleButton(rIcon);
            rotate.setPreferredSize(new Dimension(40,40));
            rotate.setMinimumSize(new Dimension(40,40));
            rotate.setMaximumSize(new Dimension(40,40));
            rotate.addActionListener(this);

            drawButton = new JToggleButton(draw);
            drawButton.setPreferredSize(new Dimension(40,40));
            drawButton.setMinimumSize(new Dimension(40,40));
            drawButton.setMaximumSize(new Dimension(40,40));
            drawButton.addActionListener(this);
            
            incBrushSize = new JButton(plus);
            incBrushSize.setPreferredSize(new Dimension(40,40));
            incBrushSize.setMinimumSize(new Dimension(40,40));
            incBrushSize.setMaximumSize(new Dimension(40,40));
            incBrushSize.addActionListener(this);
            
            decBrushSize = new JButton(minus);
            decBrushSize.setPreferredSize(new Dimension(40,40));
            decBrushSize.setMinimumSize(new Dimension(40,40));
            decBrushSize.setMaximumSize(new Dimension(40,40));
            decBrushSize.addActionListener(this);
            
            clearButton = new JButton("Clear");
            clearButton.addActionListener(this);
                   
            
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.NORTH;
            c.fill = GridBagConstraints.HORIZONTAL;
            
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            c.insets = new Insets(0,0,5,0);
            sidePanel.add(zoomIn, c);
            
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            c.insets = new Insets(0,0,5,0);
            sidePanel.add(zoomOut, c);
            
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 1;
            c.insets= new Insets(0,0,5,0);
            sidePanel.add(rotate, c);
            
            
            c.gridx = 1;
            c.gridy = 2;
            c.gridwidth = 1;
            c.insets = new Insets(0,0,5,0);
            sidePanel.add(drawButton, c);

            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 2;
            c.insets = new Insets(0,0,10,0);
            sidePanel.add(colorPanel, c);
            
            colorPanel.setLayout(new GridLayout(5,2));
            colorPanel.setPreferredSize(new Dimension(80,100));
            colorPanel.setMinimumSize(new Dimension(80,100));
            colorPanel.setMaximumSize(new Dimension(80,100));
            colorPanel.add(redPanel);
            colorPanel.add(bluePanel);
            colorPanel.add(greenPanel);
            colorPanel.add(yellowPanel);
            colorPanel.add(purplePanel);
            colorPanel.add(blackPanel);
            colorPanel.add(pinkPanel);
            colorPanel.add(orangePanel);
            colorPanel.add(lightbluePanel);
            colorPanel.add(whitePanel);
            
            
            
            c.gridx = 0;
            c.gridy = 4;
            c.gridwidth = 1;
            c.insets = new Insets(0,0,10,0);
            sidePanel.add(incBrushSize, c);
            
            c.gridx = 1;
            c.gridy = 4;
            c.gridwidth = 1;
            c.insets = new Insets(0,0,10,0);
            sidePanel.add(decBrushSize, c);
            
            c.gridx = 0;
            c.gridy = 5;
            c.gridwidth = 2;
            c.insets = new Insets(0,0,10,0);
            sidePanel.add(clearButton, c);
            
            redPanel.setBackground(Color.red);
            bluePanel.setBackground(Color.blue);
            greenPanel.setBackground(Color.green);
            yellowPanel.setBackground(Color.yellow);
            purplePanel.setBackground(Color.magenta);
            blackPanel.setBackground(Color.black);
            pinkPanel.setBackground(Color.pink);
            orangePanel.setBackground(Color.orange);
            lightbluePanel.setBackground(Color.cyan);
            whitePanel.setBackground(Color.white);
            
            redPanel.addMouseListener(this);
            bluePanel.addMouseListener(this);
            greenPanel.addMouseListener(this);
            yellowPanel.addMouseListener(this);
            purplePanel.addMouseListener(this);
            blackPanel.addMouseListener(this);
            pinkPanel.addMouseListener(this);
            orangePanel.addMouseListener(this);
            lightbluePanel.addMouseListener(this);
            whitePanel.addMouseListener(this);
            
            
            controlPanel.setLayout(new GridLayout(1,3));
            controlPanel.add(this.menuBar);
            
            setLayout(new BorderLayout());
            add("North", controlPanel);
            add("West", sidePanel);
           // add("Center", this.canvas);
             add("Center", scrollPane);
            
            canvas.setEnabled(false);
            menuItemSave.setEnabled(false);
            menuItemRemoveLayer.setEnabled(false);
            menuOptions.setEnabled(false);
            rotate.setEnabled(false);
            zoomIn.setEnabled(false);
            zoomOut.setEnabled(false);
            translate.setEnabled(false);
            menuCopy.setEnabled(false);
            menuPaste.setEnabled(false);
            colorEffects.setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            if (source == this.menuItemOpen)
            {
                openFile();
                menuItemSave.setEnabled(true);
                menuItemRemoveLayer.setEnabled(true);
                menuOptions.setEnabled(true);
                rotate.setEnabled(true);
                zoomIn.setEnabled(true);
                zoomOut.setEnabled(true);
                translate.setEnabled(true);
                menuCopy.setEnabled(true);
                menuPaste.setEnabled(true);
                colorEffects.setEnabled(true);
                
            }
            else if(source == this.menuItemSave)
            {
                saveFile();
            }
            else if(source == this.menuCopy)
            {
                if(imageList != null && !imageList.isEmpty())
                {
                    canvas.setCopy();
                    System.out.println("copyview");
                }
            }
            else if(source == this.menuPaste)
            {
                if(imageList != null && canvas.getCopy() != null)
                {
                    canvas.paste();
                    System.out.println("pasteview");
                }
            }
             else if(source == this.menuItemRemoveLayer)
            {
                if(!imageList.isEmpty())
                {
                    imageList.remove(canvas.getSelectedLayer());
                    canvas.setSelectedLayer(imageList.size() -1);
                    if(imageList.isEmpty())
                    {
                        rotate.setEnabled(false);
                        zoomIn.setEnabled(false);
                        zoomOut.setEnabled(false);
                        menuItemSave.setEnabled(false);
                        menuItemRemoveLayer.setEnabled(false);
                        menuOptions.setEnabled(false);
                        translate.setEnabled(false);
                        menuCopy.setEnabled(false);
                        menuPaste.setEnabled(false);
                        colorEffects.setEnabled(false);
                        super.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }                  
                }
                else
                {
                    rotate.setSelected(false);
                    zoomIn.setSelected(false);
                    zoomOut.setSelected(false); 
                }
                    
                    
            }
            else if(source == this.NormalEffect)
            {
                this.canvas.setConvState(c.getConvMatrix(0));
            }
            else if(source == this.SharpenSffect)
            {
                this.canvas.setConvState(c.getConvMatrix(1));
            }
            else if(source == this.BlurEffect)
            {   
                this.canvas.setConvState(c.getConvMatrix(2));
            }
            else if(source == this.EdgeDetectionEffect)
            {
                this.canvas.setConvState(c.getConvMatrix(3));
            }
            else if(source == this.LightenEffect)
            {
                this.canvas.setConvState(c.getConvMatrix(4));
            }
            else if(source == this.DarkenEffect)
            {
                this.canvas.setConvState(c.getConvMatrix(5));
            }
            else if(source == this.embossEffect)
            {
                this.canvas.setConvState(c.getConvMatrix(6));
            }
            else if(source == this.Normal)
            {
                this.canvas.setEffect("");
                this.canvas.setConvState(c.getConvMatrix(0));
            }
            else if(source == this.greyscale)
            {
                this.canvas.setEffect("greyscale");
            }
            else if(source == this.sepia)
            {   
                this.canvas.setEffect("sepia");
            }
            else if(source == this.invert)
            {
                this.canvas.setEffect("invert");
            }
            else if(source == this.posterise)
            {
                this.canvas.setEffect("posterise");
            }
            else if(source == this.frame1)
            {
                selectedImage = "frame1";
                drawFrame(selectedImage);
            }
            else if(source == this.frame2)
            {
                selectedImage = "frame2";
                drawFrame(selectedImage);
            }
            else if(source == this.frame3)
            {
                selectedImage = "frame3";
                drawFrame(selectedImage);
            }
            else if(source == this.frame4)
            {
                selectedImage = "frame4";
                drawFrame(selectedImage);
            }
            else if(source == this.frame5)
            {
                selectedImage = "frame5";
                drawFrame(selectedImage);
            }
            else if(source == this.frame6)
            {
                selectedImage = "frame6";
                drawFrame(selectedImage);
            }
            else if(source == this.frame7)
            {
                selectedImage = "frame7";
                drawFrame(selectedImage);
            }
            else if(source == this.frame8)
            {
                selectedImage = "frame8";
                drawFrame(selectedImage);
            }
            else if(source == this.zoomIn)
            {
                rotate.setSelected(false);
                zoomOut.setSelected(false);
                drawButton.setSelected(false);
                
                if(zoomIn.isSelected())
                {
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
                else
                {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            else if(source == this.zoomOut)
            {
                rotate.setSelected(false);
                zoomIn.setSelected(false);
                drawButton.setSelected(false);
                if(zoomOut.isSelected())
                {
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
                else
                {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            else if(source == this.rotate)
            {
                zoomIn.setSelected(false);
                zoomOut.setSelected(false);
                drawButton.setSelected(false);
                if(this.rotate.isSelected())
                {
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
                else
                {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            else if(source == this.drawButton)
            {
                zoomIn.setSelected(false);
                zoomOut.setSelected(false);
                rotate.setSelected(false);
                if(this.drawButton.isSelected())
                {
                    setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                    canvas.setDrawColour(Color.black);
                    canvas.setMode("DRAW");
                }
                else
                {
                    canvas.setMode("MANIPULATE");
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            else if(source == this.clearButton)
            {
                menuItemSave.setEnabled(false);
                menuItemRemoveLayer.setEnabled(false);
                menuOptions.setEnabled(false);
                rotate.setEnabled(false);
                zoomIn.setEnabled(false);
                zoomOut.setEnabled(false);
                translate.setEnabled(false);
                menuCopy.setEnabled(false);
                menuPaste.setEnabled(false);
                colorEffects.setEnabled(false);
                canvas.clear();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if(source == this.mirrorImage)
            {
                try 
                {
                    canvas.mirrorImage();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(source == this.incBrushSize)
            {
                this.canvas.incWidth();
            }
            else if(source == this.decBrushSize)
            {
                this.canvas.decWidth();
            }
            
            
            this.canvas.repaint();
        }

        private void openFile()
        {
            // Open and display an image file
            if (this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File imageFile = new File(this.fileChooser.getCurrentDirectory() + "\\" +
                                          this.fileChooser.getSelectedFile().getName());
                try
                {
                    Image img = new ImageIcon(imageFile.getPath()).getImage();
                    MediaTracker tracker = new MediaTracker(this);
                    tracker.addImage(img, 1);
                    try
                    {
                        tracker.waitForAll();
                        this.imageList.add(new Sprite(img)); // the identity of the added image. This information is used by deleteImages
                        canvas.setSelectedLayer(imageList.size() -1);
                        canvas.setWorkLayer(imageList.size() -1);
                    }
                    catch (InterruptedException interruptedException)
                    {
                        interruptedException.printStackTrace();
                    }

                this.canvas.paintList(this.imageList);

                    // show and hide appropriate buttons
                    
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            canvas.setEnabled(true);
        }

        private void saveFile()
        {
            // Save the same image file
            if (this.fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File imageFile = new File(this.fileChooser.getCurrentDirectory() + "\\" +
                                          this.fileChooser.getSelectedFile().getName());
                FileFilter filter = this.fileChooser.getFileFilter();

                try
                {
                    String fileName = imageFile.getAbsolutePath();
                    if (fileName.toUpperCase().contains(".JPG") || fileName.toUpperCase().contains(".JPEG")   || fileName.toUpperCase().contains(".JPE") || fileName.toUpperCase().contains(".PNG") || fileName.toUpperCase().contains(".GIF"))        
                    {
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));
                        System.out.println(fileName);
                    }

                    if (filter.getDescription().contains(".PNG"))
                    {
                        ImageIO.write(canvas.getImage(), "png", new File(fileName + ".png"));
                    }
                    else if (filter.getDescription().contains(".GIF"))
                    {
                        ImageIO.write(canvas.getImage(), "gif", new File(fileName + ".gif"));
                    }
                    else
                    {
                        BufferedImage img = canvas.getImage();
                        BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = temp.createGraphics();
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, 0, temp.getWidth(), temp.getHeight());
                        g2d.drawImage(img,0,0, this);
                        g2d.dispose();
                        ImageIO.write(temp, "jpg", new File(fileName + ".jpg"));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        private void drawFrame(String selectedImage)
        {
            Image img = new ImageIcon(getClass().getClassLoader().getResource("images/" + selectedImage + ".png")).getImage();
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            
            try
            {
                tracker.waitForAll();
                Sprite i =  imageList.get(0);

                //Set Frame width & Height to be size of the top Image
                //int frameWidth = i.getWidth() + 10;
                //int frameHeight = i.getHeight() + 10;

                //Set Frame width & Height to be size of the Canvas
                int frameWidth = canvas.getWidth();
                int frameHeight = canvas.getHeight();

                Image newImg = img.getScaledInstance(frameWidth, frameHeight,0);



                this.imageList.add(new Sprite(newImg)); // the identity of the added image. This information is used by deleteImages

                System.out.println(frameWidth + "--" + frameHeight);
            }
            catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace();
            }

                this.canvas.paintList(this.imageList);    
                
            
        }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(e.getSource() == this.redPanel)
        {
            canvas.setDrawColour(redPanel.getBackground());
        }
        else if(e.getSource() == this.bluePanel)
        {
            canvas.setDrawColour(bluePanel.getBackground());
        }
        else if(e.getSource() == this.greenPanel)
        {
            canvas.setDrawColour(greenPanel.getBackground());
        }
        else if(e.getSource() == this.yellowPanel)
        {
            canvas.setDrawColour(yellowPanel.getBackground());
        }
        else if(e.getSource() == this.purplePanel)
        {
            canvas.setDrawColour(purplePanel.getBackground());
        }
        else if(e.getSource() == this.blackPanel)
        {
            canvas.setDrawColour(blackPanel.getBackground());
        }
        else if(e.getSource() == this.pinkPanel)
        {
            canvas.setDrawColour(pinkPanel.getBackground());
        }
        else if(e.getSource() == this.orangePanel)
        {
            canvas.setDrawColour(orangePanel.getBackground());
        }
        else if(e.getSource() == this.lightbluePanel)
        {
            canvas.setDrawColour(lightbluePanel.getBackground());
        }
        else if(e.getSource() == this.whitePanel)
        {
            canvas.setDrawColour(whitePanel.getBackground());
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        canvas.setSize(new Dimension(canvas.getWidth() + e.getScrollAmount(),canvas.getHeight() + e.getScrollAmount()));
    }
        

        private class JpegFileFilter extends FileFilter
        {
            @Override
            public boolean accept(File file)
            {
                if (file.isDirectory())
                {
                    return true;
                }

                String fileName = file.getName().toLowerCase();
                return (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpe"));
            }

            @Override
            public String getDescription()
            {
                return ("JPEG (*.JPG, *.JPEG and *.JPE)");
            }
        }

        private class GifFileFilter extends FileFilter
        {
            @Override
            public boolean accept(File file)
            {
                if (file.isDirectory())
                {
                    return true;
                }

                String name = file.getName().toLowerCase();
                return (name.endsWith(".gif"));
            }

            @Override
            public String getDescription()
            {
                return ("(*.GIF)");
            }
        }
        
        private class PngFileFilter extends FileFilter
        {
            @Override
            public boolean accept(File file)
            {
                if (file.isDirectory())
                {
                    return true;
                }

                String name = file.getName().toLowerCase();
                return (name.endsWith(".png"));
            }

            @Override
            public String getDescription()
            {
                return ("(*.PNG)");
            }
        }
        
        private class AllFileFilter extends FileFilter
        {
            @Override
            public boolean accept(File file)
            {
                if (file.isDirectory())
                {
                    return true;
                }

                String name = file.getName().toLowerCase();
                return (true);
            }

            @Override
            public String getDescription()
            {
                return ("All Files");
            }
        }
        
        public void CustomCursor()
        {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image img = tk.getImage("mg.png");
            Point point = new Point(0,0);
            Cursor cursor = tk.createCustomCursor(img, point, "cursor");
            
            setCursor(cursor);
        }
        
        public boolean getZoomIn()
        {
            return this.zoomIn.isSelected();
        }
        
        public boolean getZoomOut()
        {
            return this.zoomOut.isSelected();
        }
        
        public boolean getRot()
        {
            return this.rotate.isSelected();
        }
        
        public boolean getDrawButton()
        {
            return this.drawButton.isSelected();
        }
        
        @Override
        public void keyTyped(KeyEvent e) 
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            if((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
            {
                System.out.println("Inside Ctrl");

                    System.out.println("Inside Ctrl + C");
                    if(imageList != null && !imageList.isEmpty())
                    {
                        canvas.setCopy();
                    }
            }
            else if((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
            {
                System.out.println("Inside Ctrl + V");
                if(imageList != null && canvas.getCopy() != null)
                {
                    canvas.paste();
                }
                repaint();
            }

            }

        @Override
        public void keyReleased(KeyEvent e) 
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
