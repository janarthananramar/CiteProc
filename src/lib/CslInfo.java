package lib;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.events.Attribute;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

public class CslInfo {
  public String title;
  public String id;
  public HashMap<String, String> authors = new HashMap<String, String>();
  public ArrayList<String> links = new ArrayList<String>();
  public HashMap<String, String> valueMap = new HashMap<String, String>();

  public CslInfo(Node domNode, CiteProc citeProc) {
    HashMap<String, String> name = new HashMap<String, String>();
    Node node = null;
    NodeList childNodes = domNode.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      node = childNodes.item(i);
      if (node.getNodeType() == 1) {
        String nodeName = node.getNodeName();
        if (nodeName.equalsIgnoreCase("author")) {

        } else if (nodeName.equalsIgnoreCase("contributor")) {
          NodeList schildNodes = node.getChildNodes();
          Node authNode = null;
          for (int j = 0; j < schildNodes.getLength(); j++) {
            if (node.getNodeType() == 1) {
              authNode = schildNodes.item(j);
              name.put(authNode.getNodeName(), authNode.getTextContent());
            }
          }
          this.authors = name;
        } else if (nodeName.equalsIgnoreCase("link")) {
          NamedNodeMap attributes = node.getAttributes();
          for (int k = 0; k < attributes.getLength(); k++) {
            Attribute attr = (Attribute) attributes.item(k);
            this.links.add(attr.getValue());
          }
        } else {
          valueMap.put(node.getNodeName(), node.getTextContent());
        }
      }
    }
  }
}
