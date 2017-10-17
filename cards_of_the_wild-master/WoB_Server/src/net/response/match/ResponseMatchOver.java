package net.response.match;

//Other Imports
import metadata.NetworkCode;
import util.GamePacket;
import net.response.GameResponse;

public class ResponseMatchOver extends GameResponse{
	private short status;

	public ResponseMatchOver() {
        response_id = NetworkCode.MATCH_OVER;
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
