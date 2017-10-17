﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class SummonCardAction : TurnAction {

	// TODO: add card variables for building card
	///private int 
	private int cardID {set ; get;}
	private int diet{ set; get;}
	private int level {set ; get ;}
	private int attack { set ; get ;}
	private int health {set; get;}
	private string species_name {set; get;}
	private string type {set; get;}
	private string description {set; get;}





	public SummonCardAction(int intCount, int stringCount, List<int> intList, List<string> stringList):
	base(intCount, stringCount, intList, stringList){ 
		cardID =intList[0];
		diet = intList[1];
		level = intList[2];
		attack = intList[3];
		health = intList[4];
		species_name = stringList[0];
		type = stringList[1];
		description = stringList[2];

	}

	override public void readData(){
		// Probably you can just do this in the constructor
	}

	override public void execute(){
		Debug.Log("TODO: summonCard() in BattlePlayer, all info:" + 
		          cardID + diet+ level+ attack+ health +species_name + type);
		Debug.Log ("Executing Summon Card");

		//GameObject instantiated for Card
		GameObject obj = GameManager.player2.instantiateCard();

			
		
		//Card back for deck
		//GameObject cardBacks = (GameObject) Instantiate(Resources.Load("Prefabs/Battle/card_old"));
		
		//Card front for hand
		obj.AddComponent("AbstractCard");
		AbstractCard script = obj.GetComponent<AbstractCard>();
		
		script.init (GameManager.player2, cardID, diet, level+1, attack, 
		             health, species_name, "Large Animal", description);

		GameManager.player2.cardsInPlay.Add(obj);
		script.handler = new InPlay(script, GameManager.player2);

		GameObject.Destroy((GameObject)GameManager.player2.hand[0]);
		GameManager.player2.hand.RemoveAt(0);

		GameManager.player2.reposition();

		script.transform.rotation =  Quaternion.Euler(script.transform.rotation.x, script.transform.rotation.y, 0); 

	
		Debug.Log(GameManager.player2.cardsInPlay.Count + " Count");
		Debug.Log(script.name + " " + obj.transform.position);

	}
}
