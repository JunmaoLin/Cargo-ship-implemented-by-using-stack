/**
 * Thrown if the user attempts to push a Cargo object onto another Cargo object that violates the CargoStrength rules indicated above.
*/
public class CargoStrengthException extends Exception{
    public CargoStrengthException(String message) {
        super(message);
    }
}
