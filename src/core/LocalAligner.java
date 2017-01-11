package core;

import structures.Alignment;

/**
 * Created by anthony on 11.01.17.
 */
public class LocalAligner extends Aligner
{

    /**
     *
     * @param gapPenalty
     * */
    public LocalAligner(double gapPenalty, int seqLen1, int seqLen2, String scoreTablePath)
    {
        super(gapPenalty, seqLen1, seqLen2, scoreTablePath);
    }


    /**
     *
     * @return
     */
    @Override
    public void traceBack()
    {
    }
    /**
     *
     */
    @Override
    public void fillTable()
    {
    }


}
