package core;
import java.util.ArrayList;
import structures.Sequence ;

/**
 * Parser-Class for files in FASTA-format.
 * Rules:
 * 1. Before header are infinite number of line breaks allowed
 * 2. header line starting with '>', followed by self defined sequence id
 * 3. infinite number of comment lines starting with ';' followed by a comment phrase
 * 4. infinite number of sequence lines containing only allowed sequence symbols
 *
 * 1-4 must occur in this order but 1-4 can repeat in one file infinitly.
 * @author anthony.bruening
 *
 */
public class FastaParser
{
    private String value;
    private String[] lines ;
    private int line_ptr ;
    private int sequence_ptr ;
    private Sequence current ;
    private int line_length ;
    private String header_symbol;
    private String comment_symbol;
    private String split_symbol;

    private ArrayList<String> err_msg = new ArrayList<>() ;
    private ArrayList<Sequence> sequences = new ArrayList<>() ;
    private ArrayList<String> sequence_symbols = new ArrayList<>() ;

    /**
     * Default-constructor
     */
    public FastaParser()
    {
        this("");
    }

    /**
     * Constructor
     * Sets all needed properties.
     * @param value file content to be parsed.
     */
    public FastaParser(String value)
    {
        this.value = value ;
        this.line_ptr = 0 ;
        this.sequence_ptr = 0 ;
        this.line_length = 80 ;
        this.header_symbol = ">" ;
        this.comment_symbol = ";" ;
        this.split_symbol = "\n" ;
        this.lines = this.value.split(split_symbol) ;

        defineSequenceSymbols();
    }

    /**
     * Starts parsing.
     * Call-Chain:
     * -> parseHeader()
     * @return True, if parsed without errors, otherwise false.
     */
    public boolean parse()
    {
		/*
		 * validate each line
		 */
        if(!validate(lines))
            return false ;

		/*
		 * reset
		 */
        this.line_ptr = 0 ;
        this.current = null ;
        this.sequences.clear() ;

		/*
		 * Start parsing for header.
		 */
        boolean result = parseHeader() ;

        if(result)
            this.sequences.add(current);

        return result  ;
    }

    /**
     * Parses one line for header Information.
     * Line breaks get ignored.
     * Call-Chain:
     * -> parseHeader() on empty line
     * -> parseComment() on successful header parse.
     * @return True, if parsed without errors, otherwise false.
     */
    public boolean parseHeader()
    {
		/*
		 * No more lines
		 */
        if(line_ptr >= lines.length)
            return true ;

		/*
		 * Ignore line breaks
		 */
        if(match(this.split_symbol))
        {
            line_ptr++ ;
            return parseHeader() ;
        }

		/*
		 * Save the last current sequence object
		 * if available.
		 */
        if(current != null)
        {
            this.sequences.add(current) ;
        }

		/*
		 * Save header information and
		 * parse Comments
		 */
        if(match(this.header_symbol))
        {
            current = new Sequence(lines[line_ptr].substring(1)) ;
            line_ptr++ ;
            return parseComments();
        }
        else
            return false ;
    }

    /**
     * Parses one line for comment information.
     * Call-Chain:
     * -> parseComments() on successful comment parse.
     * -> parseSequence_head() otherwise.
     * @return True, if parsed without errors, otherwise false.
     */
    private boolean parseComments()
    {
		/*
		 * No more lines
		 */
        if(line_ptr >= lines.length)
            return false ;

		/*
		 * Save header information and
		 * try parsing comments again.
		 */
        if(match(this.comment_symbol))
        {
            current.getComments().add(lines[line_ptr++].substring(1)) ;
            return parseComments();
        }
		/*
		 * No more comments
		 * parse sequence head
		 */
        else
            return parseSequence_head();

    }

    /**
     * Parses oneline for sequence start.
     * Call-Chain:
     * -> parseSequence_tail() on successful sequence-head parse.
     * @return True, if parsed without errors, otherwise false.
     */
    private boolean parseSequence_head()
    {
		/*
		 * No more lines
		 */
        if(line_ptr >= lines.length)
            return false ;

		/*
		 * if line starts with one allowed sequence symbol
		 * parse sequence tail.
		 */
        for(String elem  : sequence_symbols)
            if(match_sequence(elem))
            {
                this.current.getSequence().add(lines[line_ptr].charAt(sequence_ptr++));
                return parseSequence_tail() ;
            }

        return false ;
    }

