package core;

import structures.Sequence;

import java.nio.file.Path;

/**
 * Created by anthony on 11.01.17.
 */
public class SeqAlignment
{



    private static Aligner aligner ;
    private static double gapPenalty;
    private static boolean isGlobal;

    private static String fastaFile ="\n" +"\n" +"\n" +
            ">TestSequenz\n" +
            ";Erste testsequenz\n" +
            "ACGTC\n" +
            ">Zweite Sequence\n" +
            ";weitere Sequence in gleicher Datei\n" +
            ";Außerdem 2 Kommentar zeilen und Sequence zeilen\n" +
            "AGTC   cffdse" ;



    private static String testSeq = ">1\n" +
            "VADKA\n" +
            ">2\n" +
            "WHISKY" ;


    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO parse cmd args

        isGlobal = true ;
        gapPenalty = -1 ;

        /*
            Read Input File
         */
        FastaParser parser = new FastaParser();
        parser.setValue(testSeq) ;

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






}
