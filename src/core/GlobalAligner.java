package core;


import structures.Record;
import structures.Sequence;

/**
 * Aligner specification for doing a global alignment
 * Created by anthony on 11.01.17
 */
public class GlobalAligner extends Aligner
{


    /**
     * Construtor
     * @param gapPenalty the amount of the gapPenalty
     * @param sequence1 first sequence to align
     * @param sequence2 second sequence to align
     * @param scoreTablePath file path to the score table (i.e BLOSUM62)
     */
    public GlobalAligner(double gapPenalty, Sequence sequence1, Sequence sequence2, String scoreTablePath)
    {
        super(gapPenalty,  sequence1, sequence2, scoreTablePath);
    }


    /**
     * Defines how each table-cell is calculated.
     * @param row current row-ccordinate in the table
     * @param col current col-coordinate in the table
     * @return Record to insert into this specific table cell
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

        /*
         * Compare the highest previous cell entry
         * diagonal / left / top
         */

        // diagonal
        highest = diagonal + score ;
        highestPrev = this.table.get(row-1, col-1) ;

        // left
        if((left+getGapPenalty()) > highest)
        {
            highest = left + getGapPenalty();
            highestPrev = this.table.get(row, col-1) ;
        }

        // top
        if((top + getGapPenalty()) > highest)
        {
            highest = top +getGapPenalty() ;
            highestPrev = this.table.get(row-1, col) ;
        }

        return new Record(highest, highestPrev);
    }


    /**
     * Finds the traceback start
     */
    @Override
    public void findTraceBackStart()
    {
        this.ptr_row = this.table.getRowCount() - 1;
        this.ptr_col = this.table.getColCount() - 1 ;
    }


    /**
     * Initializes the table by
     * table[0][j] = j * gapPenalty
     * table[i][j] = i * gapPenalty
     */
    @Override
    public void initTable()
    {
       for(int i = 0; i < table.getColCount(); i++)
           if(i!=0)
               table.set(0,i, new Record (i * getGapPenalty(), table.get(0,i-1) ));     // setting the prev-property
           else
               table.set(0,i, new Record (i * getGapPenalty()));


       for(int i = 0; i < table.getRowCount(); i++)
           if(i!=0)
               table.set(i, 0, new Record (i * getGapPenalty(), table.get(i-1 , 0)));


    }
}
