package core;

import data.ScoreTable;
import structures.Alignment;
import structures.Record;
import structures.Sequence;
import structures.Table;

import java.util.ArrayList;

/**
 * Abstract Aligner-Object, implementing the order of
 * main alignment tasks.
 * @author anthony
 */
public abstract class Aligner
{

    private double gapPenalty ;
    public Table table ;
    public Alignment alignment ;
    public ScoreTable scoreTable ;

    public Sequence sequence1 ;
    public Sequence sequence2 ;

    public int ptr_row = 0;    // table pointer row
    public int ptr_col = 0;    // table pointer column

    ArrayList<String> sequence1New = new ArrayList<String>() ; // result sequence 1
    ArrayList<String> sequence2New = new ArrayList<String>() ; // result sequence 2


    /**
     * Constrcutor
     * @param gapPenalty the giving gapPenalty
     */
    public Aligner(double gapPenalty, Sequence sequence1, Sequence sequence2, String scoreTablePath)
    {
        this.gapPenalty = gapPenalty ;
        this.sequence1 = sequence1 ;
        this.sequence2 = sequence2 ;
        this.table = new Table(this.sequence1.length()+1, this.sequence2.length()+1);
        this.scoreTable = new ScoreTable(scoreTablePath);
        this.alignment =  new Alignment() ;
    }

    /**
     * Constrcutor
     */
    public Aligner(double gapPenalty, String scoreTablePath)
    {
        this.gapPenalty = gapPenalty ;
        this.scoreTable = new ScoreTable(scoreTablePath);
    }

    /**
     * Aligns 2 Protein sequences
     * Contains following Steps:
     * - filling the dynammic programming table
     * - tracaback to find teh best alignment
     */
    public void align()
    {
        this.fillTable();
        this.traceBack();
    }

    /**
     * Indicates the gap openalty
     * @return gap penalty
     */
    public double getGapPenalty()
    {
        return this.gapPenalty ;
    }

    /**
     * Fills the table, using the underlying
     * cellValue method, which indicates how to
     * fill each tableCell.
     * Filling the table contains of the following steps:
     * - initialize the table (first col and first row)
     * - iterate through each cell and calculate the value
     */
    public void fillTable()
    {
        initTable();

        for(int i=1; i < table.getRowCount(); i++)
            for(int j=1; j < table.getColCount(); j++)
                table.set(i, j, cellValue(i,j));
    }


    /**
     * Moves backwards through the table to find the best
     * alignment.
     * Both pointer-properties indicate where the algorithm
     * is in the table.
     */
    public void moveBackwards()
    {
        Record prev = this.table.get(ptr_row, ptr_col).getPrev();

        while(prev != null)
        {
            if(prev.equals(this.table.getDiag(ptr_row, ptr_col)))
            {
                sequence1New.add(this.sequence1.getSequence().get(ptr_row -1).toString()) ;
                sequence2New.add(this.sequence2.getSequence().get(ptr_col -1).toString()) ;

                ptr_row-- ;
                ptr_col-- ;
            }
            else if(prev.equals(this.table.getLeft(ptr_row, ptr_col)))
            {
                sequence1New.add("-") ;
                sequence2New.add(this.sequence2.getSequence().get(ptr_col -1).toString()) ;

                ptr_col-- ;

            }
            else if(prev.equals(this.table.getTop(ptr_row, ptr_col)))
            {
                sequence1New.add(this.sequence1.getSequence().get(ptr_row -1).toString()) ;
                sequence2New.add("-") ;

                ptr_row-- ;
            }

            prev = prev.getPrev() ;
        }
    }


    /**
     * Method for tracBacking.
     * First finds the start for tracebacking.
     * moves backwards through the table
     * and then saves the new alignment.
     * Traceback contains of the following steps:
     * - find the traceback starting point in the table
     * - move through table entries
     * - set the resulting alignment
     */
    public void traceBack()
    {

        findTraceBackStart();
        moveBackwards();

        this.alignment.add(sequence1New);
        this.alignment.add(sequence2New);
    }


    /**
     * The function that indicates how to fill the table.
     * @return cell Value
     */
    public abstract Record cellValue(int x, int y) ;


    /**
     * Implements the entry point for tracebacking.
     * and sets the corresponding pointer ptr_row and ptr_col
     */
    public abstract void findTraceBackStart() ;


    /**
     * Initializes the table.
     */
    public abstract void initTable();





}
