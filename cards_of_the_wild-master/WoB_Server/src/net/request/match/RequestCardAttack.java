package net.request.match;

// Java Imports
import java.io.IOException;
import java.io.DataInputStream;
// Other Imports


import core.match.*;
import metadata.Constants;
import metadata.NetworkCode;
import net.response.match.ResponseCardAttack;
import net.request.GameRequest;
import util.DataReader;
import util.Log;

/**
 *  The RequestCardAttack class sends attack data to player2
*/

public class RequestCardAttack extends GameRequest {
    private int playerID;   // match ID number
    private int attackersPosition;   // attack value from player 1
    private int attackedPosition;  // position of player 2

    
    @Override
	public void parse(DataInputStream dataInput) throws IOException {
	   playerID = DataReader.readInt(dataInput);
       attackersPosition = DataReader.readInt(dataInput);
       attackedPosition = DataReader.readInt(dataInput);
	}
    
    @Override
    public void process() throws Exception {

        ResponseCardAttack response = new ResponseCardAttack();
        MatchManager manager = MatchManager.getInstance();
        Match match = manager.getMatchByPlayer(playerID);
        MatchAction action = new MatchAction();
        action.setActionID(NetworkCode.CARD_ATTACK); 
        action.setIntCount(2);
        action.addInt(attackersPosition);
        action.addInt(attackedPosition);
        
        
        // TODO: update log
        Log.printf("Player '%d' is attacking '%d'", 1, 2);
    
        response.setStatus((short)0);
    	
        if(!Constants.SINGLE_PLAYER){
        	match.addMatchAction(playerID, action);
        }
        client.add(response);
       
    }
	
}
    
