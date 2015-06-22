package gui.projectStructure;

import gui.controls.FileControl;
import gui.opdGraphics.OpdOr;
import gui.opdProject.OpdProject;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class OrInstance {
    private OpdOr conn;

    private Hashtable instances;

    private Container cn;

    boolean isOr;

    boolean source;

    public OrInstance(Hashtable ins, boolean isSource, OpdProject project) {
        this.instances = ins;
        this.isOr = false;
        this.source = isSource;
        this.conn = new OpdOr(this, project);
        this.conn.addMouseListener(this.conn);
    }

    public boolean isSource() {
        return this.source;
    }

    public void setOr(boolean isOr) {
        this.isOr = isOr;
    }

    public boolean isOr() {
        return this.isOr;
    }

    public void add(LinkInstance instance) {
        this.instances.put(instance.getKey(), instance);
    }

    public void remove(LinkInstance instance) {
        this.instances.remove(instance.getKey());
    }

    public void add2Container(Container cn) {

        // if(FileControl.getInstance().isGUIOFF()) return ;
        cn.setIgnoreRepaint(true);
        cn.setVisible(false);

        cn.add(this.conn, JLayeredPane.PALETTE_LAYER);
        this.cn = cn;

        cn.setIgnoreRepaint(false);
        cn.setVisible(true);
    }

    public void removeFromContainer() {
        if (this.source) {
            for (Enumeration e = this.instances.elements(); e.hasMoreElements();) {
                ((LinkInstance) e.nextElement()).setSourceOr(null);
            }
        } else {
            for (Enumeration e = this.instances.elements(); e.hasMoreElements();) {
                ((LinkInstance) e.nextElement()).setDestOr(null);
            }
        }
        this.cn.remove(this.conn);
        this.cn.repaint();
    }

    public void update() {
        if (FileControl.getInstance().isGUIOFF()) return;

        if (this.instances.size() == 1) {
            this.removeFromContainer();
        } else {
            this.conn.update();
        }
    }

    public int getSize() {
        return this.instances.size();
    }

    public Enumeration getInstances() {
        return this.instances.elements();
    }

    public String getTypeString() {
        return "OR";
    }

}
