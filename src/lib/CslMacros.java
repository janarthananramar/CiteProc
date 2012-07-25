package lib;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;


public class CslMacros extends CslCollection{
	

	public CslMacros(NodeList macroNodes, CiteProc citeProc) {
		
		System.out.println("Macros nodes list--->"+macroNodes.getLength());
		for(int i=0; i<macroNodes.getLength();i++) {
			Class macro = (Class) CslFactory.create(macroNodes.item(i), citeProc, "CslMacros");
			System.out.println("Macro Name--->"+macro.getCanonicalName());
			System.out.println(macro);
			this.elements.put(macro.getName(), macro);
		}
		
	}
	
	public String renderMacro() {
		
		
		return "";
	}
	
}
