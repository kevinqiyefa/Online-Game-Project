package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

// Other Imports
import core.world.Zone;
import db.EcosystemDAO;
import db.PlayerDAO;
import db.world.WorldZoneDAO;
import model.Ecosystem;
import model.Player;
import net.response.ResponseEcosystem;
import util.DataReader;

public class RequestEcosystem extends GameRequest {

    private int world_id;
    private int player_id;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        world_id = DataReader.readInt(dataInput);
        player_id = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        ResponseEcosystem response = new ResponseEcosystem();

        Player player = PlayerDAO.getPlayer(player_id);

        if (player != null) {
            Ecosystem ecosystem = EcosystemDAO.getEcosystem(world_id, player_id);

            if (ecosystem != null) {
                List<Zone> zones = WorldZoneDAO.getZoneList(world_id, player_id);

                if (!zones.isEmpty()) {
                    response.setStatus(ResponseEcosystem.SUCCESS);
                    response.setEcosystem(ecosystem.getID(), ecosystem.getType(), ecosystem.getScore());
                    response.setPlayer(player);
                    response.setZones(zones);
                } else {
                    response.setStatus(ResponseEcosystem.FAILED);
                }
            } else {
                response.setStatus(ResponseEcosystem.FAILED);
            }
        } else {
            response.setStatus(ResponseEcosystem.INVALID_PLAYER);
        }

        client.add(response);
    }
}
