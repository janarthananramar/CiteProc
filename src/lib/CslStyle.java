package lib;

import org.w3c.dom.Node;

import core.CiteProc;

public class CslStyle extends CslElement {
	
	public CslStyle(Node domNode, CiteProc citeproc) {
		super(domNode, citeproc);
		System.out.println("calling CslStyle Constructor");
		if(domNode.getNodeName()!=null) {
			this.setAttributes(domNode);
		}
		
			
	}
	
}
