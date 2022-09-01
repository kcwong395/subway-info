package solution.model;

public enum Direction {
    // Union special case
    NORTH, SOUTH, EAST, WEST, NORTHTOFINCH, NORTHTOVAUGHAN;

    @Override
    public String toString() {
        switch(this) {
            case NORTHTOVAUGHAN:
                return "Northbound towards Vaughan Metropolitan Centre";
            case NORTHTOFINCH:
                return "Northbound towards Finch";
            default:
                String exp = this.name();
                return exp.charAt(0) + exp.toLowerCase().substring(1) + "bound";
        }
    }
}
