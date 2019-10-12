import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.*;

public class MyImage {

    public int[] getPixelData(BufferedImage image, int x, int y) {
        //// !!! Origin is in Center of image !!!
        x = image.getHeight() / 2 + x; // To change origin
        y = image.getHeight() / 2 - y; // To change origin
        int argb = image.getRGB(x, y);

        int rgb[] = new int[] { (argb >> 16) & 0xff, // red
                (argb >> 8) & 0xff, // green
                (argb) & 0xff, // blue
                (argb >> 24) & 0xff };

        // System.out.println( + rgb[2]);
        return rgb;
    }

    public void setPixelData(BufferedImage image, int x, int y, int a, int r, int g, int b) {
        // !!! Origin is in Center of image !!!
        x = image.getHeight() / 2 + x; // To change origin
        y = image.getHeight() / 2 - y; // To change origin

        int p = (a << 24) | (r << 16) | (g << 8) | b;
        image.setRGB(x, y, p);

    }

    public void printImage(BufferedImage image) {
        JFrame editorFrame = new JFrame();
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }

    public void clear(BufferedImage image) {
        for (int i = -image.getWidth() / 2 + 2; i < image.getWidth() / 2 - 1; i++) {
            for (int j = -image.getHeight() / 2 + 2; j < image.getHeight() / 2 - 1; j++) {
                setPixelData(image, i, j, 0, 0, 0, 0);
            }
        }
    }

    public void createCircle(BufferedImage image, int R, int x, int y) {
        // Circle (i - x)^2 + (j - y)^2 = R^2,,, R = 50
        int j = 0;
        increaseWidth(image, x, y);
        for (int i = x - R; i <= x + R; i++) {
            j = y + (int) Math.sqrt(Math.pow(R, 2) - Math.pow(i - x, 2));
            // setPixelData(image, i, j, 255, 255, 0, 0);
            increaseWidth(image, i, j);
            j = y - (int) Math.sqrt(Math.pow(R, 2) - Math.pow(i - x, 2));
            // setPixelData(image, i, j, 255, 255, 0, 0);
            increaseWidth(image, i, j);
        }
    }

    public void increaseWidth(BufferedImage image, int x, int y) {
        setPixelData(image, x, y, 255, 255, 0, 0);
        setPixelData(image, x - 1, y - 1, 255, 255, 0, 0);
        setPixelData(image, x, y - 1, 255, 255, 0, 0);
        setPixelData(image, x + 1, y - 1, 255, 255, 0, 0);
        setPixelData(image, x - 1, y, 255, 255, 0, 0);
        setPixelData(image, x + 1, y, 255, 255, 0, 0);
        setPixelData(image, x - 1, y + 1, 255, 255, 0, 0);
        setPixelData(image, x, y + 1, 255, 255, 0, 0);
        setPixelData(image, x + 1, y + 1, 255, 255, 0, 0);
    }

    public void line(BufferedImage image, int a, int b, int c, int d) {
        // line b/w (a,b) and (c,d)
        // line y - b = ((d - b) / (c - a)) * (x - a)
        // y = slope*x + constant;

        if (a < c) {
            double slope = ((Double.valueOf(d) - b) / (c - a));
            double constant = b - slope * a;
            int i = a;
            while (i <= c) {
                int x = i;
                int y = (int) (slope * x + constant);
                increaseWidth(image, x, y);
                i += 1;
            }

        } else if (a > c) {
            line(image, c, d, a, b);
        } else {
            int j = (int) d;
            if (d > b) {
                for (int i = (int) b; i < j; i++) {
                    increaseWidth(image, a, i);
                }
            } else {
                for (int i = (int) b; i > j; i--) {
                    increaseWidth(image, a, i);
                }
            }
        }

    }

}