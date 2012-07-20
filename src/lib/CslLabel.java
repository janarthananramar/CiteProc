package lib;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import core.CiteProc;

public class CslLabel extends CslFormat {

  public String plural;
  String text = "";

  public CslLabel(Element domNode, CiteProc citeProc) {
    super(domNode, citeProc);
  }

  public String render(JSONObject data, String mode) {
    String[] variables = this.variable.split(" ");
    String form = this.form;
    form = (!form.equalsIgnoreCase("")) ? form : "long";
    if (this.plural.equalsIgnoreCase("never"))
      plural = "single";
    else if (this.plural.equalsIgnoreCase("always"))
      plural = "multiple";
    else if (this.plural.equalsIgnoreCase("contextual")) {

    } else {
      if (data.size() == 1)
        plural = "single";
      else if (data.size() > 1)
        plural = "multiple";
    }
    /*
     * if (is_array($data) && isset($data['variable'])) {
     * $text = $this->citeproc->get_locale('term', $data['variable'], $form, $plural);
     * }
     */
    if (text.isEmpty()) {
       
      String variable = "";
      for (int i = 0; i < variables.length; i++)
        variable = variables[i];
      if (this.citeProc.getLocale("term", variable, form, plural) != null) {
        text = this.citeProc.getLocale("term", variable, form, plural);
        break;
      }

    }
    if (text.isEmpty())
      return "";
    if (!this._get("strip-periods").equalsIgnoreCase(""))
      text = text.replaceAll(".", "");
    return this.format(text);
  }
}
