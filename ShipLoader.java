/**
 * This class allows a user to create an instance of the CargoShip class, and also provides an interface for a user to manipulate the ship by creating, 
 * adding, and moving Cargo objects. In addition to the CargoShip, the ShipLoader utilizes another CargoStack called dock, 
 * which is the loading/unloading stack for Cargo being moved to/from the ship.
*/

import java.text.DecimalFormat;
import java.util.*;
public class ShipLoader {
    private static Stack<Cargo> dock = new Stack<Cargo>();
    private static CargoShip cargoShip;
    private static double maxWeight;
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to ShipLoader!\n");
        System.out.println("Cargo Ship Parameters");
        System.out.println("--------------------------------------------------");
        System.out.print("Number of stacks: ");
        int numStacks = scan.nextInt();
        System.out.print("Maximum height of stacks: ");
        int maxHeight = scan.nextInt();
        System.out.print("Maximum total cargo weight: ");
        maxWeight = scan.nextInt();
        scan.nextLine();
        cargoShip = new CargoShip(numStacks, maxHeight, maxWeight);
        
        System.out.println("\nCargo ship created.");
        System.out.println("Pulling ship in to dock...");
        System.out.println("Cargo ship ready to be loaded.");
        printMenu();
        String userInput = scan.nextLine().trim().toUpperCase();
        System.out.println();
        while(!userInput.equals("Q")){
            switch(userInput){
                case "C":
                    System.out.print("Enter the name of the cargo: ");
                    String cargoName = scan.nextLine();
                    System.out.print("Enter the weight of the cargo: ");
                    double cargoWeight = scan.nextDouble();
                    scan.nextLine();
                    System.out.print("Enter the container strength (F/M/S): ");
                    String cargoStrength = scan.nextLine().trim().toUpperCase();
                    Cargo newCargo = null;
                    if(!"FMS".contains(cargoStrength)){
                        System.out.println("Operation failed! Invalid strength.");
                        break;
                    }
                    if(cargoStrength.equals("F")){
                        newCargo = new Cargo(cargoName, cargoWeight, CargoStrength.FRAGILE);
                    }
                    else if(cargoStrength.equals("M")){
                        newCargo = new Cargo(cargoName, cargoWeight, CargoStrength.MODERATE);
                    }
                    else if(cargoStrength.equals("S")){
                        newCargo = new Cargo(cargoName, cargoWeight, CargoStrength.STURDY);
                    }
                    if(!dock.isEmpty()){
                        Cargo peekCargo = dock.peek();
                        if(peekCargo.getStrength().strength < newCargo.getStrength().strength){
                            System.out.println("\nOperation failed! Cargo at top of stack cannot support weight.");
                            break;
                        }
                    }
                    dock.push(newCargo);
                    System.out.println("\nCargo '" + cargoName + "' pushed onto the dock.\n");
                    printStackDockWeight();
                    break;
                case "L":
                    System.out.print("Select the load destination stack index: ");
                    int destinationStackIdex = scan.nextInt();
                    scan.nextLine();
                    Cargo destinationCargo = dock.peek();
                    try {
                        cargoShip.pushCargo(destinationCargo, destinationStackIdex);
                    } catch (FullStackException e) {
                        System.out.println("Operation failed! Cargo stack is at maximum height.");
                        break;
                    }
                    catch (ShipOverweightException e) {
                        System.out.println("Operation failed! Cargo would put ship overweight.");
                        break;
                    }
                    catch (CargoStrengthException e) {
                        System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Operation failed! Cargo is null or stack is not in the appropriate bounds.");
                        break;
                    }
                    dock.pop();
                    System.out.println("\nCargo '" + destinationCargo.getName() +  "' moved from dock to stack " + destinationStackIdex + ".\n");
                    printStackDockWeight();
                    break;
                case "U":
                    System.out.print("Select the unload source stack index: ");
                    int sourceStackIdex = scan.nextInt();
                    scan.nextLine();
                    Cargo removedCargo = null;
                    try {
                        removedCargo = cargoShip.popCargo(sourceStackIdex);
                    } catch (Exception e) {
                        System.out.println("Operation failed! Stack is empty.");
                        break;
                    }
                    if(dock.size() > 1){
                        Cargo peekCargo = dock.peek(); 
                        if(peekCargo.getStrength().strength < removedCargo.getStrength().strength){
                            System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        }
                        break;
                    }
                    dock.push(removedCargo);
                    System.out.println("Cargo '" + removedCargo.getName() + "'moved from stack " + sourceStackIdex +" to dock.");
                    printStackDockWeight();
                    break;
                case "M":
                    System.out.print("Source stack index: ");
                    int sourceIndex = scan.nextInt();
                    System.out.print("Destination stack index: ");
                    int destinationIndex = scan.nextInt();
                    scan.nextLine();
                    Cargo sourceCargo = null;
                    try {
                        sourceCargo = cargoShip.popCargo(sourceIndex);
                    } catch (EmptyStackException e) {
                        System.out.println("Operation failed! Stack is empty.");
                    }
                    try {
                        cargoShip.pushCargo(sourceCargo, destinationIndex);
                    } catch (FullStackException e) {
                        System.out.println("Operation failed! Cargo stack is at maximum height.");
                        break;
                    }
                    catch (ShipOverweightException e) {
                        System.out.println("Operation failed! Cargo would put ship overweight.");
                        break;
                    }
                    catch (CargoStrengthException e) {
                        System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Operation failed! Cargo is null or stack is not in the appropriate bounds.");
                        break;
                    }
                    System.out.println("Cargo '" + sourceCargo.getName() + "' moved from stack " + sourceIndex + " to stack " + destinationIndex + ".");
                    System.out.println();
                    printStackDockWeight();
                    break;
                case "K":
                    dock.clear();
                    System.out.println("Dock cleared.");
                    System.out.println();
                    printStackDockWeight();
                    break;
                case "P":
                    cargoShip.printShip();
                    break;
                case "S":
                    System.out.print("Enter the name of the cargo: ");
                    String nameOfCargo = scan.nextLine();
                    System.out.println();
                    cargoShip.findAndPrint(nameOfCargo);
                    break;
                default:
                    System.out.println("Operation failed! Invalid input. Try again.");
                    break;
            }
            printMenu();
            userInput = scan.nextLine().trim().toUpperCase();
            System.out.println();
        }
        System.out.println("Program terminating normally...");
    }
    
    /**
     * This method prints out the options for the user to choose from.
     */
    public static void printMenu(){
        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("C) Create new cargo");
        System.out.println("L) Load cargo from dock");
        System.out.println("U) Unload cargo from ship");
        System.out.println("M) Move cargo between stacks");
        System.out.println("K) Clear dock");
        System.out.println("P) Print ship stacks");
        System.out.println("S) Search for cargo");
        System.out.println("Q) Quit");
        System.out.print("\nSelect a menu option: ");
    }

    /**
     * This method prints information regarding the dock.
     */
    public static void printDock(){
        Stack<Cargo> tempCargoStack = new Stack<Cargo>();
        while(!dock.isEmpty()){
            tempCargoStack.push(dock.pop());
        }
        System.out.print("Dock: ");
        int size = tempCargoStack.size();
        for(int j = 0; j < size; j++){
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
            dock.push(cargo);
            if(j == 0){
                System.out.print(cargoStrength);
            }
            else{
                System.out.print(", " + cargoStrength);
            }
        }
    }  

    /**
     * This method prints information about the cargo ship, dock, and the weight.
     */
    public static void printStackDockWeight(){
        cargoShip.printShip();
        printDock();
        System.out.println("\n");
        DecimalFormat format = new DecimalFormat("0.#");
        System.out.println("Total Weight = " + format.format(cargoShip.getCurrentWeight()) + " / " + format.format(maxWeight));
    }
}
