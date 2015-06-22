package gui.opdGraphics.dialogs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


class TimeSpecifier extends JPanel implements SwingConstants{

		private JLabel[] labels = {null, null, null, null, null, null, null};
		private String[] strings = {"msec", "sec", "min", "hours", "days", "months", "years"};
		private JTextField[] fields = {null, null, null, null, null, null, null};
		private JCheckBox infinity;
		private JPanel cellsPanel, infinityPanel;

		public TimeSpecifier(int direction, int position, int hGap, int vGap){
			infinity = new JCheckBox("Infinity");
			infinity.addItemListener(new InfinityListener());
			for (int i = 0; i < 7; i++){
				labels[i] = new JLabel(strings[i]);
				fields[i] = new JTextField("0",4);
			}
			GridLayout layout;
			cellsPanel = new JPanel();
			infinityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			infinityPanel.add(infinity);

			if (direction == HORIZONTAL && (position == TOP || position == BOTTOM))
			{
				layout = new GridLayout(2, 7);
				layout.setHgap(hGap);
				layout.setVgap(vGap);
				cellsPanel.setLayout(layout);

				if (position == TOP){
					for(int i = 0; i < 7; i++)
						cellsPanel.add(labels[i]);
					for(int i = 0; i < 7; i++)
						cellsPanel.add(fields[i]);
				}
				if (position == BOTTOM){
					for(int i = 0; i < 7; i++)
						cellsPanel.add(fields[i]);
					for(int i = 0; i < 7; i++)
						cellsPanel.add(labels[i]);
				}
			}

			if (direction == HORIZONTAL && (position == LEFT || position == RIGHT))
			{
				layout = new GridLayout(1, 14);
				layout.setHgap(hGap);
				layout.setVgap(vGap);
				setLayout(layout);
				if (position == LEFT){
					for (int i = 0; i < 7; i++){
						cellsPanel.add(labels[i]);
						cellsPanel.add(fields[i]);
					}
				}

				if (position == RIGHT){
					for (int i = 0; i < 7; i++){
						cellsPanel.add(fields[i]);
						cellsPanel.add(labels[i]);
					}
				}
			}
			if (direction == VERTICAL)
			{
				layout = new GridLayout(14, 1);
				layout.setHgap(hGap);
				layout.setVgap(vGap);
				setLayout(layout);

				if (position == TOP || position == LEFT){
					for (int i = 0; i < 7; i++){
						cellsPanel.add(labels[i]);
						cellsPanel.add(fields[i]);
					}
				}
				if (position == BOTTOM || position == RIGHT){
					for (int i = 0; i < 7; i++){
						cellsPanel.add(fields[i]);
						cellsPanel.add(labels[i]);
					}
				}
			}
			setLayout(new BoxLayout(TimeSpecifier.this, BoxLayout.Y_AXIS));
			add(infinityPanel);
			add(cellsPanel);
		}//ctor

		public void setTime(String time){
			if (time.compareTo("infinity") == 0)
			{
				infinity.setSelected(true);
				return;
			}
			StringTokenizer st = new StringTokenizer(time, ";");
			String tmp = new String();
			int i = 0;
			while (st.hasMoreTokens()){
				tmp = st.nextToken();
				Integer.parseInt(tmp);
				fields[i].setText(tmp);
				fields[i].repaint();
				i++;
			}
		}

		public String getTime(){
			String str = new String();
			for (int i = 0; i < 7; i++){
				try
				{
					Integer.parseInt(fields[i].getText());
				}catch(Exception e)
				{
					fields[i].setText("0");
				}
				str = str.concat(fields[i].getText()+";");
			}
			str = str.substring(0, str.length()-1);
			if (infinity.isSelected() == true)
				str = "infinity";
			return str;
		}

		public void setInfinity()
		{
			setTime(" ; ; ; ; ; ; ");
		}

		class InfinityListener implements ItemListener{
			public void itemStateChanged(ItemEvent e)
			{
				if (infinity.isSelected() == true)
				{
					for(int i = 0; i < 7; i++)
						fields[i].setEditable(false);
					//setInfinity();
				}
				if (infinity.isSelected() == false)
				{
					for(int i = 0; i < 7; i++)
						fields[i].setEditable(true);
				}
			return;
			}
		};
	}// end TimeSpecifier
