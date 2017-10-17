package net.request.world;

// Java Imports
import java.io.IOException;
import java.io.DataInputStream;

// Other Imports
import db.world.WorldZoneDAO;
import net.request.GameRequest;
import net.response.world.ResponseZoneUpdate;
import util.DataReader;

public class RequestZoneUpdate extends GameRequest {

    private int tile_id, owner_id, vegetation_capacity, zone_id, natural_event;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        tile_id = DataReader.readInt(dataInput);
        owner_id = DataReader.readInt(dataInput);
        vegetation_capacity = DataReader.readInt(dataInput);
        zone_id = DataReader.readInt(dataInput);
        natural_event = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        WorldZoneDAO.updateOwner(owner_id, tile_id);
        WorldZoneDAO.updateVegetationCapacity(vegetation_capacity, tile_id);

        ResponseZoneUpdate response = new ResponseZoneUpdate();
        response.setStatus((short) 0);
        client.add(response);
    }
}
