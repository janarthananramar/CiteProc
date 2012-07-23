package lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import core.CiteProc;

public class CslRenderingElement extends CslElement {

  public CslRenderingElement(Node node, CiteProc citeproc) {
    super(node, citeproc);
    // TODO Auto-generated constructor stub
  }

  String text = "";
  String delim = "";
  ArrayList textParts = new ArrayList();

  /*
   * public CslRenderingElement(){
   * 
   * }
   */

  public String render(JSONObject data, String mode) {
    System.out.println("@@CslRenderingelement@@");
    System.out.println("@@elements@@" + this.citeProc.elements);
    String text = "";
    String delimiter = "";
    ArrayList parts = new ArrayList();

    Set s = this.elements.entrySet();
    Iterator j = s.iterator();
    while (j.hasNext()) {
      Map.Entry me = (Map.Entry) j.next();
      Object ob = me.getValue();

      if (ob instanceof CslRenderingElement)
        parts.add(((CslRenderingElement) ob).render(data, mode));
      else if (ob instanceof CslText)
        parts.add(((CslText) ob).render(data, mode));
      else if (ob instanceof CslLabel)
        parts.add(((CslLabel) ob).render(data, mode));
      /*
       * else if (ob instanceof CslGroup)
       * parts.add(((CslGroup) ob).render(data, mode));
       */
      else if (ob instanceof CslLayout)
        parts.add(((CslLayout) ob).render(data, mode));
      else if (ob instanceof CslCitation)
        parts.add(((CslCitation) ob).render(data, mode));
      /*
       * else if (ob instanceof CslChoose)
       * parts.add(((CslChoose) ob).render(data, mode));
       */
      else if (ob instanceof CslBibliography)
        parts.add(((CslBibliography) ob).render(data, mode));

    }
    for (int i = 0; i < parts.size(); i++) {
      if (i == 0)
        text = parts.get(i).toString();
      else
        text = text + delimiter + parts.get(i).toString();
    }
    System.out.println("text---" + text);
    return this.format(text);

  }
}
