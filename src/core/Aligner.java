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
        this.table = new Table(this.sequence1.length(), this.sequence2.length());
        this.scoreTable = new ScoreTable(scoreTablePath);
    }

    /**
     *
     */
    public void align()
    {
        this.fillTable();
        this.traceBack();
    }

    /**
     *
     * @return
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
        //this.scoreTable.getScore(str1, str2);
        return 0.0 ;
    }


    /**
     *
     * @return
     */
    public abstract void traceBack() ;


    /**
     *
     */
    public abstract void fillTable();



}
