package core;
import structures.Record;
import structures.Sequence;

/**
 * Aligner specification for doing a local alignment
 * Created by anthony on 11.01.17
 */
public class LocalAligner extends Aligner
{

    /**
     * Constructor
     * @param gapPenalty the amount of the gapPenalty
     * @param sequence1 first sequence to align
     * @param sequence2 second sequence to align
     * @param scoreTablePath file path to the score table (i.e BLOSUM62)
     * */
    public LocalAligner(double gapPenalty, Sequence sequence1, Sequence sequence2, String scoreTablePath)
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
         * diagonal / left / top / 0
         */

        // 0 is highest ?
        highest = 0 ;
        highestPrev = null ;

        // diagonal
        if(diagonal + score > 0)
        {
            highest = diagonal + score ;
            highestPrev = this.table.get(row-1, col-1) ;
        }

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
        double highest = -1 ;

        for(int i=0; i<this.table.getRowCount(); i++)
            for(int j=0; j < this.table.getColCount(); j++)
            {
                if(this.table.get(i,j).getData() > highest)
                {
                    highest = this.table.get(i,j).getData() ;
                    ptr_row = i ;
                    ptr_col = j ;
                }
            }
    }


    /**
     * Initializes the table by
     * table[0][j] = 0
     * table[i][j] = 0
     */
    @Override
    public void initTable()
    {
        for(int i = 0; i < table.getColCount(); i++)
            table.set(0,i, new Record (0));


        for(int i = 0; i < table.getRowCount(); i++)
            table.set(i, 0, new Record (0));
    }

}
