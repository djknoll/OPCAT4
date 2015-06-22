package gui.opdProject.simplifiedOPM.gui;

import gui.controls.FileControl;
import gui.util.BrowserLauncher2;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by raanan.
 * Date: 31/07/11
 * Time: 15:39
 */
public class HTMPanel extends JPanel {

    File htmFile;
    boolean obq;
    Color fore;
    Color back;

    public HTMPanel(File htmFile, boolean obq, Color fore, Color back) {
        this.back = back;
        this.fore = fore;
        this.htmFile = htmFile;
        this.obq = obq;

        setOpaque(obq);
        setForeground(fore);
        setBackground(back);
        init();
    }

    private void init() {
        if (htmFile != null) {
            //setBorder(new EtchedBorder(EtchedBorder.RAISED));
            JEditorPane text = new JEditorPane();
            text.addHyperlinkListener(new Hyperactive());
            text.setContentType("text/html");
            text.setOpaque(obq);
            text.setBackground(back);
            text.setForeground(fore);
            text.setEditable(false);


            try {
                String content = FileUtils.readFileToString(htmFile);
                text.setText(content);
                this.add(text);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class Hyperactive implements HyperlinkListener {

        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                JEditorPane pane = (JEditorPane) e.getSource();
                if (e instanceof HTMLFrameHyperlinkEvent) {
                    HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                    BrowserLauncher2.openURL(evt.getURL().toExternalForm()) ;
                    //HTMLDocument doc = (HTMLDocument) pane.getDocument();
                    //doc.processHTMLFrameHyperlinkEvent(evt);
                } else {
                    try {
                        //pane.setPage(e.getURL());
                        BrowserLauncher2.openURL(e.getURL().toExternalForm()) ;
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }
}


