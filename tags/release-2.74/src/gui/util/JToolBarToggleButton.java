package gui.util;


import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
// Small toolbar button without text, only icon
public class JToolBarToggleButton extends JToggleButton implements MouseListener{
    protected static final BevelBorder m_raised = new BevelBorder(BevelBorder.RAISED);
    protected static final BevelBorder m_lowered = new BevelBorder(BevelBorder.LOWERED);
    protected static final EtchedBorder m_inactive = new EtchedBorder();
    protected static final Insets myInsets = new Insets(2,2,2,2);

    protected int state;
    protected String pressedTip;
    protected String notPressedTip;


    public JToolBarToggleButton(Action act, String pressedTip, String notPressedTip){
        super((Icon)act.getValue(Action.SMALL_ICON));
        setSelected(false);
        addActionListener(act);
        addMouseListener(this);
        setRequestFocusEnabled(false);
        this.pressedTip = pressedTip;
        this.notPressedTip = notPressedTip;
        this.setContentAreaFilled(false);
    }


    public float getAlignmentY() { return 0.5f; }

    public void mousePressed(MouseEvent e)
            {}

    public void setSelected(boolean b)
    {
        super.setSelected(b);
        if(b)
        {
            setToolTipText(pressedTip);
            setBorder(m_lowered);
        }
        else
        {
            setToolTipText(notPressedTip);
            setBorder(m_inactive);
        }
    }

    public void mouseReleased(MouseEvent e){
        setSelected(isSelected());
    }

    public void mouseClicked(MouseEvent e)
            {}

    public void mouseEntered(MouseEvent e){
        if (isEnabled())
        {
            if(!isSelected())
            {
                setBorder(m_raised);
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        if(!isSelected())
        {
            setBorder(m_inactive);
        }
    }
}

