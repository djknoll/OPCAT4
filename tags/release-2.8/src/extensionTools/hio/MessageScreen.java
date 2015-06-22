package extensionTools.hio;


import gui.Opcat2;
import gui.images.misc.MiscImages;
import gui.opdProject.OpdProject;
import gui.util.HtmlPanel;
import gui.util.OpcatException;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;


/**
 * <p>Title: MessageScreen </p>
 * <p>Description: opens a window with a message to the user that his drawing
 * was'nt recognized by the system and advising him to read the tips for
 * drawing</p>
 */

public class MessageScreen {

  /**
   * OpdProject myProject - the current project that was opened in opcat
   */
  private OpdProject myProject;
 /**
   * boolean showMsg - a flag which indicates weather to show the message again
   */
  private static boolean showMsg = true;
  /**
   * boolean screenIsOpen - a flag that indicates if the message is already open
   */
  private boolean screenIsOpen = false;
  /**
   * long showTime - the length of time the message is shown
   */
  private long showTime = 5000;
  /**
   * long startTime - the time when the message was launched
   */
  private long startTime;
  /**
   * MessageTimer myMsgTimer - a timer for shawing the message
   */
  private MessageTimer myMsgTimer;
  /**
   * Checkbox dontShow - a checkbox which indicates if not to shaw the message again
   */
  private Checkbox dontShow;
  /**
   * JWindow msgScreen - the window for shawing the message
   */
  private JWindow msgScreen;
  /**
   * Button closeMsg - a button for closing the message window
   */
  private Button closeMsg;
  /**
   * Button openTips - a button for opening the tips window
   */
  private Button openTips;
  
  /**
   * empty constructor
   */
  public MessageScreen() {
  }

  /**
   * the function showMessage is the main function of the class, which 
   * invokes the creation of the message window and the timer for its
   * appearence. 
   * @param myProject_ the current project.
   */
  public void showMessage(OpdProject myProject_)
     {
  	
  		//initiating the variable to current project
       myProject = myProject_;
       
       //if user had asked not to show the message again 
       if( showMsg == false)return;
       
       //initiating the variable to the current time
       startTime = System.currentTimeMillis();
      
       //if there is no open message window then open one.
       if( screenIsOpen == false)createMsgWindow();
       //set the flag so it will indicate window is open
       screenIsOpen = true;

       //create and run a timer for the time window will be shown
       myMsgTimer = new MessageTimer();
       myMsgTimer.start();


     }
 
