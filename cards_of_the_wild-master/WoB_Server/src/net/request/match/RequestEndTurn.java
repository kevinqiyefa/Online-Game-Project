package net.request.match;

// Java Imports
import java.io.IOException;
import java.io.DataInputStream;
// Other Imports
import metadata.NetworkCode;
import core.match.*;
import metadata.Constants;
import net.response.match.ResponseEndTurn;
import net.request.GameRequest;
import util.DataReader;
import util.Log;

/**
 *  The RequestEndTurn class sets active players status to inactive and
 *  sets other players status to active 
*/

public class RequestEndTurn extends GameRequest {
    private int playerID;  
    
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    	playerID = DataReader.readInt(dataInput);
    }
    
    @Override
    public void process() throws Exception {
    	Log.printf("End of turn");
        
    	ResponseEndTurn response = new ResponseEndTurn();
        MatchManager manager = MatchManager.getInstance();
        Match match = manager.getMatchByPlayer(playerID);
        MatchAction action = new MatchAction();
        if(match == null){
        	Log.printf("match wasn't retrieved");
        }
        
        response.setStatus((short)0);
        action.setActionID(NetworkCode.END_TURN);
        
        // Debugging
        if(match == null){
	    	Log.printf("Match is Null");
	    } else {
	    	Log.printf("Match is valid");
	    }
        
        if(Constants.SINGLE_PLAYER){
        	// do nothing
        } else {
        	match.switchActive();
        }
        
        if(!Constants.SINGLE_PLAYER){
        	match.addMatchAction(playerID, action);
        }
        client.add(response);
        
    }
}
