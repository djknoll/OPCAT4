package gui.opdGraphics.dialogs;

/*
 * @(#)DirectionPanel.java	1.1 99/11/08
 *
 * Copyright (c) 1997-1999 by Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import gui.images.directionButtons.DirectionButtonsImages;

import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;


/**
 * @version 1.1 11/08/99
 * @author Jeff Dinkins
 * @author Chester Rose
 * @author Brian Beck
 */

public class DirectionPanel extends JPanel {

    private ButtonGroup group;

    public DirectionPanel(boolean enable, String selection, ActionListener l) {
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setAlignmentY(TOP_ALIGNMENT);
	setAlignmentX(LEFT_ALIGNMENT);

	Box firstThree = Box.createHorizontalBox();
	Box secondThree = Box.createHorizontalBox();
	Box thirdThree = Box.createHorizontalBox();

	if(!enable) {
	    selection = "None";
	}

    group = new ButtonGroup();
	DirectionButton b;
	b = (DirectionButton) firstThree.add(new DirectionButton(  tl_dot, tldn_dot, "NW", "Sets the orientation to the North-West", l, group, selection.equals("NW")));
	b.setEnabled(enable);
	b = (DirectionButton) firstThree.add(new DirectionButton(  tm_dot, tmdn_dot, "N",  "Sets the orientation to the North", l, group, selection.equals("N")));
	b.setEnabled(enable);
	b = (DirectionButton) firstThree.add(new DirectionButton(  tr_dot, trdn_dot, "NE", "Sets the orientation to the North-East", l, group, selection.equals("NE")));
	b.setEnabled(enable);
	b = (DirectionButton) secondThree.add(new DirectionButton( ml_dot, mldn_dot, "W", "Sets the orientation to the West", l, group, selection.equals("W")));
	b.setEnabled(enable);
	b = (DirectionButton) secondThree.add(new DirectionButton( c_dot,  cdn_dot,  "C", "Sets the orientation to the Center", l, group, selection.equals("C")));
	b.setEnabled(enable);
	b = (DirectionButton) secondThree.add(new DirectionButton( mr_dot, mrdn_dot, "E", "Sets the orientation to the East", l, group, selection.equals("E")));
	b.setEnabled(enable);
	b = (DirectionButton) thirdThree.add(new DirectionButton(  bl_dot, bldn_dot, "SW", "Sets the orientation to the South-West", l, group, selection.equals("SW")));
	b.setEnabled(enable);
	b = (DirectionButton) thirdThree.add(new DirectionButton(  bm_dot, bmdn_dot, "S", "Sets the orientation to the South", l, group, selection.equals("S")));
	b.setEnabled(enable);
	b = (DirectionButton) thirdThree.add(new DirectionButton(  br_dot, brdn_dot, "SE", "Sets the orientation to the South-East", l, group, selection.equals("SE")));
	b.setEnabled(enable);

	add(firstThree);
	add(secondThree);
	add(thirdThree);
    }

    public String getSelection() {
        return group.getSelection().getActionCommand();
    }

    public void setSelection( String selection  ) {
        Enumeration e = group.getElements();
        while( e.hasMoreElements() ) {
            JRadioButton b = (JRadioButton)e.nextElement();
            if( b.getActionCommand().equals(selection) ) {
               b.setSelected(true);
            }
        }
    }

    // Chester's way cool layout buttons
    public ImageIcon bl_dot   = loadImageIcon(DirectionButtonsImages.BL,"bottom left layout button");
    public ImageIcon bldn_dot = loadImageIcon(DirectionButtonsImages.BLDN,"selected bottom left layout button");
    public ImageIcon bm_dot   = loadImageIcon(DirectionButtonsImages.BM,"bottom middle layout button");
    public ImageIcon bmdn_dot = loadImageIcon(DirectionButtonsImages.BMDN,"selected bottom middle layout button");
    public ImageIcon br_dot   = loadImageIcon(DirectionButtonsImages.BR,"bottom right layout button");
    public ImageIcon brdn_dot = loadImageIcon(DirectionButtonsImages.BRDN,"selected bottom right layout button");
    public ImageIcon c_dot    = loadImageIcon(DirectionButtonsImages.C,"center layout button");
    public ImageIcon cdn_dot  = loadImageIcon(DirectionButtonsImages.CDN,"selected center layout button");
    public ImageIcon ml_dot   = loadImageIcon(DirectionButtonsImages.ML,"middle left layout button");
    public ImageIcon mldn_dot = loadImageIcon(DirectionButtonsImages.MLDN,"selected middle left layout button");
    public ImageIcon mr_dot   = loadImageIcon(DirectionButtonsImages.MR,"middle right layout button");
    public ImageIcon mrdn_dot = loadImageIcon(DirectionButtonsImages.MRDN,"selected middle right layout button");
    public ImageIcon tl_dot   = loadImageIcon(DirectionButtonsImages.TL,"top left layout button");
    public ImageIcon tldn_dot = loadImageIcon(DirectionButtonsImages.TLDN,"selected top left layout button");
    public ImageIcon tm_dot   = loadImageIcon(DirectionButtonsImages.TM,"top middle layout button");
    public ImageIcon tmdn_dot = loadImageIcon(DirectionButtonsImages.TMDN,"selected top middle layout button");
    public ImageIcon tr_dot   = loadImageIcon(DirectionButtonsImages.TR,"top right layout button");
    public ImageIcon trdn_dot = loadImageIcon(DirectionButtonsImages.TRDN,"selected top right layout button");

    public ImageIcon loadImageIcon(ImageIcon icon, String description) {

        icon.setDescription(description);
        return icon;
	    //return new ImageIcon(getClass().getResource(path), description);
    }


    public class DirectionButton extends JRadioButton {

        /**
         * A layout direction button
         */
        public DirectionButton(Icon icon, Icon downIcon, String direction,
                               String description, ActionListener l,
                               ButtonGroup group, boolean selected)
        {
            super();
            this.addActionListener(l);
            setFocusPainted(false);
            setHorizontalTextPosition(CENTER);
            group.add(this);
            setIcon(icon);
            setSelectedIcon(downIcon);
            setActionCommand(direction);
            getAccessibleContext().setAccessibleName(direction);
            getAccessibleContext().setAccessibleDescription(description);
            setSelected(selected);
        }

        public boolean isFocusTraversable() {
            return false;
        }

        public void setBorder(Border b) {
        }
    }
}
