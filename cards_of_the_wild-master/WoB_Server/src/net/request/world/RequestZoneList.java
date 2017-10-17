package net.request.world;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import core.world.World;
import db.world.WorldZoneDAO;
import net.request.GameRequest;
import net.response.world.ResponseZoneList;

public class RequestZoneList extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        ResponseZoneList response = new ResponseZoneList();

        World world = client.getPlayer().getWorld();

        if (world != null) {
            response.setStatus(ResponseZoneList.SUCCESS);
            response.setZonePlayers(WorldZoneDAO.getZonePlayers(world.getID()));
            response.setZoneList((short) 40, (short) 40, world.getZoneList());
        } else {
            response.setStatus(ResponseZoneList.FAILED);
        }

        client.add(response);
    }
}
