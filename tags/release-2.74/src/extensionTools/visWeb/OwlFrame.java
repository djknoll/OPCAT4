package extensionTools.visWeb;

import exportedAPI.opcatAPI.ISystem;
import gui.opdProject.OpdProject;

import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JButton;
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
            jbInit(currentProject);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit(OpdProject currentProject) throws Exception {
    	Project = currentProject ;

    	this.setLocation(200,200);
        getContentPane().setLayout(gridBagLayout1);
        URLTextField.setText("HTTP:/somewhere/");
        BasicRadioButton.setText("Basic");
        BasicRadioButton.setSelected(true);
        DetailedRadioButton.setText("Detailed");
        DetailModeLabel.setText("Detail Mode :");
        OwlRadioButton.setText("Owl");
        RdfRadioButton.setText("Rdf");
        RdfRadioButton.setSelected(true);
        FileTypeLabel.setText("File Type :");
        URLLabel.setText("URL :");
        OkButton.setText("ok");
        CancelButton.setText("cancel");
        OkButton.addMouseListener(new MouseAdapter() {
           public void mousePressed(MouseEvent e) {
              OkButton_mousePressed(e);
           }
        });
        CancelButton.addMouseListener(new MouseAdapter() {
           public void mousePressed(MouseEvent e) {
              CancelButton_mousePressed(e);
           }
        });



        DetailedButtonGroup.add(BasicRadioButton);
        DetailedButtonGroup.add(DetailedRadioButton);
        OwlRdfbuttonGroup.add(RdfRadioButton);
        OwlRdfbuttonGroup.add(OwlRadioButton);
        this.getContentPane().add(CancelButton,
                                  new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 70, 0, 13), 15, 0));
        this.getContentPane().add(OkButton,
                                  new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(15, 70, 0, 13), 37, 0));
        this.getContentPane().add(URLLabel,
                                  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(61, 13, 0, 0), 54, 0));
        this.getContentPane().add(URLTextField,
                                  new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(15, 13, 0, 0), 130, 0));
        this.getContentPane().add(FileTypeLabel,
                                  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 13, 0, 31), 0, 0));
        this.getContentPane().add(OwlRadioButton,
                                  new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 22, 0, 27), 0, 0));
        this.getContentPane().add(RdfRadioButton,
                                  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 7, 0, 10), 0, 0));
        this.getContentPane().add(DetailModeLabel,
                                  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(13, 13, 23, 7), 12, 9));
        this.getContentPane().add(DetailedRadioButton,
                                  new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 22, 23, 9), 0, 0));
        this.getContentPane().add(BasicRadioButton,
                                  new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(13, 7, 23, 0), 0, 0));

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
    	Owl MakeOwl = new Owl(Project, Detailed,URL,ToOwl);
    	MakeOwl.CreateModel();

        this.setVisible(false);
        createSaveFileDialog(MakeOwl);
   
    }

    private void  createSaveFileDialog(Owl MakeOwl)
    {
        Dialog = new FileDialog(this,"Save file",FileDialog.SAVE);
        Dialog.show();
     
        if (Dialog.getDirectory() != null)
        {
        	String fileName = "VisWebOutput"; //default file name
        	if (Dialog.getFile()!=null)
        	{
        		fileName=Dialog.getFile();
        		MakeOwl.saveFileInDirectory(Dialog.getDirectory(),fileName);
        	}
        	else
        		
        		MakeOwl.saveFileInDirectory(Dialog.getDirectory(),fileName);
        }
    }
}

