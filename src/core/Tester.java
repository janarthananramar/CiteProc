package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Tester {

	  public static void main(String args[]) throws FileNotFoundException  
	  {
		  
		  JSONParser parser = new JSONParser();
		  try {
			  StringBuffer fileData = new StringBuffer(1000); 
			  BufferedReader reader = new BufferedReader( 
			  new FileReader("/Applications/XAMPP/htdocs/citeproc/tests/name_SubstituteName.json")); 
			  char[] buf = new char[1024]; 
			  int numRead=0; 
			  while((numRead=reader.read(buf)) != -1){ 
			  String readData = String.valueOf(buf, 0, numRead); 
			  fileData.append(readData); 
			  buf = new char[1024]; 
			  } 
			  reader.close();  
			 String value =  fileData.toString();
			 int index = (int)value.indexOf("*/");
			 
			 if(index!=-1) 
				 index=index+2;
			 else 
				 index=0;

			 String data = value.substring(index);
			 
			
			Object obj = parser.parse(data);
			JSONObject jsonObject = (JSONObject) obj;
			String csl = (String) jsonObject.get("csl");
			
			System.out.println("mode-->"+jsonObject.get("mode"));
			
			if(jsonObject.get("mode").toString()!="bibliography") {
			
				JSONArray inputData = (JSONArray) jsonObject.get("input");
				int inputCount = inputData.size();
				String output = "";
				
			CiteProc c =  new CiteProc(csl,""); 
			
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = inputData.iterator();

			while (iterator.hasNext()) {
				 System.out.println((String) jsonObject.get("mode"));
				output+=(String)c.render(iterator.next(), (String) jsonObject.get("mode"));
				 System.out.println("<<<<<<<<<<<<<<<<"+(String) jsonObject.get("mode"));
				System.out.println("output--..."+output);
			}
			
			System.out.println("json Result--->"+jsonObject.get("result"));
			System.out.println("Output--->"+output);
			
			System.out.println("json Result length--->"+((String)jsonObject.get("result")).length());
			System.out.println("Output length--->"+output.length());
			
			if(output.equalsIgnoreCase(((String)jsonObject.get("result")))) {
				System.out.println("**********PASSED**********");
			}
			else {
				System.out.println("**********FAILED**********");
			}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	
	
}
