package extensionTools.MsProject;
import java.util.*;
public class MsUtility {

	public static final int HOURS = 0;
	public static final int MINUTES = 1;
	public static final int SECONDS = 2;
	public static final int NORM_LENGTH = 16;
	public static final int ADD = 1;
	public static final int SUBSTRACT = -1;
	public static final String[] CODES = {"T","H","M","S"};
	
	public static String[] getDuration(String msDuration){
		if(msDuration == null)return null;
		String[] duration = new String[3];
		int beginIndex;
		int endIndex;
		for(int i=0; i<3;i++){
			beginIndex = msDuration.indexOf(CODES[i])+1;
			endIndex = msDuration.indexOf(CODES[i+1]);
			duration[i] = msDuration.substring(beginIndex, endIndex);
		}
		return duration;
	}
	
	public static Date getEndDate(Date currDate, String[] duration){
		return getNextDate(currDate, duration, ADD);
	}
	
	public static Date getStartDate(Date currDate, String[] duration){
		return getNextDate(currDate, duration, SUBSTRACT);	
	}
	
	public static Date getNextDate(Date currDate, String[] duration, int factor){
		GregorianCalendar greg = new GregorianCalendar();
		greg.setTime(currDate);
		greg.add(GregorianCalendar.HOUR, new Integer(duration[HOURS]).intValue()*factor);
		greg.add(GregorianCalendar.MINUTE, new Integer(duration[MINUTES]).intValue()*factor);
		greg.add(GregorianCalendar.SECOND, new Integer(duration[SECONDS]).intValue()*factor);
		return greg.getTime();
	}
	
	public static String getNormalizedThingName(String name){
		
		String longestWord = getLongestWord(name);
		int lineLength = (longestWord.length()>NORM_LENGTH)?longestWord.length():NORM_LENGTH;
		String[] words = name.split(" ");
		String currLine = words[0];
		String result = "";
		String strTry = "";
		for(int i=1;i<words.length;i++){
			strTry = currLine+" "+words[i];
			if(strTry.length()>lineLength){
				result = result + currLine+ "\n";
				currLine = words[i];
			}else{
				currLine = strTry;
			}
		}
		if(!result.endsWith(currLine))result = result+currLine;
		return result;
	}
	
	public static String getLongestWord(String s){
	// Assume s contains a string of words
	String longestWord = "";
	java.util.StringTokenizer st = new java.util.StringTokenizer(s, " ,\t");
	while (st.hasMoreTokens()) {
	    String w = st.nextToken();
	    if (w.length() > longestWord.length()) {
	        longestWord = w;
	    }
	}
	return longestWord;
	}

	
	public static java.util.Date getDate(String msDate){
		if(msDate==null || !(msDate.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]")))
			return null;
		java.util.GregorianCalendar greg = new java.util.GregorianCalendar();
		String[] date = msDate.substring(0,msDate.indexOf("T")).split("-");
		String[] time = msDate.substring(msDate.indexOf("T")+1).split(":");
		greg.set(new Integer(date[0]).intValue(),new Integer(date[1]).intValue(),
				new Integer(date[2]).intValue(),
				new Integer(time[0]).intValue(),new Integer(time[1]).intValue(),
				new Integer(time[2]).intValue()
				);
		return greg.getTime();
	}
};
