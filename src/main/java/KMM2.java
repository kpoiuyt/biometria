import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class KMM2 {

    private int[][] table;
    private BufferedImage image;

    public KMM2(BufferedImage image){
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

        for(int i=0;i<10;i++) {
            table = edges1to2(table);
            table = cornersto3(table);
            table = stickingNeighboursTo4(table);
            table = delete4(table);
            table = steps(table, 2);
            table = steps(table, 3);

        }



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

                            //System.out.println("nowa tabela i:"+(i-w+1)+"j:"+(j-h+1));
                           // System.out.println("stara tabela i:"+w+"j:"+h);
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


    private int[][] delete4(int [][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){
                if(table[i][j]==4)
                    table[i][j]=0;
            }

        return table;
    }

    private int calculateWeight(int [][] tab){
        int sum=0;

        if(tab[2][2]!=0) sum+=128;
        if(tab[2][1]!=0) sum++;
        if(tab[2][0]!=0) sum+=2;
        if(tab[1][0]!=0) sum+=4;
        if(tab[0][0]!=0) sum+=8;
        if(tab[0][1]!=0) sum+=16;
        if(tab[0][2]!=0) sum+=32;
        if(tab[1][2]!=0) sum+=64;


        return sum;

    }


    private int[][] stickingNeighboursTo4(int[][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

                int[][] tab3=new int[3][3];

                if(table[i][j]==2)
                {
                    for(int w=i-1;w<i+2;w++)
                        for(int h=j-1;h<j+2;h++){

                            if(w<0||w>image.getWidth()-1) continue;
                            if(h<0||h>image.getHeight()-1) continue;

                            tab3[i-w+1][j-h+1]
                                    =table[w][h];
                        }

                    int sum=calculateWeight(tab3);

                    HashSet<Integer> val=neighboursValuesHs();

                    if(val.contains(sum))
                        table[i][j]=4;



                }

            }

        return table;
    }

    private HashSet<Integer> neighboursValuesHs(){
        HashSet<Integer> hashSet=new HashSet<Integer>();

        int [] neVal=neighboursValues();

        for(int i=0;i<neVal.length;i++)
            hashSet.add(neVal[i]);

        return hashSet;
    }

    private int[] neighboursValues(){
        return new int[]{
                3,6,12,24,48,96,192,129,
                7,14,28,56,112,224,193,131,
                15,30,60,120,240,225,195,135
        };
    }

    private int[][] cornersto3(int [][] table){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++){

                int sum=0;

                if(table[i][j]!=0)
                    for(int w=i-1;w<i+2;w++)
                        for(int h=j-1;h<j+2;h++){
                            if(w<0||w>image.getWidth()-1) continue;
                            if(h<0||h>image.getHeight()-1) continue;

                            if(table[w][h]==0)
                                sum++;

                        }

                if(sum==1) table[i][j]=3;


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

                if(sum>=1) table[i][j]=2;


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


    private void update(){
        for(int i=0;i<image.getWidth();i++)
            for(int j=0;j<image.getHeight();j++)
            {
                if(table[i][j]==0)
                    this.image.setRGB(i,j,Color.WHITE.getRGB());
                else if(table[i][j]==1) this.image.setRGB(i,j,Color.BLACK.getRGB());
                else if(table[i][j]==2) this.image.setRGB(i,j,Color.RED.getRGB());
                else if(table[i][j]==3) this.image.setRGB(i,j,Color.GREEN.getRGB());
                else if(table[i][j]==4) this.image.setRGB(i,j,Color.BLUE.getRGB());

            }
    }
}
