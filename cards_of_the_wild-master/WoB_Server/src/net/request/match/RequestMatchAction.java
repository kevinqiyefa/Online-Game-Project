package net.request.match;

import java.io.DataInputStream;
import java.io.IOException;
import util.DataReader;
import util.Log;
import core.match.Match;
import core.match.MatchManager;
import core.match.MatchPlayer;
import metadata.Constants;
import net.request.GameRequest;
import net.response.match.ResponseMatchAction;
import core.match.MatchAction;

public class RequestMatchAction extends GameRequest {
	
	private int playerID;
	
	@Override
	public void parse(DataInputStream dataInput) throws IOException {
		// TODO Auto-generated method stub
		playerID = DataReader.readInt(dataInput);
	}

	@Override
	public void process() throws Exception {
		Log.printf("Getting match action, playerID:%d" , playerID);
		ResponseMatchAction response= new ResponseMatchAction();
		 MatchManager manager = MatchManager.getInstance();
	     Match match = manager.getMatchByPlayer(playerID);
	     
	     if (match.actionWaiting(playerID)){
	    	 MatchAction action = match.getMatchAction(playerID);
	    	 Log.printf("Action waiting ID: %d", action.getActionID());
	    	 response.setCode(action.getActionID());
	    	 response.setIntCount(action.getIntCount());
	    	 response.setStringCount(action.getStringCount());
	    	 response.setIntList(action.getIntList());
	    	 response.setStringList(action.getStringList());
	     } else {
	    	// TODO: Constants code for no response
	    	response.setCode((short) 0);
	     }
	    	 
	    client.add(response); 
	}

}
