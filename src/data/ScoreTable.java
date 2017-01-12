package data;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**creates substitutions Matrix for sequence alignment of proteins
 * Created by leonard on 11.01.2017.
 */
public class ScoreTable {

     static double matrix [][] = new double [19] [19];


    /**
     * Constructor.
     *
     * @param dateipfad
     */
        public ScoreTable(String dateipfad){
        String pfad = dateipfad;

        }

    public static void main (String [] args){
        String path = "D:/Dokumente/Projekt/BLOSUM62";
        ReadData(path);
    }

    /**
     * reads score data into list
     * @param path  path of file with score information
     * @return score information in String List
     */
    public static List <String> ReadData(String path) {
        String[] parts = path.split("/");
        String newpath = "";
        for(int i = 0; i < parts.length-1; i++){
            newpath += parts[i] + "/";
        }
        Path datapath = Paths.get(newpath, parts[parts.length-1]);
        Charset charset = Charset.forName("ISO-8859-1");
        List <String> rawdata = null;
        try {
            rawdata = Files.readAllLines(datapath, charset);
        } catch (IOException e) {
            System.out.println("Substitutionsmatrix ist fehlerhaft");
        }
        fillTable(rawdata);
        return rawdata;
    }


    /**
     * fills table with data
     * @param rawdata
     * @return substitution matrix
     */
   public static Double [][] fillTable (List <String> rawdata){
       String [] proteineWerte;
        for(int i = 0; i < rawdata.size(); i++){
            for(int j = 0; j < 19; j++){
                for(int k = 0; k < 19; k++){
                  proteineWerte = rawdata.get(i).split("\\s+");
                  System.out.println(proteineWerte[3]);
                   int value = Integer.parseInt(proteineWerte[3]);
                   matrix [k] [j] = value;


               }
            }


        }
       System.out.println(rawdata);
       System.out.println(matrix [18] [16]);

       return null;
    }

    /*public double getScore(String c1, String c2){



        return null;
    }*/
}




