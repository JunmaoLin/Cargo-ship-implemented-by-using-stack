/**
 *  Thrown if the user attempts to push a Cargo object onto a stack which is currently at the maximum height.
*/
public class FullStackException extends Exception{
    public FullStackException(String message) {
        super(message);
    }
}
