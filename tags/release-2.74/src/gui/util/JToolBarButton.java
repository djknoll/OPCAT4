package gui.util;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
// Small toolbar button without text, only icon
public class JToolBarButton extends JButton implements MouseListener{
    protected static final BevelBorder m_raised = new BevelBorder(BevelBorder.RAISED);
    protected static final BevelBorder m_lowered = new BevelBorder(BevelBorder.LOWERED);
    protected static final EtchedBorder m_inactive = new EtchedBorder();
    protected static final Insets myInsets = new Insets(2,2,2,2);

    public JToolBarButton(Action act, String tip){
        super((Icon)act.getValue(Action.SMALL_ICON));

        setInitialBorder();
        setMargin(myInsets);
        setToolTipText(tip);
        addActionListener(act);
//		setRequestFocusEnabled(false);
        addMouseListener(this);

    }

    public void setInitialBorder()
    {
        setBorder(m_inactive);
    }
    public float getAlignmentY() { return 0.5f; }

    public void mousePressed(MouseEvent e){
        if (isEnabled())
        {
            setBorder(m_lowered);
        }
    }

    public void mouseReleased(MouseEvent e){
        setBorder(m_inactive);
    }

    public void mouseClicked(MouseEvent e){}

    public void mouseEntered(MouseEvent e){
        if (isEnabled())
        {
            setBorder(m_raised);
        }
    }

    public void mouseExited(MouseEvent e) {
        setBorder(m_inactive);
    }
}

