package simulation.gui;

import gui.Opcat2;
import gui.images.testing.TestingImages;
import gui.util.JToolBarButton;
import gui.util.JToolBarToggleButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.font.FontRenderContext;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulation.ISimulationStatus;
import simulation.ISimulationStatusListener;
import simulation.SimulationRunner;

public class SimulationToolBar extends JToolBar implements ISimulationStatusListener{
	private SimulationRunner runner;
	private Action zoomInAction; 
	private Action zoomOutAction;
	private Action closeAction;
	
	JToolBarButton playButton; 
	JToolBarButton forwardButton;
	JToolBarButton backwardButton;
	JToolBarToggleButton anPauseButton;
	JToolBarButton stopButton;
	JToolBarButton activateButton;
	JToolBarButton deactivateButton;
	JToolBarButton settingsButton;
	JToolBarButton zoominButton;
	JToolBarButton zoomoutButton;
	JSlider anSpeedSlider;
	
	
	public SimulationToolBar(SimulationRunner runner, Action zoomInAction, 
			Action zoomOutAction, Action closeAction) {
		super();
		this.runner = runner;
		this.zoomInAction = zoomInAction;
		this.zoomOutAction = zoomOutAction;
		this.closeAction = closeAction;
		_constructToolBar();
		updateToolBar(runner.getStatus());
		runner.addStatusListener(this);
	}
	
	public void statusChanged(final ISimulationStatus status){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				updateToolBar(status);				
			}
		});
	}
	
	private void updateToolBar(ISimulationStatus status){
		playButton.setEnabled(!status.isStarted()); 
		forwardButton.setEnabled(status.isStarted());
		backwardButton.setEnabled(status.isStarted());
		anPauseButton.setEnabled(status.isStarted() && status.isContinuous());
		anPauseButton.setSelected(status.isContinuous() && status.isPaused());
		stopButton.setEnabled(status.isStarted());
		activateButton.setEnabled(status.isStarted());
		deactivateButton.setEnabled(status.isStarted());
	}

	private void _constructToolBar() {
		anPauseButton = new JToolBarToggleButton(this.anPause, "Continue", "Pause");

		JLabel l = new JLabel("Velocity");
		Font f = l.getFont();
		int maxPanelsSize = 14 + (int) Math.max(f.getStringBounds("Velocity",
				new FontRenderContext(null, true, false)).getWidth(), f
				.getStringBounds("Velocity",
						new FontRenderContext(null, true, false)).getWidth());

		JPanel anSpeedPanel = new JPanel(new GridLayout(2, 1));
		anSpeedPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(), BorderFactory
						.createEmptyBorder(2, 5, 2, 5)));
		anSpeedSlider = new JSlider(1, 8, 2);
		anSpeedSlider.setSnapToTicks(true);
		anSpeedSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				runner.setPlayingSpeed((double)anSpeedSlider.getValue()/2.0);
			}
		});
		anSpeedSlider.setMaximumSize(anSpeedSlider
				.getPreferredSize());
		anSpeedSlider.setBorder(null);
		
		anSpeedPanel.add(new JLabel("Velocity"));
		anSpeedPanel.add(anSpeedSlider);
		anSpeedPanel.setMaximumSize(new Dimension(maxPanelsSize, 1000));

		playButton = new JToolBarButton(this.anPlay, "Play/Continue"); 
		add(playButton);
		backwardButton = new JToolBarButton(this.anBackward, "Backward"); 
		add(backwardButton);

		forwardButton = new JToolBarButton(this.anForward, "Forward"); 
		add(forwardButton);
		add(anSpeedPanel);
		add(anPauseButton);
		stopButton = new JToolBarButton(this.anStop, "Stop"); 
		add(stopButton);
		add(new JToolBar.Separator());
		activateButton = new JToolBarButton(this.anActivate, "Activate"); 
		add(activateButton);
		deactivateButton = new JToolBarButton(this.anDeactivate, "Deactivate"); 
		add(deactivateButton);
		settingsButton = new JToolBarButton(this.anSettings, "Testing Settings"); 
		add(settingsButton);
		add(new JToolBar.Separator());

		zoominButton = new JToolBarButton(zoomInAction, "Zoom In");
		zoominButton.setIcon(TestingImages.ZOOM_IN);
		zoomoutButton = new JToolBarButton(zoomOutAction,"Zoom Out");
		zoomoutButton.setIcon(TestingImages.ZOOM_OUT);
		add(zoominButton);
		add(zoomoutButton);

		add(new JToolBar.Separator());
		add(new JToolBarButton(closeAction,	"Exit Testing Mode"));

	}
	
	Action anPause = new AbstractAction("Pause", TestingImages.PAUSE) {
		public void actionPerformed(ActionEvent e) {
			if (!runner.isPaused()) {
				runner.pause();
			} else {
				runner.resume();
			}

		}
	};
	
	Action anPlay = new AbstractAction("Play", TestingImages.PLAY) {
		public void actionPerformed(ActionEvent e) {
			if (!runner.isPaused()) {
				runner.start();
			} else {
				runner.resume();
			}

		}
	};
	
	Action anBackward = new AbstractAction("Backward", TestingImages.BACKWARD) {
		public void actionPerformed(ActionEvent e) {
			runner.playBackward(1);
		}
	};
	
	Action anForward = new AbstractAction("Forward", TestingImages.FORWARD) {
		public void actionPerformed(ActionEvent e) {
			runner.playForward(1);
		}
	};
	
	Action anStop = new AbstractAction("Stop testing", TestingImages.STOP) {
		private static final long serialVersionUID = 8763689823234065805L;

		public void actionPerformed(ActionEvent e) {
			runner.stop();
		}
	};

	Action anActivate = new AbstractAction("Activate", TestingImages.ACTIVATE) {
		public void actionPerformed(ActionEvent e) {
			runner.doUserActivation();
		}
	};

	Action anDeactivate = new AbstractAction("Deactivate", TestingImages.DEACTIVATE) {
		public void actionPerformed(ActionEvent e) {
			runner.doUserDeactivation();
		}
	};

	Action anSettings = new AbstractAction("Testing Settings", TestingImages.SETTINGS) {
		public void actionPerformed(ActionEvent e) {
			runner.showSettings();
		}
	};

	
}
