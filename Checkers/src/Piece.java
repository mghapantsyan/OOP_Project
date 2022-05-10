public class Piece {
        private Checkers.PieceColor color;

        public Piece(Checkers.PieceColor color) {
            this.color = color;
        }

        public Piece() {
            this(Checkers.PieceColor.WHITE);
        }

        public Position[] allDestinations(Checkers checkers, Position p) {
            return null;
        }
        public Position[] canEat(Checkers checkers,Position p){ return null;}

        public final Checkers.PieceColor getPieceColor() {
            return this.color;
        }


}
