package lib;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

public class CslElement extends CslCollection {

  protected HashMap attributes = new HashMap();
  protected CiteProc citeProc;
  String name = "";

  /*
   * public CslElement(){
   * 
   * }
   */
  public CslElement(Node node, CiteProc citeproc) {
    System.out.println("calling CslElement Constructor" + citeproc);
    this.citeProc = citeproc;
    this.setAttributes(node);
    this.init(node, citeProc);
    System.out.println("cslElement AFTER init");
  }

  public void init(Node domNode, CiteProc citeProc) {
    System.out.println("init cslelement***" + citeProc);
    NodeList childNodes = domNode.getChildNodes();
    Node node = null;
    if (domNode == null)
      return;
    System.out.println("childNodes.getLength()***" + childNodes.getLength());
    for (int i = 0; i < childNodes.getLength(); i++) {
      System.out.println("i^^^^^^^^" + i);
      node = childNodes.item(i);
      System.out.println("i^^^^^^^^" + node);
      if (node.getNodeType() == 1) {
        System.out.println("***#####################***");
        Object obj = CslFactory.create(node, citeProc);
        System.out.println("**OBJ**" + obj);
        if (obj != null)
          this.addElement(i, obj);
        System.out.println("~~~~~~~");
      }
    }
  }

  public void _set(String name, String value) {
    this.attributes.put(name, value);
    this.citeProc.attrib.put(name, value);
  }

  public Object _isset(String name) {
    return this.attributes.get(name);
  }

  public void _unset(String name) {
    this.attributes.remove(name);
  }

  public String _get(String name) {
    if (this.attributes.containsKey(name))
      return (String) this.attributes.get(name);
    return null;
  }

  public void setAttributes(Node domNode) {
    System.out.println("calling CslElement Set Attributes");
    System.out.println("dom_node->attributes->length --->" + domNode.getAttributes().getLength());
    System.out.println("dom_node->nodeName --->" + domNode.getNodeName());
    String value = "";
    String name = "";
    String elementName = domNode.getNodeName();
    if (domNode.getAttributes().getLength() > 0) {
      for (int i = 0; i < domNode.getAttributes().getLength(); i++) {
        value = domNode.getAttributes().item(i).getNodeValue();
        name = domNode.getAttributes().item(i).getNodeName().replaceAll(" ", "_");
        System.out.println("name--->" + name + " |value--->" + value);
        if (!name.equalsIgnoreCase("xmlns")) {
          if (name.equalsIgnoreCase("type")) {
            value = this.citeProc.map_type(value);
          }
          if ((name.equalsIgnoreCase("variable") || name.equalsIgnoreCase("is-numeric")) && !elementName.equalsIgnoreCase("label")) {
            value = CslMapper.mapField(value);
          }
          _set(name, value);
        }
      }
    }
    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
  }

  public HashMap get_attributes() {
    return this.attributes;
  }

  public HashMap get_hier_attributes() {
    HashMap hier_attr = new HashMap();
    String[] hier_names = { "and", "delimiter-precedes-last", "et-al-min", "et-al-use-first", "et-al-subsequent-min",
        "et-al-subsequent-use-first", "initialize-with", "name-as-sort-order", "sort-separator", "name-form", "name-delimiter",
        "names-delimiter" };
    for (String name : hier_names) {
      if (this.attributes.containsKey(name)) {
        hier_attr.put(name, this.attributes.get(name));
      }
    }
    return hier_attr;
  }

  public String name(String name) {
    if (name != null) {

      this.name = name;
    } else {
      return this.name.replaceAll(" ", "_");
    }
    return "";
  }
}
