/**
 * This class represents a container ship which holds stacks of containers.
*/

import java.text.DecimalFormat;
public class CargoShip  {
    private CargoStack[] stacks;
    private int maxHeight;
    private double maxWeight;
    private double currentWeight;

    /**
     * Default Constructor.
     * @param numStacks
     *  The number of stacks this ship can support.
     * @param initMaxHeight
     *  The maximum height of any stack on this ship.
     * @param initMaxWeight
     *  The maximum weight for all the cargo on the ship.
     * @throws IllegalArgumentException
     *  if either of the parameters are now within the appropriate bounds.
     */
    public CargoShip(int numStacks, int initMaxHeight, double initMaxWeight) throws IllegalArgumentException{
        if(numStacks <= 0 || initMaxHeight <= 0 || initMaxWeight <= 0){
            throw new IllegalArgumentException("Invalid parameters");
        }
        stacks = new CargoStack[numStacks];
        for(int i = 0; i < numStacks; i++){
            stacks[i] = new CargoStack();
        }
        this.maxHeight = initMaxHeight;
        this.maxWeight = initMaxWeight;
        this.currentWeight = 0;
    }

    /**
     * This is the getter method for the current weight of the ship.
     * @return
     *  the current weight
     */
    public double getCurrentWeight() {
        return this.currentWeight;
    }

    /**
     * Pushes a cargo container to the indicated stack on the cargo ship
     * @param cargo
     *  The container to place on the stack.
     * @param stack
     *  The index of the stack on the ship to push cargo onto.
     * @throws FullStackException
     *  If the stack being pushed to is at the max height.
     * @throws ShipOverweightException
     *  If cargo would make the ship exceed maxWeight and sink.
     * @throws CargoStrengthException
     *  If the cargo would be stacked on top of a weaker cargo 
     * @throws IllegalArgumentException
     *  If cargo is null or stack is not in the appropriate bounds.
     */
    public void pushCargo(Cargo cargo, int stack) throws FullStackException, ShipOverweightException, CargoStrengthException, IllegalArgumentException{
        if(cargo == null || !(stack >= 1 && stack <= stacks.length)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        if(stacks[stack-1].size() >= maxHeight){
            throw new FullStackException("Stack is full");
        }
        if(currentWeight+cargo.getWeight() > this.maxWeight){
            throw new ShipOverweightException("Ship is overweight");
        }
        CargoStack selectedStack = stacks[stack-1];
        if(!selectedStack.isEmpty()){
            Cargo cargoAtPeek = selectedStack.peek();
            if(cargo.getStrength().strength > cargoAtPeek.getStrength().strength){
                throw new CargoStrengthException("Cargo is too strong for this stack");
            }
        }
        stacks[stack-1].push(cargo);
        currentWeight += cargo.getWeight();
    }

    /**
     * Pops a cargo from one of the specified stack.
     * @param stack
     *  The index of the stack to remove the cargo from.
     * @return
     *  a Cargo object representing the removed cargo.
     */
    public Cargo popCargo(int stack) throws EmptyStackException{
        if(stack < 1 || stack > stacks.length){
            throw new IllegalArgumentException("Invalid parameters");
        }
        CargoStack selectedStack = stacks[stack-1];
        if(selectedStack.isEmpty()){
            throw new EmptyStackException("Stack is empty");
        }
        Cargo poppedCargo = selectedStack.pop();
        currentWeight -= poppedCargo.getWeight();
        return poppedCargo;
    }

    /**
     * Finds and prints a formatted table of all the cargo on the ship with a given name. If the item could not be found in the stacks, notify the user accordingly.
     * @param name
     *  The name of the cargo to find and print.
     */
    public void findAndPrint(String name){
        int count = 0;
        double totalWeight = 0;
        boolean flag = false;
        String printTable = "";
        printTable += "Cargo '" + name + "' found at the following locations: \n\n";
        printTable += " Stack   Depth   Weight   Strength\n";
        printTable += "=======+=======+========+========== \n";
        DecimalFormat format = new DecimalFormat("0.#");
        for(int i  = 0; i < stacks.length; i++){
            CargoStack currentStack = stacks[i];
            CargoStack tempCargoStack = new CargoStack();
            int size = currentStack.size();
            for(int j = 0; j < size; j++){
                if(!currentStack.isEmpty()){
                    Cargo cargo = currentStack.pop();
                    tempCargoStack.push(cargo);
                    if(cargo.getName().equalsIgnoreCase(name)){
                        flag = true;
                        printTable += String.format("   %-4d|   %-4d|  %-6s|  %-8s", i+1, j, format.format(cargo.getWeight()), cargo.getStrength()) + "\n";
                        count++;
                        totalWeight += cargo.getWeight();
                    }
                }
            }
            while(!tempCargoStack.isEmpty()){
                currentStack.push(tempCargoStack.pop());
            }
        }
        printTable += "\nTotal Count:  " + count + "\n";
        
        printTable += "Total Weight: " + format.format(totalWeight) + "\n";
        if(flag){
            System.out.print(printTable);
        }
        else{
            System.out.println("Cargo '" + name + "' could not be found on the ship.");
        }
    }

    /**
     * This method prints out all the cargo on the cargoShip stack.
     */
    public void printShip(){
        for(int i = 0; i < stacks.length; i++){
            CargoStack currentStack = stacks[i];
            CargoStack tempCargoStack = new CargoStack();
            while(!currentStack.isEmpty()){
                tempCargoStack.push(currentStack.pop());
            }
            System.out.print("Stack " + (i+1) + ": ");
            int size = tempCargoStack.size();
            for(int j = 1; j < size + 1; j++){
                Cargo cargo = tempCargoStack.pop();
                String cargoStrength = "";
                if(cargo.getStrength() == CargoStrength.FRAGILE){
                    cargoStrength = "F";
                }
                else if(cargo.getStrength() == CargoStrength.MODERATE){
                    cargoStrength = "M";
                }
                else if(cargo.getStrength() == CargoStrength.STURDY){
                    cargoStrength = "S";
                }
                currentStack.push(cargo);
                if(j > 1){
                    System.out.print(", " + cargoStrength);
                    continue;
                }
                System.out.print(cargoStrength);
            }
            System.out.println();
        }
    }
}



