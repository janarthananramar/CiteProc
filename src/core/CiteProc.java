package core;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lib.CslBibliography;
import lib.CslCitation;
import lib.CslInfo;
import lib.CslLocale;
import lib.CslMacros;
import lib.CslMapper;
import lib.CslStyle;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;

public class CiteProc {
  public CslBibliography bibliography;
  public CslCitation citation;
  public CslStyle style;
  protected CslMacros macros;
  private CslInfo info;
  protected CslLocale locale;
  protected String style_locale;
  private CslMapper mapper = null;
  private Document cslDoc = null;
  public HashMap noOp = new HashMap();
  String item = "";
  public HashMap nameParts = new HashMap();
  public HashMap format = new HashMap();
 

  public CiteProc(String csl, String lang) {
	if(lang.isEmpty())
		  lang ="en";
    if (csl != null) {
      init(csl, lang);
    }
  }

  private void init(String csl, String lang) {
    this.mapper = new CslMapper();
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    try {
    	
    	InputStream stream = IOUtils.toInputStream(csl);
    	builder = builderFactory.newDocumentBuilder();

    	StringWriter writer = new StringWriter();
    	IOUtils.copy(stream, writer, "UTF-8");
    	String theString = writer.toString();
    	
    	cslDoc = builder.parse(new ByteArrayInputStream(theString.getBytes()));
    	 this.locale = new CslLocale(lang);
         this.locale.setStyleLocale(cslDoc);
    	NodeList styleNodes = (NodeList) cslDoc.getElementsByTagName("style");
    	
      if (styleNodes != null) {
         
        for (int i = 0; i < styleNodes.getLength(); i++) {
        	this.style = new CslStyle(styleNodes.item(i), this);
        	System.out.println(this.style.get_attributes());
          
        }
      }

      NodeList infoNodes = cslDoc.getElementsByTagName("info");
      if (infoNodes != null) {
        for (int i = 0; i < infoNodes.getLength(); i++) {
        	this.info = new CslInfo(infoNodes.item(i), this);
        }
      }
      
      NodeList macroNodes = cslDoc.getElementsByTagName("macro");
      if (macroNodes != null) {
    	  this.macros = new CslMacros(macroNodes, this);
    	  
      }

      NodeList citationNodes = cslDoc.getElementsByTagName("citation");
      if (citationNodes != null) {
        for (int i = 0; i < citationNodes.getLength(); i++) {
        	this.citation = new CslCitation(citationNodes.item(i), this);
        }
      }

      NodeList bibliographyNodes = cslDoc.getElementsByTagName("bibliography");
  	  System.out.println("bibliographyNodes length--->"+bibliographyNodes.getLength());
      if (bibliographyNodes != null) {
        for (int i = 0; i < bibliographyNodes.getLength(); i++) {
        	this.bibliography = new CslBibliography(bibliographyNodes.item(i), this);
        }
      }

    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  public String getLocale(String type, String arg1, String arg2, String arg3) {
System.out.println("this.locale---"+this.locale);

	    return this.locale.getLocale(type, arg1, arg2, arg3);
	  }
  public String render(JSONObject data, String mode) {
	  System.out.println("@@CiteProc@@"+data);
	  String text = "";
	  if(mode.equalsIgnoreCase("citation")) {
		if(this.citation!=null) {
			text = this.citation.render(data, mode);
		}
	  }
	  else {
			if(this.bibliography!=null) {
				text = this.bibliography.render(data, mode);
			}
	  }
	  return text;
  }
  
  public String map_type(String field) {
    return "";
  }
}