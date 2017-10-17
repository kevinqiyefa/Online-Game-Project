using UnityEngine;
using System.Collections;

//hold single node parameter 
public class ConvergeParam 
{
	public string name { get; set; }
	public int nodeId { get; set; }
	public string paramId { get; set; }
	public string nodeIdParamId { get; set; }
	public float value { get; set; }
	public float origVal { get; set; }

	public ConvergeParam (string name,
	                      int nodeId,
	                      string paramId,
	                      float value
	                      )
	{
		this.name = name;
		this.nodeId = nodeId;
		this.paramId = paramId;
		this.value = value;
		this.origVal = value;
		this.nodeIdParamId = NodeIdParamId (nodeId, paramId);
	}

	public static string NodeIdParamId (int nodeId, string paramId)
	{
		string temp = string.Format ("{0}{1}", nodeId, paramId);
		return temp;
	}

	public bool ValueUpdated () {
		return (value != origVal);
	}

}

