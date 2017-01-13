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
            "ATTGACTAGCTCATCAGCATGCACTATAGCGGCATCTCAGCCCATAGATTAGCTA\n" +
            "\n" +"\n" +"\n" +
            ">Zweite Sequence\n" +
            ";weitere Sequence in gleicher Datei\n" +
            ";Außerdem 2 Kommentar zeilen und Sequence zeilen\n" +
            "ATTGACTAGCTCATCAGCATGCACTATAGCGGCA\n" +
            "TCTCAGCCCATAGATTAGCTA";;


    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO parse cmd args

        /*
            Read Input File
         */
        FastaParser parser = new FastaParser();

        parser.setValue(fastaFile) ;

        if(parser.parse())
        {
            System.out.println("Parse war erfoglreich!") ;
            for(Sequence seq : parser.getSequences())
                System.out.println(seq+"\n");
        }
        else
        {
            System.out.println("Parse ist Fehlgeschlagen!") ;
        }

        parser.print_Err();





    }






}
