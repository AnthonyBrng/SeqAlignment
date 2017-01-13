package structures;

/**
 * Main datastructure for the alignment algorithm.
 * A 2-dimensional array of type Record representing the table.
 * The coordinate and navigation is not by two indexes for both arrays,
 * instead its translated to a fieldnumber which looks like this:
 *
 *        x1  ..  x3
 *      |---|---|---|
 *   y1 | 1 | 2 | 3 |
 *      |---|---|---|
 *  ..  | 4 | 5 | 6 |
 *      |---|---|---|
 *  y3  | 7 | 8 | 9 |
 *      |---|---|---|
 *
 *
 * @author anthony
 */
public class Table implements Iterable<Record>
{

    private Record[][] table ;

    /**
     * Constructor.
     * Initializes the main table with the defined amount of
     * rows and cols.
     * @param rows amount of rows
     * @param cols amount of coloumns
     */
    public Table(int rows, int cols)
    {
        this.table = new Record[rows][cols] ;
        this.init();
    }


    /**
     * Retruns the record in a specfic Field.
     * @param fieldNumber The number of the field in the table.
     * @return the record in field with number fieldNumber.
     */
    public Record get(int fieldNumber)
    {
        TableCoordinates coor = TableCoordinates.parseNumber(fieldNumber, this) ;
        return this.table[coor.getX()][coor.getY()] ;
    }


    /**
     *
     * @param row
     * @param col
     * @return
     */
    public Record get(int row, int col)
    {
        return this.table[row][col];
    }

    /**
     * Sets a Record into a specific field.
     * @param fieldnumber Number of the field that gets set
     * @param rec New Record.
     */
    public void set(int fieldnumber, Record rec)
    {
        TableCoordinates coor = TableCoordinates.parseNumber(fieldnumber,this) ;
        this.table[coor.getX()][coor.getY()] = rec ;
    }

    /**
     *
     */
    public void set(int row, int col, Record rec)
    {
        this.table[row][col] = rec ;
    }



    /**
     * Gets the diagonal element from a specfic field
     * @param fieldNumber number of the field from which to look for the diagonal Field.
     * @return the diagonal field from field number.
     */
    public Record getDiag(int fieldNumber)
    {
        TableCoordinates coor = TableCoordinates.parseNumber(fieldNumber, this) ;

        int x = coor.getX() - 1 ;
        int y = coor.getY() - 1 ;

        if(x < 0 || y < 0)
            return null ;

        return this.table[x][y] ;

    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    public Record getDiag(int row, int col)
    {
        if(row-1 < 0 || col-1 <0)
            return null;

        return this.table[row-1][col-1] ;
    }



    /**
     * Gets the  element above a specfic field
     * @param fieldNumber number of the field from which to look for the above Field.
     * @return the field above from field number.
     */
    public Record getTop(int fieldNumber)
    {
        TableCoordinates coor = TableCoordinates.parseNumber(fieldNumber, this) ;

        int x = coor.getX() - 1 ;

        if(x < 0 )
            return null ;

        return this.table[x][coor.getY()] ;

    }


    /**
     *
     * @param row
     * @param col
     * @return
     */
    public Record getTop(int row, int col)
    {
        if(row-1 < 0)
            return null ;

        return this.table[row-1][col] ;
    }


    /**
     * Gets the left element from a specfic field
     * @param fieldNumber number of the field from which to look for the left Field.
     * @return the left field from field number.
     */
    public Record getLeft(int fieldNumber)
    {
        TableCoordinates coor = TableCoordinates.parseNumber(fieldNumber, this) ;

        int y = coor.getY() - 1 ;

        if(y < 0 )
            return null ;

        return this.table[coor.getX()][y] ;

    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    public Record getLeft(int row, int col)
    {
        if(col-1 < 0)
            return null ;

        return this.table[row][col-1] ;
    }


    /**
     * @return amount of rows in the table
     */
    public int getRowCount()
    {
        return this.table.length ;
    }


    /**
     * @return amount of coloumns in the table
     */
    public int getColCount()
    {
        return this.table[0].length ;
    }


    /**
     * @return the number of elements
     */
    public int size()
    {
        return (getRowCount() * getColCount());
    }


    /**
     * Creates a TableIterator
     * @return Ierator for this table object.
     */
    @Override
    public TableIterator iterator()
    {
        return new TableIterator(this) ;
    }


    /**
     * Default-Table-Representation
     * @return Table in normal table-style
     */
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder( ) ;

        for (int i = 0; i < this.table.length; i++)
        {
            for(int j = 0 ; j < this.table[0].length ; j++)
             {
                 result.append(this.table[i][j] + "\t\t") ;
             }
            result.append("\n");
        }

        return result.toString() ;
    }


    /**
     * Initializes the table by an increasing number starting at 0
     */
    public void init()
    {
        int c = 0 ;
        for (int i = 0; i < this.table.length; i++)
            for(int j = 0 ; j < this.table[0].length ; j++)
                this.table[i][j] = new Record(c++, null) ;
    }

    /**********
     * Debug
     */

    /**
     * Test method
     * @param args user args
     */
    public static void main(String[] args)
    {
        Table t = new Table(3,3) ;
        System.out.println(t);

    }



}
