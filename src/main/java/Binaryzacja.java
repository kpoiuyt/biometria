import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Binaryzacja {

    private BufferedImage image;
    private int sumHi;
    private int[][] table;
    private int imageWidth,imageHeigh;


    public Binaryzacja(String filenae){
        image=getImage(filenae);
        sumHi=0;
        imageWidth=image.getWidth();
        imageHeigh=image.getHeight();
        table=new int[imageWidth][imageHeigh];

        for(int i=0;i<imageWidth;i++)
            for(int j=0;j<imageHeigh;j++)
                table[i][j] = image.getRGB(i, j);
    }

    public void setImage(BufferedImage image){
        this.image=image;
    }

    public BufferedImage getImage(){
        return image;
    }

   public BufferedImage getImage(String filename){
        File file=new File(filename);
        BufferedImage bufferedImage=null;
        try {
            bufferedImage=ImageIO.read(file);
        } catch (IOException e) {
            System.err.print("File read error");
        }
        sumHi=0;
        return bufferedImage;
    }

    public int binaryzacja_otsu(){




        float result=Float.MAX_VALUE;
        int T=0;

        for(int i=255;i>0;i--){
            float omegaB=omegaB(i);
            float omegaF=omegaF(i);
            float sigmaB=sigmaB(i);
            float sigmaF=sigmaF(i);




            float sigmaW=  (omegaF*(float)Math.pow(sigmaF,2)+omegaB*(float)Math.pow(sigmaB,2));





            if(result>sigmaW){
                result=sigmaW;
                T=i;

            }else break;


        }

        binaryzacja_z_progiem(T);

        return T;

    }





    private float sigmaF(int T){
        float sum=0;

        float miFT=miF(T);
        float omegaBT=omegaB(T);

        for(int i=T;i<255;i++){
            sum+=p(i)*(Math.pow((double)i-miFT,2)/omegaBT);
        }
        return sum;
    }

    private float omegaF(int T){
        float sum=0;

        for(int i=T;i<255;i++){
            sum+=p(i);
        }
        return sum;
    }


    private float miB(int T){
        float sum=0;
        float omeagaBT=omegaB(T);

        for(int i=0;i<T-1;i++){
            sum+=p(i)*((float)i/omeagaBT);
        }
        return sum;
    }

    private float sigmaB(int T){
        float sum=0;
        float miFT=miF(T);
        float omegaBT=omegaB(T);

        for(int i=0;i<T-1;i++){
            sum+=p(i)*(Math.pow((double)i-miFT,2)/omegaBT);
        }
        return sum;
    }

    private float miF(int T){
        float sum=0;
        float omegaT=omegaB(T);

        for(int i=T;i<255;i++){
            sum=p(i)*((float)i/omegaT);
        }
        return sum;
    }



    private float omegaB(int T){
        float sum=0;



        for(int i=0;i<T-1;i++)
            sum+=p(i);
        return sum;
    }

    private int h(int x){
        int sum=0;

        for(int w=0;w<imageWidth;w++)
            for(int h=0;h<imageHeigh;h++){
                int path=table[w][h];
                int value=new Color(path).getRed();
                if(value==x)
                    sum++;
            }
        return sum;
    }




    private float p(int x){
        int hx=h(x);

        int sum=0;

        if(sumHi==0){
            for(int i=0;i<255;i++){
                sum+=h(i);

            }
            sumHi=sum;
        }else sum=sumHi;



        return (float)hx/(float)sum;
    }


    public void binaryzacja_z_progiem(int prog){
        for(int i=0;i<imageWidth;i++)
            for(int j=0;j<imageHeigh;j++){
                Color color=new Color(table[i][j]);
                int red=color.getRed();
                image.setRGB(i,j,red<prog ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
    }
}
