package data;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**creates substitutions Matrix for sequence alignment of proteins
 * from BLOSUM62 file
 *
 *@auther leonard
 */
public class ScoreTable {

     static String matrix [][] = new String [20] [20];


    /**
     * Constructor.
     *
     * @param dateipfad
     */
    public ScoreTable(String dateipfad) {
        ReadData(dateipfad);

    }

    /**
     * reads score data into list
     * @param path  path of file with score information
     */
    public void ReadData(String path) {
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
    }


    /**
     * fills matrix with data from file
     * @param rawdata String List of all score information from BLOSUM62 file
     * @return substitution matrix
     */
   public String [][] fillTable (List <String> rawdata) {


       int raw = 0;
        for(int j = 0; j < 20; j++){
            for (int i = 0; i < 20; i++) {

                matrix [j] [i] = rawdata.get(raw);
                raw ++;


            }
        }

       //System.out.println(matrix[19][19]);



       return matrix;

   }

    /**
     * searching for score in substitution matrix
     * @param c1 protein 1
     * @param c2 protein 2
     * @return alignement score of c1 and c2
     */

    public double getScore(String c1, String c2){
       String [] eintrag;
       String fir;
       String sec;
       int val;

       int raw = 0;
       for(int j = 0; j < 20; j++){
           for (int i = 0; i < 20; i++) {
               eintrag = matrix[j][i].split("\\s+");
               fir = eintrag[1];
               sec = eintrag[2];
               val =  Integer.parseInt(eintrag[3]);

                if(c1.equals(fir)){
                    if(c2.equals(sec))
                            return val;

                }

            }
        }
    System.out.println("Eintrag nicht in BLOSUM62 Tabelle gefunden!");
    System.exit(0);
    return 0;

    }
}




