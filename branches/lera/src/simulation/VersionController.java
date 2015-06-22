package simulation;

import exportedAPI.opcatAPIx.IXSystem;

public class VersionController {
	protected IXSystem opmSystem;
	protected String ProjectFileName;
	private static VersionController myCtrl = null;
	
	protected VersionController(IXSystem opmSystem_){
		opmSystem = opmSystem_;
	}
	public static VersionController getInstance(IXSystem opmSystem_){
		if(myCtrl != null)
		return myCtrl;
		return (myCtrl = new VersionController(opmSystem_));
	}
	public IXSystem getSystem(){
		return opmSystem;
	}
	public void releaseSystem(){
	}
}
