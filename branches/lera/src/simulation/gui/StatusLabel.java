package simulation.gui;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import simulation.ISimulationStatus;
import simulation.ISimulationStatusListener;
import simulation.SimulationRunner;
import simulation.SimulationRunner.CREATOR_TYPE;
import simulation.creator.FileCreator;
import simulation.reader.IQueueReaderStatus;

public class StatusLabel extends JLabel implements ISimulationStatusListener{
	SimulationRunner runner;	
	public StatusLabel(SimulationRunner runner){
		this.runner = runner;
		updateStatus(runner.getStatus());
		runner.addStatusListener(this);
	}
	
	public void statusChanged(ISimulationStatus status){
		updateStatus(status);
	}
	
	private void updateStatus(final ISimulationStatus status){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!status.isStarted()){
					setText(" Stopped");
				}else{
					String text =" ";
					if ( runner.getCreatorType() == CREATOR_TYPE.FILE){
						text += "Playing file - "+runner.getPlayedFileName()+" ;";
					}

					if (status.isContinuous()){
						text += "Play Mode - Continouos; ";
						if (status.isPlayingForward()){
							text += "Direction - Forward; ";
						}else{
							text += "Direction - Backward; ";
						}

						if (status.isPaused()){
							text += "Simulation is Paused; ";
						}
					}else{
						text += "Play Mode - Step by Step; ";
					}

					text += " Play speed - Real X "+Math.round(status.getPlayingSpeed())+";";

					text+= " Timeline = "+status.getTimeline();
					setText(text);
				}
			}
		});
	}
}
