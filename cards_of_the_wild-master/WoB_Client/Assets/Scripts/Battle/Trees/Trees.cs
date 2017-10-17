using UnityEngine;
using System.Collections;

public class Trees : MonoBehaviour {

	private int hp,maxHP;
	private BattlePlayer player;
	private TreesHandler handler;


	Texture2D tree1Texture = (Texture2D) Resources.Load ("Prefabs/Battle/tree1", typeof(Texture2D));
	Texture2D tree2Texture = (Texture2D) Resources.Load ("Prefabs/Battle/tree2", typeof(Texture2D));
	Texture2D tree3Texture = (Texture2D) Resources.Load ("Prefabs/Battle/tree3", typeof(Texture2D));

	// Use this for initialization
	void Start () {

	}

	public void init(BattlePlayer player){
		this.player = player;
		maxHP =hp= 11; 

		if (player.player1) { //Your name is pink
			//transform.Find ("NameText").GetComponent<TextMesh> ().text = this.player.playerName;
			transform.Find ("NameText").GetComponent<TextMesh> ().color = Color.magenta;
		} else { //Enemy name is red
			//transform.Find ("NameText").GetComponent<TextMesh> ().text = this.player.playerName;
			transform.Find("NameText").GetComponent<TextMesh>().color = Color.red;
		}
		handler = new LivingTreeClick(this, player);
		transform.position = new Vector3(player.TreePos.x, player.TreePos.y, player.TreePos.z);
	}
	
	
	
	//OnMouseOver does not accept mouse clicks if mouse is not moving when the input from user
	//was received. I don't think it's necessary for the tree to get larger when the player clicks the tree
//	void OnMouseDown () 
//	{
//		if(handler != null)
//			handler.clicked ();
//
//	}
	void OnMouseOver ()
	{
		if (Input.GetMouseButtonDown (0)) {
			if(handler != null )
				handler.clicked ();
		}
		this.transform.localScale = new Vector3 (30, 1, 30);
	}
	
	void OnMouseExit()
	{
		this.transform.localScale = new Vector3 (25, 1, 25);

	}

	/*public void attack(Trees target){
		
		GameManager.curPlayer.clickedCard.calculateDirection (target.transform.position, true);
		target.receiveAttack(GameManager.curPlayer.clickedCard.getDamage());	
	}*/

	public void receiveAttack(int dmg){
		
		hp -= dmg;
		Debug.Log ("Was dealt " + dmg + " damage and is now at " + hp + " hp");
		
		if(hp <= 0){
			Debug.Log("End Game");	
			handler = new EndGame(this, player);
			handler.affect();
		}
		
	}



	// Update is called once per frame
	void Update () {
		//Display health and change texture accordingly
		transform.Find("HealthText").GetComponent<TextMesh>().text = hp.ToString();
		if(hp <= (maxHP)/4) { //Under 1/4 hp
			renderer.material.mainTexture = tree3Texture;
			transform.Find("HealthText").GetComponent<TextMesh>().color = Color.red;
		} else if (hp <= (3*maxHP)/4) {//Under 3/4 hp
			renderer.material.mainTexture = tree2Texture;
			transform.Find("HealthText").GetComponent<TextMesh>().color = Color.yellow;
		} else if (hp > (3*maxHP)/4) { //Over 3/4 hp
			renderer.material.mainTexture = tree1Texture;
			transform.Find ("HealthText").GetComponent<TextMesh> ().color = Color.green;
		}
		if (GameManager.curPlayer==player) {
			transform.Find ("NameText").GetComponent<TextMesh> ().text = ">>"+this.player.playerName+"<<";
		} else { //Enemy name is red
			transform.Find ("NameText").GetComponent<TextMesh> ().text = this.player.playerName;
		}
	}
}
