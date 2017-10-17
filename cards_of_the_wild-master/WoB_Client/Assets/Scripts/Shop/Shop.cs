using UnityEngine;

using System.Collections;
using System.Collections.Generic;

public class Shop : MonoBehaviour {
	
	private GameObject worldObject;
	private GameObject shopObject;
	private GameObject mainObject;

	public Dictionary<int, SpeciesData> itemList { get; set; }
	public SpeciesData selectedSpecies { get; set; }

	// Window Properties
	private float width = 280;
	private float height = 100;
	// Other
	private Rect windowRect;
	private Rect avatarRect;
	private Texture avatar;
	private Rect[] buttonRectList;
	private GameObject messageBox;
	private Vector2 scrollPosition = Vector2.zero;
	private bool isHidden { get; set; }
	
	void Awake() {
		buttonRectList = new Rect[3];
		
		itemList = new Dictionary<int, SpeciesData>();
		mainObject = GameObject.Find("MainObject");
		//shopObject.AddComponent("GameState");
		isHidden = true;
		//NetworkManager.Listen(NetworkCode.SHOP, ResponseShop);
		NetworkManager.Listen(
			NetworkCode.SHOP_ACTION,
			ProcessShopAction
		);

		NetworkManager.Listen(
			NetworkCode.UPDATE_RESOURCES,
			ProcessUpdateResources
		);

		//NetworkManager.Listen(Constants.SMSG_CHART, ResponseChart);
		//shopObject.GetComponent<Shop>().enabled = true;
	}

	// Use this for initialization
	void Start () {
		//windowRect = new Rect (0, 0, 200, 200); //0,0,width,height
		//windowRect.x = (Screen.width - windowRect.width) / 2;
		//windowRect.y = (Screen.height - windowRect.height) / 2;
		//worldObject = GameObject.Find("WorldObject");

		//ConnectionManager cManager = mainObject.GetComponent<ConnectionManager>();

		/*if (cManager) {
			//Debug.Log("Reached here!!");
			cManager.Send(RequestShop(1));
		}*/

		int[] temp = new int[SpeciesTable.speciesList.Count];
		//Debug.Log("Reached here!!----shop" + Constants.speciesList.Count);
		int i = 0;
		foreach (KeyValuePair<int, SpeciesData> s in SpeciesTable.speciesList) {
			//Debug.Log ("species  level is: " + s.Value.level);
			//Debug.Log ("playe level is is: "+ Constants.USER_LEVEL);
			if (!(s.Value.level>GameState.player.level)){
				temp[i++] = s.Key;
			    //Debug.Log ("species " + s.Key +" added");
			}
		}
		Initialize(null, temp);
		Constants.shopList = itemList;
		//Debug.Log ("shop list size is:-------------- " + itemList.Count);
		shopObject = GameObject.Find("Cube");
		mainObject = GameObject.Find("MainObject");
		shopObject.AddComponent("ShopPanel");
		shopObject.AddComponent("ShopInfoPanel");
		shopObject.AddComponent("ShopCartPanel");
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	
	void OnGUI() {
		if (!isHidden) {
						windowRect = new Rect(25, Screen.height - height - 10f, 100, height);
						windowRect = GUI.Window(Constants.SHOP_WIN, windowRect, ShopMakeWindow, "Shop");
				}

	}
	
	void ShopMakeWindow(int id) {
		//if (GUI.Button(new Rect(10, 50, width - 20, 30), "Select Tile")) Submit();
		//GUILayout.Label("Species:");
		//GUI.SetNextControlName("username_field");
		//Debug.Log("Reached here!!!");
		if (GUI.Button(new Rect(10, 50, 80, 30), "Species")) {
						//GUIStyle style = new GUIStyle(GUI.skin.label);
						//style.fontSize = 18;
			//Debug.Log("Clicked button!!");

			//isHidden = true;
						//GUI.Label(new Rect(30, 30, 80, 50), "Choose Your Species", style);
		
			GameObject.Find("Cube").GetComponent<ShopPanel>().Show();
			GameObject.Find("Cube").GetComponent<ShopCartPanel>().Show();
			GameObject.Find("Cube").GetComponent<ShopInfoPanel>().Show();
//			GameObject.Find("MapCamera").GetComponent<MapCamera>().enabled = false;
//			GameObject.Find("MapCamera").GetComponent<MapCamera>().RoamingCursor.SetActive(false);
//			mainObject.GetComponent<TileInfoGUI>().Hide();
			Hide();
			/*
			//shopObject.GetComponent<ShopPanel>().MakeWindow();
						//shopObject.GetComponent<ShopInfoPanel>().MakeWindow();
						//shopObject.GetComponent<ShopCartPanel>().MakeWindow();
		
						GUI.DragWindow(); */
				}
	}

	public void Initialize(string[] config, int[] speciesList) {
		foreach (int species_id in speciesList) {
			if (species_id!=0){
				//Debug.Log ("try to locate " + species_id);
				SpeciesData species = new SpeciesData(SpeciesTable.speciesList[species_id]);
				//Debug.Log ("species name: "+ species.name +"----shop species mass: " + species.biomass);

				species.image = Resources.Load(Constants.IMAGE_RESOURCES_PATH + species.name) as Texture;
				
				if (!itemList.ContainsKey(species_id)) {
					itemList.Add(species_id, species);
				}
			}
		}
	}

	/*public RequestShop RequestShop(short type) {
		RequestShop request = new RequestShop();
		request.Send(type);
		
		return request;
	}*/
	
	/*public void ResponseShop(NetworkResponse response) {
		ResponseShopresponse args = response as ResponseShopresponse;
		
		Initialize(args.config, args.speciesList);
	}*/
	public void ProcessShopAction(NetworkResponse response) {
		ResponseShopAction args = response as ResponseShopAction;
		//things to do after shop confirmation
	}

	public void ProcessUpdateResources(NetworkResponse response) {
		ResponseUpdateResources args = response as ResponseUpdateResources;
		//things to do after update resources

	}
	
	public void Show() {
		isHidden = false;
	}
	
	public void Hide() {
		isHidden = true;
	}
}
