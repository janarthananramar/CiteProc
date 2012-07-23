package lib;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import core.CiteProc;

public class CslLabel extends CslFormat {

  public String plural;
  String text = "";

  public CslLabel(Node domNode, CiteProc citeProc) {
    super(domNode, citeProc);
  }

  public String render(JSONObject data, String mode) {
    String text = "";
    System.out.println("***************");
    System.out.println("CSL LABEL");
    System.out.println("***************" + data);
    // String[] variables = this.citeProc.variable;
    String[] variables = this.citeProc.variable.toString().split(" ");
    String form = (String) this.attributes.get("form");

    System.out.println("form====>" + this.attributes.get("form"));
    System.out.println("data.size()====>" + data.size());
    form = (!form.equalsIgnoreCase("")) ? form : "long";
    if (this.plural != null && this.plural.equalsIgnoreCase("never"))
      plural = "single";
    else if (this.plural != null && this.plural.equalsIgnoreCase("always"))
      plural = "multiple";
    else if (this.plural != null && this.plural.equalsIgnoreCase("contextual")) {

    } else {
      if (data.size() == 1)
        plural = "single";
      else if (data.size() > 1)
        plural = "multiple";
    }
    System.out.println("plural====>" + plural);
    System.out.println("data--" + data);

    if (data != null && data.get("variable") != null) {
      text = this.citeProc.getLocale("term", this.citeProc.variable, form, plural);
    }

    if (text.isEmpty()) {

      String variable = "";
      for (int i = 0; i < variables.length; i++)
        variable = variables[i];
      if (this.citeProc.getLocale("term", variable, form, plural) != null) {
        text = this.citeProc.getLocale("term", variable, form, plural);
      }

      System.out.println("data variable" + text);
    }
    if (text.isEmpty())
      return "";
    System.out.println("strip-periods" + this._get("strip-periods"));
    if (this._get("strip-periods") != null)
      text = text.replaceAll(".", "");
    System.out.println("text" + text);
    return this.format(text);
  }
}
