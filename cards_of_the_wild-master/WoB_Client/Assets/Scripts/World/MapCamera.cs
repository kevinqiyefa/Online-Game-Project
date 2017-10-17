using UnityEngine;

using System.Collections.Generic;

public class MapCamera : MonoBehaviour {

	bool dragging = false;
	Vector3 mouseDownPos;
	Vector3 oldCameraPos;
	//Holds mouse mode; Mode 0 = normal, Mode 1 = Attack tile selected, Mode 2 = Defender tile selected, Mode 3 = Selected Home Tile, Mode 4 = possible Home Time clicked
	public int mode { get; set;}
	
	//Camera Zoom related variables
	private float cameraZoomed = 10f;
	private float cameraNormal = 60f;
	private float cameraPulled = 70f;
	private float cameraSmoothing = 3f;
	public bool isPanning { get; set; }
	public bool isZooming { get; set; }
	public bool isZoomed { get; set; }
	public bool isLeaving { get; set; }
	public short cameraStage = 0;

	public float cameraOffset { get; set; }
	private Vector3 cameraNextPos;
	private float deltaTime;
	private Vector3 startPos;
	private float startZoom;

	// Use this for initialization
	void Start() {
		Setup();
	}

	// Update is called once per frame
	void Update() {
		if (isLeaving) {
			switch (cameraStage) {
				case 1:
					if (Vector3.Distance(transform.position, cameraNextPos) > 0.1f || Mathf.Abs(camera.fieldOfView - cameraPulled) > 0.1f) {
						deltaTime += Time.deltaTime * cameraSmoothing;
						transform.position = Vector3.Lerp(startPos, cameraNextPos, deltaTime);
						camera.fieldOfView = Mathf.Lerp(startZoom, cameraPulled, deltaTime);
					} else {
						cameraStage++;
						deltaTime = 0;
						startZoom = camera.fieldOfView;
					}
					break;
				case 2:
					if (Mathf.Abs(camera.fieldOfView - cameraZoomed) > 0.1f) {
						deltaTime += Time.deltaTime * 2;
						camera.fieldOfView = Mathf.Lerp(startZoom, cameraZoomed, deltaTime);
					} else {
						Game.SwitchScene("Ecosystem");
					}
					break;
			}
		}

		if (isPanning && Vector3.Distance(transform.position, cameraNextPos) > 0.1f) {
			deltaTime += Time.deltaTime * cameraSmoothing;
			transform.position = Vector3.Lerp(startPos, cameraNextPos, deltaTime);
		} else {
			isPanning = false;
		}
		
		if (Input.GetMouseButtonDown(0)) {
			dragging = true;
			mouseDownPos = Input.mousePosition;
			oldCameraPos = transform.position;
		}
		
		if (Input.GetMouseButtonUp(0)) {
			dragging = false;
		}
		
		if (dragging) {
			if (isZoomed) {
				transform.position = new Vector3(oldCameraPos.x + (mouseDownPos.x - Input.mousePosition.x) * .04f, oldCameraPos.y, oldCameraPos.z + (mouseDownPos.y - Input.mousePosition.y) * .04f);
			} else {
				transform.position = new Vector3(oldCameraPos.x + (mouseDownPos.x - Input.mousePosition.x) * .25f, oldCameraPos.y, oldCameraPos.z + (mouseDownPos.y - Input.mousePosition.y) * .25f);
			}
		}
	}

	public void Center(int player_id) {
		Vector3 center = GameObject.Find("Map").GetComponent<Map>().GetCenterPoint(player_id);
		center = new Vector3(center.x, transform.position.y, center.z + cameraOffset);

		cameraNextPos = center;
		isPanning = true;
		deltaTime = 0;
	}

	public void Move(int player_id) {
		Move(GameObject.Find("Map").GetComponent<Map>().GetCenterPoint(player_id));
	}

	public void Move(Vector3 position) {
		camera.fieldOfView = cameraNormal;
		startPos = camera.transform.position;
		startZoom = camera.fieldOfView;
		deltaTime = 0;
		cameraStage = 1;
		cameraNextPos = new Vector3(position.x, cameraNextPos.y, position.z + cameraOffset);

		isLeaving = true;
		isPanning = true;
	}

	public void Setup() {
		RaycastHit hit = new RaycastHit();
		if (Physics.Raycast(camera.ScreenPointToRay(new Vector2(Screen.width / 2, Screen.height / 2)), out hit)) {
			cameraOffset = transform.position.z - hit.point.z;
		}
	}
}
