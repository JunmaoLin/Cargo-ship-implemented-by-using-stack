/**
 * Thrown if the user attempts to pop a CargoStack that currently has no Cargo items on it.
*/
public class EmptyStackException extends Exception {
    public EmptyStackException(String message) {
        super(message);
    }
}
