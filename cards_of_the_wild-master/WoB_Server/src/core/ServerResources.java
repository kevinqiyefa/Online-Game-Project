package core;

// Other Imports
import metadata.SpeciesTable;
import util.ConfigureException;
import util.Log;

public class ServerResources {

    private static SpeciesTable speciesTable;

    public static void init() throws ConfigureException {
        speciesTable = new SpeciesTable();
        speciesTable.initialize();
    }

    public static SpeciesTable getSpeciesTable() {
        if (speciesTable == null) {
            try {
                init();
            } catch (ConfigureException ex) {
                Log.println_e(ex.getMessage());
            }
        }

        return speciesTable;
    }
}
