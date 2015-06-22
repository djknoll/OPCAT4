package gui.procDependencies.criticalPath;

import exportedAPI.opcatAPIx.IXProcessEntry;

/**
 * @author - Perelman Valeria
 * This structure is needed for the Critical Path calculations,
 * each OPM Process is covered by such structure to keep temporal
 * information related to the Process 
 * 
 * */
class Activity {
	  /**
	   * procID - the ID of the process represented by the Activity
	   * */
	  public long procID;
	  /**
	   * duration - depending on the algorithm, the process duration
	   * @format - "msec;sec;min;hours;days;years"
	   * @zero_value - "0;0;0;0;0;0"
	   * */
	  public String duration;
	  /**
	   * est - process early start
	   * @format - "msec;sec;min;hours;days;years"
	   * @zero_value - "0;0;0;0;0;0"
	   * */
	  public String est;
	  /**
	   * lst - process late start
	   * @format - "msec;sec;min;hours;days;years"
	   * @zero_value - "0;0;0;0;0;0"
	   * */
	  public String lst;
	  /**
	   * eet - process early end
	   * @format - "msec;sec;min;hours;days;years"
	   * @zero_value - "0;0;0;0;0;0"
	   * */
	  public String eet;
	  /**
	   * let - process late end
	   * @format - "msec;sec;min;hours;days;years"
	   * @zero_value - "0;0;0;0;0;0"
	   * */
	  public String let;
	  
	  public static String ZERO_DURATION = "0;0;0;0;0;0";
	  
	  /**
	   * @param proc - if the process has unnormalized duration time
	   * it will be kept in the Activity structure in the normalized form
	   * (for example, 24 hours will be kept as 1 day)
	   * */
	  public Activity(IXProcessEntry proc, String duration){
		  procID = proc.getId();
		  this.duration = duration;
		  this.duration = getSum("0;0;0;0;0;0;0", this.duration);
	  }
	  
	  /**
	   * @return - true if the process is critical
	   * this method should be called only after Critical Path
	   * Calculation
	   * */
	  boolean isCritical(){
		  if(est==null||lst==null||eet==null||let==null)return false;
		  return (est.equals(lst) && eet.equals(let));
	  }
	  
	  /**
	   * Compares two durations in the Activity duration format
	   * returns true if the first parameter is equal or smaller 
	   * than the second one
	   * if the first parameter is null - returns true
	   * if the first parameter includes "infinity" word - returns false
	   * */
	  public static boolean isSmaller(String dur1, String dur2){
		  if(dur1 == null)return true;
		  if(dur1.contains("infinity"))return false;
		  if(dur2.contains("infinity"))return true;
		  String[] durr1 = dur1.split(";");
		  String[] durr2 = dur2.split(";");
		  for(int i=6; i>=0;i--){
			  if(new Integer(durr1[i]).intValue() > new Integer(durr2[i]).intValue())
				  return false;
		  }
		  return true;
	  }
	  /**
	   * This method normalizes duration
	   * accepts only positive durations
	   * */
	  //TODO - make normalization for both positive and negative cases
	  public static String getNormalizedDuration(String strDuration){
		  return getSum(strDuration, ZERO_DURATION);
	  }
	  
	  /**
	   * @return sum of the two durations given as the method parameters
	   * The method has no side-effects, the output duration is normalized
	   * */
	  public static String getSum(String dur1, String dur2){
		  if(dur1 == null)return dur2;
		  int[] result = {0,0,0,0,0,0,0};
		  int[] factor = {1000,60,60,24,30,12};
		  if(dur1.contains("infinity")||dur2.contains("infinity"))return "infinity";
		  String[] durr1 = dur1.split(";");
		  String[] durr2 = dur2.split(";");
		  int x = 0;
		  int y = 0;
		  for(int i=0; i<6;i++){
			  x = new Integer(durr1[i]).intValue();
			  y = new Integer(durr2[i]).intValue();
			  result[i] += x+y;
			  if(result[i] >= factor[i]){
				  result[i] = result[i]%factor[i];
				  result[i+1] = result[i]/factor[i];
			  }
		  }
		  x = new Integer(durr1[6]).intValue();
		  y = new Integer(durr2[6]).intValue();
		  result[6] += x+y;
		  String res = ""+result[0];
		  for(int i=1;i<7;i++){
			  res+=";"+result[i];
		  }
		  return res;
	  }
	  
	  /**
	   * @return sum of the two durations given as the method parameters
	   * The method has no side-effects
	   * returns "null" if the second parameter is greater
	   * */
	  public static String getDif(String dur1, String dur2){
		if(dur2 == null)return dur1;
		if(dur1.contains("infinity"))return dur1;
		if(dur2.contains("infinity"))return null; 
		if(Activity.isSmaller(dur1, dur2))return null;
		int[] result = new int[7];
		int[] factor = {1000,60,60,24,30,12};
		String[] durr1 = dur1.split(";");
		String[] durr2 = dur2.split(";");
		for(int i=6;i>=0;i--){
			result[i] = new Integer(durr1[i]).intValue() - new Integer(durr2[i]).intValue();
			if(result[i]<0){
				for(int j=i+1; j<7;j++){
					if(result[j]>0){
						result[j]--;
						for(int k = j-1;k<i;k--){
							result[k]+=factor[k]-1;
						}
						result[i]=result[i] + factor[i];
						j=7;
					}
				}
			}
		}
		String res =""+result[0];
		for(int i=1;i<7;i++){
			  res+=";"+result[i];
		  }
		  return res;
	  }
	    
}


