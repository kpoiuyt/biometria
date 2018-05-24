import java.awt.*;
import java.awt.image.BufferedImage;

public class CN1 {
    private BufferedImage image;
    private int[][] table;

    public CN1(BufferedImage image){
        table=new int[image.getWidth()][image.getHeight()];

        this.image=image;

        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
            {
                Color c=new Color(image.getRGB(i,j));

                if(c.getRed()==255)
                    table[i][j]=0;
                else table[i][j]=1;
            }
    }

    public BufferedImage getImage(){
        update();
        return image;
    }

    public BufferedImage getImage(int how_close){
        removeIfToClose(how_close);
        return getImage();
    }

    public void start(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int [][] tab3=new int[3][3];

            if(table[i][j]==1){
                for(int w=i-1;w<i+2;w++)
                    for(int h=j-1;h<j+2;h++){
                    tab3[i-w+1][j-h+1]=table[w][h];
                    }

                   int sum=cnSUM(tab3);


                        if(sum==0||sum==1||sum==3||sum==4)
                        table[i][j]=Color.YELLOW.getRGB();






            }
            }
    }

    private int cnSUM(int[][] ta){

        int sum=0;
        sum+=wartosc_bezwzgledna(ta[0][0]-ta[1][0]);
        sum+=wartosc_bezwzgledna(ta[1][0]-ta[2][0]);
        sum+=wartosc_bezwzgledna(ta[2][0]-ta[2][1]);
        sum+=wartosc_bezwzgledna(ta[2][1]-ta[2][2]);
        sum+=wartosc_bezwzgledna(ta[2][2]-ta[1][2]);
        sum+=wartosc_bezwzgledna(ta[1][2]-ta[0][2]);
        sum+=wartosc_bezwzgledna(ta[0][2]-ta[0][1]);
        sum+=wartosc_bezwzgledna(ta[0][1]-ta[0][0]);

        sum=sum/2;

        return sum;

    }

    private int wartosc_bezwzgledna(int x){
        if(x>0)
            return x;
        else return -x;
    }

    private void removeIfToClose(int co_ile_moze_byc){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int[][] tab3=new int[co_ile_moze_byc][co_ile_moze_byc];

            if(table[i][j]==Color.YELLOW.getRGB())
            for(int w=i-co_ile_moze_byc/2;w<i+1+co_ile_moze_byc/2;w++)
                for(int h=j-co_ile_moze_byc/2;h<j+1+co_ile_moze_byc/2;h++){

                    if(w<0||w>image.getWidth()-1) continue;
                    if(h<0||h>image.getHeight()-1) continue;

                    tab3[i-w+co_ile_moze_byc/2][j-h+co_ile_moze_byc/2]=table[w][h];
                }

                int count=0;

            for(int w=0;w<co_ile_moze_byc;w++)
                for(int h=0;h<co_ile_moze_byc;h++)
                    if(tab3[w][h]==Color.YELLOW.getRGB()&&count==0)
                        count++;
                    else if(tab3[w][h]==Color.YELLOW.getRGB()) table[i][j]=1;

            }

    }

    private void update(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
            {
                if(table[i][j]==0)
                    this.image.setRGB(i,j,Color.WHITE.getRGB());
                else if(table[i][j]==1) this.image.setRGB(i,j,Color.BLACK.getRGB());
                else if(table[i][j]==Color.YELLOW.getRGB()) {

                    for(int k=i-1;k<i+2;k++)
                        for(int l=j-1;l<j+2;l++){
                        if(k==i&&l==j) continue;


                    this.image.setRGB(k,l,Color.RED.getRGB());}}

            }
    }
}
