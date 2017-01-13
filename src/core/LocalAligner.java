package core;
import structures.Record;
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
    public Record cellValue(int row, int col)
    {

        double highest ;
        Record highestPrev ;

        double diagonal = this.table.getDiag(row,col).getData();
        double left     = this.table.getLeft(row,col).getData();
        double top      = this.table.getTop(row,col).getData();

        String character1 =  sequence1.getSequence().get(row-1).toString() ;
        String character2 =  sequence2.getSequence().get(col-1).toString() ;

        double score    = this.scoreTable.getScore(character1, character2) ;  // matchfactor


        highest = diagonal + score ;
        highestPrev = this.table.get(row-1, col-1) ;

        if((left+getGapPenalty()) > highest)
        {
            highest = left + getGapPenalty();
            highestPrev = this.table.get(row, col-1) ;
        }

        if((top + getGapPenalty()) > highest)
        {
            highest = top +getGapPenalty() ;
            highestPrev = this.table.get(row-1, col) ;
        }
        if(0 > highest)
        {
            highest = 0;
            highestPrev = null;

        }


        return new Record(highest, highestPrev);
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

        for(int i = 0; i < table.getColCount(); i++) {
            table.set(0,i, new Record (0));
        }
        for(int i = 0; i < table.getRowCount(); i++) {
            table.set(i, 0, new Record (0));
        }

    }

}
