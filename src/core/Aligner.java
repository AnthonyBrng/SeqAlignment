package core;

import data.ScoreTable;
import structures.Alignment;
import structures.Sequence;
import structures.Table;

/**
 * Main-programm to align sequences
 *
 * @author anthony
 */
public abstract class Aligner
{

    private double gapPenalty ;
    private Table table ;
    private Alignment alignment ;
    private ScoreTable scoreTable ;

    private Sequence sequence1 ;
    private Sequence sequence2 ;




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

        System.out.println(sequence1);
        System.out.println(sequence2);
        System.out.println(scoreTable.getScore(sequence1.getSequence().get(0).toString(), sequence2.getSequence().get(0).toString()));
    }

    /**
     * Aligns 2 Protein sequences
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
     * Abgleich
     * @return
     */
    public double score(String str1, String str2)
    {
        return this.scoreTable.getScore(str1, str2);
    }


    /**
     * The function that indicates how to fill the table.
     * @return cell Value
     */
    public abstract double cellValue(int x, int y) ;


    /**
     *
     * @return
     */
    public abstract void traceBack() ;


    /**
     *
     */
    public abstract void initTable();

    /**
     *
     */
    public void fillTable()
    {
        initTable();

        for(int i=1; i < table.getRowCount(); i++)
            for(int j=1; j < table.getColCount(); j++)
                table. = cellValue(i,j)  ;


    }



}
