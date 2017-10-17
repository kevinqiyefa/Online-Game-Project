package net.request.match;
import java.io.DataInputStream;
import java.io.IOException;

import core.match.MatchManager;
import util.DataReader;
import util.Log;
import net.request.GameRequest;
import net.response.match.ResponseMatchInit;
import core.match.*;

public class RequestMatchInit extends GameRequest {

	private int playerID1;
	private int playerID2;
	
	@Override
	public void parse(DataInputStream dataInput) throws IOException {
		playerID1 = DataReader.readInt(dataInput);
		playerID2 = DataReader.readInt(dataInput);
	}

	@Override
	public void process() throws Exception {
		ResponseMatchInit response = new ResponseMatchInit();
		MatchManager manager = MatchManager.getInstance();
		short status; 
		int matchID = 0;
	
		// Assume player is in DB otherwise 
		Match match = manager.createMatch(playerID1, playerID2);
		if(match != null){
			// TODO: add response success constant
			status = 0;
			matchID = match.getMatchID();
		} else {
			// status !=0 means failure
			status = 1;
			Log.printf("Failed to create Match");
		}
		Log.printf("Initializing match for players '%d' and '%d' in match %d", 
				playerID1, playerID2, matchID);
	
		response.setStatus(status);
		response.setMatchID(matchID);
		client.add(response);
	}
}
