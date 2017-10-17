using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class TreeAttackAction : TurnAction {

	private int attackersPosition;

	public TreeAttackAction(int intCount, int stringCount, List<int> intList, List<string> stringList):
	base(intCount, stringCount, intList, stringList){}	
	
	override public void readData(){
		attackersPosition = (int)intList[0];
	}
	
	override public void execute(){
		readData ();
		Debug.Log ("Executing TreeAttackAction");
		// Do stuff
	}
}
