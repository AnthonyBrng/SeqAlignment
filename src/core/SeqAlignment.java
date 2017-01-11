package core;

import java.nio.file.Path;

/**
 * Created by anthony on 11.01.17.
 */
public class SeqAlignment
{



    private boolean isGlobal;
    private String file ;
    private Aligner aligner ;

    private double gapPenalty;



    public void main(String[] args)
    {



        // TODO parse cmd args




        if(isGlobal)
            aligner = new GlobalAligner(gapPenalty,null, null, "") ;
        else
            aligner = new LocalAligner(gapPenalty,null, null, "") ;


    }






}
