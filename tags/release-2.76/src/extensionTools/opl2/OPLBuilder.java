package extensionTools.opl2;
import exportedAPI.opcatAPI.ISystem;
import gui.opdProject.OplColorScheme;

public class OPLBuilder{

private OPLgenerator oplGenerator;

public OPLBuilder(ISystem system, OplColorScheme colorScheme){
  oplGenerator = new OPLgenerator(system);
}

public StringBuffer getOplXmlScript(){
  return oplGenerator.extractOPLScript();
}

public StringBuffer getOplHTML(){
  return oplGenerator.getOplHTML();
}

public StringBuffer getOplText(){
  return oplGenerator.getOplText();
}

public StringBuffer getOplText(long opd){
  return oplGenerator.getOplText(opd);
}


public StringBuffer getOplHTML(long opd){
  return oplGenerator.getOplHTML(opd);
}

//public BufferedString getOplHTMLScript(long opd){
//}

//public BufferedString getOplHTMLScript(){
//  return oplGenerator.getOplHTMLScript();
//}

}