  /**
   * the function createMsgWindow builds the message window, sets its
   * location and shows it. 
   */
  private void createMsgWindow()
  {
  	 //building the screen
    buildScreen();

    //add listeners to screens button
    addListeners();

    msgScreen.pack();
    
    //determine where to place the message on the computer screen 
    msgScreen.setLocation(myProject.getMainFrame().getWidth()  -
                             msgScreen.getSize().width - 10,
                             myProject.getMainFrame().getHeight()  -
                             msgScreen.getSize().height - 35 );

    //open the window
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        msgScreen.setVisible(true);
      }
    });
  	}

  		/**
  		 * the function hideWaitMessage close the message window.
  		 *
  		 */
       private void hideWaitMessage()
       {
       	//set the flag so it will indicate window is close
       	screenIsOpen = false;
       	
       	//if window is already close then return
         if (msgScreen == null)
         {
           return;
         }
         //dispose the window
         SwingUtilities.invokeLater(new Runnable() {
           public void run() {
             msgScreen.dispose();
           }
         });

       }

       /**
        * a class which creates a thread that runs a timer for the time the window should stay
        * open, and close the window after it expires.
        */
       class MessageTimer
           extends Thread {
       	//create a timer
         java.util.Timer time = new java.util.Timer();
         
         /**
           * <p>Title:class RemindTask </p>
           * <p>Description: runs a timer for the time the window should be open and
           * closes the window when the time expires</p>
           */
          class RemindTask
              extends TimerTask {
            public void run() {
              //if the time expired close the window
                if ( startTime + showTime <= System.currentTimeMillis()) {
                    hideWaitMessage();                  
                }
                time.cancel(); //Terminate the timer thread
                
            }
          } //end of class RemindTask


                     MessageTimer() { //the constructor
                         super();
                       }
                     
                     //run the thread
                       public void run() {
                       		//create the timer
                       		time.schedule(new RemindTask(), System.currentTimeMillis() + showTime - startTime);                         
                         
                       		//if timer is done then kill the thread
                         try {
                         	myMsgTimer.join();                         	
                         }
                         catch (InterruptedException e) {
                           System.out.println("msgTimer exception");
                         }
                       }
                     } //end of class MessageTimer

       /**
        * the function buildScreen builds the message window
        */
        private void buildScreen() {

        	//create a window
          msgScreen = new JWindow(myProject.getMainFrame());
          //create a panel and sets its look (frame and background) 
          JPanel mainPanel = new JPanel(new GridLayout(3,1));
          mainPanel.setBorder(new LineBorder(new Color(0,51,102),2,true));
          mainPanel.setBackground(Color.WHITE);

          //add message and buttons panels
          mainPanel.add(buildMsg());
          mainPanel.add(buildButtonsPanel());
          mainPanel.add(buildCheckbox());

          msgScreen.getContentPane().add(mainPanel, BorderLayout.CENTER);

              }

        /**
         * the function buildMsg create the panel of the message
         * @return the message panel
         */
         private JPanel buildMsg(){
         	//create a panel
         JPanel msgPanel = new  JPanel(new GridLayout(2,1));
         
         //set panel background
         msgPanel.setBackground(Color.WHITE);
         
         //create the message, its size and looks
         JLabel msgLabel = new JLabel(" The sketch was not recognized ");
          msgLabel.setForeground(new Color(0,51,102));
          msgLabel.setFont(new Font("Arial",Font.BOLD,16));

          //add message to panel
          msgPanel.add(msgLabel);

           return msgPanel;
         }

         /**
          * the function buildButtonsPanel create the panel of the buttons
          * @return the buttons panel
          */
         private JPanel buildButtonsPanel(){

           JPanel ButtonsPanel = new JPanel(new FlowLayout());
           ButtonsPanel.setBackground(Color.WHITE);

           openTips = new Button("Show Tips");

           closeMsg = new Button("Close Window");

           ButtonsPanel.add(openTips);
           ButtonsPanel.add(closeMsg);

           return ButtonsPanel;
         }

         /**
          * the function addListeners creates and adds the listeners for the
          * buttons and the check box
          */
         private void addListeners(){

         	//add a listener to "Close Window" button
           closeMsg.addActionListener(
               new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      hideWaitMessage();
                    }
                  }
             );

           	//add a listener to "Show Tips" button
             openTips.addActionListener(
               new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      openTips();
                    }
                  }
             );

             //add a listener for the chackbox
             dontShow.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        hideWaitMessage();
        showMsg = false;
      }
    });

         }


         /**
          * the function buildCheckbox create a check box
          * @return the checkbox
          */
          private Checkbox buildCheckbox(){          	 
            dontShow = new Checkbox("Don't show this message again");
            return dontShow;
          }

          /**
           * the function openTips close the message window and opens the 
           * tips window
           */
          private void openTips(){
          	//close message window
            hideWaitMessage();
            
            //create a thread for the tips window
            Thread runner = new Thread() {

            	/**
            	 * the main function for the tips window thread to run, creates and run
            	 * the window
            	 */
                public void run() {
                  JFrame tipsWindow;
                  
                  //create the window, its size and location
                  final ImageIcon logoIcon = MiscImages.LOGO_SMALL_ICON;
                  final String fileSeparator = System.getProperty(
            	  "file.separator");

                  tipsWindow = new JFrame("Drawing Tips");
                  tipsWindow.setIconImage(logoIcon.getImage());
					HtmlPanel helpHtml = null;
                    try {
                        helpHtml = new HtmlPanel("file:" +
                                System.getProperty("user.dir") +
        						fileSeparator + "help" +
        						fileSeparator + "HIO" +
                                fileSeparator + "tips1.htm");
                    } catch (OpcatException e) {
                        try	{
                            helpHtml = new HtmlPanel("file:"
                                + "C:" + fileSeparator +"Program Files"
                                + fileSeparator +"Opcat"
                                + fileSeparator + "help" 
                                + fileSeparator + "HIO"
                                + fileSeparator + "tips1.htm");
                        }
                        catch (OpcatException e2) {
                            JOptionPane.showMessageDialog(
                					Opcat2.getFrame(),
                					"Help files were not found",
                					"Help error",
                					JOptionPane.ERROR_MESSAGE);    
                        }
                    }

                  tipsWindow.getContentPane().add(helpHtml);

                  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                  tipsWindow.setBounds(0, 0, (int) (screenSize.getWidth() * 7 / 8),
                                                           (int) (screenSize.getHeight() * 7 / 8));
                  tipsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                  tipsWindow.setVisible(true);

                }
          };
          //run the thread
          runner.start();
          }

}