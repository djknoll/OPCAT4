package simulation.queue;

import simulation.tasks.SimulationTask;
import java.util.ArrayList;
import java.io.File;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import simulation.creator.FileCreator;
import java.io.*;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;

import org.jdom.output.XMLOutputter;

import exportedAPI.opcatAPIx.IXSystem;

/**
 * <p>Title: Simulation Module</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public class QueueSerializer {
  private ArrayList<SimulationTask> queue;
  private XMLOutputter xmlOutput = new XMLOutputter();

  public QueueSerializer(ArrayList<SimulationTask> queue) {
    this.queue = queue;
  }

  public void saveAsXML(File targetFile, IXSystem ownerSystem) {
    try {
      FileOutputStream fos = new FileOutputStream(targetFile);
      Document document = new Document();
      Element root = new Element("root");
      document.setRootElement(root);

      root.setAttribute(FileCreator.XML_TAGS.NUM_OF_TASKS,
                        Integer.toString(queue.size()));
      root.setAttribute(FileCreator.XML_TAGS.MAX_ENTITY_ID,
          Long.toString(ownerSystem.getEntityMaxEntry()));
      
      
      for (int i = 0; i < queue.size(); i++) {
        Element currTask = new Element(FileCreator.XML_TAGS.TASK_PREFIX + i);
        queue.get(i).fillXML(currTask);
        root.addContent(currTask);
      }

      Format format = xmlOutput.getFormat();
      format.setIndent("      ");
      format.setTextMode(TextMode.TRIM);
      format.setLineSeparator(System.getProperty("line.separator"));
      xmlOutput.output(document, fos);
      fos.close();
    }
    catch (FileNotFoundException ex) {
    }
    catch (IOException ex) {
    }
  }
}
