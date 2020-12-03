/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.commons.io.FilenameUtils;
 
 
public class GUI extends JFrame {
 
    public GUI(String ditaa) {
        File t = new File(ditaa);
        if (!t.exists()){
            System.out.println("Unable to find ditaa jar. Closing");
            System.exit(0);
        }
        
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 7 / 8;
        int width = screenSize.width * 1 / 2;
        
        
        JFileChooser fc = new JFileChooser();
        
        JFrame frame = new JFrame();
        JLabel jLabel = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton readButton = new JButton("OPEN FILE");
        
        readButton.addActionListener(ev -> { 
          int returnVal = fc.showOpenDialog(frame);
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            StringBuilder line = new StringBuilder();
            File file = fc.getSelectedFile();
            String temp = "java -jar " + ditaa + " ";
            line.append(temp);
            line.append(file.getPath());
            line.append(" output.jpg");
            try{
                Process proc =  Runtime.getRuntime().exec(line.toString());
                proc.waitFor();
                InputStream in = proc.getInputStream();
                InputStream err = proc.getErrorStream();

                byte b[]=new byte[in.available()];
                in.read(b,0,b.length);
                System.out.println(new String(b));

                byte c[]=new byte[err.available()];
                err.read(c,0,c.length);
                System.out.println(new String(c));
            }
            catch(Exception e){
                System.out.println("Unable to run ditaa");
                System.out.println(e);
            }
            try{
                BufferedImage bufImg = ImageIO.read(new File("output.jpg"));
                ImageIcon imageIcon = new ImageIcon(bufImg);
                jLabel.setIcon(imageIcon);
                frame.setName(file.getName());
                frame.getContentPane().add(jLabel, BorderLayout.CENTER);
                frame.repaint();
                frame.revalidate();
            }
            catch (Exception e){
                    System.out.println("Unable to read output file");
                    System.out.println(e);
            }
          }});
        frame.getContentPane().add(readButton, BorderLayout.PAGE_END);
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
    }
     
    public static void main(String[] args) {
        try{
            String ditaa = args[0];
            if (!FilenameUtils.getExtension(ditaa).equals("jar") || !FilenameUtils.getName(ditaa).toLowerCase().contains("ditaa")){
                System.out.println("Please provide the ditaa jar.");
                System.out.println("java -jar GUI.jar ditaa.jar");
                System.exit(0);
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GUI(ditaa).setVisible(true);
                }
            });
        }
        catch(Exception e){
            System.out.println("Please provide the ditaa jar");
            System.out.println("java -jar GUI.jar ditaa.jar");
            System.exit(0);
        }
 
}}

