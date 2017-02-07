package structures;

import java.util.ArrayList;

/**
 * Object for aligned Sequences
 * Created by anthony on 11.01.17.
 */
public class Alignment
{

    ArrayList<Sequence> sequences = new ArrayList<Sequence>() ;
    /**
     * Default Constructor
     */
    public Alignment(){}


    /**
     *
     * @param seq1
     */
    public void add(ArrayList<String> seq1)
    {
        Sequence result = new Sequence("AlignedSequence") ;

        for(int i=seq1.size()-1 ; i>= 0 ; i--)
            result.addSequencePart(seq1.get(i).charAt(0));

        this.sequences.add(result) ;
    }

    public ArrayList<Sequence> getSequences()
    {
        return this.sequences ;
    }

    /**
     *
     * @return
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder() ;
        for(Sequence seq : this.sequences)
        {
            for (char c : seq.getSequence())
                result.append(c + "\t" ) ;

            result.append("\n") ;
        }

        return result.toString() ;
    }


}
