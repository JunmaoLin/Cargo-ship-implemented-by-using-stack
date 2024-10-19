/**
 * Thrown if the user attempts to push a Cargo object on to any stack of the CargoShip which would put it over it's maximum weight limit.
*/
public class ShipOverweightException extends Exception{
    public ShipOverweightException(String message) {
        super(message);
    }
}