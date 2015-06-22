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
public class ChatRoom extends JPanel {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

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
		this.myOpcat2 = opcat2;
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(this.borderLayout1);
		// this.setPreferredSize(new
		// Dimension((int)(Opcat2.getFrame().getWidth()),
		// (int)(Opcat2.getFrame().getHeight() * 0.33)));
		this.scheme.getAttribute(OplColorScheme.PROMPT_STYLE).setBold(true);

		this.chatRoomWriteArea.setEditable(true);
		this.chatRoomWriteArea.setContentType("text");
		this.chatRoomReadArea.setBackground(new Color(230, 230, 230));
		this.chatRoomReadArea.setEditable(false);
		this.chatRoomReadArea.setContentType("text/html");
		this.chatRoomReadArea.setText(this.EMPTY_HTML);

		this.jScrollPane1.getViewport().add(this.chatRoomWriteArea, null);
		this.jScrollPane2.getViewport().add(this.chatRoomReadArea, null);

		this.jSplitPane1.add(this.jScrollPane1, JSplitPane.LEFT);
		this.jSplitPane1.add(this.jScrollPane2, JSplitPane.RIGHT);
		this.jSplitPane1.setDividerLocation(225);
		this.jSplitPane1.setOneTouchExpandable(true);
		// jSplitPane1.setResizeWeight(1.0);

		this.add(this.jSplitPane1, BorderLayout.CENTER);

		this.chatRoomWriteArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"pressed");
		this.chatRoomWriteArea.getActionMap().put("pressed", this.enterKeyPressed);
	}

	public void addNewLineToWriteArea(ChatMessageObject chatMessageObject) {

		String loginName = chatMessageObject.getSenderLoginName();
		Date timeStamp = chatMessageObject.getTimeStamp();
		String messageBody = chatMessageObject.getMessageBody();

		// LERA
		// Set new line
		this.readBF.insert(0, "<BR>");
		this.printAttr(OplColorScheme.DEFAULT_STYLE, messageBody);
		this.printAttr(OplColorScheme.MSG_STYLE, timeStamp.toString() + "> ");
		if (loginName.equals("System")) {
			this.printAttr(OplColorScheme.NOTE_STYLE, " " + loginName + "::");
		} else {
			this.printAttr(OplColorScheme.PROMPT_STYLE, " " + loginName + "::");
		}

		// Set new line
		this.readBF.append("<BR>");
		// String str1 = "<" + loginName + "> " + timeStamp.toString() + " > " +
		// messageBody + '\r'+'\n';
		// String str2 = chatRoomReadArea.getText() + str1;
		// chatRoomReadArea.setText(str2);
		this.chatRoomReadArea.setText(this.startHTML + this.readBF + this.endHTML);
	}

	Action enterKeyPressed = new AbstractAction() {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		 

		public void actionPerformed(ActionEvent e) {
			Integer userID = ChatRoom.this.myOpcat2.getTeamMember().getLocalEnhancedUser()
					.getUserID();
			Integer sessionID = ChatRoom.this.myOpcat2.getActiveCollaborativeSession()
					.getSessionID();
			ChatMessageObject chatMessageObject = new ChatMessageObject(userID,
					ChatRoom.this.myOpcat2.getTeamMember().getLocalEnhancedUser()
							.getLoginName(), ChatRoom.this.chatRoomWriteArea.getText(),
					new Date());
			try {
				ChatRoom.this.myOpcat2.getChatMessageHandler().publishMessageToTopic(userID,
						sessionID, ChatMessages.USER_CHAT_MESSAGE,
						chatMessageObject);
			} catch (Exception ex) {
			}
			ChatRoom.this.chatRoomWriteArea.setText("");
		}
	};

	public void printAttr(int attrName, String value) {
		this.readBF.insert(0, this.scheme.getAttribute(attrName).closingHTMLFontTag());
		this.readBF.insert(0, value);
		this.readBF.insert(0, this.scheme.getAttribute(attrName).openingHTMLFontTag());

	}
}
