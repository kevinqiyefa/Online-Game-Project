package net.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;

// Other Imports
import core.GameServer;
import net.response.ResponsePlayers;

public class RequestPlayers extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        ResponsePlayers response = new ResponsePlayers();
        response.setPlayers(GameServer.getInstance().getActivePlayers());
        client.add(response);
    }
}
