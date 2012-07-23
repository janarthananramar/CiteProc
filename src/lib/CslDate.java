package lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.CiteProc;

public class CslDate extends CslFormat {
  public CslDate(Element domNode, CiteProc citeProc) {
    super(domNode, citeProc);
    // TODO Auto-generated constructor stub
  }

  public void init(Element domNode, CiteProc citeProc) {
    HashMap localeElements = new HashMap();
    String form;// = this.form;
    // if
    String localDate = this.citeProc.getLocale("date_options", form,"","");
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(localDate);
    Element domElem = doc.getDocumentElement();
    if (domElem != null) {
      NodeList nodes = domElem.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        if (node.getNodeType() == 1) {
          localeElements.put(i, CslFactory.create(node, citeProc));
        }
      }
    }
    NodeList nodes = domNode.getChildNodes();
    Object element = null;
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node.getNodeType() == 1) {
        element = CslFactory.create(node, citeProc);
        for (int k = 0; k < localeElements.size(); k++) {
        	Object localeElement = (Object) localeElements.get(k);
          if (localeElement.getName().equalsIgnoreCase(element.getName())) {

            /*
             * $locale_elements[$key]->attributes = array_merge($locale_element->attributes, $element->attributes);
             * $locale_elements[$key]->format = $element->format;
             */
            break;
          } else {
            localeElements.put(i, element);
          }
        }
      }
    }
    String dateParts = this._get("date-parts");
    if (!dateParts.equalsIgnoreCase("")) {
      String[] parts1 = dateParts.split("-");
      ArrayList parts = (ArrayList) Arrays.asList(parts1);
      for (int j = 0; j < localeElements.size(); j++) {
        element = (Object) localeElements.get(j);
        if (!parts.contains(element.getName())) {
          localeElements.remove(j);
          j--;
        }

      }
      if (localeElements.size() != parts.size()) {
        for (int j = 0; j < parts.size(); j++) {
          String part = (String) parts.get(j);
          CslDatePart emt = new CslDatePart(domElem, citeProc);
          emt.name = part;
          localeElements.put(j, emt);
        }
      }

      // now re-order the elements
      for (int i = 0; i < parts.size(); i++) {
        String part = (String) parts.get(i);
        for (int j = 0; j < localeElements.size(); j++) {
          Class elmt = (Class) localeElements.get(j);
          if (elmt.getName().equalsIgnoreCase(part)) {
            this.elements.put(j, elmt);
            localeElements.remove(j);
          }
        }
      }

    } else {
      this.elements = localeElements;
    }
    // else
    // super.init(domNode, citeProc);
  }

  public String render(String data, String mode) {
    ArrayList dateParts = new ArrayList();
    String date = "";
    String text = "";
    String var = this.variable;
    if (data.contains(var)) { // if (($var = $this->variable) && isset($data->{$var}))
      // $date = $data->{$var}->{'date-parts'}[0];
      for (int i = 0; i < elements.size(); i++) {
        CslDatePart element = (CslDatePart) elements.get(i);
        dateParts.add(element.render(date, mode));
        if (i == 0)
          text = element.render(date, mode);
        else
          text = text + element.render(date, mode);
      }

    } else {
      text = this.citeProc.getLocale("term", "no date");
    }
    return this.format(text);
  }

}
