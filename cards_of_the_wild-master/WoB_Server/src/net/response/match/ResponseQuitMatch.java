package net.response.match;

// Other Imports
import metadata.NetworkCode;
import util.GamePacket;
import net.response.GameResponse;

/**
 *  Sets other player's status to inactive
 */
public class ResponseQuitMatch extends GameResponse {
    private short status;

    public ResponseQuitMatch() {
        response_id = NetworkCode.QUIT_MATCH;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);

        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
