package exportedAPI;
import java.util.*;

public class OpdLayout {
	TreeMap<Integer, ArrayList<Long>> constraints;
	
	public OpdLayout(){
		constraints = new TreeMap<Integer, ArrayList<Long>>(); 
	}
	
	public void clear(){
		constraints.clear();
	}
	
	public void addOpd(long opdNum, int row){
		ArrayList<Long> currRow = constraints.get(row);
		if (currRow == null){
			currRow = new ArrayList<Long>();
			constraints.put(row, currRow);
		}
		currRow.add(opdNum);
	}
	
	public ArrayList<Long> getRow(int row){
		return constraints.get(row);
	}
	
	public boolean isInLayout(long opdNum){
		Iterator<ArrayList<Long>> iter = constraints.values().iterator();
		while (iter.hasNext()){
			ArrayList<Long> currRow = iter.next();
			for (int i = 0; i < currRow.size(); i++){
				if (currRow.get(i) == opdNum){
					return true;
				}
			}
		}
		return false;
	}
	
	public Iterator<ArrayList<Long>> getRows(){
		return constraints.values().iterator();
	}
	
	public int getNumOfRows(){
		return constraints.size();
	}
}
