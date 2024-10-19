/**
 * This class represents a Cargo stack
*/
import java.util.Stack;
public class CargoStack extends Stack {
    private Stack<Cargo> stackOfCargo;

    /**
     * This is the default constructor
     */
    public CargoStack() {
        stackOfCargo = new Stack<Cargo>();
    }

    /**
     * This method adds a cargo to the stack
     * @param cargo
     *  a Cargo object
     */
    public void push(Cargo cargo) {
        this.stackOfCargo.push(cargo);
    }

    /**
     * This method removes a cargo from the stack
     * @return
     *  a Cargo object
     */
    public Cargo pop() {
        return this.stackOfCargo.pop();
    }

    /**
     * This method check the top cargo in the stack
     * @return
     *  a Cargo object
     */
    public Cargo peek() {
        return this.stackOfCargo.peek();
    }

    /**
     * This method returns the number of cargo in the stack
     * @return
     *  amount of cargo in the stack
     */
    public int size() {
        return this.stackOfCargo.size();
    }

    /**
     * This method checks if the stack is empty
     * @return
     *  true if the stack is empty
     */
    public boolean isEmpty() {
        return this.stackOfCargo.isEmpty();
    }
}
