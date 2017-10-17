package model;

// Other Imports
import metadata.Constants;

public class Plant extends Organism {

    public Plant(int plant_id) {
        organism_type = Constants.ORGANISM_TYPE_PLANT;
        organism_id = plant_id;
    }
}
