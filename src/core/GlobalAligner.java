package core;



/**
 * Created by anthony on 11.01.17.
 */
public class GlobalAligner extends Aligner
{


    /**
     *
     * @param gapPenalty
     */
    public GlobalAligner(double gapPenalty, int seqLen1, int seqLen2, String scoreTablePath)
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
