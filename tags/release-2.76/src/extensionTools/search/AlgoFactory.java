package extensionTools.search;


import org.apache.commons.collections.Factory;

/**
 * A search algorithem factory. takes an {@link OptionBase} 
 * It then looks at  those options and returns the correct search implemantation 
 * of {@link AlgoInterface}
 * 
 * @param optionBase - the options needed for any search algo. 
 * @author raanan
 *
 */
public class AlgoFactory implements Factory{
	OptionsBase options = null ; 
	
	AlgoFactory(OptionsBase optionbase) {
		options = optionbase; 
	}
	
	public Object create() {
		// TODO when you add search options here is the selection between them
		// for now it's only the in string search 
		
		AlgoInterface search = null ; 
		if (options instanceof OptionsInString ) {
			search = new AlgoInString((OptionsInString) options) ;
		}
		
		
		return search;
	}

}
