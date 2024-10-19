/**
 * This class represents a container which holds the ‘cargo’ the program will place on the ship. 
 * It contains the following private member variables: name (String), weight (double) and strength (CargoStrength).
*/
public class Cargo {
  private String name;
  private double weight;
  private CargoStrength strength;

  /**
   * Default Constructor.
   * @param initName
   *    Non-null name for the cargo item.
   * @param initWeight
   *    The weight for the cargo.
   * @param initStrength
   *    Either FRAGILE, MODERATE, or STURDY.
   * @throws IllegalArgumentException
   *    If initName is null.
   *    If initWeight ≤ 0.
   */
  public Cargo(String initName, double initWeight, CargoStrength initStrength) throws IllegalArgumentException {
    if(initName == null || initWeight <=0){
        throw new IllegalArgumentException("Invalid name or weight");
    }
    this.name = initName;
    this.weight = initWeight;
    this.strength = initStrength;
  }

  /**
   * Getter method for the name of the cargo.
   * @return
   *    The name of the cargo.
   */
  public String getName(){
    return this.name;
  }

  /**
   * Getter method for the weight of the cargo.
   * @return
   *    The weight of the cargo.
   */
  public double getWeight(){
    return this.weight;
  }

  /**
   * Getter method for the strength of the cargo.
   * @return
   *    The strength of the cargo.
   */
  public CargoStrength getStrength(){
    return this.strength;
  } 
}
