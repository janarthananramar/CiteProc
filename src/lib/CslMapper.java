package lib;

import java.util.HashMap;

public class CslMapper {

  protected static String mapField(String field) {
    HashMap<String, String> fieldMap = new HashMap<String, String>();

    if (fieldMap.size() == 0) {

      fieldMap.put("title", "title");
      fieldMap.put("container-title", "container-title");
      fieldMap.put("collection-title", "collection-title");
      fieldMap.put("original-title", "original-title");
      fieldMap.put("publisher", "publisher");
      fieldMap.put("publisher-place", "publisher-place");
      fieldMap.put("original-publisher", "original-publisher");
      fieldMap.put("original-publisher-place", "original-publisher-place");
      fieldMap.put("archive", "archive");
      fieldMap.put("archive-place", "archive-place");
      fieldMap.put("authority", "authority");
      fieldMap.put("archive_location", "archive_location");
      fieldMap.put("event", "event");
      fieldMap.put("event-place", "event-place");
      fieldMap.put("page", "page");
      fieldMap.put("page-first", "page-first");
      fieldMap.put("locator", "locator");
      fieldMap.put("version", "version");
      fieldMap.put("volume", "volume");
      fieldMap.put("number-of-volumes", "number-of-volumes");
      fieldMap.put("number-of-pages", "number-of-pages");
      fieldMap.put("issue", "issue");
      fieldMap.put("chapter-number", "chapter-number");
      fieldMap.put("medium", "medium");
      fieldMap.put("status", "status");
      fieldMap.put("edition", "edition");
      fieldMap.put("section", "section");
      fieldMap.put("genre", "genre");
      fieldMap.put("note", "note");
      fieldMap.put("annote", "annote");
      fieldMap.put("abstract", "abstract");
      fieldMap.put("keyword", "keyword");
      fieldMap.put("number", "number");
      fieldMap.put("references", "references");
      fieldMap.put("URL", "URL");
      fieldMap.put("DOI", "DOI");
      fieldMap.put("ISBN", "ISBN");
      fieldMap.put("call-number", "call-number");
      fieldMap.put("citation-number", "citation-number");
      fieldMap.put("citation-label", "citation-label");
      fieldMap.put("first-reference-note-number", "first-reference-note-number");
      fieldMap.put("year-suffix", "year-suffix");
      fieldMap.put("jurisdiction", "jurisdiction");

      // Date Variables
      fieldMap.put("issued", "issued");
      fieldMap.put("event", "event");
      fieldMap.put("accessed", "accessed");
      fieldMap.put("container", "container");
      fieldMap.put("original-date", "original-date");

      // Name Variables
      fieldMap.put("author", "author");
      fieldMap.put("editor", "editor");
      fieldMap.put("translator", "translator");
      fieldMap.put("recipient", "recipient");
      fieldMap.put("interviewer", "interviewer");
      fieldMap.put("publisher", "publisher");
      fieldMap.put("composer", "composer");
      fieldMap.put("original-publisher", "original-publisher");
      fieldMap.put("original-author", "original-author");
      fieldMap.put("container-author", "container-author");
      fieldMap.put("collection-editor", "collection-editor");

    }

    String[] vars = null;
    vars = field.split(" ");

    String returnVars = "";

    for (String eachVars : vars) {
      if (fieldMap.containsKey(eachVars.trim())) {
        returnVars = returnVars.concat(eachVars + " ");
      }
    }

    return returnVars;
  }

  protected String mapType(String types) {

    HashMap<String, String> typeMap = new HashMap<String, String>();

    if (typeMap.size() == 0) {

      typeMap.put("article", "article");
      typeMap.put("article-magazine", "article-magazine");
      typeMap.put("article-newspaper", "article-newspaper");
      typeMap.put("article-journal", "article-journal");
      typeMap.put("bill", "bill");
      typeMap.put("book", "book");
      typeMap.put("broadcast", "broadcast");
      typeMap.put("chapter", "chapter");
      typeMap.put("entry", "entry");
      typeMap.put("entry-dictionary", "entry-dictionary");
      typeMap.put("entry-encyclopedia", "entry-encyclopedia");
      typeMap.put("figure", "figure");
      typeMap.put("graphic", "graphic");
      typeMap.put("interview", "interview");
      typeMap.put("legislation", "legislation");
      typeMap.put("legal_case", "legal_case");
      typeMap.put("manuscript", "manuscript");
      typeMap.put("map", "map");
      typeMap.put("motion_picture", "motion_picture");
      typeMap.put("musical_score", "musical_score");
      typeMap.put("pamphlet", "pamphlet");
      typeMap.put("paper-conference", "paper-conference");
      typeMap.put("patent", "patent");
      typeMap.put("post", "post");
      typeMap.put("post-weblog", "post-weblog");
      typeMap.put("personal_communication", "personal_communication");
      typeMap.put("report", "report");
      typeMap.put("review", "review");
      typeMap.put("review-book", "review-book");
      typeMap.put("song", "song");
      typeMap.put("speech", "speech");
      typeMap.put("thesis", "thesis");
      typeMap.put("treaty", "treaty");
      typeMap.put("webpage", "webpage");

    }

    String[] vars = null;
    vars = types.split(" ");

    String returnVars = "";

    for (String eachVars : vars) {
      if (typeMap.containsKey(eachVars.trim())) {
        returnVars = returnVars.concat(eachVars + " ");
      }
    }

    return returnVars;
  }
}
