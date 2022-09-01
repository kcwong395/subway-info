package solution.model;

public enum Direction {
    // Union special case
    NORTH, SOUTH, EAST, WEST;

    @Override
    public String toString() {
        String exp = this.name();
        return exp.charAt(0) + exp.toLowerCase().substring(1) + "bound";
    }
}
