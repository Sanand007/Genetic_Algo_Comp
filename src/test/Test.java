  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author Smit Anand
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    //private static final int TIMER_DELAY = 10;
    String filename="outputfile_new.txt";
    int r1=1,r2=3,r3=5,r4=7;
    int[] organism = new int[30];
    int[] level = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    String[] bin_val = {"00000","00001","00010","00011","00100","00101","00110","00111","01000","01001","01010","01011","01100"
   ,"01101","01110","01111","10000","10001","10010","10011"};
    String[][] chromosome=new String[5][30];
    int[][] fitness=new int[5][30];
    float[] percent_fitness= new float[30];
    float[] percent_anger= new float[30];
    float[] percent_shape= new float[30];
    float[] percent_color= new float[30];
    float r_parent1,r_parent2,r_parent3,r_parent4;
    int anger,shape,color;
    int[] a=new int[30];
    int[] s=new int[30];
    int[] c=new int[30];
    int count_roulete=0,count_tour=0,count_hybrid=0,count_rank=0;
    
    
    public int ffitness(int a,int b,int c,int d,int e)
    {
        String[][] s1=new String[4][15];
        s1[0]=chromosome[a][b].split("");
        s1[1]=chromosome[a][c].split("");
        s1[2]=chromosome[a][d].split("");
        s1[3]=chromosome[a][e].split("");
        String[] s=new String[12];
        int j1=0;int[] total=new int[4];int m=0;
        for(int i=0;i<9;i+=3)
        {
            s[i]="";s[i+1]="";s[i+2]="";
            for(int k=0;k<15;k++)
            {
                if(k<5)
                    s[i]=s[i]+s1[j1][k];
                else if(k<10)
                    s[i+1]=s[i+1]+s1[j1][k];
                else
                    s[i+2]=s[i+2]+s1[j1][k];
            }
            j1+=1;
            int fit=1;int k;int count=0;
            for(int j=i;j<i+3;j++)
            {
                for(k=0;k<20;k++)
                {
                    if(s[j].equals(bin_val[k]))
                        break;
                }
                fit=fit*level[k];
                if(level[k]>=18)
                    count+=1;
            }
            total[m]=fit;
            m+=1;
            if(count==3)
                return 1;
        }
        fitness[a][b]=total[0];
        fitness[a][c]=total[1];
        fitness[a][d]=total[2];
        //fitness[a][e]=total[3];
        return 0;
    }
    
    
    public int mutation(int n)
    {
        int n1=n;
        String[][] s1=new String[4][15];
        s1[0]=chromosome[n][r1].split("");
        s1[1]=chromosome[n][r2].split("");
        s1[2]=chromosome[n][r3].split("");
        //s1[3]=chromosome[n][r4].split("");
        String[] s=new String[12];
        int indic=0,j1=0;
        for(int i=0;i<9;i+=3)
        {
            s[i]="";s[i+1]="";s[i+2]="";
            for(int k=0;k<15;k++)
            {
                if(k<5)
                    s[i]=s[i]+s1[j1][k];
                else if(k<10)
                    s[i+1]=s[i+1]+s1[j1][k];
                else
                    s[i+2]=s[i+2]+s1[j1][k];
                }
            j1+=1;
            //System.out.println(s[i]+"  "+s[i+1]+"  "+s[i+2]);
            for(int j=i;j<i+3;j++)
            {
                do 
                {
                    for(int k=0;k<20;k++)
                    {
                        if(s[j].equals(bin_val[k]))
                        {
                            indic=1;
                            break;
                        }
                    }
                    //System.out.println(indic);
                    char[] ch=s[j].toCharArray();
                    if(indic==0)
                    {
                        //System.out.println(indic);
                        int rand=(int)(Math.random()*4);
                        if(ch[rand]=='0')
                            ch[rand]='1';
                        else
                            ch[rand]='0';
                        s[j]=String.valueOf(ch);
                    }
                }while(indic==0);
                indic=0;
            }
        }
        chromosome[n][r1]=s[0]+s[1]+s[2];
        chromosome[n][r2]=s[3]+s[4]+s[5];
        chromosome[n][r3]=s[6]+s[7]+s[8];
        //chromosome[n][r4]=s[9]+s[10]+s[11];
        return ffitness(n1,r1,r2,r3,r4);
    }
    
    public int crossover(int n)
    {
        int n1=n;
        int x,y;
        x=(int)(Math.random()*15);
        //System.out.println(x);
        do{
            y=(int)(Math.random()*15);
        }while(x==y);
        //System.out.println(y);
        
        int lower,upper;
        if(x<y)
        {
            lower=x;
            upper=y;
        }
        else
        {
            lower=y;
            upper=x;
        }
        String[] s1=chromosome[n][r1].split("");
        String[] s2=chromosome[n][r2].split("");
        String[] s3=chromosome[n][r3].split("");
        //String[] s4=chromosome[n][r4].split("");
        String sr1="",sr2="",sr3="",sr4="";
        for(int i=0;i<15;i++)
        {
            if(i<lower || i>upper)
            {
                sr1=sr1+s1[i];
                sr2=sr2+s2[i];
                sr3=sr3+s3[i];
                //sr4=sr4+s4[i];
            }
            else
            {
                sr1=sr1+s2[i];
                sr2=sr2+s3[i];
                sr3=sr3+s1[i];
                //sr4=sr4+s1[i];
            }
        }
        chromosome[n][r1]=sr1;
        chromosome[n][r2]=sr2;
        chromosome[n][r3]=sr3;
        //chromosome[n][r4]=sr4;
        return mutation(n1);
    }
    
    public int selection_hybrid_new(int n)
    {
        Vector v=new Vector();
        for(int i=0;i<30;i++)
            v.add(new Integer(fitness[n][i]));
        Collections.sort(v);
        Vector target=new Vector();
        for(int i=0, j=29; i<15; i++,j--)
        {
            target.add(v.get(i));
            target.add(v.get(j));
        }
        int random=(int)(Math.random()*28)+1;
        r1=random-1;r2=random;r3=random+1;
        return crossover(n);
    }
    
    public int selection_hybrid_first3(int n)
    {
        int random=(int)(Math.random()*28);
        r1=random;r2=random+1;r3=random+2;
        return crossover(n);
    }
    
    public int selection_hybrid_rank(int n)
    {
        int n1=n;
        Vector v_anger=new Vector();
        Vector v_shape=new Vector();
        Vector v_color=new Vector();
        for(int i=0;i<30;i++)
            v_anger.add(new Integer(level[a[i]]));
        for(int i=0;i<30;i++)
            v_shape.add(new Integer(level[s[i]]));
        for(int i=0;i<30;i++)
            v_color.add(new Integer(level[c[i]]));
        Collections.sort(v_shape);
        Collections.sort(v_anger);
        Collections.sort(v_color);
        Vector cumul=new Vector();
        int sum=1;
        cumul.add(new Integer(1));
        for(int i=1;i<30;i++)
        {
            cumul.add(new Integer((int) (cumul.get(i-1))) + i + 1);
            sum+=(int)(cumul.get(i));
        }
        int random=(int)(Math.random()*sum);
        for(int i=0;i<30;i++)
        {
            int a=(int)(cumul.get(i));
            if(random>a)
                random-=a;
            else {
                for(int j=0;j<30;j++)
                {
                    int g=(Integer)v_shape.elementAt(i);
                    if(fitness[n][i]==g)
                        r1=j;
                }
                break;
            }
        }
        do {
            random=(int)(Math.random()*sum);
            for(int i=0;i<30;i++)
            {
                int a=(int)(cumul.get(i));
                if(random>a)
                    random-=a;
                else {
                    for(int j=0;j<30;j++)
                    {
                        int g=(Integer)v_shape.elementAt(i);
                        if(fitness[n][i]==g)
                            r2=j;
                    }
                    break;
                }
            }
        }while(r1==r2);
        
        do {
            random=(int)(Math.random()*sum);
            for(int i=0;i<30;i++)
            {
                int a=(int)(cumul.get(i));
                if(random>a)
                    random-=a;
                else {
                    for(int j=0;j<30;j++)
                    {
                        int g=(Integer)v_shape.elementAt(i);
                        if(fitness[n][i]==g)
                            r3=j;
                    }
                    break;
                }
            }
        }while(r1==r3 || r2==r3);
        return crossover(n1);
    }
    
    public int selection_hybrid(int n)
    {
        int n1=n;
        int sum_fitness=0,x,v=0,sum_anger=0,sum_shape=0,sum_color=0;
        for(int i=0;i<30;i++)
        {
            sum_fitness=sum_fitness+fitness[n][i];
            sum_anger+=level[a[i]];
            sum_shape+=level[s[i]];
            sum_color+=level[c[i]];
        }
        for(int i=0;i<30;i++)
        {
            percent_fitness[i]=(float)((float)fitness[n][i]/(float)sum_fitness)*(float)100;
            percent_anger[i]=(float)((float)level[a[i]]/(float)sum_anger)*(float)100;
            percent_color[i]=(float)((float)level[c[i]]/(float)sum_color)*(float)100;
            percent_shape[i]=(float)((float)level[s[i]]/(float)sum_shape)*(float)100;
        }
        float[] cumulative_fitness=new float[30];
        float[] cumulative_anger=new float[30];
        float[] cumulative_shape=new float[30];
        float[] cumulative_color=new float[30];
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<=i;j++)
            {
                cumulative_fitness[i]+=percent_fitness[j];
                cumulative_anger[i]+=percent_anger[j];
                cumulative_shape[i]+=percent_shape[j];
                cumulative_color[i]+=percent_color[j];
            }
        }
        r_parent1=(float)(Math.random()*100);
        for(r1=0;r1<30;r1++)
        {
            if(r_parent1<=cumulative_shape[r1])
                break;
        }
        do{
        r_parent2=(float)(Math.random()*100);
        for(r2=0;r2<30;r2++)
        {
            if(r_parent2<=cumulative_anger[r2])
                break;
        }
        }while(r1==r2);
        do{
        r_parent2=(float)(Math.random()*100);
        for(r3=0;r3<30;r3++)
        {
            if(r_parent2<=cumulative_color[r3])
                break;
        }
        }while(r1==r3 || r2==r3);
        /*do{
        r_parent2=(float)(Math.random()*100);
        for(r4=0;r4<30;r4++)
        {
            if(r_parent2<=cumulative_color[r4])
                break;
        }
        }while(r1==r4 || r2==r4 || r3==r4);*/
        return crossover(n1);
    }
    
    public int selection_roulete(int n)
    {
        int n1=n;
        int sum_fitness=0,x,v=0,sum_anger,sum_shape,sum_color;
        for(int i=0;i<30;i++)
        {
            sum_fitness=sum_fitness+fitness[n][i];
        }
        for(int i=0;i<30;i++)
        {
            percent_fitness[i]=(float)((float)fitness[n][i]/(float)sum_fitness)*(float)100;
            //x=percent_fitness[i]-r_parent;
            //System.out.println(percent_fitness[i]);
        }
        //sort(percent_fitness);
        float[] cumulative=new float[30];
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<=i;j++)
            {
                cumulative[i]+=percent_fitness[j];
            }
        }
        
        float r_parent1=(float)(Math.random()*100);
        for(r1=0;r1<30;r1++)
        {
            if(r_parent1<=cumulative[r1])
                break;
        }
        float r_parent2,r_parent3,r_parent4;
        do{
        r_parent2=(float)(Math.random()*100);
        for(r2=0;r2<30;r2++)
        {
            if(r_parent2<=cumulative[r2])
                break;
        }
        }while(r1==r2);
        
        do{
        r_parent3=(float)(Math.random()*100);
        for(r3=0;r3<30;r3++)
        {
            if(r_parent3<=cumulative[r3])
                break;
        }
        }while(r1==r3 || r2==r3);
        
        /*do{
        r_parent4=(float)(Math.random()*100);
        for(r4=0;r4<30;r4++)
        {
            if(r_parent4<=cumulative[r4])
                break;
        }
        }while(r1==r4 || r2==r4 || r3==r4);*/
        //parent 1 = chromosome[r1-1];
        //parent 2 = chromosome[r2-1];
       // System.out.println(chromosome[r1]+"  "+chromosome[r2]+"  "+chromosome[r3]);
        return crossover(n1);
    }
    
    public int tournament(int n)
    {
        int n1=n;
        int x=(int)(Math.random()*30);
        int y;
        do {
            y=(int)(Math.random()*30);
        }while(x==y);
        if(fitness[n][x]>fitness[n][y])
            r1=x;
        else
            r1=y;
        do {
            x=(int)(Math.random()*30);
        }while(x==r1);
        do {
            y=(int)(Math.random()*30);
        }while(x==y || r1==y);
        if(fitness[n][x]>fitness[n][y])
            r2=x;
        else
            r2=y;
        do {
            x=(int)(Math.random()*30);
        }while(x==r1 || x==r2);
        do {
            y=(int)(Math.random()*30);
        }while(x==y || r1==y || r2==y);
        if(fitness[n][x]>fitness[n][y])
            r3=x;
        else
            r3=y;
        /*do {
            x=(int)(Math.random()*30);
        }while(x==r1 || x==r2 || x==r3);
        do {
            y=(int)(Math.random()*30);
        }while(x==y || r1==y || r2==y || r3==y);
        if(fitness[n][x]>fitness[n][y])
            r4=x;
        else
            r4=y;*/
        return crossover(n1);
    }
    
    public int selection_rank(int n)
    {
        int n1=n;
        Vector fitness_rank=new Vector();
        for(int i=0;i<30;i++)
            fitness_rank.add(new Integer(fitness[n][i]));
        Collections.sort(fitness_rank);
        Vector cumul=new Vector();
        int sum=1;
        cumul.add(new Integer(1));
        for(int i=1;i<30;i++)
        {
            cumul.add(new Integer((int) (cumul.get(i-1))) + i + 1);
            sum+=(int)(cumul.get(i));
        }
        int random=(int)(Math.random()*sum);
        for(int i=0;i<30;i++)
        {
            int a=(int)(cumul.get(i));
            if(random>a)
                random-=a;
            else {
                for(int j=0;j<30;j++)
                {
                    int g=(Integer)fitness_rank.elementAt(i);
                    if(fitness[n][i]==g)
                        r1=j;
                }
                break;
            }
        }
        do {
        for(int i=0;i<30;i++)
        {
            int a=(int)(cumul.get(i));
            if(random>a)
                random-=a;
            else {
                for(int j=0;j<30;j++)
                {
                    int g=(Integer)fitness_rank.elementAt(i);
                    if(fitness[n][i]==g)
                        r2=j;
                }
                break;
            }
        }}while(r2==r1);
        
        do {
        for(int i=0;i<30;i++)
        {
            int a=(int)(cumul.get(i));
            if(random>a)
                random-=a;
            else {
                for(int j=0;j<30;j++)
                {
                    int g=(Integer)fitness_rank.elementAt(i);
                    if(fitness[n][i]==g)
                        r3=j;
                }
                break;
            }
        }}while(r3==r1 || r3==r2);
        
        /*do {
        for(int i=0;i<30;i++)
        {
            int a=(int)(cumul.get(i));
            if(random>a)
                random-=a;
            else {
                r4=i;
                break;
            }
        }}while(r4==r1 || r4==r2 || r4==r3);*/
        return crossover(n1);
    }
    
   
    
    public void initial()
    {
        String s11;
        try {
            File file =new File("outputfile_new.txt");
            if(!file.exists()){
    			file.createNewFile();
    		}
            FileWriter fileWritter = new FileWriter(file.getName(),true);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write("Initial Fitness Value");
            bufferWritter.newLine();
            bufferWritter.newLine();
            for(int i=0;i<30;i++)
            {
                int r_anger=(int) (Math.random()*20);
                a[i]=r_anger;
                int r_shape=(int) (Math.random()*20);
                s[i]=r_shape;
                int r_color=(int) (Math.random()*20);
                c[i]=r_color;
                chromosome[0][i]=bin_val[r_anger]+bin_val[r_shape]+bin_val[r_color];
                fitness[0][i]=level[r_anger]*level[r_shape]*level[r_color];
                s11=String.valueOf(fitness[0][i]);
                bufferWritter.write(s11);
                bufferWritter.write("  ");
            }
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.newLine();
            for(int i=1;i<5;i++)
            {
                for(int j=0;j<30;j++) {
                chromosome[i][j]=chromosome[0][j];
                fitness[i][j]=fitness[0][j];
                }
            }
            int res1,res2,res3,res4,res5;
            do {
                count_rank++;
                res1=selection_rank(0);
                count_roulete++;
                res2=selection_roulete(1);
                count_tour++;
                res3=tournament(2);
                count_hybrid++;
                res4=selection_hybrid_new(3);
            }while(res1==0 && res2==0 && res3==0 && res4==0);
            Vector v_rank=new Vector();
            Vector v_roulete=new Vector();
            Vector v_tour=new Vector();
            Vector v_hybrid=new Vector();
            for(int i=0;i<30;i++)
                v_rank.add(new Integer(fitness[0][i]));
            for(int i=0;i<30;i++)
                v_roulete.add(new Integer(fitness[1][i]));
            for(int i=0;i<30;i++)
                v_tour.add(new Integer(fitness[2][i]));
            for(int i=0;i<30;i++)
                v_hybrid.add(new Integer(fitness[3][i]));
            Collections.sort(v_rank);
            Collections.sort(v_tour);
            Collections.sort(v_roulete);
            Collections.sort(v_hybrid);
            int c_rank=0,c_roulete=0,c_tour=0,c_hybrid=0;
            for(int i=0;i<30;i++)
                c_rank+=fitness[0][i];
            for(int i=0;i<30;i++)
                c_roulete+=fitness[1][i];
            for(int i=0;i<30;i++)
                c_tour+=fitness[2][i];
            for(int i=0;i<30;i++)
                c_hybrid+=fitness[3][i];
            bufferWritter.write("NUMBER OF STEPS");
            bufferWritter.newLine();
            s11=String.valueOf(count_rank);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.write("RANK OUTPUT");
            bufferWritter.newLine();
            s11=String.valueOf(v_rank);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            s11=String.valueOf(c_rank);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.write("ROULETE OUTPUT");
            bufferWritter.newLine();
            s11=String.valueOf(v_roulete);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            s11=String.valueOf(c_roulete);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.write("TOURNAMENT OUTPUT");
            bufferWritter.newLine();
            s11=String.valueOf(v_tour);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            s11=String.valueOf(c_tour);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.write("HYBRID OUTPUT");
            bufferWritter.newLine();
            s11=String.valueOf(v_hybrid);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            s11=String.valueOf(c_hybrid);
            bufferWritter.write(s11);
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.newLine();
            bufferWritter.close();
            System.out.println(c_rank+"\n"+c_roulete+"\n"+c_tour+"\n"+c_hybrid);
        }
        catch(IOException e){
    		e.printStackTrace();
    	}
        
        
    }
    public static void main(String[] args) {
        Test a=new Test();
        a.initial();
    }
}
