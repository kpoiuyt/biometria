import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFrame {
    private BufferedImage bufferedImage;
    private JFrame frame;

    public ImageFrame(BufferedImage image){
        this.bufferedImage=image;
    }

    public void show(){
        JLabel jLabel=new JLabel(new ImageIcon(bufferedImage));
        JScrollPane jScrollPane=new JScrollPane(jLabel);
        frame=new JFrame();
        frame.setContentPane(jScrollPane);
        frame.setSize(new Dimension(800,600));

        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }


}
