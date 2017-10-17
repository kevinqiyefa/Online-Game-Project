package net.request.match;

import java.io.DataInputStream;
import java.io.IOException;

import util.DataReader;
import util.Log;
import core.match.Match;
import core.match.MatchAction;
import core.match.MatchManager;
import core.match.MatchPlayer;
import metadata.Constants;
import metadata.NetworkCode;
import net.request.GameRequest;
import net.response.match.ResponseDealCard;

public class RequestDealCard extends GameRequest {

	private int playerID;   
    private int handPosition;
 
    
    @Override
	public void parse(DataInputStream dataInput) throws IOException {
       playerID = DataReader.readInt(dataInput);
       handPosition = DataReader.readInt(dataInput);

    }
   

    @Override
    public void process() throws Exception {
     
        ResponseDealCard response = new ResponseDealCard();   
        MatchManager manager = MatchManager.getInstance();
        Match match = manager.getMatchByPlayer(playerID);
        if(match == null){
        	Log.printf("Match is null, playerID = %d\n", playerID);
        }
        MatchAction action = new MatchAction();
        action.setActionID(NetworkCode.DEAL_CARD); 
        action.setIntCount(1);
        action.addInt(handPosition);
       
        response.setStatus((short)0);
        
        client.add(response);
        if(!Constants.SINGLE_PLAYER){
        	match.addMatchAction(playerID, action);
        }
    }
}