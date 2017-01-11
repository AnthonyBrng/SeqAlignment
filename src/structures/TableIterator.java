package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator-object to iterator through a 2-dimensional array.
 *
 * @author anthony
 */
public class TableIterator implements Iterator<Record>
{

    private Table table ;
    private int pointer ;

    /**
     * Constructor.
     * Needs the table to get the size etc. for correct iterating
     * @param table table to iterate over.
     */
    public TableIterator(Table table )
    {
        this.table = table ;
        this.pointer = 0 ;
    }

    /**
     * @return True, if iteration got more elements, false otherwise.
     */
    @Override
    public boolean hasNext()
    {
        return pointer >= table.size() ;
    }


    /**
     * Checks if next element is available and return this.
     * Afterwards increasing the pointer by one.
     * @return the next record of the iteration.
     */
    @Override
    public Record next()
    {
        if(hasNext())
            return table.get(this.pointer++) ;
        else
            throw new NoSuchElementException("No more Elements in this Iteration") ;
    }


    /**
     * Returns the current Fieldnumber.
     * Is equal to getPointer();
     * @return the current index of the table.
     */
    public int current()
    {
        return this.pointer ;
    }




}
