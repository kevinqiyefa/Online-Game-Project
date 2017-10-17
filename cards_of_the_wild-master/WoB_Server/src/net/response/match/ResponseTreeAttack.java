package net.response.match;

// Other Imports
import metadata.NetworkCode;
import util.GamePacket;
import net.response.GameResponse;

/**
 *
 */
public class ResponseTreeAttack extends GameResponse {
    private short status;

    public ResponseTreeAttack() {
        response_id = NetworkCode.TREE_ATTACK;
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
