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
 *
 * @author justinacotter
 */
public class ResponseConvergeNewAttemptScore extends GameResponse {

    private int status;

    public ResponseConvergeNewAttemptScore() {
        response_id = NetworkCode.CONVERGE_NEW_ATTEMPT_SCORE;
    }

    public void setStatus (int status) {
        this.status = status;
    }
    
    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);

        packet.addInt32(status);

        return packet.getBytes();
    }
}
