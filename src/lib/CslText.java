package lib;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import core.CiteProc;

public class CslText extends CslFormat {

  public CslText(Node domNode, CiteProc citeProc) {
    super(domNode, citeProc);
  }

  public String source;
  protected String var;

  public void init(Element domNode, CiteProc citeproc) {
    String[] attrbs = { "variable", "macro", "term", "value" };
    String attr = "";
    for (int i = 0; i < attrbs.length; i++) {
      attr = attrbs[i].toString().trim();
      if (domNode.hasAttribute(attr)) {
        this.source = attr;
        if (this.source.equalsIgnoreCase("macro")) {
          this.var = domNode.getAttribute(attr).replaceAll(" ", "_");
        } else {
          this.var = domNode.getAttribute(attr);
        }
      }
    }
  }

  public void initFormatting() {
    super.initFormatting();
  }

  public String render(JSONObject data, String mode) {
	  System.out.println("@@CslText@@"+data);
    String text = "";
    if (this.source.equalsIgnoreCase("variable")) {
      /*
       * if(!isset($data->{$this->variable}) || empty($data->{$this->variable})) return;
       * $text = $data->{$this->variable};
       */// $this->data[$this->var]; // include the contents of a variable
    } else if (this.source.equalsIgnoreCase("macro")) {
      String macro = this.var;
      text = this.citeProc.renderMacro(macro, data, mode);
    } else if (this.source.equalsIgnoreCase("term")) {
      String form = this.form;// doubt
      text = this.citeProc.getLocale("term", this.var, form, null);
    } else if (this.source.equalsIgnoreCase("value")) {
      text = this.var;
    }

    if (text.equalsIgnoreCase(""))
      return "";
    return this.format(text);

  }
}
