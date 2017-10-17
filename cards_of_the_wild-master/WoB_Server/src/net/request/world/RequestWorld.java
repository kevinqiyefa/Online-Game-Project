package net.request.world;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import core.world.WorldController;
import net.request.GameRequest;

public class RequestWorld extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        if (client.getPlayer().getWorld() == null) {
            WorldController.enterWorld(client.getPlayer(), WorldController.getInstance().first().getID());
        }
    }
}
