/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.response.convergegame;

import metadata.NetworkCode;
import net.response.GameResponse;
import util.GamePacket;

/**
 * ResponseConvergeHintCount returns # of hints in database
 * @author justinacotter
 */
public class ResponseConvergeHintCount extends GameResponse {

    private int count = 0;
    
    public ResponseConvergeHintCount() {
        response_id = NetworkCode.CONVERGE_HINT_COUNT;
    }

    public void setCount (int count) {
        this.count = count;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);

        packet.addInt32(count);

        return packet.getBytes();
    }
}
