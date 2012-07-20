package lib;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

public class CslCitation extends CslFormat {
  private CslLayout layout = null;
  String value = "";
  String name = "";

  public CslCitation(Node node, CiteProc citeProc) {
    super(node, citeProc);
  }

  public void init(Element domNode, CiteProc citeProc) {
    NodeList options = domNode.getElementsByTagName("option");
    for (int i = 0; i < options.getLength(); i++) {
      value = options.item(i).getNodeValue();
      name = options.item(i).getNodeName();
      this.attributes.put(name, value);
    }
    NodeList layouts = domNode.getElementsByTagName("layout");
    for (int i = 0; i < layouts.getLength(); i++) {
      this.layout = new CslLayout(layouts.item(i), citeProc);
    }
  }

  public String render(JSONObject data, String mode) { 
	  System.out.println("@@CITATION@@"+data);
	  System.out.println("******CITATION**this.elements*"+this.elements);
	 
	  Set s = this.elements.entrySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
    	  Map.Entry me = (Map.Entry)i.next(); 
    	  Object ob =me.getValue();
    	  System.out.println("******CITATION**ob*"+ob);
    	  if (ob instanceof CslLayout) {
	    	  System.out.println("******CITATION**CslLayout*");
	        this.layout = (CslLayout) ob;
	        text = this.layout.render(data, "citation");
	      }
      }
	   /* for (int i = 0; i < this.elements.size(); i++) {
	      Object ob = this.elements.get(i); 
	      System.out.println("******CITATION**this.elements.get(i)*"+this.elements.get(i));
	      System.out.println("******CITATION**ob*"+ob);
	      if (ob instanceof CslLayout) {
	    	  System.out.println("******CITATION**CslLayout*");
	        this.layout = (CslLayout) ob;
	        text = this.layout.render(data, "citation");
	      }
	    }*/

	    return text;
	  }
}