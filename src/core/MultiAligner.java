package core;

import structures.Record;
import structures.Sequence;


import java.util.ArrayList;


/**
 * Created by anthony on 07.02.17.
 */
public class MultiAligner extends Aligner
{


    private ArrayList<Sequence> sequences = new ArrayList<>();
    private Sequence seqToAdd ;

    /**
     *
     * @param gapPenalty
     * @param sequences
     * @param sequence
     * @param scoreTablePath
     */
    public MultiAligner(double gapPenalty, ArrayList<Sequence> sequences, Sequence sequence, String scoreTablePath)
    {
        super(gapPenalty, scoreTablePath);
        this.sequences = sequences ;
        this.seqToAdd = sequence ;
    }


    @Override
    public Record cellValue(int x, int y)
    {
        return null;
    }

    @Override
    public void findTraceBackStart()
    {

    }

    @Override
    public void initTable()
    {

    }
}
