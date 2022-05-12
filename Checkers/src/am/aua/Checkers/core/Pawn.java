package am.aua.Checkers.core;

import java.util.ArrayList;

public class Pawn {
    //color of the object
    private Checkers.PieceColor color;
    // this is a boolean flag that indicates is this object a king or no
    private boolean isKing = false;

    /**
     * <p>This is a non-arg constructor of Pawn that creates an white pawn</p>
     */
    public Pawn() {
        this(Checkers.PieceColor.WHITE);
    }

    /**
     * <p>This is a parameterized constructor that creates an pawn of the wanted color </p>
     * @param color it is a PieceColor type parameter that indicates the color of the pawn that we want to create
     */
    public Pawn(Checkers.PieceColor color) {
        this.color = color;
    }

    /**
     *<p>This is a parameterized constructor that creates according to his char representation </p>
     * @param pawn it is a char representation of the piece
     */
    public Pawn(char pawn){
        switch (pawn){
            case 'K':
                this.color = Checkers.PieceColor.WHITE;
                this.isKing = true;
                break;
            case 'k':
                this.color = Checkers.PieceColor.BLACK;
                this.isKing = true;
                break;
            case 'P':
                this.color = Checkers.PieceColor.WHITE;
                this.isKing = false;
                break;
            case 'p':
                this.color = Checkers.PieceColor.BLACK;
                this.isKing = false;
                break;
        }

    }

    /**
     * This method determines whether a piece is king or not
     * @param king is boolean, if it is true then the pawn is king
     */

    public void setKing(boolean king) {
        isKing = king;
    }

    /**
     * This method returns the color of pawn
     * @return PieceColor enum type.
     */
    public Checkers.PieceColor getPieceColor(){
        return this.color;
    }

    /**
     * This method return whether this pawn is king or not
     * @return true if king, else false
     */
    public boolean isKing() {
        return isKing;
    }

    /**
     * This method returns all possible steps of the piece
     * @param checkers Checkers type variable of our board
     * @param p is Position type variable of the piece that we want to know destinations
     * @return Position array of all step destinations
     */
    public Position[] allDestinations(Checkers checkers, Position p) {
        if(this.isKing){
            return reachablePositionsKing(checkers,p);
        }
        return reachablePositionsPawn(checkers,p);

    }
    /**
     * This method returns all possible positions to eat of the piece
     * @param checkers Checkers type variable of our board
     * @param p is Position type variable of the piece that we want to know positions
     * @return Position array of all eating positions
     */
    public Position[] eatPositions(Checkers checkers, Position p){
        if(this.isKing){
            return eatPositionsKing(checkers,p);
        }
        return eatPositionsPawn(checkers,p);
    }
    private Position[] reachablePositionsPawn(Checkers checkers, Position p){
        ArrayList<Position> res = new ArrayList<>();

        if(this.getPieceColor() == Checkers.PieceColor.BLACK){
            Position temp = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() + 1);
            if(temp!= null && checkers.isEmpty(temp) ) {
                res.add(temp);
            }
            temp = Position.generateFromRankAndFile(p.getRank()+1,p.getFile()-1);
            if(temp!= null && checkers.isEmpty(temp)){

                res.add(temp);
            }
        }else {
            Position temp = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() + 1);
            if(temp!= null && checkers.isEmpty(temp) ) {
                res.add(temp);
            }
            temp = Position.generateFromRankAndFile(p.getRank()-1,p.getFile()-1);
            if(temp!= null && checkers.isEmpty(temp)){
                res.add(temp);
            }
        }
        return res.toArray(new Position[]{});
    }

    private Position[] eatPositionsPawn(Checkers checkers, Position p){
        Position[] result = new Position[0];
        ArrayList<Position> res = new ArrayList<>();
        if(this.getPieceColor() == Checkers.PieceColor.BLACK){
            Position temp = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() + 1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.WHITE ) {
                Position temp1 =  Position.generateFromRankAndFile(p.getRank() + 2, p.getFile() + 2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    res.add(temp1);
                }
            }
            temp = Position.generateFromRankAndFile(p.getRank()+1,p.getFile()-1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.WHITE ){
                Position temp1 = Position.generateFromRankAndFile(p.getRank()+2, p.getFile()-2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    res.add(temp1);
                }
            }
        }else {
            Position temp = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() + 1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.BLACK ) {
                Position temp1 =  Position.generateFromRankAndFile(p.getRank() - 2, p.getFile() + 2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    res.add(temp1);
                }
            }
            temp = Position.generateFromRankAndFile(p.getRank()-1,p.getFile()-1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.BLACK ){
                Position temp1 = Position.generateFromRankAndFile(p.getRank()-2, p.getFile()-2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    res.add(temp1);
                }
            }
        }
        return res.toArray(new Position[]{});
    }
    private Position[] reachablePositionsKing(Checkers checkers, Position p) {

        ArrayList<Position> result = new ArrayList<>();

        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];
            Position current = Position.generateFromRankAndFile(i, j);
            if (current!= null && checkers.isEmpty(current))
                result.add(current);
            i  += rankOffsets[d];
            j += fileOffsets[d];

        }

        return result.toArray(new Position[]{});
    }
    private Position[] eatPositionsKing(Checkers checkers, Position p){
        ArrayList<Position> res = new ArrayList<>();
        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];
            Position current = Position.generateFromRankAndFile(i, j);
            if (current!= null && !checkers.isEmpty(current) && checkers.getPieceAt(current).getPieceColor()!=this.getPieceColor()) {
                i += rankOffsets[d];
                j += fileOffsets[d];
                Position temp = Position.generateFromRankAndFile(i, j);
                if (temp!= null && checkers.isEmpty(temp)) {
                    res.add(temp);
                }
            }

        }
        return res.toArray(new Position[]{});
    }

}
