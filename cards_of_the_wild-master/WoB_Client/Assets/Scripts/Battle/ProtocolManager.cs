using UnityEngine;
using System.Collections;

public class ProtocolManager : MonoBehaviour{


	// Use this for initialization
	void Start () {
		// Start pollilng 
		StartCoroutine(PollAction(Constants.UPDATE_RATE));
	}
	
	// Update is called once per frame
	void Update () {

	}



	public void init() {
		Debug.Log("init Protocol Manager");
		//StartCoroutine(PollAction(Constants.UPDATE_RATE));
	}

	private IEnumerator PollAction(float time) {
		
		while (true) {
			Debug.Log ("PollAction ");
			NetworkManager.Send(MatchActionProtocol.Prepare(GameManager.player1.playerID), processMatchAction );
			yield return new WaitForSeconds(time);
		}
	}
	
	


	public void processMatchAction(NetworkResponse response){
		ResponseMatchAction args = response as ResponseMatchAction;
				short actionID = args.actionID;

		if(actionID != 0){
			TurnAction action = args.action;
			action.execute();
		}
		Debug.Log ("Match Action code:= " + args.actionID);
	}





	
	private void initMatch() {
		// matchID variable is accessible via the GameState 
		// adding here for clarity
		GameManager.player1.isReady = true;
		GameManager.matchID = GameState.matchID;
		
		setMatchStatus();
		
	}
	
	
	// Let server know that you are logged into the game
	public void setMatchStatus() {
		Debug.Log("Setting match for playerID: " + GameManager.player1.playerID);
		//Informs the Server we are ready for take off
		NetworkManager.Send (
			MatchStatusProtocol.Prepare (GameManager.player1.playerID, GameManager.player1.playerName), 
			ProcessMatchStatus);
	}
	
	
	
	//Response to the server's ready request
	public void ProcessMatchStatus(NetworkResponse response) {
		
		//Retrieve the response arguments
		ResponseMatchStatus args = response as ResponseMatchStatus;
		
		//Request the deck from Server
		GetDeck ();	
		//Set variables passed in from server related to the match's status
		GameManager.player1.isActive = args.isActive;	
		GameManager.opponentIsReady = args.opponentIsReady;
		
		//Display the arguments passed from server
		Debug.Log("GameManager.player1 isActive: " + GameManager.player1.isActive +
		          " opponentIsReady " + GameManager.opponentIsReady );
		
	}

	
	public void sendSummon(int playerID, int cardID,  int diet, 
	                      int level, int attack, int health, 
	                       string species_name,  string type, 
	                      string description){

			NetworkManager.Send (
			SummonCardProtocol.Prepare ( playerID, cardID,  diet, 
		                             level, attack,  health, 
		                            species_name,  type, 
		                            description), 
				ProcessSummonCard);
	}
	
	public void ProcessSummonCard(NetworkResponse response) {
		ResponseSummonCard args = response as ResponseSummonCard;
		
		Debug.Log("Summon Response MatchID:  " );
	}

	
	public void GetDeck () {
		
		NetworkManager.Send ( GetDeckProtocol.Prepare (GameManager.player1.playerID), 
		                     ProcessGetDeck);
	}

	// Get deck for player1 -- player2 only if Single player
	public void ProcessGetDeck(NetworkResponse response){
		ResponseGetDeck args = response as ResponseGetDeck;
		int numFields = args.numFields;
		int numCards = args.numCards;
		DeckData deck = args.deck;
		//CardData card = deck.popCard();
		
		GameManager.player1.setDeck(deck);
		GameManager.player1.dealCard(3);
		// Don't allow player1 to move cards until they hasDeck
		GameManager.player1.hasDeck = true;
		
		// Only deal cards for opponent if 
		if(Constants.SINGLE_PLAYER){
			Debug.Log ("Deal player2 Cards");
			GameManager.player2.setDeck (deck);
			GameManager.player2.dealCard (3);
			
		}
		
		Debug.Log("Protocols Deck Response: deck count: " + deck.getSize() + 
		          " numCards: " + numCards + " num fields: " + numFields); 
		//Debug.Log ("Card data: cardID: " + card.cardID + " diet Type: " +
		//          card.dietType + " SpeciesName: " + card.speciesName);
		
	}



	
	public void sendDealCard( int playerID, int handPosition){
	// Probably not needed -- can automatically set player2 cards dealt
	// on turn switch
		
		NetworkManager.Send ( DealCardProtocol.Prepare (playerID, handPosition), 
		                     ProcessDealCard);	
	}
	

	public void ProcessDealCard(NetworkResponse response){
		ResponseDealCard args = response as ResponseDealCard;
		
		Debug.Log ("DealCard status" + args.status);
		// TODO: 
		//dealDummyCard(1);
		
	}



	public void sendEndTurn (int playerID){
		NetworkManager.Send ( EndTurnProtocol.Prepare (playerID), 
		                     ProcessEndTurn);
		GameManager.player1.isActive = false;
	}
	

	public void ProcessEndTurn(NetworkResponse response){
		ResponseEndTurn args = response as ResponseEndTurn;

		Debug.Log ("End Turn Response: isActive :" + args.status);
	}


	// Sent when attacking tree
	public void sendTreeAttack(int playerID, int fieldPosition){
		NetworkManager.Send (
			TreeAttackProtocol.Prepare (playerID, fieldPosition), 
			ProcessTreeAttack);
	}

	
	public void ProcessTreeAttack(NetworkResponse response) {
		ResponseTreeAttack args = response as ResponseTreeAttack;
		
		Debug.Log("TreeAttack attack: " + args.status );
		
	}


	// Sent when attacking opponents cards
	public void sendCardAttack(int playerID, int attackersPosition, int attackedPosition){
		NetworkManager.Send (
			CardAttackProtocol.Prepare (playerID, attackersPosition, attackedPosition), 
			ProcessCardAttack);
	}
	
	public void ProcessCardAttack(NetworkResponse response) {
		ResponseCardAttack args = response as ResponseCardAttack;
		
		Debug.Log("CardAttack MatchID:  " + args.status);
		
	}


	// Send if "leave/quit match" button clicked
	public void sendQuitMatch (int playerID){
		NetworkManager.Send ( QuitMatchProtocol.Prepare (playerID), 
		                     ProcessQuitMatch);
	}
	
	public void ProcessQuitMatch(NetworkResponse response){
		ResponseQuitMatch args = response as ResponseQuitMatch;
		bool opponentReadyResponse = false;
		Debug.Log ("Quit Match Response: opponentIsReady :" + opponentReadyResponse);
	}
	// Sent if player wins game
	public void TestGameOver(int playerID){
		int wonGame = 1;
		NetworkManager.Send ( MatchOverProtocol.Prepare (playerID, wonGame), 
		                     ProcessGameOver);	
	}
	
	public void ProcessGameOver(NetworkResponse response) {
		ResponseMatchOver args = response as ResponseMatchOver;
		int statusResponse = args.status;
		Debug.Log ("MatchOver status: " + statusResponse);
		
	}


}
