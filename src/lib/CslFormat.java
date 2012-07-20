package lib;

import java.util.HashMap;

import org.apache.commons.lang.WordUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import core.CiteProc;

public class CslFormat extends CslRenderingElement {
  protected boolean noOp;
  protected String format;
  HashMap quotes = new HashMap();
  
  String spanCass;
  String divClass;
  String prefix = "";
  String suffix = "";
  String divStyle;

 /* public CslFormat(){
	  
  }*/
  public CslFormat(Node node, CiteProc citeProc) {
	  super(node,citeProc);
     System.out.println("citeProc**&&&***"+citeProc);
     this.citeProc = citeProc;
	 // new CslElement(node, citeProc);
	System.out.println("...............initformatting.........");
    this.initFormatting(node);
    
  }

  public void initFormatting(Node domNode) {
    this.noOp = true;
    this.format = "";
    try{
    	System.out.println("this.quotes--"+this.quotes);
    if(this.quotes.size()==0) {
    	System.out.println("this.citeProc format*****"+this.citeProc);
	    this.quotes.put("punctuation-in-quote", this.citeProc.getLocale("style_option", "punctuation-in-quote","","")); 
	    this.quotes.put("punctuation-in-quote", this.citeProc.getLocale("style_option", "punctuation-in-quote","",""));
	    this.quotes.put("open-quote", this.citeProc.getLocale("term", "open-quote","",""));
	    this.quotes.put("close-quote", this.citeProc.getLocale("term", "close-quote","",""));
	    this.quotes.put("open-inner-quote", this.citeProc.getLocale("term", "open-inner-quote","",""));
	    this.quotes.put("close-inner-quote", this.citeProc.getLocale("term", "close-inner-quote","",""));
	    this.noOp = false; 
    }
    System.out.println("this.quotes--->"+this.quotes);
    
    if (this._get("prefix")!=null)
      this.noOp = false;
    if (this._get("suffix")!=null)
      this.noOp = false;
    if (this._get("display")!=null)
      this.noOp = false;

    this.format += this._get("font-style") !=null ? "font-style: " + this._get("font-style") + ";" : "";
    this.format += this._get("font-family")!=null ? "font-family: " + this._get("font-family") + ";" : "";
    this.format += this._get("font-weight")!=null ? "font-weight: " + this._get("font-weight") + ";" : "";
    this.format += this._get("font-variant")!=null ? "font-variant: " + this._get("font-variant") + ";" : "";
    this.format += this._get("text-decoration")!=null ? "text-decoration: " + this._get("text-decoration") + ";" : "";
    this.format += this._get("vertical-align")!=null ? "vertical-align: " + this._get("vertical-align") + ";" : "";

    if (this._get("text-case")!=null || this.format!=null || this.spanCass != null || this.divClass!=null) {
      this.noOp = false;
    }
    }
    catch(Exception e){
    	e.printStackTrace();
    }
  }

  public String format(String text) {
    String style = "";
    String Class = "";
    if (text.equalsIgnoreCase("") || this.noOp)
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
    System.out.println("**"+this.quotes.get("open-quote"));
    if(this.quotes.get("open-quote") != null)
    prefix = this.prefix + this.quotes.get("open-quote");
    suffix = this.suffix;
    if (this.quotes.get("close-quote") !=null && suffix !=null
        && this.quotes.get("punctuation-in-quote") != null) {
      if (suffix.contains(".") || suffix.contains(",")) {
    	  if(this.quotes.get("close-quote") != null)
        suffix = suffix + this.quotes.get("close-quote");
      }
    } else if (this.quotes.get("close-quote") != null) {
      suffix = this.quotes.get("close-quote") + suffix;
    }
    if (suffix !=null && !suffix.equalsIgnoreCase("")) {
      String noTags = text.replaceAll("\\<.*?\\>", "");

      if (noTags.length() > 0 && noTags.charAt(noTags.length() - 1) == suffix.charAt(0)) {
        suffix = suffix.substring(1);
      }
    }
    if (this.format != null || this.spanCass !=null) {
      style = (this.format !=null) ? "style=\"" + this.format + "\"" : "";
      Class = (this.spanCass != null) ? "class=\"" + this.spanCass + "\"" : "";
      text = "<span " + Class + style + ">" + text + "</span>";
    }

    if (this.divClass != null) {
      divClass = (this.divClass != null) ? "class=\"" + this.divClass + "\"" : "";
    }
    if (this._get("display") != null && this._get("display").equalsIgnoreCase("indent")) {
      divStyle = "style=\"text-indent: 0px; padding-left: 45px;\"";
    }
    if (divClass !=null || divStyle !=null)
      return "<div" + divClass + divStyle + ">" + prefix + text + suffix + "</div>";

    
    return prefix + text + suffix;
  }
}