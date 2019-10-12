import app.MyImage;
import java.util.concurrent.TimeUnit;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.*; 
import java.awt.*; 
import javax.swing.*;

public class Pattern {

    public static void main(String[] args) {

        try {
            MyImage m = new MyImage();
            BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

            TimeUnit.MILLISECONDS.sleep(100);

            JFrame editorFrame = new JFrame();
            editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel jLabel = new JLabel();
            jLabel.setIcon(imageIcon);
            editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
            editorFrame.pack();
            editorFrame.setLocationRelativeTo(null);
            editorFrame.setVisible(true);

            for (int i = 180; i < 181; i += 1) {

                for (double f = 2; f < 100; f += 0.08) {
                    m.createCircle(image, 400, 0, 0);
                    
                    int angle = 360 / i;

                    // y = mx;
                    // x^2 + y^2 = 400^2
                    double slp = 0;
                    int[][] arr = new int[i][2];

                    for (int ang = 0; ang <= 90; ang += angle) {

                        
                        // x = +- sqrt(R^2 / m^2 + 1)
                        slp = Math.tan(Math.PI * (ang / 180.0));
                        double k = Math.sqrt(Math.pow(400, 2) / (Math.pow(slp, 2) + 1));
                        int x = (int) k; // positive x
                        int y = (int) (slp * k); // positive y

                        // !!! Origin is in Center of image !!!
                        m.createCircle(image, 5, x, y);
                        m.createCircle(image, 5, -x, -y);
                        m.createCircle(image, 5, x, -y);
                        m.createCircle(image, 5, -x, y);

                        arr[ang / angle][0] = x;
                        arr[ang / angle][1] = y;
                        arr[(i / 2) + ang / angle][0] = -x;
                        arr[(i / 2) + ang / angle][1] = -y;

                        if (ang != 0) {
                            arr[i - ang / angle][0] = x;
                            arr[i - ang / angle][1] = -y;
                            arr[(i / 2) - ang / angle][0] = -x;
                            arr[(i / 2) - ang / angle][1] = y;
                        }

                    }

                    for (int t = 1; t < i; t++) {

                        int tar = (int) (t * f) % i;
                        m.line(image, arr[t][0], arr[t][1], arr[tar][0], arr[tar][1]);
                        // imageIcon = new ImageIcon(image);
                        // jLabel.setIcon(imageIcon);
                        // TimeUnit.MILLISECONDS.sleep(5);
                    }
                    
                    imageIcon = new ImageIcon(image);
                    jLabel.setIcon(imageIcon);
                    
                    TimeUnit.MILLISECONDS.sleep(0);
                    m.clear(image);
                }

            }

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}