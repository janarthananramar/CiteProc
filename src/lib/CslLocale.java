package lib;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.*;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.xpath.XPathEvaluator;
import org.w3c.dom.xpath.XPathNSResolver;
import org.xml.sax.InputSource;

public class CslLocale {
  protected String localeXmlString = null;
  protected String styleLocaleXmlstring = null;
  protected String locale = null;
  protected Document styleLocale = null;
  private String modulePath = "/Users/janarthananramar/Documents/workspace/CiteProc/";
  String lang = "en";
  public CslLocale(String lang){
	  if(lang.isEmpty())
		  lang = "en";
    this.lang = lang;
    
    //XPathContext context = new XPathContext("locale", this.getLocalesFileName(lang));
    //context.m
    //System.out.println(this.getLocalesFileName(lang));
    //System.exit(0);
    //this.modulePath = System.getProperty("user.dir").toString();
   // this.locale = new SimpleXMLElement()
  }
  @SuppressWarnings("unchecked")
public String getLocalesFileName(String lang){
    @SuppressWarnings("rawtypes")
	HashMap langBases = new HashMap();
    langBases.put("af", "af-ZA");
    langBases.put("ar","ar-AR");
    langBases.put("bg","bg-BG");
    langBases.put("ca","ca-AD");
    langBases.put("cs","cs-CZ");
    langBases.put("da","da-DK");
    langBases.put("de","de-DE");
    langBases.put("el","el-GR");
    langBases.put("en","en-GB"); 
    langBases.put("en","en-US");
    langBases.put("es","es-ES");
    langBases.put("et","et-EE");
    langBases.put("fa","fa-IR");
    langBases.put("fi","fi-FI");
    langBases.put("fr","fr-FR");
    langBases.put("he","he-IL");
    langBases.put("hu","hu-HU");
    langBases.put("is","is-IS");
    langBases.put("it","it-IT");
    langBases.put("ja","ja-JP");
    langBases.put("km","km-KH");
    langBases.put("ko","ko-KR");
    langBases.put("mn","mn-MN");
    langBases.put("nb","nb-NO");
    langBases.put("nl","nl-NL");
    langBases.put("nn","nn-NO");
    langBases.put("pl","pl-PL");
    langBases.put("pt","pt-PT");
    langBases.put("ro","ro-RO");
    langBases.put("ru","ru-RU");
    langBases.put("sk","sk-SK");
    langBases.put("sl","sl-SI");
    langBases.put("sr","sr-RS");
    langBases.put("sv","sv-SE");
    langBases.put("th","th-TH");
    langBases.put("tr","tr-TR");
    langBases.put("uk","uk-UA");
    langBases.put("vi","vi-VN");
    langBases.put("zh","zh-CN");
    String content = null;
    if(langBases.containsKey(lang)) {
    	try {
			content = IOUtils.toString(new FileReader(this.modulePath + "/locale/locales-" + langBases.get(lang) +".xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    else
    {
    	try {
			content = IOUtils.toString(new FileReader(this.modulePath +"/locale/locales-en-US.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    return content;
  }
  public String getLocale(String type, String arg1, String arg2, String arg3){
	  
	  	
	  System.out.println("Type--->"+type);
	  XPathFactory  factory=XPathFactory.newInstance();
      XPath xPath=factory.newXPath();
      String expresion = "";
      NodeList nodes = null;
      Node option;
    	if(type.equalsIgnoreCase("style_option")){
	      String form, plural = "";
	      form = arg2 == "" ? " and @form='"+arg2+"'" : "";
	      plural = arg3 == "" ? "/cs:"+arg3 : "";
	      
			System.out.println(printXmlDocument(this.styleLocale).getBytes());
		
	      
	      ArrayList attribs = new ArrayList();

	      if(this.styleLocale.getNodeName()!=null) {
		      
			try {
				 expresion = "//locale/style-options[@"+arg1+"]";
				System.out.println("***"+expresion);
				nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
				option = nodes.item(0);
				if(option !=null){
					attribs.add(option.getAttributes());
				}
				 if (attribs.size()==0) {
					 expresion = "//cs:style-options[@"+arg1+"]";
					 nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
				     option = nodes.item(0); 
			        }
				 if(option !=null){
				 NamedNodeMap nodeMap = option.getAttributes();
				 String name = "";
				 String value = "";
				 for(int i=0;i<nodeMap.getLength();i++){
					name =  nodeMap.item(i).getNodeName();
					value = nodeMap.item(i).getNodeValue();
					if(name.equalsIgnoreCase(arg1)){ 
						return value;
					}
				 }	
				 }
			     
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  //term = this.styleLocale
	      }
    	}else if(type.equalsIgnoreCase("date_options")){
    		 try{
            if (this.styleLocale != null) {
            	expresion = "//locale[@xml:lang='en']/date[@form='"+arg1+"']";
            	 nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
			     //option = nodes.item(0); 
             
              if (nodes == null){
            		expresion = "//locale/date[@form='"+arg1+"']";
               	 nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
               	// option = nodes.item(0); 
              }
            }
            if (nodes == null) {
            	expresion = "//cs:date[@form='"+arg1+"']";
              	 nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
              	// option = nodes.item(0); 
              
            }
            if(nodes.getLength()>0)
            	return nodes.item(0).toString(); 
    		 } catch (XPathExpressionException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
    	}else if(type.equalsIgnoreCase("term")){ 
            String term = "";
            String form = arg2.equalsIgnoreCase("") ? "":" and @form='"+arg2+"'" ;
            String plural = arg3.equalsIgnoreCase("") ?"": "/cs:$arg3";
            try{
            if(this.styleLocale != null){
             expresion = "//locale[@xml:lang='en']/terms/term[@name='"+arg1+"'"+form+"]"+plural;
           	 nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
           	 if(nodes == null){
           	   expresion = "//locale/terms/term[@name='"+arg1+"'"+form+"]"+plural;
               nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET);
           	 }
			   
			   
            }
            if(nodes == null){
				  // $term = $this->locale->xpath("//cs:term[@name='$arg1'$form]$plural");
			   }
             if(nodes.item(0) !=null){
            	 if(arg3 !=null && arg3.equalsIgnoreCase("")){
            		 //return (string)$term[0]->{$arg3};
            	 }
            	// if (!isset($arg3) && isset($term[0]->single)) return (string)$term[0]->single;
               //  return (string)$term[0];
             }
       	 } catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
           

    	}
    	else if(type.equalsIgnoreCase("date_options")){
    	 
           try{
            if (this.styleLocale !=null) {
            	 expresion = "//locale[@xml:lang='en']/date[@form='"+arg1+"']";
                 nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET); 
              if (nodes == null) {
            	  expresion = "//locale/date[@form='"+arg1+"']";
                  nodes = (NodeList) xPath.evaluate(expresion, this.styleLocale, XPathConstants.NODESET); 
                
              }
            }
            if (nodes == null) {
              //$options = $this->locale->xpath("//cs:date[@form='$arg1']");
            }
           // if (isset($options[0]))return $options[0];
           } catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
    	}
		return null;
  	}
  
  public void setStyleLocale(Document cslDoc) {
	  
	  String xml = "";
	  Document newXML = null;
	  try {
		newXML =  DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  NodeList localNodes = cslDoc.getElementsByTagName("locale");
		  
		    Element el = newXML.createElement("style-locale");
	      for(int i=0; i<localNodes.getLength(); i++) {
	    	  Node node = localNodes.item(i);
	    	  Node copyNode = newXML.importNode(node, true);
			  el.appendChild(copyNode);
		  }
		  newXML.appendChild(el);
		  
		  if(newXML.getNodeName()!="")
		  {
			  this.styleLocale = newXML;
		  }
		  //printXmlDocument(newXML);
	  }
  
  public String printXmlDocument(Document document) {
	    DOMImplementationLS domImplementationLS = 
	        (DOMImplementationLS) document.getImplementation();
	    LSSerializer lsSerializer = 
	        domImplementationLS.createLSSerializer();
	    String string = lsSerializer.writeToString(document);
	     System.out.println(string);
	    return string;
	    //System.out.println(string);
	}

  }
