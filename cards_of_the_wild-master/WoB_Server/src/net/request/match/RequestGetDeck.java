package net.request.match;

// Java Imports
import java.io.IOException;
import java.io.DataInputStream;
// Other Imports


import core.match.*;
import metadata.Constants;
import net.response.match.ResponseGetDeck;
import net.request.GameRequest;
import util.DataReader;
import util.Log;
import model.CardDeck;

/**
 *  The RequestGetDeck class sends attack data to player2
*/

public class RequestGetDeck extends GameRequest {
    private int playerID;
	
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        playerID = DataReader.readInt(dataInput);
    }
    
    @Override
    public void process() throws Exception {
    	MatchManager manager = MatchManager.getInstance();
        Match match = manager.getMatchByPlayer(playerID);
        ResponseGetDeck opponentResponse = new ResponseGetDeck();    

        Log.printf("Player '%d' is gettting Deck ", playerID);
        
        CardDeck cardDeck = match.getPlayer(playerID).getDeck();
        opponentResponse.setNumCards(cardDeck.getDeckSize());
        //TODO: This field is not used on client side 
        opponentResponse.setNumFields(7);
        opponentResponse.setDeck(cardDeck);
        
        client.add(opponentResponse);
   }
    
}

