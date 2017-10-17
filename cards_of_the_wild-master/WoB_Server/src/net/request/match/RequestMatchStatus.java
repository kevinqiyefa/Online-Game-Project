package net.request.match;

import java.io.DataInputStream;
import java.io.IOException;

import core.match.*;
import util.DataReader;
import util.Log;
import metadata.Constants;
import metadata.NetworkCode;
import net.request.GameRequest;
import net.response.match.ResponseMatchStatus;

public class RequestMatchStatus extends GameRequest {
	private int playerID;
	private Boolean isActive;
	private Boolean opponentIsReady;
	private String playerName;
	
	@Override
	public void parse(DataInputStream dataInput) throws IOException {
		playerID = DataReader.readInt(dataInput);
		playerName = DataReader.readString(dataInput);
	}

	@Override
	public void process() throws Exception {
		Log.printf("Setting match status for player '%d, %s'", playerID, playerName);
		
		MatchManager manager = MatchManager.getInstance();
		Match match = manager.getMatchByPlayer(playerID);
		
		// Set ready true	
		match.getPlayer(playerID).setDeck();
		match.getPlayer(playerID).setReady(true);
		// Adds name to opponent's player2
		
		MatchAction action = new MatchAction();
	    action.setActionID(NetworkCode.MATCH_STATUS);
		action.setIntCount(0);
		action.setStringCount(1);
	    action.addString(playerName); 
		
	    // Debugging
	    if(match == null){
	    	Log.printf("Match is Null");
	    } else {
	    	Log.printf("Match is valid");
	    }
	    int matchID = match.getMatchID();
	    
		if(Constants.SINGLE_PLAYER){
			opponentIsReady = true;
			isActive = true;
		} else {
			match.addMatchAction(playerID, action);
			opponentIsReady = match.isOpponentReady(playerID);
			
			// If opponent is ready they should be active but double checking
			if (match.isOpponentActive(playerID)){
				Log.printf("Player: playerID '%d' is not Active", playerID);
				isActive = false;
				match.getPlayer(playerID).setActive(isActive);
			} else {
				Log.printf("Setting playerID '%d' active", playerID);	
				isActive = true;
				match.getPlayer(playerID).setActive(isActive);
			}
		}
		
		ResponseMatchStatus response = new ResponseMatchStatus();
		response.setMatchID(matchID);
		response.setIsActive(isActive);
		response.setIsReady(opponentIsReady);
		client.add(response);
	}
	
}
