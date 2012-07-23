package lib;

import org.w3c.dom.Node;

import core.CiteProc;

public class CslFactory {

  public static Object create(Node domNode, CiteProc citeProc) {

    char[] stringArray = domNode.getNodeName().replaceAll("-", "").toCharArray();
    stringArray[0] = Character.toUpperCase(stringArray[0]);
    String className = new String(stringArray);

    className = "lib.Csl" + className;
    System.out.println("fasctory class name--->" + className);
    try {
      Class.forName(className);
      System.out.println("factory class name--->" + Class.forName(className));
      if (className.equalsIgnoreCase("lib.CslLocale") /* || className.equalsIgnoreCase("lib.CslLabel") */)
        return Class.forName(className).getConstructors().getClass();
      else
        return Class.forName(className).getConstructor(Node.class, CiteProc.class).newInstance(new Object[] { domNode, citeProc });
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("**" + className + " not found**");
      return null;
    }
  }
}