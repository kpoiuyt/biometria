import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Panel {
    private JLabel wczytanyPlikLabel;
    private JTextField textField1;
    private JButton wczytajButton;
    private JButton pokażButton;
    private JPanel panel;
    private JButton scienianieButton;

    private BufferedImage image;

    private JFrame frame;
    private Binaryzacja binaryzacja;


    public Panel(){

        frame=new JFrame();
        frame.setContentPane(panel);
        frame.setSize(new Dimension(800,600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        wczytajButton.addActionListener(getWczytajButtonListener());
        pokażButton.addActionListener(getPokażButtonListener());

        scienianieButton.addActionListener(getScienianieButtonListener());


        frame.setVisible(true);
    }



    private ActionListener getScienianieButtonListener(){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                binaryzacja.setImage(image);
                binaryzacja.binaryzacja_otsu();
                KMM scienianie=new KMM(binaryzacja.getImage());
                scienianie.start();
                image=scienianie.getImage();

                CN cn=new CN(image);
                cn.start();
                image=cn.getImage();
            }
        };
    }





    private ActionListener getPokażButtonListener(){
        ActionListener actionListener=new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              new ImageFrame(image).show();
            }
        };

        return actionListener;
    }

    private ActionListener getWczytajButtonListener(){
        ActionListener actionListener=new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename=textField1.getText();

                binaryzacja=new Binaryzacja(filename);

                image=binaryzacja.getImage();

                if(binaryzacja.getImage(filename)!=null)
                {
                    pokażButton.setEnabled(true);
                    wczytanyPlikLabel.setText(filename);
                }else{
                    pokażButton.setEnabled(false);
                    wczytanyPlikLabel.setText("BŁĄD");
                }

            }
        };

        return actionListener;
    }

}
