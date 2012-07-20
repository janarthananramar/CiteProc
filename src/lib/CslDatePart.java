package lib;

import org.w3c.dom.Element;

import core.CiteProc;

public class CslDatePart extends CslFormat {

  public CslDatePart(Element domNode, CiteProc citeProc) {
    super(domNode, citeProc);
  }

  public String render(Integer[] date, String mode) {
    String text = "";
    Integer dtext = null;
    if (this.name.equalsIgnoreCase("year")) {
      dtext = date[0];
      if (dtext > 0 && dtext < 500) {
        text = dtext + this.citeProc.getLocale("term", "ad", "", "");
      } else if (dtext < 0) {
        dtext = dtext * -1;
        text = dtext + this.citeProc.getLocale("term", "bc", "", "");
      }
      // text = (!date[0].equals(null)) ? date[0]:null;
    } else if (this.name.equalsIgnoreCase("month")) {
      dtext = date[1];

      if (this.attributes.get("form") != null && !this.attributes.get("form").toString().equalsIgnoreCase("")) {
        String form = (String) this.attributes.get("form");
        if (form.equalsIgnoreCase("numeric")) {

        } else if (form.equalsIgnoreCase("numeric-leading-zeros")) {
          if (dtext < 10) {
            text = '0' + text;
          }
        } else if (form.equalsIgnoreCase("short")) {
          String month = "month-" + text.substring(0, 1);
          text = this.citeProc.getLocale("term", month, "short", "");
        } else {
          String month = "month-" + text.substring(0, 1);
          text = this.citeProc.getLocale("term", month, "", "");
        }
      }
    } else if (this.name.equalsIgnoreCase("day")) {

      text = date[2].toString();
    }
    return this.format(text);
  }
}
