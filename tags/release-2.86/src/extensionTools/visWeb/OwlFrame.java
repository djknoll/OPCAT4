package extensionTools.visWeb;

import gui.opdProject.OpdProject;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FileDialog;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author  VisWeb Team
 * @version 1.0
 */
public class OwlFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	JTextField URLTextField = new JTextField();
    ButtonGroup DetailedButtonGroup = new ButtonGroup();
    JRadioButton BasicRadioButton = new JRadioButton();
    JRadioButton DetailedRadioButton = new JRadioButton();
    JLabel DetailModeLabel = new JLabel();
    ButtonGroup OwlRdfbuttonGroup = new ButtonGroup();
    JRadioButton OwlRadioButton = new JRadioButton();
    JRadioButton RdfRadioButton = new JRadioButton();
    JLabel FileTypeLabel = new JLabel();
    JLabel URLLabel = new JLabel();
    JButton OkButton = new JButton();
    JButton CancelButton = new JButton();
    FileDialog Dialog;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    OpdProject Project ;

    public OwlFrame(OpdProject currentProject) {
        try {
            this.jbInit(currentProject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit(OpdProject currentProject) throws Exception {
    	this.Project = currentProject ;

    	this.setLocation(200,200);
        this.getContentPane().setLayout(this.gridBagLayout1);
        this.URLTextField.setText("HTTP:/somewhere/");
        this.BasicRadioButton.setText("Basic");
        this.BasicRadioButton.setSelected(true);
        this.DetailedRadioButton.setText("Detailed");
        this.DetailModeLabel.setText("Detail Mode :");
        this.OwlRadioButton.setText("Owl");
        this.RdfRadioButton.setText("Rdf");
        this.RdfRadioButton.setSelected(true);
        this.FileTypeLabel.setText("File Type :");
        this.URLLabel.setText("URL :");
        this.OkButton.setText("ok");
        this.CancelButton.setText("cancel");
        this.OkButton.addMouseListener(new MouseAdapter() {
           public void mousePressed(MouseEvent e) {
              OwlFrame.this.OkButton_mousePressed(e);
           }
        });
        this.CancelButton.addMouseListener(new MouseAdapter() {
           public void mousePressed(MouseEvent e) {
              OwlFrame.this.CancelButton_mousePressed(e);
           }
        });



        this.DetailedButtonGroup.add(this.BasicRadioButton);
        this.DetailedButtonGroup.add(this.DetailedRadioButton);
        this.OwlRdfbuttonGroup.add(this.RdfRadioButton);
        this.OwlRdfbuttonGroup.add(this.OwlRadioButton);
        this.getContentPane().add(this.CancelButton,
                                  new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 70, 0, 13), 15, 0));
        this.getContentPane().add(this.OkButton,
                                  new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(15, 70, 0, 13), 37, 0));
        this.getContentPane().add(this.URLLabel,
                                  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(61, 13, 0, 0), 54, 0));
        this.getContentPane().add(this.URLTextField,
                                  new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(15, 13, 0, 0), 130, 0));
        this.getContentPane().add(this.FileTypeLabel,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 13, 0, 31), 0, 0));
        this.getContentPane().add(this.OwlRadioButton,
                                  new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 22, 0, 27), 0, 0));
        this.getContentPane().add(this.RdfRadioButton,
                                  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 7, 0, 10), 0, 0));
        this.getContentPane().add(this.DetailModeLabel,
                                  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(13, 13, 23, 7), 12, 9));
        this.getContentPane().add(this.DetailedRadioButton,
                                  new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 22, 23, 9), 0, 0));
        this.getContentPane().add(this.BasicRadioButton,
                                  new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 7, 23, 0), 0, 0));

        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }


    public void CancelButton_mousePressed(MouseEvent e) {
        this.setVisible(false);
    }

    public void OkButton_mousePressed(MouseEvent e) {


    	boolean ToOwl = this.OwlRadioButton.isSelected() ;
    	boolean Detailed = this.DetailedRadioButton.isSelected();
    	String URL = this.URLTextField.getText();
    	Owl MakeOwl = new Owl(this.Project, Detailed,URL,ToOwl);
    	MakeOwl.CreateModel();

        this.setVisible(false);
        this.createSaveFileDialog(MakeOwl);
   
    }

    private void  createSaveFileDialog(Owl MakeOwl)
    {
        this.Dialog = new FileDialog(this,"Save file",FileDialog.SAVE);
        this.Dialog.setVisible(true);
     
        if (this.Dialog.getDirectory() != null)
        {
        	String fileName = "VisWebOutput"; //default file name
        	if (this.Dialog.getFile()!=null)
        	{
        		fileName=this.Dialog.getFile();
        		MakeOwl.saveFileInDirectory(this.Dialog.getDirectory(),fileName);
        	} else {
				MakeOwl.saveFileInDirectory(this.Dialog.getDirectory(),fileName);
			}
        }
    }
}

