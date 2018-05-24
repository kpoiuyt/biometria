import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashSet;

public class KMM1 {
    private int[][] table;
    private BufferedImage image;

    public KMM1(BufferedImage image){
        this.image=image;

        table=new int[image.getWidth()][image.getHeight()];

        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
            {
                Color c=new Color(image.getRGB(i,j));

                if(c.getRed()==255)
                    table[i][j]=0;
                else {table[i][j]=1; }

            }
    }

    public BufferedImage getImage(){
        update();
        return this.image;
    }


    public void start(){


            table = edges1to2(table);
            table = stickingNeighboursTo4(table);
           table = delete4(table);
            table = steps(table, 2);
            table = steps(table, 3);
             table=steps(table,4);

            for(int i=0;i<10;i++) {
                table = edges1to2(table);
                table = stickingNeighboursTo4(table);
                //table=delete4(table);
                table = steps(table, 2);
                table = steps(table, 3);
                table=steps(table,4);

            }
        table = edges1to2(table);
        table = stickingNeighboursTo4(table);
            table=delete4(table);



    }

    public int[][] steps(int [][] table,int N){
        HashSet<Integer> deleteArray=to_removehs();

        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){
            if(table[i][j]==N){
                int[][] tab3=new int[3][3];

                ///calculating weight
                for(int w=i-1;w<i+2;w++)
                    for(int h=j-1;h<j+2;h++){

                        if(w<0||w>image.getWidth()-1) continue;
                        if(h<0||h>image.getHeight()-1) continue;

                        tab3[i-w+1][j-h+1]
                                =table[w][h];
                    }

               int weight=calculateWeight(tab3);

                //weight calculated

                if(deleteArray.contains(weight))
                    table[i][j]=0;
                else table[i][j]=1;


            }else continue;

            }

            return table;
    }

    private int calculateWeight(int [][] tab){
        int sum=0;

        if(tab[0][0]!=0) sum+=128;
        if(tab[0][1]!=0) sum++;
        if(tab[0][2]!=0) sum+=2;
        if(tab[1][2]!=0) sum+=4;
        if(tab[2][2]!=0) sum+=8;
        if(tab[2][1]!=0) sum+=16;
        if(tab[2][0]!=0) sum+=32;
        if(tab[1][0]!=0) sum+=64;


        return sum;

    }

    private int[][] delete4(int [][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){
            if(table[i][j]==4)
                table[i][j]=0;
            }

            return table;
    }

    private int[][] stickingNeighboursTo4(int[][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int[][] tab3=new int[3][3];

            if(table[i][j]!=0&&table[i][j]!=1)
            {
                for(int w=i-1;w<i+2;w++)
                    for(int h=j-1;h<j+2;h++){

                        if(w<0||w>image.getWidth()-1) continue;
                        if(h<0||h>image.getHeight()-1) continue;

                    tab3[i-w+1][j-h+1]
                            =table[w][h];
                    }

                    int sum=0;

                if(tab3[0][0]!=0&&tab3[0][1]!=0) sum++;
                if(tab3[0][1]!=0&&tab3[0][2]!=0) sum++;
                if(tab3[0][2]!=0&&tab3[1][2]!=0) sum++;
                if(tab3[1][2]!=0&&tab3[2][2]!=0) sum++;
                if(tab3[2][2]!=0&&tab3[2][1]!=0) sum++;
                if(tab3[2][1]!=0&&tab3[2][0]!=0) sum++;
                if(tab3[2][0]!=0&&tab3[1][0]!=0) sum++;
                if(tab3[1][0]!=0&&tab3[0][0]!=0) sum++;

                if(sum==1||sum==2||sum==3)
                    table[i][j]=4;

            }

            }

            return table;
    }

    private int[][] edges1to2(int [][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

            int sum=0;

            if(table[i][j]==1)
            for(int w=i-1;w<i+2;w++)
                for(int h=j-1;h<j+2;h++){
                if(w<0||w>image.getWidth()-1) continue;
                if(h<0||h>image.getHeight()-1) continue;

                if(table[w][h]==0)
                    sum++;

                }

                if(sum>1) table[i][j]=2;
            if(sum==1) table[i][j]=3;

            }
            return table;
    }

    private HashSet<Integer> to_removehs(){
        HashSet<Integer> hashSet=new HashSet<Integer>();

        int[] to_remove=to_remove();

        for(int i=0;i<to_remove.length;i++)
            hashSet.add(to_remove[i]);

        return hashSet;
    }


    private int[] to_remove(){
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

    private int[][] getNewTable(int[][] table){
        int[][] tab=new int[image.getWidth()][image.getHeight()];

        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
                tab[i][j]=table[i][j];

        return tab;
    }

    private void update(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
            {
                if(table[i][j]==0)
                    this.image.setRGB(i,j,Color.WHITE.getRGB());
               else if(table[i][j]==1) this.image.setRGB(i,j,Color.BLACK.getRGB());
               else if(table[i][j]==2||table[i][j]==3) this.image.setRGB(i,j,Color.RED.getRGB());
               else if(table[i][j]==4) this.image.setRGB(i,j,Color.GREEN.getRGB());

                }
            }
    }

