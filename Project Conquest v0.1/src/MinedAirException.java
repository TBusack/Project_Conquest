/*
    Exception that gets thrown if an unminable Block is mined
 */
public class MinedAirException extends Exception{
    Location location;
    public MinedAirException(Location location){
        this.location = location;
    }

    @Override
    public String toString() {
        return ("Air block at (" + location.x + ", " + location.y + ")");
    }
}
