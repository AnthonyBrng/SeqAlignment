package core;
import structures.Sequence;

/**
 * Created by anthony on 11.01.17.
 */
public class LocalAligner extends Aligner
{

    /**
     *
     * @param gapPenalty
     * */
    public LocalAligner(double gapPenalty, Sequence sequence1, Sequence sequence2, String scoreTablePath)
    {
        super(gapPenalty,  sequence1, sequence2, scoreTablePath);
    }


    /**
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public double cellValue(int x, int y)
    {
        return 0;
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
    public void initTable()
    {

    }

}
