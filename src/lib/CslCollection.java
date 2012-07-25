package lib;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class CslCollection {

   

  protected HashMap elements = new HashMap();

  protected void addElement(int i, Object elem) {
	System.out.println("@@CslCollection add Element method@@"+elem);
    if (elem != null){
      elements.put(i, elem);
    }
  }

  protected String render(JSONObject data, String mode) {
	  System.out.println("@@CslCollection@@"+data);
	  return "";
  }

  protected String format(String text) {
    return text;
  }
}
