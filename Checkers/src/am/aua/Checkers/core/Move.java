package am.aua.Checkers.core;

public class Move {
    //immutable, contains mutable references
    private Position origin;
    private Position destination;

    /**
     * <p>This is parameterized constructor that create a Move class</p>
     * @param origin It is Position type variable, and it represents origin of the move
     * @param destination It is Position type variable, and it represents final destination of the move
     */
    public Move(Position origin, Position destination) {
        this.origin = new Position(origin);
        this.destination = new Position(destination);
    }

    /**
     * It is a copy constructor of Move class
     * @param other is Move object that we want to create a copy of.
     */
    public Move(Move other) {
        this.origin = new Position(other.origin);
        this.destination = new Position(other.destination);
    }

    /**
     * It is getter method for origin of the move
     * @return is Position type variable of the origin of the move
     */
    public Position getOrigin() {
        return new Position(this.origin);
    }
    /**
     * It is getter method for destination of the move
     * @return is Position type variable of the destination of the move
     */
    public Position getDestination() {
        return new Position(this.destination);
    }

}