    /**
     * Parses one line for more available sequence symbols.
     * Call-Chain:
     * -> parseSequence_nextLine() if no more symbols in this line.
     * -> parseSequence_tail() on successful sequence symbol parse.
     * -> parseHeader() if line starts with header or split symbol.
     * @return True, if parsed without errors, otherwise false.
     */
    private boolean parseSequence_tail()
    {
		/*
		 * No more lines
		 */
        if(line_ptr>= lines.length)
            return true ;

		/*
		 * No more Sequence Symbols in this line
		 * parse nextline for sequence Symbols.
		 */
        if(sequence_ptr >= lines[line_ptr].length())
        {
            sequence_ptr = 0 ;
            line_ptr ++ ;
            return parseSequence_nextLine() ;
        }
        else
        {
			/*
			 * if line stars with header or split symbol
			 * parse for header information.
			 */
            for(String elem  : sequence_symbols)
                if(match_sequence(elem))
                {
                    this.current.getSequence().add(lines[line_ptr].charAt(sequence_ptr++));
                    return parseSequence_tail() ;
                }

			/*
			 * If current Symbol is allowed sequence symbol
			 * parse for more sequence symbols
			 */
            if(match(this.header_symbol) || match(this.split_symbol))
                return parseHeader() ;


            return false ;
        }
    }

    /**
     * Parses a new line whether properties are correct for parsing more
     * sequence symbols.
     * Call-Chain:
     * -> parseHeader if line starts with header or split symbol.
     * -> parseSequence_head() otherwise
     * @return True, if parsed without errors, otherwise false.
     */
    private boolean parseSequence_nextLine()
    {
		/*
		 * no more lines
		 */
        if(line_ptr >= lines.length)
            return true ;

		/*
		 * if line starts with header or split symbol
		 * parse for header information
		 */
        if(match(this.header_symbol) || match(this.split_symbol))
            return parseHeader() ;

        return parseSequence_head();
    }

    /**
     * @param match String to test.
     * @return True, if current line starts with match.
     */
    private boolean match(String match)
    {
        return lines[line_ptr].startsWith(match) ;
    }

    /**
     * Difference between this and match():
     * Checks a specific character in a line, isntead
     * of just checking the start of a line.
     * @param match String to test.
     * @return True, if match is equal to current character in current line, false otherwise.
     */
    private boolean match_sequence(String match)
    {
        return lines[line_ptr].charAt(sequence_ptr) == match.charAt(0) ;
    }

    /**
     * Defines all alowed sequence Symbol.
     * In this example all DNA-Bases are added
     */
    private void defineSequenceSymbols()
    {
        this.sequence_symbols.add("A");
        this.sequence_symbols.add("C");
        this.sequence_symbols.add("G");
        this.sequence_symbols.add("T");
    }


    /**
     * Validates all lines to be parsed.
     * Checks if all line are below allowed line length
     * and replaces all empty lines by splitsymbol character.
     * @param lines lines to validate.
     * @return True, if each line is below the allowed line length
     */
    private boolean validate(String[] lines)
    {
        err_msg.clear() ;

		/*
		 * Checks line length
		 */
        for(String line : lines)
        {
            if(line.length() > this.line_length)
                err_msg.add(String.format("Zeile\n>'%s'\nist länger als %d Zeichen!", line, this.line_length)) ;
        }

		/*
		 * replace empty lines
		 */
        for(int i=0 ; i<lines.length ; i++)
        {
            if(lines[i].equals(""))
                lines[i] = this.split_symbol ;
        }

        return (err_msg.size() == 0) ;
    }


    /**
     * prints the line errors that occured in validate().
     */
    public void print_Err()
    {
        if(err_msg.size() == 0)
            return ;

        int i = 1 ;
        System.out.println("Fehlermeldungen:");
        for(String msg: err_msg)
        {
            System.out.print(i++ + ".");
            System.out.println(msg);
        }
    }

    /**
     * @param value new Value to be parsed
     */
    public void setValue(String value)
    {
        this.value = value ;
        this.lines = this.value.split(split_symbol) ;
    }

    /**
     * @return All parsed sequences.
     */
    public ArrayList<Sequence> getSequences()
    {
        return this.sequences ;
    }


    /**
     * Test method
     * @param args user start arguments
     */
    public static void main(String[] args)
    {

        /*
        FastaParser p = new FastaParser() ;
        String datei = "\n" +"\n" +"\n" +
                ">TestSequenz\n" +
                ";Erste testsequenz\n" +
                "ATTGACTAGCTCATCAGCATGCACTATAGCGGCATCTCAGCCCATAGATTAGCTA\n" +
                "\n" +"\n" +"\n" +
                ">Zweite Sequence\n" +
                ";weitere Sequence in gleicher Datei\n" +
                ";Außerdem 2 Kommentar zeilen und Sequence zeilen\n" +
                "ATTGACTAGCTCATCAGCATGCACTATAGCGGCA\n" +
                "TCTCAGCCCATAGATTAGCTA";

        p.setValue(datei) ;

        if(p.parse())
        {
            System.out.println("Parse war erfoglreich!") ;
            for(Sequence seq : p.getSequences())
                System.out.println(seq+"\n");
        }
        else
        {
            System.out.println("Parse ist Fehlgeschlagen!") ;
        }

        p.print_Err();*/


    }

}

