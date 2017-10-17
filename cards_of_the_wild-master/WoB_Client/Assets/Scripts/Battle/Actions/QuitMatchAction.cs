using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class QuitMatchAction : TurnAction {

	public QuitMatchAction(int intCount, int stringCount, List<int> intList, List<string> stringList):
	base(intCount, stringCount, intList, stringList){}	
	
	override public void readData(){
		// Nothing to do 
		
	}
	
	override public void execute(){
		readData ();
		Debug.Log ("Executing QuitMatchAction");
		// Do stuff
	}
}
