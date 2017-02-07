package core;

import structures.Sequence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Created by anthony on 11.01.17.
 */
public class SeqAlignment
{



    private static Aligner aligner ;
    private static double gapPenalty;
    private static boolean isGlobal;

    private static String fastaFile ;




    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO parse cmd args
        if(args.length != 2)
            return ;


        ReadData(args[0]);

        isGlobal = args[1].equals("true") ;

        gapPenalty = -1 ;

        /*
            Read Input File
         */
        FastaParser parser = new FastaParser();
        parser.setValue(fastaFile) ;

        if(parser.parse())
        {
            Sequence seq1 = parser.getSequences().get(0) ;
            Sequence seq2 = parser.getSequences().get(1) ;

            if(isGlobal)
                aligner = new GlobalAligner(gapPenalty, seq1, seq2, "BLOSUM62");
            else
                aligner = new LocalAligner(gapPenalty, seq1, seq2, "BLOSUM62");



            aligner.align();

            System.out.println(aligner.alignment);



        }
        else
        {
            System.out.println("Parse ist Fehlgeschlagen!") ;
            parser.print_Err();
            return ;
        }





    }



    /**
     * reads score data into list
     * @param path  path of file with score information
     */
    public static void ReadData(String path)
    {

        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Charset charset = Charset.forName("ISO-8859-1");
        fastaFile = new String(encoded, charset);
    }





}
