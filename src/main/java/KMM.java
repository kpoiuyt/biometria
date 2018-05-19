import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.awt.image.BufferedImage;

public class KMM {
    private BufferedImage image;
    private int[][] table;

    public KMM(BufferedImage image){
        this.image=image;

        table=new int[image.getWidth()][image.getHeight()];

        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){
            table[i][j]=new Color(image.getRGB(i,j)).getRed();
            }

    }

    public BufferedImage getImage(){
        return image;
    }


    public void start(){
      table=do_mapy_bitowej(table);
      table=kontury_i_katy(table);
      table=pixele_z_234_sasiadami(table);
      table=usuwanie_wedlog_tablicy(table);
      update();


    }

    private int[][] usuwanie_wedlog_tablicy(int[][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++) {

                int[][] tab3 = new int[3][3];

                if(table[i][j]==4)
                {
                for (int w = i - 1; w < i + 2; w++)
                    for (int h = j - 1; h < j + 2; h++) {

                        if (w < 0 || w > image.getWidth() - 1)
                            continue;
                        if (h < 0 || h > image.getHeight() - 1)
                            continue;


                        tab3[w - i + 1][h - j + 1] = table[w][h];


                    }

                int sum = 0;

                for (int w = 0; w < 3; w++)
                    for (int h = 0; h < 3; h++) {
                    /////maska
                    }


            }
            }
            return table;
    }

    private int[][] pixele_z_234_sasiadami(int [][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int sum=0;

                if(table[i][j]!=0)
            for(int w=i-1;w<i+2;w++)
                for(int h=j-1;h<j+2;h++){


                    if(w<0||w>image.getWidth()-1)
                        continue;
                    if(h<0||h>image.getHeight()-1)
                        continue;

                    if(w!=i&&h!=j){
                        if(table[w][h]!=0)
                            sum++;
                    }


                }

                if(sum==2||sum==3||sum==4)
                    table[i][j]=4;

            }

            return table;
    }

    private int[][] kontury_i_katy(int[][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int sum=0;

            if(table[i][j]!=0)
            for(int w=i-1;w<i+2;w++)
                for(int h=j-1;h<j+2;h++){
                if(w<0||w>image.getWidth()-1)
                    continue;
                if(h<0||h>image.getHeight()-1)
                    continue;

                if(w!=i&&h!=j){
                    if(table[w][h]==0)
                        sum+=1;
                }

                }

                if(sum==1)
                    table[i][j]=3;
                if(sum>1)
                    table[i][j]=2;

            }

            return table;
    }


    private int[][] do_mapy_bitowej(int [][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
                if(table[i][j]==0)
                    table[i][j]=1;
                else table[i][j]=0;

                return table;
    }



    private void maska() throws ArrayIndexOutOfBoundsException{
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){
                if(table[i][j]!=1&&table[i][j]!=255){
                    int count=0;
                    int sum=0;
                    for(int w=i-1;w<i+2;w++)
                        for(int h=j-1;h<j+2;h++){

                            if(w<0||w>image.getWidth()-1)
                                continue;
                            if(h<0||h>image.getHeight()-1)
                                continue;

                            if(table[i][j]==2||table[i][j]==3||table[i][j]==4){

                                if(i-w==-1&&j-h==-1&&table[w][h]!=255)
                                    sum+=128;
                                if(i-w==-1&&j-h==0&&table[w][h]!=255)
                                    sum+=1;
                                if(i-w==-1&&j-h==1&&table[w][h]!=255)
                                    sum+=2;
                                if(i-w==0&&j-h==-1&&table[w][h]!=255)
                                    sum+=64;
                                if(i-w==0&&j-h==1&&table[w][h]!=255)
                                    sum+=4;
                                if(i-w==1&&j-h==-1&&table[w][h]!=255)
                                    sum+=32;
                                if(i-w==1&&j-h==0&&table[w][h]!=255)
                                    sum+=16;
                                if(i-w==1&&j-h==1&&table[w][h]!=255)
                                    sum+=8;

                            }



                        }

                        int[] tab=to_remove();

                        for(int z=0;z<tab.length;z++)
                            if(sum==tab[z])
                                table[i][j]=255;
                        else tab[z]=4;


                }
            }
    }

    private static int[] to_remove(){
        return new int[]{
                3,5,7,12,13,14,15,20,
                21,22,23,28,29,30,31,48,
                52,53,54,55,56,60,61,62,
                63,65,67,69,71,77,79,80,
                81,83,84,85,86,87,88,89,
                103,109,111,112,113,115,116,117,
                118,119,120,121,123,124,125,126,
                127,131,133,135,141,143,149,151,
                157,159,181,183,189,191,192,193,
                195,197,199,205,207,208,209,211,
                212,213,214,215,216,217,219,220,
                221,222,223,224,225,227,229,231,
                237,239,240,241,243,244,245,246,
                247,248,249,251,252,253,254,255
        };
    }

    private void delete4(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
                if(table[i][j]==4)
                    table[i][j]=255;
    }



    public void update(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++) {
                if(table[i][j]!=0)
                image.setRGB(i, j, new Color(table[i][j], table[i][j], table[i][j]).getRGB());
                else
                    image.setRGB(i,j,Color.WHITE.getRGB());


                if(table[i][j]==4) System.out.print("4");

            }




    }


}
