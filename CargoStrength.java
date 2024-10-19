/**
 * This a simple Enum named CargoStrength, which lists the strength values a cargo container may have. 
*/
public enum CargoStrength{
    FRAGILE(1), 
    MODERATE(2), 
    STURDY(3);

    final int strength;
    CargoStrength(int strength){
        this.strength = strength;
    }
} 