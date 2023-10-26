package finalproject;

import java.util.ArrayList;

public class RatingDistributionByProf extends DataAnalyzer {
	private ArrayList<MyHashTable<Integer,Integer>> info;
	private ArrayList<String> professors;
    public RatingDistributionByProf(Parser p) {super(p);}
	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String profName) {
		profName = profName.toLowerCase().trim();
		MyHashTable<String,Integer> result = new MyHashTable<String,Integer>(6);
		int index = this.professors.indexOf(profName);
		if(index < 0 ){
			return null;
		}
		MyHashTable<Integer, Integer> thisProf = this.info.get(index);
		result.put("1",thisProf.get(1));
		result.put("2",thisProf.get(2));
		result.put("3",thisProf.get(3));
		result.put("4",thisProf.get(4));
		result.put("5",thisProf.get(5));
		return result;
	}
	@Override
	public void extractInformation() {
		this.info = new ArrayList<MyHashTable<Integer,Integer>>();
		this.professors = new ArrayList<String>();
		// Creates Professor
		for(int i = 0 ; i < this.parser.data.size();i++){
			if(!this.professors.contains(this.parser.data.get(i)[0].toLowerCase())){
				this.professors.add(this.parser.data.get(i)[0].toLowerCase());
			}
		}
		// Creates info according to this.professors size
		for(int j = 0 ; j< this.professors.size();j++){
			MyHashTable<Integer,Integer> prof = new MyHashTable<Integer,Integer>(6);
			prof.put(1,0);
			prof.put(2,0);
			prof.put(3,0);
			prof.put(4,0);
			prof.put(5,0);
			this.info.add(prof);
		}
		for(String[] d : this.parser.data ){
			String profName = d[0].toLowerCase().trim();
			double rate =Double.parseDouble(d[4]);
			Integer rating = (int) Math.floor(rate);
			int index = this.professors.indexOf(profName);
			Integer newValue = this.info.get(index).get(rating);
			this.info.get(index).put(rating,newValue+1);
		}
	}
}
