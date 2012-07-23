package lib;

import java.util.HashMap;

import org.apache.commons.lang.WordUtils;
import org.w3c.dom.Node;

import core.CiteProc;

public class CslFormat extends CslRenderingElement {
  protected boolean noOp;
  protected String format = "";
  HashMap quotes = new HashMap();

  String spanCass = "";
  String divClass = "";
  String prefix = "";
  String suffix = "";
  String divStyle;

  /*
   * public CslFormat(){
   * 
   * }
   */
  public CslFormat(Node node, CiteProc citeProc) {
    super(node, citeProc);
    System.out.println("citeProc**&&&***" + citeProc);
    this.citeProc = citeProc;
    // new CslElement(node, citeProc);
    System.out.println("...............initformatting.........");
    this.initFormatting(node);

  }

  public void initFormatting(Node domNode) {
    this.noOp = true;
    this.format = "";
    try {
      System.out.println("this.quotes--" + this.quotes);
      if (this.quotes.size() > 0) {

        this.quotes = new HashMap();
        System.out.println("this.citeProc format*****" + this.citeProc);
        if (this.citeProc.getLocale("style_option", "punctuation-in-quote", "", "") != null)
          this.quotes.put("punctuation-in-quote", this.citeProc.getLocale("style_option", "punctuation-in-quote", "", ""));
        if (this.citeProc.getLocale("term", "open-quote", "", "") != null)
          this.quotes.put("open-quote", this.citeProc.getLocale("term", "open-quote", "", ""));
        if (this.citeProc.getLocale("term", "close-quote", "", "") != null)
          this.quotes.put("close-quote", this.citeProc.getLocale("term", "close-quote", "", ""));
        if (this.citeProc.getLocale("term", "open-inner-quote", "", "") != null)
          this.quotes.put("open-inner-quote", this.citeProc.getLocale("term", "open-inner-quote", "", ""));
        if (this.citeProc.getLocale("term", "close-inner-quote", "", "") != null)
          this.quotes.put("close-inner-quote", this.citeProc.getLocale("term", "close-inner-quote", "", ""));

        this.noOp = false;
      }
      System.out.println("this.quotes--->" + this.quotes);

      if (this._get("prefix") != null)
        this.noOp = false;
      if (this._get("suffix") != null)
        this.noOp = false;
      if (this._get("display") != null)
        this.noOp = false;

      this.format += this._get("font-style") != null ? "font-style: " + this._get("font-style") + ";" : "";
      this.format += this._get("font-family") != null ? "font-family: " + this._get("font-family") + ";" : "";
      this.format += this._get("font-weight") != null ? "font-weight: " + this._get("font-weight") + ";" : "";
      this.format += this._get("font-variant") != null ? "font-variant: " + this._get("font-variant") + ";" : "";
      this.format += this._get("text-decoration") != null ? "text-decoration: " + this._get("text-decoration") + ";" : "";
      this.format += this._get("vertical-align") != null ? "vertical-align: " + this._get("vertical-align") + ";" : "";

      if (this._get("text-case") != null || this.format != null || this.spanCass != null || this.divClass != null) {
        this.noOp = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String format(String text) {
    String style = "";
    String Class = "";
    if (text.isEmpty() || this.noOp)
      return text;
    if (this._get("text-case") != null && this._get("text-case").length() > 0) {
      if (this._get("text-case").equalsIgnoreCase("uppercase"))
        text = text.toUpperCase();
      else if (this._get("text-case").equalsIgnoreCase("lowercase"))
        text = text.toLowerCase();
      else if (this._get("text-case").equalsIgnoreCase("capitalize-all")) {

      } else if (this._get("text-case").equalsIgnoreCase("title")) {
        text = WordUtils.capitalize(text);
      } else if (this._get("text-case").equalsIgnoreCase("capitalize-first")) {
        char[] stringArray = text.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        text = new String(stringArray);
      }

    }
    System.out.println("*this.quotes*" + this.quotes);
    if (this.quotes.get("open-quote") != null && this.attributes.get("prefix") != null)
      prefix = this.attributes.get("prefix").toString() + this.quotes.get("open-quote");
    else if (this.attributes.get("prefix") != null)
      prefix = this.attributes.get("prefix").toString();
    System.out.println("prefix.. cslFormat..." + prefix);
    if (this.attributes.get("suffix") != null)
      suffix = this.attributes.get("suffix").toString();
    if (this.quotes.get("close-quote") != null && !suffix.isEmpty() && this.quotes.get("punctuation-in-quote") != null) {
      if (suffix.contains(".") || suffix.contains(",")) {
        if (this.quotes.get("close-quote") != null)
          suffix = suffix + this.quotes.get("close-quote");
      }
    } else if (this.quotes.get("close-quote") != null) {
      suffix = this.quotes.get("close-quote") + suffix;
    }
    if (!suffix.isEmpty()) {
      String noTags = text.replaceAll("\\<.*?\\>", "");

      if (noTags.length() > 0 && noTags.charAt(noTags.length() - 1) == suffix.charAt(0)) {
        suffix = suffix.substring(1);
      }
    }
    if (!this.format.isEmpty() || !this.spanCass.isEmpty()) {
      style = (!this.format.isEmpty()) ? "style=\"" + this.format + "\"" : "";
      Class = (!this.spanCass.isEmpty()) ? "class=\"" + this.spanCass + "\"" : "";
      text = "<span " + Class + style + ">" + text + "</span>";
    }

    if (!this.divClass.isEmpty()) {
      divClass = (!this.divClass.isEmpty()) ? "class=\"" + this.divClass + "\"" : "";
    }
    if (this._get("display") != null && this._get("display").equalsIgnoreCase("indent")) {
      divStyle = "style=\"text-indent: 0px; padding-left: 45px;\"";
    }

    if ((divClass != null && !divClass.isEmpty()) || (divStyle != null && !divStyle.isEmpty()))
      return "<div" + divClass + divStyle + ">" + prefix + text + suffix + "</div>";
    System.out.println("@@@@-------------" + prefix + text + suffix);

    return prefix + text + suffix;
  }
}
