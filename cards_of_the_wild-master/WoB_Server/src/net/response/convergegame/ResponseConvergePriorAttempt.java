/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.response.convergegame;

import convergegame.ConvergeAttempt;
import metadata.Constants;
import metadata.NetworkCode;
import net.response.GameResponse;
import util.GamePacket;

/**
 * ResponseConvergePriorAttempt returns prior attempts for a specific
 player-ecosystem.  Can only return one record at a time due to size of 
 * csv file. 
 * @author justinacotter
 */
public class ResponseConvergePriorAttempt extends GameResponse {

    private int playerId = Constants.ID_NOT_SET;
    private int ecosystemId = Constants.ID_NOT_SET;
    private int attemptId = Constants.ID_NOT_SET;
    private boolean allowHints = false;
    private int hintId = Constants.ID_NOT_SET;
    private String config = "";
    private String csv = "";
    
    ConvergeAttempt attempt = null;

    public ResponseConvergePriorAttempt(int playerId, int ecosystemId) {
        response_id = NetworkCode.CONVERGE_PRIOR_ATTEMPT;
        this.playerId = playerId;
        this.ecosystemId = ecosystemId;
    }

    public void setConvergePriorAttempt(ConvergeAttempt attempt) {
        this.attempt = attempt;
    }

    @Override
    public byte[] getBytes() {
        GamePacket packet = new GamePacket(response_id);

        //if attempts were found, send first attempt
        if (attempt != null) {
            packet.addInt32(attempt.getPlayerId());
            packet.addInt32(attempt.getEcosystemId());
            packet.addInt32(attempt.getAttemptId());
            packet.addBoolean(attempt.getAllowHints());
            packet.addInt32(attempt.getHintId());
            packet.addString(attempt.getConfig());
            packet.addString(attempt.getCsv());
        } else {
            packet.addInt32(playerId);
            packet.addInt32(ecosystemId);
            packet.addInt32(attemptId);
            packet.addBoolean(allowHints);
            packet.addInt32(hintId);
            packet.addString(config);
            packet.addString(csv);
        }

        return packet.getBytes();
    }
}
