package lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

import lib.CslFormat;

public class CslLayout extends CslFormat{
	
	public CslLayout(Node node, CiteProc citeProc) {
		super(node, citeProc);
		// TODO Auto-generated constructor stub
	}

	public String div_class = null;
	//public NodeList elements;
	
	/*CslLayout(Node domNode, CiteProc citeProc) {
		
	}*/
	
	public void init_formatting() {
		this.div_class = "csl-entry";
		this.initFormatting(); 
	}
	
	public String render(JSONObject data, String mode) {
		 System.out.println("@@CslLayout@@"+data);
	    String text = "";
	    String delimiter = "";
	    ArrayList parts = new ArrayList();
System.out.println("********************LAYOUT***************"+this.elements);
	  //  for (int i = 0; i < this.elements.size(); i++) {
		Set s = this.elements.entrySet();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			Map.Entry me = (Map.Entry)iter.next(); 
			Object ob =me.getValue();
	     // Object ob = this.elements.get(i);
	      if (ob instanceof CslRenderingElement)
	        parts.add(((CslRenderingElement) ob).render(data, mode));
	      else if (ob instanceof CslText) 
	        parts.add(((CslText) ob).render(data, mode)); 
	        else if (ob instanceof CslLabel)
	        parts.add(((CslLabel) ob).render(data, mode));
	     /* else if (ob instanceof CslGroup)
	        parts.add(((CslGroup) ob).render(data, mode));*/
	      else if (ob instanceof CslLayout)
	        parts.add(((CslLayout) ob).render(data, mode));
	      else if (ob instanceof CslCitation)
	        parts.add(((CslCitation) ob).render(data, mode));
	     /* else if (ob instanceof CslChoose)
	        parts.add(((CslChoose) ob).render(data, mode));*/
	      else if (ob instanceof CslBibliography)
	        parts.add(((CslBibliography) ob).render(data, mode));
	      else if (ob instanceof CslNames){
	    	  System.out.println("*******CSLLayout*****CslNames");
		    parts.add(((CslNames) ob).render(data, mode));
	      }

	    }
	    for (int i = 0; i < parts.size(); i++) {
	      if (i == 0)
	        text = parts.get(i).toString();
	      else
	        text = text + delimiter + parts.get(i).toString();
	    }

	    if (mode.equalsIgnoreCase("bibliography"))
	      return this.format(text);
	    else
	      return text;

	  }
	
}
