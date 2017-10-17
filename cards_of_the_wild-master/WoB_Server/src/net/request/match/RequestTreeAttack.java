package net.request.match;

// Java Imports
import java.io.IOException;
import java.io.DataInputStream;
// Other Imports


import core.match.*;
import metadata.Constants;
import metadata.NetworkCode;
import net.response.match.ResponseTreeAttack;
import net.request.GameRequest;
import util.DataReader;
import util.Log;

/**
 *  The RequestTreeAttack class sends attack data to player2
*/

public class RequestTreeAttack extends GameRequest {
    private int playerID;   // changed from match ID number
    private int attackersPosition;   // attack value from player 1
   

    
    @Override
	public void parse(DataInputStream dataInput) throws IOException {
	   playerID = DataReader.readInt(dataInput);
       attackersPosition = DataReader.readInt(dataInput);
	}
    
    @Override
    public void process() throws Exception {
        ResponseTreeAttack response = new ResponseTreeAttack();
        MatchAction action = new MatchAction();
        
        action.setActionID(NetworkCode.TREE_ATTACK);
        action.setIntCount(1);
        action.setStringCount(0);
        action.addInt(attackersPosition);
        
        MatchManager manager = MatchManager.getInstance();
        Match match = manager.getMatchByPlayer(playerID);
    
    
        response.setStatus((short)0);
        
    	client.add(response);
        if (!Constants.SINGLE_PLAYER){
        	match.addMatchAction(playerID, action);
        }
   }

	
}
