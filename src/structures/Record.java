package structures;

/**
 * Simple object representing a table record containing
 * a data for the numeric value of the record and
 * a previous property for storing the best preivous record (for tracebacking)
 *
 * @author anthony
 */
public class Record implements Comparable<Record>
{
    private double data ;
    private Record prev ;

    /**
     * Construcotr for creating a new table record
     * @param data data to store
     * @param prev previous highest record
     */
    public Record(double data, Record prev)
    {
        this.data = data ;
        this.prev = prev ;
    }


    /**
     * @return the stored data
     */
    public double getData()
    {
        return this.data ;
    }


    /**
     * @return the previous highest record
     */
    public Record getPrev()
    {
        return this.prev ;
    }


    /**
     * Sets the data property.
     * @param data the new data property
     */
    public void setData(double data)
    {
        this.data = data ;
    }

    /**
     * Seets the previous record property
     * @param prev the new previous-record
     */
    public void setPrev(Record prev)
    {
        this.prev = prev ;
    }

    /**
     * https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html#compareTo(T)
     * @return see compareTo doc.
     */
    @Override
    public int compareTo(Record record)
    {
        if(this.data == record.getData())
            return 0;
        else if(this.data > record.getData())
            return 1;
        else
            return -1 ;
    }

    /**
     * @return the data as String.
     */
    public String toString()
    {
        return this.data + "" ;
    }

}
