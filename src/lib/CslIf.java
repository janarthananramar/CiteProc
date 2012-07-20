package lib;

public class CslIf {
	private String match;
	private String types;
	private String[] variable;
	
	boolean evaluate(Object data) {
		 
		 String match ="";
		 int matches = 0; 
		 String [] types = null;
		 String type = null;
		 String[] variables = null;
		 match = (match == this.match) ? match : "all";
		 
		 if((type = this.types) != null) {
			 types = type.split(" ");
		
			 for(String eachType : types) {
				 if(data.type != null){
					 if(data.type==type && match == "any") 
						 return true;
					 if(data.type!=type && match == "all") 
						 return false;
					 if(data.type==type) matches++;
				 }
			 }
			 
			 if ((match == "all") && (matches == types.length)) 
				 return true;
			 if ((match == "none") && (matches == 0)) 
				 return true;
			 return false;
		}
		 
		 if((variables = this.variable) != null) {
			 
		 }
	}

}
