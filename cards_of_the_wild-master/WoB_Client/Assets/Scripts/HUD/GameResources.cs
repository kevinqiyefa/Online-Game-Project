using UnityEngine;

using System;
using System.Collections;

public class GameResources : MonoBehaviour {

	public GUISkin skin;
	
	private int credits;
	private int coins;

	void Awake() {
		skin = Resources.Load("Skins/DefaultSkin") as GUISkin;
	}

	// Use this for initialization
	void Start () {

	}
	
	// Update is called once per frame
	void Update () {
		try {
//			credits = GameState.world.credits;
			credits = 100;
		} catch (NullReferenceException e) {
		}
	}
	
	void OnGUI() {
		GUI.BeginGroup(new Rect(Screen.width - 150 - 20, 10, 150, 70), GUI.skin.box);

			GUIStyle style = new GUIStyle(skin.label);
			style.font = skin.font;
			style.fontSize = 20;
			style.alignment = TextAnchor.UpperRight;
			
			GUIExtended.Label(new Rect(-10, 10, 150, 50), credits.ToString("n0") + " Credits", style, Color.black, Color.green);
	
			style = new GUIStyle(skin.label);
			style.font = skin.font;
			style.fontSize = 20;
			style.alignment = TextAnchor.UpperRight;
	
			GUIExtended.Label(new Rect(-10, 30, 150, 50), coins.ToString("n0") + " Coins", style, Color.black, Color.yellow);

		GUI.EndGroup();
	}
	
	public void SetCredits(int credits) {
		this.credits = credits;
	}
	
	public void SetCoins(int coins) {
		this.coins = coins;
	}
}
