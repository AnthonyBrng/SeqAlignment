package core;

import data.ScoreTable;
import structures.Alignment;
import structures.Record;
import structures.Sequence;
import structures.Table;

import java.util.ArrayList;

/**
 * Main-programm to align sequences
 *
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


    public int ptr1 = 0;    // table pointer row
    public int ptr2 = 0;    // table pointer spalte

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
     *
     */
    public Aligner(double gapPenalty, String scoreTablePath)
    {
        this.gapPenalty = gapPenalty ;
        this.scoreTable = new ScoreTable(scoreTablePath);
    }

    /**
     * Aligns 2 Protein sequences
     */
    public void align()
    {
        this.fillTable();
        System.out.println(this.table);
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
     * Abgleich
     * @return
     */
    public double score(String str1, String str2)
    {
        return this.scoreTable.getScore(str1, str2);
    }

    /**
     * Fills the table, using the underlying
     * cellValue method.
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
     */
    public void moveBackwards()
    {
        Record prev = this.table.get(ptr1, ptr2).getPrev();

        while(prev != null)
        {
            if(prev.equals(this.table.getDiag(ptr1,ptr2)))
            {
                sequence1New.add(this.sequence1.getSequence().get(ptr1-1).toString()) ;
                sequence2New.add(this.sequence2.getSequence().get(ptr2-1).toString()) ;

                ptr1-- ;
                ptr2-- ;
            }
            else if(prev.equals(this.table.getLeft(ptr1,ptr2)))
            {
                sequence1New.add("-") ;
                sequence2New.add(this.sequence2.getSequence().get(ptr2-1).toString()) ;

                ptr2-- ;

            }
            else if(prev.equals(this.table.getTop(ptr1,ptr2)))
            {
                sequence1New.add(this.sequence1.getSequence().get(ptr1-1).toString()) ;
                sequence2New.add("-") ;

                ptr1-- ;
            }

            prev = prev.getPrev() ;
        }
    }


    /**
     * Method for tracBacking.
     * First finds the start for tracebacking.
     * moves backwards throug the table
     * and then saves the new alignment.
     * @return
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
     * and sets the corresponding pointer ptr1 and ptr2
     */
    public abstract void findTraceBackStart() ;

    /**
     * Initializes the table.
     */
    public abstract void initTable();





}
