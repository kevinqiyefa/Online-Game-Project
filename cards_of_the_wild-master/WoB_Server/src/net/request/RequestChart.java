package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import db.CSVDAO;
import core.world.World;
import db.EcosystemDAO;
import model.Ecosystem;
import net.response.ResponseChart;
import util.DataReader;

public class RequestChart extends GameRequest {

    private short type;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        type = DataReader.readShort(dataInput);
    }

    @Override
    public void process() throws Exception {
        World world = client.getPlayer().getWorld();

        if (world != null) {
            Ecosystem ecosystem = EcosystemDAO.getEcosystem(world.getID(), client.getPlayer().getID());

            String csv;
            if (type == 0) {
                csv = CSVDAO.getBiomassCSV(ecosystem.getManipulationID());
            } else {
                csv = CSVDAO.getScoreCSV(ecosystem.getID());
            }

            if (csv != null) {
                ResponseChart response = new ResponseChart();
                response.setType(type);
                response.setCSV(csv);
                client.add(response);
            }
        }
    }
}
