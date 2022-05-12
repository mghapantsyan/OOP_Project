package am.aua.Checkers.core;
import javax.swing.*;
import java.util.ArrayList;

/**
 * <p>A mutable class for storing and representing a checkers board. Contains static methods
 * that generate valid objects after ensuring that the given arguments are valid. It has all
 * the methods those are responsible for moving the pieces .</p>
 * @author Jivan Shmavonyan
 * @author Mekhak Ghapantsyan
 * @author Vardan Danielyan
 * @version 2.1
 * @since 1.0
 */
public class Checkers extends JFrame {
    public enum PieceColor {WHITE, BLACK}

    /**
     * It is the Rank of the Checkers board
     */
    public static final int BOARD_RANKS = 8;
    /**
     * It is the File of the Checkers board
     */
    public static final int BOARD_FILES = 8;
    private int numberOfBlacks = 12;
    private int numberOfWhites = 12;
    //It is a checkers board
    private Pawn[][] board;
    private int numberOfMoves;

    /**
     * <P>It is a non-arg constructor which generates the initial board with default positions of pieces</P>
     */
    public Checkers() {
        this.board = new Pawn[8][8];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    this.board[i][j] = new Pawn(PieceColor.BLACK);
                }
            }
        }
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    board[i][j] = new Pawn(PieceColor.WHITE);
                }
            }
        }
    }

    /**
     * <p>It is constructor that receives the String representation of the board and create corresponding board</p>
     * @param board
     */
    public Checkers(String board){

        if(board.length()==65){
            this.board = new Pawn[BOARD_RANKS][BOARD_FILES];
            boolean flag = true;
            switch (board.charAt(64)){
                case 'B':
                    numberOfMoves=1;
                    break;
                case 'W':
                    numberOfBlacks = 0;
                    break;
                default:
                    flag = false;
            }

            for(int i = 0;i<64 && flag;i++){
                switch (board.charAt(i)){
                    case 'K':
                        this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn('K');
                        break;
                    case 'k':
                        this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn('k');
                        break;
                    case 'P':
                        this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn('P');
                        break;
                    case 'p':
                        this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn('p');
                        break;
                    case '-':
                        break;
                    default:
                        this.board =null;
                        flag = false;

                }
            }
        }

    }

