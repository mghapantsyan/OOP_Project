import javax.swing.*;

public class Checkers extends JFrame {
    public enum PieceColor {WHITE, BLACK}

    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;
    private int numberOfBlacks = 12;
    private int numberOfWhites = 12;
    private Pawn[][] board;
    private int numberOfMoves;

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

    public Pawn[][] getBoard() {
        Pawn[][] boardCopy = new Pawn[BOARD_RANKS][BOARD_FILES];
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                boardCopy[i][j] = this.board[i][j];
        return boardCopy;
    }

    public PieceColor getTurn() {
        return PieceColor.values()[this.numberOfMoves % 2];
    }

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
            return true;
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
            return true;
        }
    }

    public boolean isEmpty(Position p) {
        return this.board[p.getRank()][p.getFile()] == null;
    }

    public Pawn getPieceAt(Position p) {
        return this.board[p.getRank()][p.getFile()];
    }

    public Position[] reachableFrom(Position origin) {

        if (origin == null || this.isEmpty(origin))
            return null;
        Position[] t = peacesMustEat(this.getPieceAt(origin).getPieceColor());
        boolean flag = false;
        for (int i = 0; i < t.length; i++) {
            System.out.println(t[i].toString());
            if (t[i].getRank() == origin.getRank() && t[i].getFile() == origin.getFile()) {
                flag = true;
                break;
            }

        }
        if (t.length != 0 && !flag) {
            return null;
        }
        if (board[origin.getRank()][origin.getFile()].canEat(this,
                origin).length == 0) {
            return board[origin.getRank()][origin.getFile()].allDestinations(this,
                    origin);
        } else {
            return board[origin.getRank()][origin.getFile()].canEat(this,
                    origin);
        }

    }

    public Position[] peacesMustEat(PieceColor turn) {
        Position[] res = new Position[0];
        Checkers checkers = this;
        for (int i = 0; i < BOARD_RANKS; i++) {
            for (int j = 0; j < BOARD_FILES; j++) {
                Position temp = Position.generateFromRankAndFile(i, j);
                if (checkers.getPieceAt(temp) != null && checkers.getPieceAt(temp).getPieceColor() == turn &&
                        checkers.getPieceAt(temp).canEat(checkers, temp).length != 0) {
                    res = Position.appendPositionsToArray(res, temp);
                }

            }
        }
        return res;
    }

    public boolean performMove(Move m) {
        Position o = m.getOrigin();
        Position d = m.getDestination();

        if (this.getPieceAt(o).getPieceColor() != this.getTurn())
            return false;
        Position[] must = this.peacesMustEat(this.getTurn());
        Position[] reachable;
        if (must.length == 0) {
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
        } else {

            for (int i = 0; i < must.length; i++) {
                if (o.getRank() == must[i].getRank() && o.getFile() == must[i].getFile()) {
                    Pawn k = this.getPieceAt(o);
                    Position[] t = k.canEat(this, o);
                    System.out.println(t.length);
                    for (int j = 0; j < t.length; j++) {
                        if (t[j].getRank() == d.getRank() && t[j].getFile() == d.getFile()) {
                            System.out.println("mm");
                            this.board[d.getRank()][d.getFile()] = this.board[o.getRank()][o.getFile()];
                            this.board[o.getRank()][o.getFile()] = null;
                            // es el inch vor kerela eta jnjum
                            System.out.println("y");
                            this.board[(d.getRank() + o.getRank()) / 2][(d.getFile() + o.getFile()) / 2] = null;

                            if (this.getPieceAt(d).canEat(this, d).length == 0 && this.getPieceAt(d).canEat(this, d) != null) {
                                this.numberOfMoves++;
                            }
                            if (this.getPieceAt(d).getPieceColor() == PieceColor.BLACK) {
                                if (d.getRank() == 7) {
                                    this.getPieceAt(d).setKing(true);
                                }
                            } else {
                                if (d.getRank() == 0) {
                                    this.getPieceAt(d).setKing(true);
                                }
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
        return false;
    }

    public Position[] getAllDestinationsByColor (PieceColor color){
        Position[] result = new Position[0];

        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (board[i][j] != null && board[i][j].getPieceColor() == color) {
                    Position[] current = board[i][j].allDestinations(this,
                            Position.generateFromRankAndFile(i, j));

                    duplicates:
                    for (int k = 0; k < current.length; k++) {
                        for (int l = 0; l < result.length; l++)
                            if (current[k].equals(result[l]))
                                continue duplicates;
                        result = Position.appendPositionsToArray(result, current);
                    }
                }

        return result;
    }
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

}
