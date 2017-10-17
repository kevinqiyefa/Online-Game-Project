using UnityEngine;
using System;


public class InHand : AbstractCardHandler
{
	public InHand(AbstractCard card, BattlePlayer player) : base(card, player) {

	}
	

	override public void affect(){



	//set Max card in field.
	if (player.cardsInPlay.Count < 6 && player.isActive && player.currentMana >= card.getManaCost() && player == GameManager.player1) {
		
		GameObject removeCard = (GameObject)player.hand[player.hand.IndexOf(card.gameObject)];
		
		int temp = 0, temp2 = 0, count = 0;
		string cardName, cardName2, newCardName, newCardName2;
		
	
		player.hand.Remove (removeCard);
		player.currentMana -= card.getManaCost();
	
		//reset hand card postion
		for (int i = 0; i < player.hand.Count; i++) {

			/*cardName = "p1card" + (temp);
			newCardName = "p1card" + temp;*/
			GameObject setCard = (GameObject) player.hand[i];
			setCard.transform.position = new Vector3((player.handPos.x + 280) - 185 * i, player.handPos.y, player.handPos.z);
		}


		player.cardsInPlay.Add (removeCard.gameObject);

	
		card.setCanAttack(false);
		card.handler = new InPlay (card, player);
		Vector3 targetPosition  = new Vector3(player.FieldPos.x + 185 * (player.cardsInPlay.Count - 1), player.FieldPos.y, player.FieldPos.z);
		card.calculateDirection(targetPosition, false);

		GameManager.player1.getProtocolManager().sendSummon (player.playerID, card.cardID,  card.dietNum, 
			                                        card.level, card.dmg,  card.maxHP, 
			                                        card.name,  card.type, 
			                                        card.description);

		}
	
	}
	override public void clicked(){
	
		if(player.isActive)
			affect();
			
		
	}
}


	

