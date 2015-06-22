package groups.GUI;

import groups.GroupsManager;
import groups.OpcatGroup;
import gui.util.OpcatFile;
import modelControl.OpcatMCManager;
import util.FileUtils;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: Dec 27, 2010
 * Time: 11:38:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class addFileDialogImpl {
    ArrayList<OpcatGroup> groups = new ArrayList<OpcatGroup>();

    public void setFile(OpcatFile root, JList filesList) {
        ArrayList<File> files = FileUtils.getFileListFlat(root, "opx");

        filesList.removeAll();
        DefaultListModel listModel = new DefaultListModel();
        for (File file : files) {
            listModel.addElement(OpcatMCManager.getInstance().getFileURL(file).getPath());
        }
        filesList.setModel(listModel);
        filesList.repaint();

    }

    private final String defComboAddText = "Enter new group name";

    public void fillCombo(JComboBox combo) {
        groups = GroupsManager.getAllGroups();
        combo.removeAllItems();
        combo.addItem(defComboAddText);
        for (OpcatGroup group : groups) {
            combo.addItem(group.getName());
        }
        combo.setSelectedIndex(0);
        combo.repaint();
    }

    public void fillText(JTextField text, int guiIndex) {
        if ((GUIToListIndex(guiIndex) >= 0) && (groups.get(GUIToListIndex(guiIndex)) != null)) {
            text.setText(groups.get(GUIToListIndex(guiIndex)).getDescription());
        } else {
            text.setText("Enter Description");
            text.selectAll();
        }
    }

    public void manageGroup(int guiIndex, JComboBox comboBox, JTextField textArea, JList list, JPanel exportPanel) {

        if (guiIndex > 0) {
            comboBox.setEditable(false);
            textArea.setEditable(false);
            manageExport(guiIndex, exportPanel);
            fillConnectedList(list, guiIndex);
        } else {
            comboBox.setEditable(true);
            textArea.setEditable(true);
            exportPanel = new ExportPanel();
        }
        fillText(textArea, guiIndex);
    }

    public void manageExport(int guiIndex, JPanel exportPanel) {
        //exportPanel = new ExportPanel();

        ((ExportPanel) exportPanel).fillData(GroupsManager.getAllExports(groups.get(GUIToListIndex(guiIndex)), true), groups.get(GUIToListIndex(guiIndex)));

    }

    public void exportButton(int guiIndex, JPanel exportPanel) {
        GroupsManager.exportGroup(groups.get(GUIToListIndex(guiIndex)));
        manageExport(guiIndex, exportPanel);
    }


    public boolean canCreateGroup(String newName) {
        return !(newName.equalsIgnoreCase(defComboAddText));
    }

    public boolean canRemoveGroup(int guiIndex) {
        return GroupsManager.getConnectedFilesByID(groups.get(GUIToListIndex(guiIndex)).getId()).size() <= 0;
    }

    public void removeGroup(int guiIndex, JComboBox combo) {
        GroupsManager.removeGroup(groups.get(GUIToListIndex(guiIndex)));
        fillCombo(combo);
    }

    public void onOKAdd(JList filesList, int guiIndex, String name, String description, JComboBox combo) {
        if (name.equalsIgnoreCase(defComboAddText)) {
            return;
        }
        OpcatGroup group;
        if (guiIndex <= 0) {
            //add the group
            group = GroupsManager.createGroup(name, description);
        } else {
            group = GroupsManager.getGroup(groups.get(GUIToListIndex(guiIndex)).getId());
        }

        //connect to group

        ArrayList<String> files = new ArrayList<String>();
        for (int i = 0; i < filesList.getSelectedIndices().length; i++) {
            files.add((String) filesList.getModel().getElementAt(filesList.getSelectedIndices()[i]));
        }
        GroupsManager.connectToGroup(group, files);
        if (guiIndex <= 0) fillCombo(combo);
    }

    public void onOKRemove(JList removeList, int guiIndex, String name, String description, JComboBox combo) {

        OpcatGroup group;
        if (guiIndex <= 0) {
            return;
        } else {
            group = GroupsManager.getGroup(groups.get(GUIToListIndex(guiIndex)).getId());
        }

        ArrayList<String> files = new ArrayList<String>();
        for (int i = 0; i < removeList.getSelectedIndices().length; i++) {
            files.add((String) removeList.getModel().getElementAt(removeList.getSelectedIndices()[i]));
        }
        GroupsManager.removeFromGroup(group, files);
    }

    private int GUIToListIndex(int guiIndex) {
        return (guiIndex - 1);
    }

    public void fillConnectedList(JList list, int guiIndex) {
        ArrayList<String> connected = GroupsManager.getConnectedFilesByID(groups.get(GUIToListIndex(guiIndex)).getId());
        list.removeAll();
        DefaultListModel listModel = new DefaultListModel();
        for (String file : connected) {
            listModel.addElement(file);
        }
        list.setModel(listModel);
        list.repaint();
    }

}
