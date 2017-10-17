package net.response.match;

import util.GamePacket;
import metadata.NetworkCode;
import net.response.GameResponse;

public class ResponseDealCard extends GameResponse {

	private short status;
    
    public ResponseDealCard() {
        response_id = NetworkCode.DEAL_CARD;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);
        packet.addInt32(status);
         

        return packet.getBytes();
    }
    
    public void setStatus(short status){
    	this.status = status;
    }
   


}
