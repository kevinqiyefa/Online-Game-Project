package net.request.world;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import core.world.WorldController;
import net.request.GameRequest;
import net.response.world.ResponseZone;
import net.response.world.ResponseZoneUpdate;
import util.DataReader;
import util.Log;
import util.NetworkFunctions;

/**
 *
 * @author Ari
 */
public class RequestZone extends GameRequest {

    private int zone_id;
    private int player_id;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        zone_id = DataReader.readInt(dataInput);
        player_id = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        try {
            ResponseZone response = new ResponseZone();

            if (WorldController.getInstance().isOwned(zone_id) == true) {
                response.setStatus((short) 1);
            } else {
                response.setStatus((short) 0);
                WorldController.getInstance().ownZone(player_id, zone_id);

                ResponseZoneUpdate responseTileUpdate = new ResponseZoneUpdate();
                responseTileUpdate.setStatus((short) 0);
                responseTileUpdate.setTileId(zone_id);
                responseTileUpdate.setTileOwner(player_id);
                NetworkFunctions.sendToGlobal(responseTileUpdate);
            }

            client.add(response);
        } catch (NullPointerException e) {
            Log.printf("Error fetching tile %d from database.", zone_id);
        }
    }
}
