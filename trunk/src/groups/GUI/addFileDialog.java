package groups.GUI;

import groups.OpcatGroup;
import gui.util.OpcatFile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class addFileDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JButton removeButton;
    private JTabbedPane tabbedPane1;
    private JList currentFiles;
    private JList connectedFiles;
    private JButton createGroupButton;
    private JButton removeButton1;
    private JPanel exportPanel;
    private JTextField textField1;
    private JButton exportButton;
    private addFileDialogImpl impl = new addFileDialogImpl();

    private ArrayList<OpcatGroup> groups = new ArrayList<OpcatGroup>();

    public addFileDialog(OpcatFile file) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onGroupChange();
                enableDisableButtons();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOKAdd();
                enableDisableButtons();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOKRemove();
                enableDisableButtons();
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

        impl.setFile(file, currentFiles);
        impl.fillCombo(comboBox1);

        tabbedPane1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                //impl.manageExport(comboBox1.getSelectedIndex(), exportPanal);
                enableDisableButtons();
            }
        });
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOKAdd();
                enableDisableButtons();
            }
        });
        enableDisableButtons();
        removeButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impl.removeGroup(comboBox1.getSelectedIndex(), comboBox1);
                enableDisableButtons();
            }
        });
        comboBox1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                enableDisableButtons();
            }

            @Override
            public void focusGained(FocusEvent e) {
                enableDisableButtons();
            }
        });


        ArrayList<String> cols = new ArrayList<String>();
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impl.exportButton(comboBox1.getSelectedIndex(), exportPanel);
            }
        });
        currentFiles.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                enableDisableButtons();
            }
        });
        connectedFiles.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                enableDisableButtons();
            }
        });
    }

    private void enableDisableButtons() {

        if (comboBox1.getSelectedIndex() == -1) {
            exportButton.setEnabled(false);
            buttonOK.setEnabled(false);
            removeButton.setEnabled(false);
            removeButton1.setEnabled(false);
            createGroupButton.setEnabled(true);
        } else if (comboBox1.getSelectedIndex() > 0) {
            createGroupButton.setEnabled(false);
            if (impl.canRemoveGroup(comboBox1.getSelectedIndex())) {
                removeButton1.setEnabled(true);
                exportButton.setEnabled(false);
            } else {
                removeButton1.setEnabled(false);
                exportButton.setEnabled(true);
            }
            if (tabbedPane1.getSelectedIndex() == 0) {
                if (currentFiles.getSelectedIndices().length > 0)
                    buttonOK.setEnabled(true);
                removeButton.setEnabled(false);

            } else if (tabbedPane1.getSelectedIndex() == 1) {
                buttonOK.setEnabled(false);
                if (!impl.canRemoveGroup(comboBox1.getSelectedIndex()) && (connectedFiles.getSelectedIndices().length > 0))
                    removeButton.setEnabled(true);
            } else {
                buttonOK.setEnabled(false);
                removeButton.setEnabled(false);
            }
        } else {
            buttonOK.setEnabled(false);
            removeButton.setEnabled(false);
            removeButton1.setEnabled(false);
            createGroupButton.setEnabled(false);
            exportButton.setEnabled(false);
        }
    }

    private void onOKAdd() {
// add your code here
        impl.onOKAdd(currentFiles, comboBox1.getSelectedIndex(), comboBox1.getSelectedItem().toString(), textField1.getText(), comboBox1);
        impl.fillConnectedList(connectedFiles, comboBox1.getSelectedIndex());
        //dispose();
    }

    private void onOKRemove() {
// add your code here
        impl.onOKRemove(connectedFiles, comboBox1.getSelectedIndex(), comboBox1.getSelectedItem().toString(), textField1.getText(), comboBox1);
        impl.fillConnectedList(connectedFiles, comboBox1.getSelectedIndex());
        //dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        addFileDialog dialog = new addFileDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onGroupChange() {
        impl.manageGroup(comboBox1.getSelectedIndex(), comboBox1, textField1, connectedFiles, exportPanel);
        enableDisableButtons();
    }

    private void createUIComponents() {
        exportPanel = new ExportPanel();
    }
}
