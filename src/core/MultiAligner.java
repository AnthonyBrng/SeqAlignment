package core;

import structures.Record;
import structures.Sequence;

import java.util.List;

/**
 * Created by anthony on 07.02.17.
 */
public class MultiAligner extends Aligner
{


    /**
     *
     * @param gapPenalty
     * @param sequences
     * @param sequence
     * @param scoreTablePath
     */
    public MultiAligner(double gapPenalty, List<Sequence> sequences, Sequence sequence, String scoreTablePath)
    {
        super(gapPenalty, scoreTablePath);
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
