using UnityEngine;
using System.Collections;

public class GameManager : MonoBehaviour {
	
	public static BattlePlayer player1;
	public static BattlePlayer player2;
	public static GameManager manager;
	public static int matchID;
	public static BattlePlayer curPlayer;
	public static bool opponentIsReady = false;
	public static ProtocolManager protocols;

	// Login tie in -- following pattern of ConvergeGame
	void Awake(){
		// Used for scene transition 
		DontDestroyOnLoad (gameObject.GetComponent ("Login"));
	}
	
	void Start () {
		// Needed to to fade into scene
		Game.StartEnterTransition ();
		protocols = (ProtocolManager)gameObject.AddComponent("ProtocolManager");
		protocols.init ();

		enabled = true;
		player1 = (BattlePlayer)gameObject.AddComponent("BattlePlayer");
		player1.init (true);
		player1.playerID = GameState.player.GetID ();
		Debug.Log("player1 ID : " + player1.playerID);
		player1.playerName = GameState.player.GetName ();

		// initialize match here

		if (Constants.SINGLE_PLAYER){
			player2 = (BattlePlayer)gameObject.AddComponent("BattlePlayer");
			player2.init (false);
			player2.playerName = "Player 2";
		} else {
			// TODO: Need different logic here to make player 2
			// not have cards dealt face up, etx.
			player2 = (BattlePlayer)gameObject.AddComponent("BattlePlayer");
			player2.init (false);
			player2.playerName = "Player 2";
		}
		// Send MatchStatus protocol
		initMatch();
		
		curPlayer = player1;
		GameManager.manager = this;

		// Poll for turn updates
		//StartCoroutine(PollAction(Constants.UPDATE_RATE));
		
	}



	private void initMatch() {
		// matchID variable is accessible via the GameState 
		// adding here for clarity
		player1.isReady = true;
		matchID = GameState.matchID;

		protocols.setMatchStatus();
		
	}


	
	//Keeps all the cards in the field positioned correctly
	//Called by RemoveFromPlay : AbstractCardHandler
	public void repositionField(){
		//Individual player objects responsible for 
		//positioning their active cards on the field
		player1.reposition();
		player2.reposition();
	}

	
	//Ends the current players turn and gives
	//the other player their turn.
	void Update () {
		
		
	}

	// TEMP: activated when EndTurnAction is received.
	public void startTurn(){
		player1.isActive = true;
		player2.isActive = false;
		
		//Deal player 1 a card and set as current player
		GameManager.curPlayer = player1;
		player1.startTurn();
	}
	
	void endTurn(){
	
		// If two_player, only switch turns if player1 isActive
		if(!Constants.SINGLE_PLAYER && player1.isActive){
			protocols.sendEndTurn(player1.playerID);
			//Player's endturn refreshes the player's minions so they can attack next turn.
			player1.endTurn();
			// Not sure if this needs to be here
			player2.endTurn();
		
		}

		if(Constants.SINGLE_PLAYER){
			//Player's endturn refreshes the player's minions so they can attack next turn.
			player1.endTurn();
			// Not sure if this needs to be here
			player2.endTurn();
		//PLAYER 1'S TURN ENDING
			if(player1.isActive){
				
				//the isActive variables switching for next turn
				player1.isActive = false;
				player2.isActive = true;
				
				//Sets the current player to player 2 and deals a card
				GameManager.curPlayer = player2;
				player2.startTurn();

				
			//PLAYER 2'S TURN ENDING
			}else{
				
				//Player 1 is active, Player 2 unactive now			
				player1.isActive = true;
				player2.isActive = false;
				
				//Deal player 1 a card and set as current player
				GameManager.curPlayer = player1;
				player1.startTurn();

			}
		}// End if SinglePlayer
		
	}

	


	//Called many times a second listening for button clicks
	void OnGUI(){
		
		//End turn button, on left side of screen
		//TODO maybe make a new graphic for the button
		if(GUI.Button(new Rect(0, (Screen.height/2.0f), (Screen.width/12.8f)/100 *150, (Screen.width/12.8f)/100 *40), "End Turn")){
			endTurn();
		}		
	}
}
