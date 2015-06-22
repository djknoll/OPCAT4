package simulation.plugin;

public class SimulationControlCommand {
	public final static class TYPE{
		public final static int PAUSE = 0;
	}
	
	private int type;
	public SimulationControlCommand(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
}
