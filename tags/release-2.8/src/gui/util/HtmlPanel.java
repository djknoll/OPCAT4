package gui.util;
import gui.images.browser.BrowserImages;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

public class HtmlPanel extends JPanel{
	private JEditorPane html;
	private Stack forward, backward;
	private JComboBox urlBar;
	private Cursor waitCursor, defaultCursor;
    private JToolBarButton forwardButton, backwardButton;
    private boolean goMutex;

	public HtmlPanel(String defaultURL) throws OpcatException
    {
		setLayout(new BorderLayout());
		forward = new Stack();
		backward = new Stack();
		waitCursor = new Cursor(Cursor.WAIT_CURSOR);
		defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        goMutex = true;
		urlBar = new JComboBox();
		urlBar.setEditable(false);
		urlBar.addItem(defaultURL.toString());
        urlBar.addActionListener(goAction);
		add(ConstructToolBar(), BorderLayout.NORTH);
		try{
			URL url = null;
			try{
				url = new URL(defaultURL);
			}catch (java.net.MalformedURLException e){
			    url = null;
				throw new OpcatException("Cannot open " + defaultURL);
			    //JOptionPane.showMessageDialog(null, "Cannot open " + defaultURL, "URL Error" ,JOptionPane.ERROR_MESSAGE);
			}
			if(url != null){
				html = new JEditorPane(url);
				html.setEditable(false);
				html.addHyperlinkListener(createHyperLinkListener());
				JScrollPane scroller = new JScrollPane();
				JViewport vp = scroller.getViewport();
				vp.add(html);
				add(scroller, BorderLayout.CENTER);
			}
		}catch (MalformedURLException e) {
		    throw new OpcatException("URL is not valid: " + defaultURL);
		}
		catch (IOException e) {
		    throw new OpcatException("URL is not valid: " + defaultURL);
		}
	}

	// construct navigation toolbar
	private JToolBar ConstructToolBar(){
		JToolBar tb = new JToolBar();
		backAction.setEnabled(false);
		forwardAction.setEnabled(false);
        backwardButton = new JToolBarButton(backAction, "Return to Previous Page");
        backwardButton.setEnabled(false);
		tb.add(backwardButton);
        forwardButton = new JToolBarButton(forwardAction, "Go to NextPage");
        forwardButton.setEnabled(false);
		tb.add(forwardButton);
		//tb.add(new JToolBarButton(stopAction, "Stop Loading"));
		//tb.add(new JToolBarButton(searchAction, "Search Hep for..."));
		tb.add(new JToolBar.Separator());
		tb.add(urlBar);
		urlBar.setSize( (int)(tb.getSize().getWidth() -100 - tb.getMargin().left - tb.getMargin().right- (new JToolBar.Separator()).getSize().getWidth()), (int)(tb.getSize().getHeight()- tb.getMargin().top - tb.getMargin().bottom));
//		tb.add(new JToolBarButton(goAction, "Jump to this URL"));
		return tb;
	}

	// navigation toolbar actions
	Action backAction = new AbstractAction("Back", BrowserImages.BACK){
		public void actionPerformed(ActionEvent e){
			try
			{

				forward.push(html.getPage());

                URL currUrl = (URL)backward.pop();

				html.setPage(currUrl);
                goMutex = false;
				urlBar.setSelectedItem(currUrl.toString());
                goMutex = true;

                forwardButton.setEnabled(true);
                forwardAction.setEnabled(true);
				if (backward.isEmpty())
				{
					backAction.setEnabled(false);
                    backwardButton.setEnabled(false);
				}
				else
				{
					backAction.setEnabled(true);
                    backwardButton.setEnabled(true);
				}
			}catch(IOException ioe) {
				System.out.println("IOE: " + ioe);
			}
		}
	};

	Action forwardAction = new AbstractAction("Forward", BrowserImages.FORWARD){
		public void actionPerformed(ActionEvent e){
			try
			{
				backward.push(html.getPage());
                backwardButton.setEnabled(true);
                backAction.setEnabled(true);

				html.setPage((URL)forward.peek());

                goMutex = false;
				urlBar.setSelectedItem(((URL)forward.pop()).toString());
                goMutex = true;
				if (forward.isEmpty())
				{
					forwardAction.setEnabled(false);
                    forwardButton.setEnabled(false);
				}
				else
				{
					forwardAction.setEnabled(true);
                    forwardButton.setEnabled(true);
				}
			}catch(IOException ioe) {
				System.out.println("IOE: " + ioe);
			}

		}
	};

	Action stopAction = new AbstractAction("Stop", BrowserImages.STOP){
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "This option is not implemented yet", "Message" ,JOptionPane.INFORMATION_MESSAGE);
		}
	};

	Action searchAction = new AbstractAction("Search", BrowserImages.SEARCH){
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "This option is not implemented yet", "Message" ,JOptionPane.INFORMATION_MESSAGE);
		}
	};

	Action goAction = new AbstractAction("Go", BrowserImages.GO){
		public void actionPerformed(ActionEvent e){
            if (!goMutex)
            {
                return;
            }

            try
            {
                if (!html.getPage().equals(new URL((String)urlBar.getSelectedItem())))
                {
                    backward.push(html.getPage());

			        setCursor(waitCursor);
/******************************************************************/
                    html.setPage(new URL((String)urlBar.getSelectedItem()) );
/******************************************************************/
                    setCursor(defaultCursor);
                    forward.clear();
                    backwardButton.setEnabled(true);
                    backAction.setEnabled(true);
                    forwardButton.setEnabled(false);
                    forwardAction.setEnabled(false);
                }
            }catch (IOException ioe)
            {
                backward.pop();
                System.out.println("IOE: " + ioe);
            }
		}
	};



	public HyperlinkListener createHyperLinkListener()
	{
		return new HyperlinkListener()
		{
		    public void hyperlinkUpdate(HyperlinkEvent e)
			{
				final HyperlinkEvent tempE = e;

                if (tempE.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    if (tempE instanceof HTMLFrameHyperlinkEvent)
                    {
                        ((HTMLDocument)html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)tempE);
                    }
                    else
                    {
                        setCursor(waitCursor);

                        if (!html.getPage().equals(tempE.getURL()))
                        {
                            backward.push(html.getPage());

                            try
                            {
                                html.setPage(tempE.getURL());

                                urlBar.addItem(tempE.getURL().toString());

                                if (urlBar.getItemCount() > 10)
                                {
                                    urlBar.removeItemAt(urlBar.getItemCount()-1);
                                }

                                goMutex = false;
                                urlBar.setSelectedItem(tempE.getURL().toString());
                                goMutex = true;
                                setCursor(defaultCursor);
                                forward.clear();
                                backwardButton.setEnabled(true);
                                backAction.setEnabled(true);
                                forwardButton.setEnabled(false);
                                forwardAction.setEnabled(false);
                            }
                            catch (IOException ioe)
                            {
                                backward.pop();
                                setCursor(defaultCursor);
                                System.out.println("IOE: " + ioe);
                            }
                        }
                    }
                }
	    	}
		};
	}

}
