package extensionTools.reuse;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.opdProject.OpdProject;
import gui.projectStructure.ThingInstance;
import gui.util.CustomFileFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * The target of this class is to manage the import dialog with the user,
 * allowing the user to choose the location of a file to be imported directly to
 * the current model.
 *  
 */
public class ImportDialog extends JDialog {
    JPanel importPanel = new JPanel();

    JProgressBar pBar = new JProgressBar();

    Border border1;

    TitledBorder titledBorder1;

    JTextField fileNameTextField = new JTextField();

    JButton chooseFileButton = new JButton();

    JLabel reuseTypeLabel = new JLabel();

    JLabel saveWarningLabel = new JLabel();

    JComboBox reuseTypeComboBox = new JComboBox();

    JButton cancelButton = new JButton();

    JButton importButton = new JButton();

    OpdProject reusedProject = null;

    OpdProject curr;

    JDesktopPane desktop;

    JFileChooser fc = new JFileChooser();

    Opcat2 opcat2;

    JWindow waitScreen;

    private boolean isOperated = true;

    private int typeOfReuse = ReuseCommand.NO_REUSE;

    private String importFileName = "";

    //ProgressBar progressBar;
    public ImportDialog(OpdProject currProject, JDesktopPane _desktop)
            throws HeadlessException {
        super(currProject.getMainFrame(), true);
        GuiControl gui = GuiControl.getInstance() ; 
        if(!gui.IsProjectOpen()) return ; 
        try {
            curr = currProject;
            jbInit();
            desktop = _desktop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComboBox();
        addListeners();
        int fX = currProject.getMainFrame().getX();
        int fY = currProject.getMainFrame().getY();
        int pWidth = currProject.getMainFrame().getWidth();
        int pHeight = currProject.getMainFrame().getHeight();
        setLocation(fX + Math.abs(pWidth / 3 - getWidth() / 3), fY
                + Math.abs(pHeight / 3 - getHeight() / 3));
        this.setSize(450, 250);
    }

    public ImportDialog() {
    	GuiControl gui = GuiControl.getInstance() ; 
    	if (!gui.IsProjectOpen()) return ; 
    	
        try {
            jbInit();
            this.setBounds(200, 300, 300, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        KeyListener kListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelButton.doClick();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    importButton.doClick();
                    return;
                }

            }
        };
        this.addKeyListener(kListener);

    }

    private void jbInit() throws Exception {
        border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(
                134, 134, 134));
        titledBorder1 = new TitledBorder(BorderFactory
                .createEtchedBorder(Color.white, new Color(134, 134, 134)),
                "Import Model");
        this.setTitle("Import Model");
        this.getContentPane().setLayout(null);
        importPanel.setBorder(titledBorder1);
        importPanel.setMinimumSize(new Dimension(22, 37));
        importPanel.setInputVerifier(null);
        importPanel.setBounds(new Rectangle(7, 8, 409, 112));
        importPanel.setLayout(null);
        fileNameTextField.setText("");
        fileNameTextField.setBounds(new Rectangle(15, 33, 253, 20));
        chooseFileButton.setBounds(new Rectangle(289, 33, 101, 21));
        chooseFileButton.setText("Browse...");
        reuseTypeLabel.setText("Reuse Type:");
        reuseTypeLabel.setBounds(new Rectangle(16, 67, 142, 19));

        String message = "";
        if (curr == null ) return ; 
        if (curr.getFileName() == null || curr.getFileName().equals("")) {
            message = "You will be prompted to save the project before importing";
        } else {
            message = "Your project will be automatically saved before importing";
        }
        
        saveWarningLabel.setText(message);
        saveWarningLabel.setBounds(new Rectangle(16, 125, 440, 20));

        reuseTypeComboBox.setBounds(new Rectangle(223, 70, 169, 20));

        cancelButton.setBounds(new Rectangle(318, 155, 95, 25));
        cancelButton.setText("Cancel");
        importButton.setText("Import");
        importButton.setBounds(new Rectangle(215, 155, 95, 25));
        importPanel.add(fileNameTextField, null);
        importPanel.add(chooseFileButton, null);
        importPanel.add(reuseTypeLabel, null);
        importPanel.add(reuseTypeComboBox, null);
        this.getContentPane().add(saveWarningLabel, null);
        this.getContentPane().add(cancelButton, null);
        this.getContentPane().add(importButton, null);
        this.getContentPane().add(importPanel, null);
        this.setSize(60, 25);
    }

    private void addListeners() {
        //open "Open File" dialog window
        chooseFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fc.resetChoosableFileFilters();
                String[] exts = { "opx", "opz" };
                CustomFileFilter myFilter = new CustomFileFilter(exts,
                        "Opcat2 Files");

                fc.addChoosableFileFilter(myFilter);
                String ld = FileControl.getInstance().getLastDirectory();
                if (!ld.equals("")) {
                    fc.setCurrentDirectory(new File(ld));
                }
                fc.showOpenDialog(null);

                if (fc.getSelectedFile() != null) {
                    fileNameTextField.setText(fc.getSelectedFile().getPath());
                    String newDirectory = fc.getSelectedFile().getParent();
                    FileControl.getInstance().setLastDirectory(newDirectory);
                }
            }
        });

        //import close window and import selected system
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importPerformed();
            }
        });

        //cancel - close Dialog without doint anything
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CancelPerformed();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing() {
                CancelPerformed();
            }
        });
    }

    private void initComboBox() {
        //reuseTypeComboBox.addItem("Select Reuse Type");
        reuseTypeComboBox.addItem("Copy Elements");
        //reuseTypeComboBox.addItem("Copy and Lock");
        /*
         * TODO: commanted untill we fix the bug in the locked import type
         * raanan
         */
        //Commented by Eran
        //reuseTypeComboBox.addItem("Open Reuse");

    }

    private void CancelPerformed() {
        isOperated = false;
        this.dispose();
    }

    private void importPerformed() {
        importFileName = fileNameTextField.getText();
        typeOfReuse = reuseTypeComboBox.getSelectedIndex();
        this.dispose();
    }

    /**
     * @return Returns the type Of Reuse. See ReuseCommand static elements for
     *         possible types of reuse.
     */
    public int getTypeOfReuse() {
        return typeOfReuse;
    }

    /**
     * @return Returns the import File Name.
     */
    public String getImportFileName() {
        return importFileName;
    }

    /**
     * @return Returns <code>true</code> if the user choose to import.
     *         <code>false</code> if the user choose to cancel the import.
     */
    public boolean isOperated() {
        return isOperated;
    }

}