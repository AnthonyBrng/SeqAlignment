package structures;

/**
 * Simple wrapper object to store x and y coordinate for table navigation.
 * Provides a function to transform a fieldNumber to a table coordinate.
 *
 * @author anthony
 */
public class TableCoordinates
{
    private int x ;
    private int y ;
    private int fieldNumber ;

    /**
     * Construtor.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public TableCoordinates(int x, int y)
    {
        this.x = x ;
        this.y = y ;
    }



    /**
     * @return the x-coordinate
     */
    public int getX()
    {
        return this.x ;
    }


    /**
     * @return the y-coordinate
     */
    public int getY()
    {
        return this.y ;
    }


    /**
     * Transforms a FieldNumber in Table-Coordinates.
     * @param fieldNumber field number to parse
     * @param table table the coordinates are for
     * @return table coordinates .
     */
    public static TableCoordinates parseNumber(int fieldNumber, Table table)
    {
        if(fieldNumber >=  table.size() )
            throw new ArrayIndexOutOfBoundsException("Field with numnber " + fieldNumber + " does not exist. Highest field number is " + (table.size()-1)) ;

        int y = fieldNumber % table.getColCount() ;
        int x = (fieldNumber - y) / table.getColCount() ;

        return new TableCoordinates(x,y) ;

    }

}
