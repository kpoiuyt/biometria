import java.awt.*;
import java.awt.image.BufferedImage;

public class CN {
    private BufferedImage image;
    private int[][] table;

    public BufferedImage getImage(){

        update();
        return this.image;
    }

    public CN(BufferedImage image){
        this.image=image;
        table=new int[image.getWidth()][image.getHeight()];

        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){
                {
                    Color c=new Color(image.getRGB(i,j));

                    if(c.getRed()==255)
                        table[i][j]=0;
                    else table[i][j]=1;


                }

        }

    }

    public void start(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int [][] ta=new int[3][3];


            if(table[i][j]==1)
            for(int w=i-1;w<i+2;w++)
                for(int h=j-1;h<j+2;h++){

                if(w<0||w>image.getWidth()-1)
                    continue;
                if(h<0||h>image.getHeight()-1)
                    continue;

                if(table[w][h]!=0)
                ta[w-i+1][h-j+1]=1;
                else
                    ta[w-i+1][h-j+1]=0;

                }

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


                //System.out.println(sum);

                if(table[i][j]==1)
                if(sum==0||sum==1||sum==3||sum==4){

                    for(int x=i-2;x<i+3;x++)
                        for(int y=j-2;y<j+3;y++) {
                        if(table[x][y]==1)
                            continue;


                            if(sum==1)
                            table[x][y] =Color.YELLOW.getRGB();
                             else if(sum==2)
                                table[x][y] =Color.BLUE.getRGB();
                            else if(sum==3)
                                table[x][y] =Color.GREEN.getRGB();
                            else if(sum==4)
                                table[x][y] =Color.RED.getRGB();
                           else  if(sum==0)
                                table[x][y] =Color.PINK.getRGB();

                            System.out.println(sum);
                        }

                }

            }
    }

    private int wartosc_bezwzgledna(int x){
        if(x>0)
            return x;
        else return -x;
    }

    private void update(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
            {
                if(table[i][j]==0)
                    this.image.setRGB(i,j,Color.WHITE.getRGB());
                else this.image.setRGB(i,j,table[i][j]);

            }
    }
}
