/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author ToshibaPC
 */
public class MetaGenomic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        read_file(args[0]);
    }
    
    String chemin = new String();

   public static void read_file(String file_name2) throws IOException
   {
        String file_name=file_name2;        
        BufferedReader lecteurAvecBuffer = null;
        String ligne;

        //reference table (tertanucléotide)
        int num_tetra=256;
        String[] tab_tetra=new String[num_tetra];
        char[] Tetra;
        int s=0;
        //table of all possible tetramer
        for(int i=0;i<4;i++)
        {
            Tetra=new char[4];
            if(i==0) Tetra[0]='A';
            if(i==1) Tetra[0]='T';
            if(i==2) Tetra[0]='C';
            if(i==3) Tetra[0]='G';
            for(int j=0;j<4;j++)
            {   
                if(j==0) Tetra[1]='A';
                if(j==1) Tetra[1]='T';
                if(j==2) Tetra[1]='C';
                if(j==3) Tetra[1]='G';
                for(int k=0;k<4;k++)
                {   
                    if(k==0) Tetra[2]='A';
                    if(k==1) Tetra[2]='T';
                    if(k==2) Tetra[2]='C';
                    if(k==3) Tetra[2]='G';
                    for(int l=0;l<4;l++)
                    {   
                        if(l==0) Tetra[3]='A';
                        if(l==1) Tetra[3]='T';
                        if(l==2) Tetra[3]='C';
                        if(l==3) Tetra[3]='G';
                        tab_tetra[s]=String.valueOf(Tetra);
                        s++;
                    }
                }
            }    
        }
        
        for(int i=0;i<tab_tetra.length; i++)
        {
            System.out.print(tab_tetra[i]+"  ");
        }
        
        int i=0;
        String tetra_help=new String();
        
        int row_nbr=10;
        try
        {
            //read of file
            lecteurAvecBuffer = new BufferedReader(new FileReader(file_name));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("Erreur d'ouverture");
        }
        
        row_nbr=0;

        while ((ligne = lecteurAvecBuffer.readLine()) != null )
        {
                //search line number in the file
                 row_nbr++;
        }
        
        int seq_nbr=row_nbr/2;
       // System.out.println(" seq_nbr  "+seq_nbr+"   row_nbr  "+row_nbr);
        int[][] mat_f_tetra=new int[seq_nbr][num_tetra];
         
        for(int p=0;p<seq_nbr;p++)
        {
            //initialisation of tetra frequency matrix
            for(int o=0;o<tab_tetra.length; o++)
            {
               mat_f_tetra[p][o]=0;
            }
        }
        
        int ss=0;//seq actuel
        boolean stop=false;
        i=0;
        try
        {
            lecteurAvecBuffer = new BufferedReader(new FileReader(file_name));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("Erreur d'ouverture");
        }
        int comptage=0;
        int l=0;
        i=0;
        String tetra1,tetra2,tetra3,tetra4;
        while ((ligne = lecteurAvecBuffer.readLine()) != null)
        {
           
           //System.out.println(ligne);
            //for each line (sequence)
            if((l%2)==0)
            {
                //name of sequence
                //recupérer les information sur la séquence nom....
               // System.out.println("regarde je suis la é je ne bouge pas moi");
            } 
            else
            {   
                
                //the sequence
            //    System.out.println("length of rows is "+ligne.length());
                //tester N?
                for(i=0;i<tab_tetra.length;i++)
                {
                    int h=0; 
                   // int count_tetra=0;
                    tetra1=tab_tetra[i];
                    tetra2=tab_tetra[i];
                    tetra3=tab_tetra[i];
                    tetra4=tab_tetra[i];
                    //to eliminate the N cases
                    tetra1=replaceCharAt(tetra1,0,'N');
                    tetra2=replaceCharAt(tetra1,1,'N');
                    tetra3=replaceCharAt(tetra1,2,'N');
                    tetra4=replaceCharAt(tetra1,3,'N');
                    //if the tetra existe on the sequence do
                    if(ligne.contains(tab_tetra[i]) || ligne.contains(tetra1)|| ligne.contains(tetra2)|| ligne.contains(tetra3)|| ligne.contains(tetra4))
                    {
                        for(h=0;(h+4)<ligne.length();h++)
                        {
                            //compute de frenquency of the existed tetra
                            tetra_help=ligne.substring(h, h+4);
                            if(tetra_help.equalsIgnoreCase(tab_tetra[i]) || tetra_help.equalsIgnoreCase(tetra1)|| tetra_help.equalsIgnoreCase(tetra2)|| tetra_help.equalsIgnoreCase(tetra3)|| tetra_help.equalsIgnoreCase(tetra4))
                            {
                               // count_tetra++;
                                //update the matrix, when we find a tetra
                                mat_f_tetra[ss][i]++;
                            }
                        }
                    }
                }
                ss++;//sequence
            }
           l++;//line
        }
        System.out.println("comptage : "+comptage);
        lecteurAvecBuffer.close();
     /*
        for(int p=0;p<seq_nbr;p++)
        {
            for(int o=0;o<tab_tetra.length; o++)
            {
                System.out.print(mat_f_tetra[p][o]+"  ");
            }
            System.out.println();
        }
       */
        String outputfile="presMeta.data";//ùùùùùùùùùùùùùù je reviens sur ça
        
        File file=new File(outputfile);
        Writer output = new BufferedWriter(new FileWriter(file));
    
        //prepare the first line
        String line=new String();
        line="Sequence ID  ";
        for( i=0;i<tab_tetra.length; i++)
        {
            line+=tab_tetra[i]+"  ";
           // System.out.println(tab_tetra[i]);
        }
        line+="\n";
        //write the first line
        output.write(line);
        
        for(int j=0;j<seq_nbr;j++)
        {
            line="";
            line+=(j+1)+"  ";
            for(int p=0;p<num_tetra;p++)
            {
                line+=mat_f_tetra[j][p]+"  ";
            }
            line+="\n";
            output.write(line);
        }
        output.close();
    }
    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0,pos) + c + s.substring(pos+1);
    }
}
