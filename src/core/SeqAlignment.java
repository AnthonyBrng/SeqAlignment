package core;

import structures.Sequence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Starting point for aligning Sequences stored in one fasta-file.
 * Created by anthony
 */
public class SeqAlignment
{
    private static Aligner aligner ;
    private static double gapPenalty;
    private static boolean isGlobal;
    private static String fastaFile ;

    /**
     * Main
     * @param args containing
     */
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("java SeqAlignment [Pfad der fastaDatei] [true/false = gloabl/local Alignment]");
            return;
        }

        ///////////////////////
        // set properties    //
        ///////////////////////
        ReadData(args[0]);
        isGlobal = args[1].equals("true") ;
        gapPenalty = -1 ;

        //////////////
        // Parse    //
        //////////////
        FastaParser parser = new FastaParser();
        parser.setValue(fastaFile) ;

        if(parser.parse())
        {
            Sequence seq1 = parser.getSequences().get(0) ;
            Sequence seq2 = parser.getSequences().get(1) ;

            ///////////////
            // Alignment //
            ///////////////
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
     * function for multialigning
     * @param parser parser, that parses the fastafile, containing all sequence objects
     */
    public static void multiAlign(FastaParser parser)
    {
        ArrayList<Sequence> sequences  = parser.getSequences() ;
        ArrayList<Sequence> alignedSequences  = new ArrayList<>() ;
        Aligner aligner ;

        aligner = new GlobalAligner(gapPenalty, sequences.get(0), sequences.get(1), "BLOSUM62") ;
        aligner.align();
        System.out.println(aligner.alignment);
        for(int i = 2 ; i < sequences.size()-1 ;  i ++)
        {
            aligner = new MultiAligner(gapPenalty, aligner.alignment.getSequences(), sequences.get(i), "BLOSUM62" );
            aligner.align();
        }

        System.out.println(aligner.alignment);
    }



    /**
     * reads fasta-file into a String
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
