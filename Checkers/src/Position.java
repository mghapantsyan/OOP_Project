public class Position {
    //mutable
    /** <p>Position rank on the board, starting from the top.
     * Classic chessboard squares are ranked from 1 to 8. The value 0 corresponds with the 8th rank and
     * a value of 7 corresponds with the 1st rank.</p>
     */
    private int rank;
    /** <p>Position file on the board, starting from the left.
     * Classic chessboard files are from A to H. The value 0 corresponds with A and
     * a value of 7 corresponds with H.</p>
     */
    private int file;


    /** <p>A no-arg constructor, initializes with 0's, corresponds with A8</p>
     */
    public Position() {
        this.rank = 0;
        this.file = 0;
    }

    /** <p>A copy constructor, initializes according to another Position object.</p>
     * @param other The original position, according to which the new object is created.
     */
    public Position(Position other) {
        this.setRank(other.rank);
        this.setFile(other.file);
    }

    /**
     * A parameterized consturctor, initializes the fields with given values.
     * @param rank An integer representing the rank.
     * @param file An integer representing the file.
     */
    private Position(int rank, int file) {
        this.setRank(rank);
        this.setFile(file);
    }

    /**
     * Accessor method for the rank of the position.
     * @return The rank of the position.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Accessor method for the file of the position.
     * @return The file of the position.
     */
    public int getFile() {
        return this.file;
    }

    /**
     * Mutator method for the rank of the position.
     * @param rank The new rank.
     */
    public void setRank(int rank) {
        if (rank >= 0 && rank < Checkers.BOARD_RANKS)
            this.rank = rank;
    }

    /**
     * Mutator method for the file of the position.
     * @param file The new file.
     */
    public void setFile(int file) {
        if (file >= 0 && file < Checkers.BOARD_FILES)
            this.file = file;
    }

    /**
     * Mutator method for the file of the position.
     * @return A string representation of the position on the board.
     */
    public String toString() {
        return "" + (char)('A' + this.file) + (Checkers.BOARD_RANKS - this.rank);
    }

    /**
     * <p>A static method that given a valid string indicating a chessboard square in chess notation,
     * e.g. "a8" or "A8", creates a position object after verification. Returns null otherwise.
     * Such methods are known as static factory methods and are advantageous in a multitude of ways.</p>
     *
     * @param s The name of the chessboard square, such as "A8"
     * @return A position object or null.
     */
    public static Position generateFromString(String s) {
        s = s.toLowerCase();
        if (s.length() != 2
                || (s.charAt(0) < 'a' || s.charAt(0) >= 'a' + Checkers.BOARD_FILES)
                || (s.charAt(1) < '1' || s.charAt(1) >= '1' + Checkers.BOARD_RANKS)
        )
            return null;
        return generateFromRankAndFile(Checkers.BOARD_RANKS - s.charAt(1) + '0',
                s.charAt(0) - 'a');
    }

    /**
     * <p>A static method that takes two valid integers representing rank and file,
     * creates a position object after verification. Returns null otherwise.
     * Such methods are known as static factory methods and are advantageous in a multitude of ways.</p>
     *
     * @param rank The name of the chessboard square, such as "A8"
     * @param file An integer, representing
     * @return A position object or null.
     */
    public static Position generateFromRankAndFile(int rank, int file) {
        Position result = null;
        if (rank >= 0 && rank < Checkers.BOARD_RANKS
                && file >= 0 && file < Checkers.BOARD_FILES
        )
            result = new Position(rank, file);
        return result;
    }

    public static Position[] appendPositionsToArray(Position[] arr, Position... elements){
        Position[] result = new Position[arr.length + elements.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        for (int i = 0; i < elements.length; i++)
            result[arr.length + i] = elements[i];

        return result;
    }
}
