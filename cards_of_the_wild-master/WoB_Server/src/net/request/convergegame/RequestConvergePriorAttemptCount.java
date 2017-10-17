/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request.convergegame;

import db.ConvergeAttemptDAO;
import java.io.DataInputStream;
import java.io.IOException;
import metadata.Constants;
import net.request.GameRequest;
import net.response.convergegame.ResponseConvergePriorAttemptCount;
import util.DataReader;
import util.Log;

/**
 *
 * @author justinacotter
 */
public class RequestConvergePriorAttemptCount extends GameRequest {
    
    private int playerId;
    private int ecosystemId;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        playerId = DataReader.readInt(dataInput);
        ecosystemId = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        ResponseConvergePriorAttemptCount response = new ResponseConvergePriorAttemptCount(
        playerId, ecosystemId);
        //if ecosystem not specified, return most recent ecosystem's first attempt
        //for this player
        if (ecosystemId == Constants.ID_NOT_SET) {
            response.setConvergePriorAttemptInfo(
                    ConvergeAttemptDAO.getMostRecentConvergeAttemptCount(playerId));
        } else {
            response.setCount(
                    ConvergeAttemptDAO.getConvergeAttemptCount(
                    playerId, ecosystemId));
        }
        client.add(response);
        //Log.consoleln("Processed RequestConvergePriorAttemptCount");
    }
}
