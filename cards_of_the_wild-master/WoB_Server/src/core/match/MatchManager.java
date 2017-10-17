package core.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.Log;
import core.GameServer;
import model.Player;




/*
 * @author Nathanael Aff
 */
public class MatchManager {

    // Singleton Instance
    public static MatchManager manager;
    int matchID = 100;
    // Active battles: <matchID, Match> 
    private final Map<Integer, Match> matchList = new HashMap<Integer, Match>();
    // <playerID, matchID>
    private final Map<Integer, Integer> matchIDList = new HashMap<Integer, Integer>();
    public MatchManager() { }

    public static MatchManager getInstance() {
    	if (manager == null) {
    		manager = new MatchManager();
    	}
    	return manager;
    }
    
    
    public Match createMatch(int playerID1, int playerID2) {
    	List<Player> players = new ArrayList<Player>();
    	Match match = null;
    
    	match = getMatchByPlayer(playerID1);
    	// if match not initialized, initialize match
    	if (match == null){
    		Log.printf("Manager creating new match");
    		matchID = makeMatchID();
	    	System.out.println("Creating match" + matchID);
	    	// get players
	    	players.add(GameServer.getInstance().getActivePlayer(playerID1));
	    	players.add(GameServer.getInstance().getActivePlayer(playerID2));
	    	match = new Match(players, matchID);
	    	//TODO: update when Lobby has design for passing data to games
	    	matchList.put(matchID, match);
	    	matchIDList.put(playerID1, matchID);
	    	matchIDList.put(playerID2, matchID);
    	} 
    	return match;
    }
    
    int makeMatchID(){
    	return ++matchID;
    }	 
    
    public Match getMatch(Integer matchID){
    	return matchList.get(matchID);
    }
    
    public Match getMatchByPlayer(Integer playerID){
    	return matchList.get(matchIDList.get(playerID));
    }
    
    public Integer getMatchIDByPlayer(Integer playerID){
    	return matchIDList.get(playerID);
    }
    

}






