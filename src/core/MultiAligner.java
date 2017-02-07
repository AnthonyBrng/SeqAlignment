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

    /**
     * Moves backwards through the table to find the best
     * alignment.
     */
    public void moveBackwards()
    {
        Record prev = this.table.get(ptr1, ptr2).getPrev();

        while(prev != null)
        {
            if(prev.equals(this.table.getDiag(ptr1,ptr2)))
            {
                sequence2New.add(this.sequence2.getSequence().get(ptr2-1).toString()) ;

                ptr1-- ;
                ptr2-- ;
            }
            else if(prev.equals(this.table.getLeft(ptr1,ptr2)))
            {

                sequence2New.add(this.sequence2.getSequence().get(ptr2-1).toString()) ;

                ptr2-- ;

            }
            else if(prev.equals(this.table.getTop(ptr1,ptr2)))
            {
                sequence2New.add("-") ;

                ptr1-- ;
            }

            prev = prev.getPrev() ;
        }
    }

    public void traceBack()
    {

        findTraceBackStart();

        moveBackwards();

        this.alignment.add(sequence2New);
        for(Sequence s : this.sequences)
        {
            ArrayList<String> bla = new ArrayList<>();
            for(Character c: s.getSequence())
                bla.add(c.toString());

            this.alignment.add(bla);
        }
    }

    @Override
    public Record cellValue(int row, int col)
    {
        double highest ;
        Record highestPrev ;

        double diagonal = this.table.getDiag(row,col).getData();
        double left     = this.table.getLeft(row,col).getData();
        double top      = this.table.getTop(row,col).getData();

        String character1 ;
        String character2;

        double score  = 0;


        for(Sequence seq : this.sequences)
        {
            character1 = seq.getSequence().get(row-1).toString() ;
            character2 = seqToAdd.getSequence().get(col-1).toString() ;
            score += this.scoreTable.getScore(character1, character2) ;  // matchfactor
        }


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

    @Override
    public void findTraceBackStart()
    {
        this.ptr1 = this.table.getRowCount() - 1;
        this.ptr2 = this.table.getColCount() - 1 ;
    }

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
