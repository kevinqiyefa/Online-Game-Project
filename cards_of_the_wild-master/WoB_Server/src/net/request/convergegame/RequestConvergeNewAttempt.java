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
import net.response.convergegame.ResponseConvergeNewAttempt;
import simulation.simjob.SimJobConverge;
import util.DataReader;

/**
 *
 * @author justinacotter
 */
public class RequestConvergeNewAttempt extends GameRequest {

    private int playerId;
    private int ecosystemId;
    private int attemptId;
    private boolean allowHints;
    private int hintId;
    private int timesteps;
    private String config;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        playerId = DataReader.readInt(dataInput);
        ecosystemId = DataReader.readInt(dataInput);
        attemptId = DataReader.readInt(dataInput);
        allowHints = DataReader.readBoolean(dataInput);
        hintId = DataReader.readInt(dataInput);
        timesteps = DataReader.readInt(dataInput);
        config = DataReader.readString(dataInput);
        //Log.consoleln("Parsing RequestConvergeNewAttempt, config = " + config);        
    }

    @Override
    public void process() throws Exception {
        //Log.consoleln("Processing RequestConvergeNewAttempt");
        //run simulation
        SimJobConverge convergeJob = new SimJobConverge(config, timesteps);
        //create response
        ResponseConvergeNewAttempt response = new ResponseConvergeNewAttempt(
                playerId, ecosystemId, allowHints, hintId, config);
        //if sim was successful, save to db and return results to client
        if (convergeJob.getStatus() == Constants.STATUS_SUCCESS) {
            //process sim output
            String csv = convergeJob.getJobCSV();
            response.setCSV(csv);
            //store results to database
            if (!csv.isEmpty()) {
                attemptId = ConvergeAttemptDAO.createAttempt(playerId,
                        ecosystemId,
                        attemptId,
                        allowHints,
                        hintId,
                        config,
                        csv
                );
            }
            response.setAttemptId(attemptId);
        }

        client.add(response);
    }
}
