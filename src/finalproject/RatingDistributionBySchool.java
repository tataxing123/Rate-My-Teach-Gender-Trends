package finalproject;


import java.util.ArrayList;

public class RatingDistributionBySchool extends DataAnalyzer {
	private ArrayList<MyHashTable<String,ArrayList<Double>>> info;
	private ArrayList<String> schools;
	public RatingDistributionBySchool(Parser p) {
		super(p);
	}
	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String school) {
		school = school.toLowerCase().trim();
		MyHashTable<String,Integer> result = new MyHashTable<String,Integer>(20);
		int index = this.schools.indexOf(school);
		if(index < 0 ){return null;}
		MyHashTable<String, ArrayList<Double>> curr = this.info.get(index);
		ArrayList<String> listOfProf = curr.getKeySet();
		for(int i = 0 ; i< curr.size(); i++){
			if(curr.isEmpty()){continue;}
			ArrayList<Double> ratings = curr.get(listOfProf.get(i));
			Integer totalNumRating =  ratings.size();
			Double totalScore = 0.0;
			for(Double r : ratings){
				totalScore+=r;
			}Double avg = Math.round((totalScore / totalNumRating)*100.0)/100.0;
			String x = listOfProf.get(i) +"\n"+ avg;
			result.put(x,totalNumRating);
		}
		return result;
	}

	@Override
	public void extractInformation() {
		this.info = new ArrayList<MyHashTable<String,ArrayList<Double>>>();
		this.schools = new ArrayList<String>();
		// Creates Schools
		for(int i = 0 ; i < this.parser.data.size();i++){
			if(!this.schools.contains(this.parser.data.get(i)[1].toLowerCase().trim())){
				this.schools.add(this.parser.data.get(i)[1].toLowerCase().trim());
			}
		}
		// Creates info according to this.schools size
		for(int j = 0 ; j< this.schools.size();j++){
			MyHashTable<String,ArrayList<Double>> prof = new MyHashTable<String,ArrayList<Double>>();
			this.info.add(prof);
		}
		for(String[] d : this.parser.data ){
			String profName = d[0].toLowerCase().trim();
			String schoolName = d[1].toLowerCase().trim();
			int index = this.schools.indexOf(schoolName);
			MyHashTable<String,ArrayList<Double>> curr = this.info.get(index);
			if(curr.get(profName)==null){
				ArrayList<Double> rate = new ArrayList<>();
				rate.add(Double.parseDouble(d[4]));
				curr.put(profName,rate);
			}else{
				curr.get(profName).add(Double.parseDouble(d[4]));
			}

		}
	}
}
