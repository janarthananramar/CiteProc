package lib;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

public class CslBibliography extends CslFormat {

	private CslLayout layout = null;
	String divClass = null;
	
	public CslBibliography(Node item, CiteProc citeProc) {
		super(item,citeProc);
		// TODO Auto-generated constructor stub
	}

	public void init(Document domNode, CiteProc citeProc) {
		
		//String heirNameAttr = this.getHeirAttributes();
		NodeList options = domNode.getElementsByTagName("option");
		String value, name = null;

		for(int i=0; i<options.getLength();i++) {
			value = options.item(i).getNodeValue();
			name = options.item(i).getNodeName();
			//this.attributes.put(name, value);
		}
		
		NodeList layouts = domNode.getElementsByTagName("layout");
		for(int i=0; i<layouts.getLength();i++) {
			this.layout = new CslLayout(layouts.item(i), citeProc);
		}
		
	}
	
	public void init_formatting() {
		
		this.divClass = "";
		
		 
		super.initFormatting();
		
	}
	

	public String render(JSONObject data, String mode) {
		 System.out.println("******Bibliography***");
		String text = this.layout.render(data, "bibliography");
		if(this.attributes.get("hanging-indent")=="true") {
			text = "<div style='text-indent:-25px;padding-left:25px;'>"+text+"</div>";
		}
		text = text.replaceAll("?.", "?").replaceAll("..", ".");
		return this.format(text);
	}
}
