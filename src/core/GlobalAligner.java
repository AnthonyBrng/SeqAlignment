package core;


import structures.Record;
import structures.Sequence;

/**
 * Created by anthony on 11.01.17.
 */
public class GlobalAligner extends Aligner
{


    /**
     *
     * @param gapPenalty
     */
    public GlobalAligner(double gapPenalty, Sequence sequence1, Sequence sequence2, String scoreTablePath)
    {
        super(gapPenalty,  sequence1, sequence2, scoreTablePath);
    }



    @Override
    public Record cellValue(int x, int y)
    {
        return null;
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

       for(int i = 0; i < table.getRowCount(); i++) {
           table.set(0,i, new Record (-1 * i * getGapPenalty()));
       }
       for(int i = 1; i < table.getColCount(); i++) {
           table.set(i, 0, new Record (-1 * i * getGapPenalty()));
       }



    }

}
