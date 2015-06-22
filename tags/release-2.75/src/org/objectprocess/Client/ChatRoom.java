package org.objectprocess.Client;

import gui.Opcat2;
import gui.opdProject.OplColorScheme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

/**
 * @author moonwatcher
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ChatRoom  extends JPanel{
  Opcat2 myOpcat2;

  private JSplitPane jSplitPane1 = new JSplitPane();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTextPane chatRoomWriteArea = new JTextPane();
  private JTextPane chatRoomReadArea = new JTextPane();
  private final String EMPTY_HTML = "<html><head></head><body></body></html>";
  private final String startHTML = "<HTML><HEAD></HEAD><BODY>";
  private final String endHTML = "</BODY></HTML>";
  private StringBuffer readBF = new StringBuffer(4096);
  private OplColorScheme scheme = new OplColorScheme();
  BorderLayout borderLayout1 = new BorderLayout();

  public ChatRoom(Opcat2 opcat2) {
    myOpcat2 = opcat2;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
//    this.setPreferredSize(new Dimension((int)(Opcat2.getFrame().getWidth()),
//                                        (int)(Opcat2.getFrame().getHeight() * 0.33)));
    scheme.getAttribute(scheme.PROMPT_STYLE).setBold(true);

    chatRoomWriteArea.setEditable(true);
    chatRoomWriteArea.setContentType("text");
    chatRoomReadArea.setBackground(new Color(230, 230, 230));
    chatRoomReadArea.setEditable(false);
    chatRoomReadArea.setContentType("text/html");
    chatRoomReadArea.setText(EMPTY_HTML);

    jScrollPane1.getViewport().add(chatRoomWriteArea, null);
    jScrollPane2.getViewport().add(chatRoomReadArea, null);

    jSplitPane1.add(jScrollPane1, JSplitPane.LEFT);
    jSplitPane1.add(jScrollPane2, JSplitPane.RIGHT);
    jSplitPane1.setDividerLocation(225);
    jSplitPane1.setOneTouchExpandable(true);
//    jSplitPane1.setResizeWeight(1.0);

    this.add(jSplitPane1, BorderLayout.CENTER);

    chatRoomWriteArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressed");
    chatRoomWriteArea.getActionMap().put("pressed",enterKeyPressed);
  }

  public void addNewLineToWriteArea(ChatMessageObject chatMessageObject) {

    String loginName=  chatMessageObject.getSenderLoginName();
    Date timeStamp = chatMessageObject.getTimeStamp();
    String messageBody = chatMessageObject.getMessageBody();

    //LERA
    //Set new line
    readBF.insert(0,"<BR>");
    printAttr(scheme.DEFAULT_STYLE,messageBody);
    printAttr(scheme.MSG_STYLE, timeStamp.toString()+"> ");
    if (loginName.equals("System"))
      printAttr(scheme.NOTE_STYLE," "+loginName+"::");
    else
      printAttr(scheme.PROMPT_STYLE," "+loginName+"::");

    //Set new line
    readBF.append("<BR>");
    //String str1 = "<" + loginName + "> " + timeStamp.toString() + " > " +
    //  messageBody + '\r'+'\n';
    //String str2 = chatRoomReadArea.getText() + str1;
    //chatRoomReadArea.setText(str2);
    chatRoomReadArea.setText(startHTML + readBF + endHTML);
  }

  Action enterKeyPressed = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        Integer userID = myOpcat2.getTeamMember().getLocalEnhancedUser().getUserID();
        Integer sessionID = myOpcat2.getActiveCollaborativeSession().getSessionID();
        ChatMessageObject chatMessageObject = new ChatMessageObject(
                         userID,
                         myOpcat2.getTeamMember().getLocalEnhancedUser().getLoginName(),
                         chatRoomWriteArea.getText(),
                         new Date());
        try {
          myOpcat2.getChatMessageHandler().publishMessageToTopic(
              userID,
              sessionID,
              ChatMessages.USER_CHAT_MESSAGE,
              chatMessageObject);
        }
        catch (Exception ex) {}
        chatRoomWriteArea.setText("");
      }
  };


  public void printAttr(int attrName, String value){
    readBF.insert(0,scheme.getAttribute(attrName).closingHTMLFontTag());
    readBF.insert(0,value);
    readBF.insert(0,scheme.getAttribute(attrName).openingHTMLFontTag());

  }
}


