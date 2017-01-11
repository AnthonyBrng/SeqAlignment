package core;

import data.ScoreTable;
import structures.Alignment;
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




    /**
     * Constrcutor
     * @param gapPenalty the giving gapPenalty
     */
    public Aligner(double gapPenalty, int seqLen1, int seqLen2, String scoreTablePath)
    {
        this.gapPenalty = gapPenalty ;
        this.table = new Table(seqLen1, seqLen2);
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
