package structures;
import java.util.ArrayList;

/**
 * Represents a Sequence object containing
 * a sequence specific id, comments and the Sequence symbols.
 *
 * @author anthony.bruening
 *
 */
public class Sequence
{
    private String id ;
    private ArrayList<String> comments ;
    private ArrayList<Character> sequence ;

    /**
     * Constructor
     * @param id sequence specific id
     */
    public Sequence(String id)
    {
        this.id = id ;
        this.comments = new ArrayList<>() ;
        this.sequence = new ArrayList<>() ;
    }


    /**
     * @return the number of characters in this sequence
     */
    public int length()
    {
        return this.sequence.size() ;
    }

    /**
     * @return List of all comment lines
     */
    public ArrayList<String> getComments() {
        return comments;
    }


    /**
     * @return List of all sequence symbols
     */
    public ArrayList<Character> getSequence() {
        return sequence;
    }

    /**
     * Sets a new comment list.
     * @param comments new list of comments
     */
    public void setComments(ArrayList<String> comments)
    {
        this.comments = comments;
    }

    /**
     * Sets a new list of sequence symbols.
     * @param value
     */
    public void setSequence(ArrayList<Character> value)
    {
        this.sequence = value;
    }


    /**
     * String representation is equal to
     * method-javadoc
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder() ;

        result.append("/**\n") ;
        for(String com : this.comments)
            result.append(" * "+com+"\n") ;
        result.append(" */\n") ;
        result.append("Sequenz: " + this.id+"\n") ;

        for(char c : sequence)
            result.append(c) ;

        return result.toString() ;
    }




}

