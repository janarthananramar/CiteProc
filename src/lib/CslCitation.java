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

  public CslCitation() {
    //super(node, citeProc);
  }

  public CslCitation(Node item, CiteProc citeProc, String calledFrom) {
	    super(item, citeProc, calledFrom);
}

public void init(Node node, CiteProc citeProc) {

	System.out.println("CslCitation Init Called");
	
	Element el = (Element)node;
    NodeList options = el.getElementsByTagName("option");
    System.out.println("CslCitation options length--->"+options.getLength());
    for (int i = 0; i < options.getLength(); i++) {
      value = options.item(i).getNodeValue();
      name = options.item(i).getNodeName();
      this.attributes.put(name, value);
    }
    
    NodeList layouts = el.getElementsByTagName("layout");
    System.out.println("CslCitation layouts length--->"+layouts.getLength());
    for (int i = 0; i < layouts.getLength(); i++) {
      this.layout = new CslLayout(layouts.item(i), citeProc, "CslLayout");
    }
    CslElement cslElem = new CslElement();
    cslElem.setAttributes(node);
    cslElem.initEle(node, citeProc);
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

	    return this.format(text);
	  }
}