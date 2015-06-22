package extensionTools.opl2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import exportedAPI.OpcatExtensionTool;
import exportedAPI.opcatAPI.ISystem;
import extensionTools.opl2.alg.OPLContainer;
import extensionTools.opl2.generated.OPLscript;
import extensionTools.opl2.generated.ObjectFactory;


public class OPLgenerator implements OpcatExtensionTool
{
	public OPLgenerator(ISystem opcatSystem_){
          opcatSystem = opcatSystem_;

          try{
          jc = JAXBContext.newInstance( "extensionTools.opl2.generated" );
          }catch(Exception e){e.printStackTrace();}
          objFactory = new ObjectFactory();
          alg = new OPLContainer(opcatSystem,objFactory);
	}

        public OPLgenerator(){
          try{
          jc = JAXBContext.newInstance( "extensionTools.opl2.generated" );
          }catch(Exception e){e.printStackTrace();}
          objFactory = new ObjectFactory();
	}

	public String getName()
	{
		return "Simple OPL";
	}

	public JPanel getAboutBox(){ return null; }

	public String getHelpURL(){ return null;}


        public StringBuffer extractOPLScript(){
          StringWriter writer = new StringWriter(4096);
          try{
            alg.refresh();
            opl = alg.getOPLTree();

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(opl, writer);
            writer.flush();
          }catch(Exception e){e.printStackTrace();}
          finally{  }
          return writer.getBuffer();
        }

        public StringBuffer getOplHTML() {
          StringBuffer bf = new StringBuffer();
          try {
            alg.refresh();
            opl = alg.getOPLTree();
            printer = new PrettyPrinter(opcatSystem.getISystemStructure(),
                                        opcatSystem);
            printer.visit(opl);
            //PlainTextPrinter pr = new PlainTextPrinter(opcatSystem.getISystemStructure(),
                                //        opcatSystem);
            //pr.visit(opl);
            //System.out.println("My Plain Text: "+pr.bf.toString());
            bf = printer.getBuffer();
            //initFileName();
            //extractOPLFile(filename, opl);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          return bf;
        }

        public StringBuffer getOplText(){
          StringBuffer bf = new StringBuffer();
          try {
            alg.refresh();
            opl = alg.getOPLTree();
            PlainTextPrinter pr = new PlainTextPrinter(opcatSystem.getISystemStructure(),
                                        opcatSystem);
            pr.visit(opl);
            //System.out.println("My Plain Text: "+pr.bf.toString());
            bf = pr.getBuffer();

          }
          catch (Exception e) {
            e.printStackTrace();
          }
          return bf;
        }

        public StringBuffer getOplText(long opd) {
      StringBuffer bf = new StringBuffer();
      try {
        alg.refresh();
        opl = alg.getOPLTreePerOPD(opd);
        PlainTextPrinter pr = new PlainTextPrinter(opcatSystem.getISystemStructure(),
                                        opcatSystem);
            pr.visit(opl);
            //System.out.println("My Plain Text: "+pr.bf.toString());
            bf = pr.getBuffer();


      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return bf;
    }



        public StringBuffer getOplHTML(long opd) {
           // System.err.println(""+opcatSystem.getName());
          StringBuffer bf = new StringBuffer();
          try {
            alg.refresh();
            opl = alg.getOPLTreePerOPD(opd);
            printer = new PrettyPrinter(opcatSystem.getISystemStructure(),
                                        opcatSystem);
            printer.visit(opl);
            bf = printer.getBuffer();
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          return bf;
        }




       /**
        * Creates OPL script file in XML format
        * @param name - full path to place where
        * the sript will be extracted
        * @param opl - OPLscript
        * * */
        public void extractOPLFile(String name, OPLscript opl){
          try{
            File f = new File(name);
            //System.out.println("Name of file: "+name);
            if(f.exists())
              f.delete();
            f.createNewFile();
            // true - makes append mode
            Writer fw = new BufferedWriter(new FileWriter(f),4096);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(opl, fw);
            fw.close();
          }
     /*     catch(PropertyException e){
            System.err.println("\n Couldn't create new file, Bad property !!!");
            e.printStackTrace();
          }
          catch(JAXBException e){
            System.err.println("\n Couldn't create new file, JAXB problem !!!");
            e.printStackTrace();
          }
          catch(IOException e){
            System.err.println("\n Couldn't create new file, IOException !!!");
            e.printStackTrace();
          }*/
          catch(Exception e){
            //System.err.println("\n Couldn't create new file !!!");
            e.printStackTrace();
          }
        }

        public OPLscript getOPLTree(){
          try{
            alg.refresh();
            return alg.getOPLTree();
          }catch (Exception e){return null;}
        }

        public OPLscript getOPLTreePerOPD(long opd)throws Exception{
          try{
            return alg.getOPLTreePerOPD(opd);
          } catch(Exception e){
           // System.out.println("The given OPD name does not exist!");
            e.printStackTrace();
            throw e;
          }
       }

	// the "main" of your tool
	public JPanel execute(ISystem opcatSystem_)
	{

//                StringBuffer bf;
//                PrettyPrinter printer = new PrettyPrinter();
//                opcatSystem = opcatSystem_;
//                initFileName();
//		JPanel returnPanel = new JPanel(new BorderLayout()); // this panel we will return
//		JPanel contentPanel = new JPanel(); // this panel will hold all inf
//                alg = new OPLContainer(opcatSystem);
//                opl = getOPLTree();
//                extractOPLFile(filename, opl);
//                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//		contentPanel.setBackground(new Color(230, 230, 230));
//                label = new JLabel();
//                bf = printer.getBuffer();
//                //label.setText(bf.toString());
//                contentPanel.add(label);
//		JScrollPane sp = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		returnPanel.add(sp);
//                returnPanel.add(refreshButton);
//		return returnPanel;
               StringBuffer bf;
               opcatSystem = opcatSystem_;
               alg = new OPLContainer(opcatSystem,objFactory);
               opl = getOPLTree();
               initFileName();
               JPanel returnPanel = new JPanel(new BorderLayout()); // this panel we will return
               JTextPane contentPanel = new JTextPane();
               contentPanel.setContentType("text/html");
               contentPanel.setEditable(false);
               //opl = getOPLTree();
               extractOPLFile(filename, opl);
               //StringBuffer b = extractOPLScript();
               //System.out.println(b.toString());
               contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
               contentPanel.setBackground(new Color(230, 230, 230));
               printer = new PrettyPrinter(opcatSystem.getISystemStructure(),opcatSystem);
               printer.visit(opl);
               bf = printer.getBuffer();
               //System.err.println(bf);
               contentPanel.setText(bf.toString());
               JScrollPane sp = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
               returnPanel.add(sp);
               return returnPanel;

	}

        private void initFileName(){

          filename = opcatSystem.getName();
          filename = filename.concat(".xml");
        }


        // The variable is used while marshalling OPLscript
        protected JAXBContext jc;
        protected OPLContainer alg;
        private String filename;
        private OPLscript opl;
        private ISystem opcatSystem;
        private PrettyPrinter printer;
        private ObjectFactory objFactory;


}

