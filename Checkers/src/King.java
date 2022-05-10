
public class King extends Piece {
    public King() {
        super();
    }

    public King(Checkers.PieceColor color) {
        super(color);
    }

    public String toString() {
        if (this.getPieceColor() == Checkers.PieceColor.WHITE)
            return "B";
        else
            return "b";
    }

    public Position[] allDestinations(Checkers checkers, Position p) {
        return King.reachablePositions(checkers, p);
    }

    static Position[] reachablePositions(Checkers checkers, Position p) {
        //Accessed by Queens, does not have to be public
        Position[] result = new Position[0];

        int[] rankOffsets = {1, -1, 1, -1};
        int[] fileOffsets = {1, 1, -1, -1};

        for (int d = 0; d < 4; d++) {
            int i = p.getRank() + rankOffsets[d];
            int j = p.getFile() + fileOffsets[d];
            while (i >= 0 && i < Checkers.BOARD_RANKS
                    && j >= 0 && j < Checkers.BOARD_FILES) {
                Position current = Position.generateFromRankAndFile(i, j);

                if (checkers.isEmpty(current))
                    result = Position.appendPositionsToArray(result, current);
                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }

        return result;
    }
    public Position[] canEat(Checkers checkers,Position p){
        Position[] result = new Position[0];



        return result;
    }
}