//    public Pawn[][] getBoard() {
//        Pawn[][] boardCopy = new Pawn[BOARD_RANKS][BOARD_FILES];
//        for (int i = 0; i < BOARD_RANKS; i++)
//            for (int j = 0; j < BOARD_FILES; j++)
//                boardCopy[i][j] = this.board[i][j];
//        return boardCopy;
//    }

    /**
     * <p>This method is returning whose turn is to do a step</p>
     * @return It returns PieceColor enum type representation of turn;
     */

    public PieceColor getTurn() {
        return PieceColor.values()[this.numberOfMoves % 2];
    }

    /**
     * <p>This boolean method indicates whether the game is over or not</p>
     * @return  boolean value, if game is over it returns true, else false.
     */
    public boolean isGameOver() {
        if(this.getTurn() == PieceColor.WHITE){
            for (int i = 0; i < BOARD_RANKS; i++) {
                for (int j = 0; j < BOARD_FILES; j++) {
                    Position temp = Position.generateFromRankAndFile(i, j);
                    if (this.getPieceAt(temp) != null && this.getPieceAt(temp).getPieceColor() == PieceColor.WHITE) {
                        if(this.reachableFrom(temp) != null &&this.reachableFrom(temp).length != 0 ){
                            return false;
                        }
                    }

                }
            }
        }else{
            for (int i = 0; i < BOARD_RANKS; i++) {
                for (int j = 0; j < BOARD_FILES; j++) {
                    Position temp = Position.generateFromRankAndFile(i, j);
                    if (this.getPieceAt(temp) != null && this.getPieceAt(temp).getPieceColor() == PieceColor.BLACK) {
                        if(this.reachableFrom(temp) != null && this.reachableFrom(temp).length != 0){
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }

    /**
     * <p>This boolean method indicates whether this square is empty or not</p>
     * @param p is Position class representation of the coordinates of the piece.
     * @return if the square is empty return true, else false
     */
    public boolean isEmpty(Position p) {
        return this.board[p.getRank()][p.getFile()] == null;
    }

    /**
     * <p>It returns the object in corresponding position</p>
     * @param p it is Position class representation of the coordinate;
     * @return the object in that place.
     */
    public Pawn getPieceAt(Position p) {
        return this.board[p.getRank()][p.getFile()];
    }

    /**
     * <p>It returns all Positions where the piece can do a step</p>
     * @param origin is a Position of the pieces that we want to do a move
     * @return Position array with all possible positions.
     */
    public Position[] reachableFrom(Position origin) {

        if (origin == null || this.isEmpty(origin))
            return null;
        Position[] t = peacesMustEat(this.getPieceAt(origin).getPieceColor());
        boolean flag = false;
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals(origin)) {
                flag = true;
                break;
            }

        }
        if (t.length != 0 && !flag) {
            return null;
        }
        if (board[origin.getRank()][origin.getFile()].eatPositions(this,
                origin).length == 0) {
            return board[origin.getRank()][origin.getFile()].allDestinations(this,
                    origin);
        } else {
            return board[origin.getRank()][origin.getFile()].eatPositions(this,
                    origin);
        }

    }

    /**
     * <p>It returns all positions of pieces that can do eat step</p>
     * @param turn it is the PieceColor representation of whose turn it is
     * @return Position array that indicates the positions of all pieces that can do a step
     */
    public Position[] peacesMustEat(PieceColor turn) {
        ArrayList<Position> res = new ArrayList<>();
        Checkers checkers = this;
        for (int i = 0; i < BOARD_RANKS; i++) {
            for (int j = 0; j < BOARD_FILES; j++) {
                Position temp = Position.generateFromRankAndFile(i, j);
                if (checkers.getPieceAt(temp) != null && checkers.getPieceAt(temp).getPieceColor() == turn &&
                        checkers.getPieceAt(temp).eatPositions(checkers, temp).length != 0) {
                    res.add(temp);
                }

            }
        }
        return res.toArray(new Position[]{});
    }

    /**
     * <p>This methods do a step or eat some pece</p>
     * @param m This is Move type objects which indicates from where to where to move
     * @return boolean if move was successfully or not
     */
    public boolean performMove(Move m) {
        Position o = m.getOrigin();
        Position d = m.getDestination();

        if (this.getPieceAt(o).getPieceColor() != this.getTurn())
            return false;
        Position[] must = this.peacesMustEat(this.getTurn());

        if (must.length == 0) {
            return performStep(must,o,d);
        } else {
            return performEat(must,o,d);
        }
    }

    /**
     * <p>It generates String representation of the board</p>
     * @return String type value that represents our board
     */
    public String toString(){
        StringBuilder st = new StringBuilder();
        for(int i = 0;i<BOARD_RANKS;i++){
            for (int j = 0;j<BOARD_FILES;j++){
                if(this.board[i][j]==null){
                    st.append('-');
                }
                else if(this.board[i][j].getPieceColor()==PieceColor.WHITE) {
                    if(this.board[i][j].isKing()){
                        st.append('K');
                    }
                    else
                        st.append('P');
                }
                else{
                    if(this.board[i][j].isKing()){
                        st.append('k');
                    }
                    else
                        st.append('p');
                }
            }
        }
        if(getTurn()==PieceColor.BLACK){
            st.append('B');
        }
        else{
            st.append('W');
        }
        return st.toString();
    }
    // This method does eat action
    private boolean performEat(Position[] must,Position o, Position d){
        for (int i = 0; i < must.length; i++) {
            if (o.equals(must[i])) {
                Pawn k = this.getPieceAt(o);
                Position[] t = k.eatPositions(this, o);
                System.out.println(t.length);
                for (int j = 0; j < t.length; j++) {
                    if (t[j].equals(d)) {
                        System.out.println("mm");
                        this.board[d.getRank()][d.getFile()] = this.board[o.getRank()][o.getFile()];
                        this.board[o.getRank()][o.getFile()] = null;
                        System.out.println("y");
                        this.board[(d.getRank() + o.getRank()) / 2][(d.getFile() + o.getFile()) / 2] = null;
                        if (this.getPieceAt(d).getPieceColor() == PieceColor.BLACK) {
                            if (d.getRank() == 7) {
                                this.getPieceAt(d).setKing(true);
                            }
                        } else {
                            if (d.getRank() == 0) {
                                this.getPieceAt(d).setKing(true);
                            }
                        }
                        if (this.getPieceAt(d).eatPositions(this, d).length == 0 && this.getPieceAt(d).eatPositions(this, d) != null) {
                            this.numberOfMoves++;
                        }

                        if (this.getPieceAt(d).getPieceColor() == PieceColor.WHITE) {
                            numberOfBlacks--;
                        } else {
                            numberOfWhites--;
                        }
                        System.out.println(numberOfBlacks + " " + numberOfWhites);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //this method do a single step
    private boolean performStep(Position[] must, Position o, Position d){
        Position[] reachable;
        reachable = this.reachableFrom(o);

        for (int i = 0; i < reachable.length; i++)
            if (d.getRank() == reachable[i].getRank()
                    && d.getFile() == reachable[i].getFile()) {
                this.board[d.getRank()][d.getFile()] = this.board[o.getRank()][o.getFile()];
                this.board[o.getRank()][o.getFile()] = null;
                if (this.getPieceAt(d).getPieceColor() == PieceColor.BLACK) {
                    if (d.getRank() == 7) {
                        this.getPieceAt(d).setKing(true);
                    }
                } else {
                    if (d.getRank() == 0) {
                        this.getPieceAt(d).setKing(true);
                    }
                }

                this.numberOfMoves++;
                return true;
            }
        return false;
    }
}
