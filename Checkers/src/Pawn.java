public class Pawn {
    private Checkers.PieceColor color;


    private boolean isKing = false;
    public Pawn() {
        this(Checkers.PieceColor.WHITE);
    }

    public Pawn(Checkers.PieceColor color) {
        this.color = color;
    }
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
    public void setKing(boolean king) {
        isKing = king;
    }
    public Checkers.PieceColor getPieceColor(){
        return this.color;
    }
    public boolean isKing() {
        return isKing;
    }

    public Position[] allDestinations(Checkers checkers, Position p) {
        if(this.isKing){
            return reachablePositionsKing(checkers,p);
        }
        return reachablePositionsPawn(checkers,p);

    }
    public Position[] canEat(Checkers checkers,Position p){
        if(this.isKing){
            return canEatKing(checkers,p);
        }
        return canEatPawn(checkers,p);
    }
    public Position[] reachablePositionsPawn(Checkers checkers,Position p){
        Position[] result = new Position[0];

        if(this.getPieceColor() == Checkers.PieceColor.BLACK){
            Position temp = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() + 1);
            if(temp!= null && checkers.isEmpty(temp) ) {
                result = Position.appendPositionsToArray(result, temp);
            }
            temp = Position.generateFromRankAndFile(p.getRank()+1,p.getFile()-1);
            if(temp!= null && checkers.isEmpty(temp)){
                result = Position.appendPositionsToArray(result,temp);
            }
        }else {
            Position temp = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() + 1);
            if(temp!= null && checkers.isEmpty(temp) ) {
                result = Position.appendPositionsToArray(result, temp);
            }
            temp = Position.generateFromRankAndFile(p.getRank()-1,p.getFile()-1);
            if(temp!= null && checkers.isEmpty(temp)){
                result = Position.appendPositionsToArray(result,temp);
            }
        }
        return result;
    }

    public Position[] canEatPawn(Checkers checkers, Position p){
        Position[] result = new Position[0];
        if(this.getPieceColor() == Checkers.PieceColor.BLACK){
            Position temp = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() + 1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.WHITE ) {
                Position temp1 =  Position.generateFromRankAndFile(p.getRank() + 2, p.getFile() + 2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    result = Position.appendPositionsToArray(result, temp1);
                }
            }
            temp = Position.generateFromRankAndFile(p.getRank()+1,p.getFile()-1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.WHITE ){
                Position temp1 = Position.generateFromRankAndFile(p.getRank()+2, p.getFile()-2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    result = Position.appendPositionsToArray(result, temp1);
                }
            }
        }else {
            Position temp = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() + 1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.BLACK ) {
                Position temp1 =  Position.generateFromRankAndFile(p.getRank() - 2, p.getFile() + 2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    result = Position.appendPositionsToArray(result, temp1);
                }
            }
            temp = Position.generateFromRankAndFile(p.getRank()-1,p.getFile()-1);
            if(temp!= null && checkers.getPieceAt(temp)!=null && checkers.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.BLACK ){
                Position temp1 = Position.generateFromRankAndFile(p.getRank()-2, p.getFile()-2);
                if(temp1!= null && checkers.isEmpty(temp1)){
                    result = Position.appendPositionsToArray(result, temp1);
                }
            }
        }
        return result;
    }
    static Position[] reachablePositionsKing(Checkers checkers, Position p) {
        //Accessed by Queens, does not have to be public
        Position[] result = new Position[0];

        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];
            Position current = Position.generateFromRankAndFile(i, j);
            if (current!= null && checkers.isEmpty(current))
                result = Position.appendPositionsToArray(result, current);
            i += rankOffsets[d];
            j += fileOffsets[d];

        }

        return result;
    }
    public Position[] canEatKing(Checkers checkers,Position p){
        Position[] result = new Position[0];
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
                    result = Position.appendPositionsToArray(result, temp);
                }
            }

        }
        return result;
    }

}
