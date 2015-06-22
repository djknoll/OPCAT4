package versions.GUI;

import gui.Opcat2;
import gui.util.OpcatFileFilter;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class OPXDiffDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JComboBox comboBox1;
    private JButton button1;
    private JComboBox comboBox2;
    private JButton button2;
    private JPanel targetPanel;
    private JPanel sourcePanel;
    private JCheckBox useNameForDiffCheckBox;
    private JCheckBox addTargetAsInCheckBox;
    private JCheckBox addTargetAsInCheckBox1;
    private File source;
    private File target;
    private boolean run = false;

    ArrayList<SVNLogEntry> sourceLogs;
    ArrayList<SVNLogEntry> targetLogs;

    public boolean isUseName() {
        return useNameForDiffCheckBox.isSelected();
    }

    public boolean isAddTargetAsTemplate() {
        return addTargetAsInCheckBox1.isSelected();
    }

    public boolean isAddSourceAsTemplate() {
        return addTargetAsInCheckBox.isSelected();
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public long getSourceRevision() {
        if (comboBox1.getSelectedIndex() == 0) {
            return -1;
        } else {
            return sourceLogs.get(comboBox1.getSelectedIndex() - 1).getRevision();
        }
    }

    public File getSource() {
        return source;
    }

    public File getTarget() {
        return target;
    }

    public long getTargetRevision() {
        if (comboBox2.getSelectedIndex() == 0) {
            return -1;
        } else {
            return targetLogs.get(comboBox2.getSelectedIndex() - 1).getRevision();
        }
    }


    public void setTarget(File target) {
        this.target = target;
        targetLogs = OpcatMCManager.getInstance().getVersions(target);
        if (targetLogs == null) {
            comboBox2.setEnabled(false);
        } else {
            comboBox2.setEnabled(true);
            fillCombo(targetLogs, comboBox2);
        }
        ((TitledBorder) targetPanel.getBorder()).setTitle("Compare To : " + target.getPath());

        SVNURL url = OpcatMCManager.getInstance(true).getFileURL(
                new File(source.getPath()));
        addTargetAsInCheckBox.setEnabled((Opcat2.getCurrentProject() != null) && (url != null));

        url = OpcatMCManager.getInstance(true).getFileURL(
                new File(target.getPath()));
        addTargetAsInCheckBox1.setEnabled((Opcat2.getCurrentProject() != null) && (url != null));

        targetPanel.repaint();
    }

    public void setSource(File source) {
        this.source = source;
        sourceLogs = OpcatMCManager.getInstance().getVersions(source);
        if (sourceLogs == null) {
            comboBox1.setEnabled(false);
        } else {
            comboBox1.setEnabled(true);
            fillCombo(sourceLogs, comboBox1);
        }
        ((TitledBorder) sourcePanel.getBorder()).setTitle("File : " + source.getPath());
        sourcePanel.repaint();
    }

    private void fillCombo(Collection<SVNLogEntry> logs, JComboBox combo) {
        combo.removeAllItems();
        combo.addItem("Local Copy");
        if (logs != null) {
            for (SVNLogEntry log : logs) {
                combo.addItem("Revision - " + log.getRevision());
            }
        }

    }

    public OPXDiffDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (sourceLogs != null && comboBox1.getSelectedIndex() > 0) {
                    SVNLogEntry entry = (SVNLogEntry) sourceLogs.toArray()[comboBox1.getSelectedIndex() - 1];
                    textPane2.setText("Author : " + entry.getAuthor() + " \nDate : " + entry.getDate() + "\nMessage : " + entry.getMessage());
                    textPane2.setEnabled(true);
                } else {
                    textPane2.setEnabled(false);
                    textPane2.setText("");
                }
                //pack();              
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (targetLogs != null && comboBox2.getSelectedIndex() > 0) {
                    SVNLogEntry entry = (SVNLogEntry) targetLogs.toArray()[comboBox2.getSelectedIndex() - 1];
                    textPane1.setText("Author : " + entry.getAuthor() + " \nDate : " + entry.getDate() + "\nMessage : " + entry.getMessage());
                    textPane1.setEnabled(true);
                } else {
                    textPane1.setEnabled(false);
                    textPane1.setText("");
                }
                //pack();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(target.getParent());
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new OpcatFileFilter(new String[]{"opx"}, "OPX Filter", true, false));
                int returnVal = chooser.showOpenDialog(Opcat2.getFrame());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    setTarget(chooser.getSelectedFile());
                }
            }
        });

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(source.getParent());
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new OpcatFileFilter(new String[]{"opx"}, "OPX Filter", true, false));
                int returnVal = chooser.showOpenDialog(Opcat2.getFrame());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    setSource(chooser.getSelectedFile());
                }
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                run = true;
                setVisible(false);
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                run = false;
                setVisible(false);
            }
        });


    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {

        OpcatMCManager.doSilentLogin("raanan", "raanan");
        OpcatMCManager.getInstance().forceConnection();

        OPXDiffDialog dialog = new OPXDiffDialog();
        dialog.setSource(new File("C:\\Program Files (x86)\\Opcat\\Working Copy\\DSP13\\Programs\\QDSPSRCRLE\\DSPA0002.opx"));
        dialog.setTarget(new File("C:\\Program Files (x86)\\Opcat\\Working Copy\\DSP13\\Programs\\QDSPSRCRLE\\DSPA0002.opx"));


        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
