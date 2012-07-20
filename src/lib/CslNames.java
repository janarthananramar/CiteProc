package lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

public class CslNames extends CslFormat{
	private Object substitutes = null;
	private String delimiter = "";
	private String styleDelimiter = "";
	private String modeDelimiter = "";
	String variable = "";
	public CslNames(Node node, CiteProc citeProc) {
		super(node, citeProc);
		// TODO Auto-generated constructor stub
	} 
	public void initFormatting(Node node) {
		super.initFormatting(node);
	}
	public void init(Node domNode,CiteProc citeProc){
		Object etal = null;
		Node tag = null;
		tag = ((Element)domNode).getElementsByTagName("substitute").item(0); 
		if(tag != null){
			this.substitutes = CslFactory.create(tag, citeProc);
			domNode.removeChild(tag);
		}
		tag = ((Element)domNode).getElementsByTagName("et-al").item(0);   
		if(tag != null){
			etal = CslFactory.create(domNode, citeProc);
			domNode.removeChild(tag);
		} 
         String var =((Element)domNode).getAttribute("variable"); 
         Node node = null;
         NodeList nodeList = domNode.getChildNodes();
         for(int i=0;i<nodeList.getLength();i++){
        	 node = nodeList.item(i);
        	 if(node.getNodeType() == 1){
        		Object element = CslFactory.create(node, citeProc);
        		/* if(element instanceof CslLabel)
        			 ((CslLabel)element).variable = var;
        		 if(element instanceof CslName)
        			 ((CslName)element).etal = etal;*/
        		 this.addElement(i, element);
        	 }
         }
		    

	}
	public String render(JSONObject data,String mode){
		int matches = 0;
		System.out.println("*****************************");
		System.out.println("*********CSLNAMES************");
		System.out.println("*****************************");
		ArrayList variableParts = new ArrayList();
		if(this.delimiter.equalsIgnoreCase(""))
		{
			if(this.citeProc.style.attributes.get("names-delimiter") != null)
			this.styleDelimiter = this.citeProc.style.attributes.get("names-delimiter").toString();
			if(mode.equalsIgnoreCase("citation") &&  this.citeProc.citation.attributes.get("names-delimiter") != null)
			this.modeDelimiter = this.citeProc.citation.attributes.get("names-delimiter").toString();
			else if(mode.equalsIgnoreCase("bibliography") && this.citeProc.bibliography.attributes.get("names-delimiter") != null)
				this.modeDelimiter = this.citeProc.bibliography.attributes.get("names-delimiter").toString();
			this.delimiter = modeDelimiter.equalsIgnoreCase("")?(styleDelimiter.equalsIgnoreCase("")?styleDelimiter:""):modeDelimiter;
			
		}
		variable = this.attributes.get("variable").toString();
		System.out.println("variable---"+variable);
		String[] variables = this.variable.split(" ");
		String var = "";
		System.out.println("variables---"+variables);
		for(int i=0;i< variables.length;i++){
			var = variables[i].toString();
			if(data.containsKey(var) && data.get(var) != null){
				matches++;
				break;
			}
		}
		System.out.println("matches---"+matches);
		if(matches == 0){
			if(this.substitutes != null ){
				for(int i=0;i<this.elements.size();i++)   {
				      Object ob = this.elements.get(i);
				      if (ob instanceof CslNames){
				    	  variables = this.variable.split(" ");
				    	  for(int j=0;j<variables.length;j++){
				            
				              if (data.get(var)== null) {
				                matches++;
				                break;
				              }
				            }
				      } 
				      else if (ob instanceof CslText) 
				      ((CslText) ob).render(data, mode); 
				        else if (ob instanceof CslLabel)
				       ((CslLabel) ob).render(data, mode);
				   /*   else if (ob instanceof CslGroup)
				        parts.add(((CslGroup) ob).render(data, mode));*/
				      else if (ob instanceof CslLayout)
				        ((CslLayout) ob).render(data, mode);
				      else if (ob instanceof CslCitation)
				       ((CslCitation) ob).render(data, mode);
				    /*  else if (ob instanceof CslChoose)
				        parts.add(((CslChoose) ob).render(data, mode));*/
				      else if (ob instanceof CslBibliography)
				        ((CslBibliography) ob).render(data, mode);
				      else if (ob instanceof CslName)
					        ((CslName) ob).render(data, mode);

				    }
				 
			}
		}
		for(int m=0;m<variables.length;m++){
			text = "";
			 var = variables[m].toString();
			 System.out.println("var"+var);
			 System.out.println("data"+data);
			if(data.containsKey(var))
			{
				
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$"+this.elements);
				  Set s = this.elements.entrySet();
			      Iterator i = s.iterator();
			      while (i.hasNext()) {
			    	  Map.Entry me = (Map.Entry)i.next(); 
			    	  Object ob =me.getValue();
			    	  System.out.println("ob---"+ob); 
				      if (ob instanceof CslName){
				    	  System.out.println("@@CSLNAME@@");
				    	  text += ((CslName) ob).render((JSONArray)data.get(var), mode);
				      } 

				    }
			}
			 if (!text.isEmpty()) 
				 variableParts.add(text);
		}
		 if (!variableParts.isEmpty()) {
			 
			 for(int i=0;i<variableParts.size();i++){
				 if(i==0)
					 text = variableParts.get(i).toString();
				 else
					 text = text +this.delimiter+variableParts.get(i).toString();
			 }
				System.out.println("text-CSLNAMES--"+text);
		      return this.format(text);
		    } 
		    return "" ; 
	}
  
}
