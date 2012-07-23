package lib;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import core.CiteProc;

public class CslGroup extends CslFormat {

  public CslGroup(Element domNode, CiteProc citeProc) {
    super(domNode, citeProc);
  }

  public String render(JSONObject data, String mode){
	  String text = "";
	  ArrayList textParts = new ArrayList();
	  int terms = 0;
	  int variables = 0;
	  int haveVariables = 0;
	  Object element = null;
	  CslText csltext = null;
	  CslLabel csllbl = null;
	  for(int i=0;i<this.elements.size();i++){
		  element = this.elements.get(i);
		  if(element instanceof CslText){
			  csltext = (CslText)element;
		   if(csltext.source.equalsIgnoreCase("term") || csltext.source.equalsIgnoreCase("value")){
			   terms++;
		   }
		  }
		  if(element instanceof CslLabel){
			  csllbl = (CslLabel) element;
			  terms++;
		  }
			 
	  }
  }
  function render($data, $mode) {
	    $text = '';
	    $text_parts = array();

	    $terms = $variables = $have_variables = 0;
	    foreach ($this->elements as $element) {
	      if (($element instanceof csl_text) &&
	          ($element->source == 'term' ||
	           $element->source == 'value' )) {
	    	  
	        $terms++;
	      }
	      if (($element instanceof csl_label)) $terms++;
	      if ($element->source == 'variable' &&
	          isset($element->variable) &&
	          !empty($data->{$element->variable})
	         ) {
	        $variables++;
	      }

	      $text = $element->render($data, $mode); 
	      

	      if (!empty($text)) {
	        $text_parts[] = $text;
	        if ($element->source == 'variable' || isset($element->variable)) $have_variables++;
	        if ($element->source == 'macro') $have_variables++;
	      }
	    }

	    if (empty($text_parts)) return;
	    if ($variables  && !$have_variables ) return; // there has to be at least one other none empty value before the term is output
	    if (count($text_parts) == $terms) return; // there has to be at least one other none empty value before the term is output

	    $delimiter = $this->delimiter;
	    $text = implode($delimiter, $text_parts); // insert the delimiter if supplied.


	    return $this->format($text);
	  }
}
