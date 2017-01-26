package core;


import structures.Record;
import structures.Sequence;

import java.util.ArrayList;

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


    /**
     *
     * @param row
     * @param col
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


        return new Record(highest, highestPrev);
    }


    /**
     *
     * @return
     */
    @Override
    public void findTraceBackStart()
    {

        this.ptr1 = this.table.getRowCount() - 1;
        this.ptr2 = this.table.getColCount() - 1 ;

    }


    /**
     *
     */
    @Override
    public void initTable()
    {

       for(int i = 0; i < table.getColCount(); i++)
       {
           if(i!=0)
               table.set(0,i, new Record (i * getGapPenalty(), table.get(0,i-1) ));
           else
               table.set(0,i, new Record (i * getGapPenalty()));
       }

       for(int i = 0; i < table.getRowCount(); i++)
       {
           if(i!=0)
               table.set(i, 0, new Record (i * getGapPenalty(), table.get(i-1 , 0)));

       }



    }

}
