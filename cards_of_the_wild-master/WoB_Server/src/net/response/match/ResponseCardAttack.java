package net.response.match;

// Other Imports
import metadata.NetworkCode;
import util.GamePacket;
import net.response.GameResponse;

/**
 *
 */
public class ResponseCardAttack extends GameResponse {
    private short status;

    public ResponseCardAttack() {
        response_id = NetworkCode.CARD_ATTACK;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addShort16(status);

        return packet.getBytes();
    }

    public void setStatus(short status){
    	this.status = status;
    }
}
