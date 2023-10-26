package finalproject;

import java.util.ArrayList;

public class RatingByGender extends DataAnalyzer{
	private ArrayList<ArrayList<ArrayList<Integer>>> info;
	public RatingByGender(Parser p) {super(p);}
	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		ArrayList<Integer> temp;
		String[] keywords = getWordsFromComment(keyword);
		if(keywords[0].equals("f")){
			ArrayList<ArrayList<Integer>> female = this.info.get(1);
			if(keywords[1].equals("difficulty")){
				temp = female.get(1);
			}else if(keywords[1].equals("quality")){
				temp = female.get(0);
			}else{return null;}
		}else if(keywords[0].equals("m")){
			ArrayList<ArrayList<Integer>> male = this.info.get(0);
			if(keywords[1].equals("difficulty")){
				temp = male.get(1);
			}else if(keywords[1].equals("quality")){
				temp = male.get(0);
			}else{return null;}
		}else{return null;}
		MyHashTable<String, Integer> hashTable = new MyHashTable<String, Integer>(5);
		for (int i = 0; i < 5; i++){
			hashTable.put(Integer.toString(i+1), temp.get(i));
		}return hashTable;
	}

	@Override
	public void extractInformation() {
		this.info = new ArrayList<ArrayList<ArrayList<Integer>>>(2);
		for(int i = 0 ;i<2;i++) {
			ArrayList<ArrayList<Integer>> rate = new ArrayList<ArrayList<Integer>>(2);
			for (int j = 0; j < 2; j++) {
				ArrayList<Integer> something = new ArrayList<Integer>(5);
				for (int z = 0; z < 5; z++){
					something.add(0);
				}
				rate.add(something);
			}
			this.info.add(rate);
		}
		for (String[] d : this.parser.data) {
			double rateQ = Double.parseDouble(d[4]);
			int ratingQ = (int) (Math.floor(rateQ)-1);
			double rateD =Double.parseDouble(d[5]);
			int ratingD = (int) (Math.floor(rateD)-1);
			String gender = d[7];
			if(gender.equals("M")){
				int newCount = this.info.get(0).get(0).get(ratingQ);
				this.info.get(0).get(0).set(ratingQ,newCount+1);
				int newCountD = this.info.get(0).get(1).get(ratingD);
				this.info.get(0).get(1).set(ratingD,newCountD+1);
			}else if(gender.equals("F")){
				int newCount = this.info.get(1).get(0).get(ratingQ);
				this.info.get(1).get(0).set(ratingQ,newCount+1);
				int newCountD = this.info.get(1).get(1).get(ratingD);
				this.info.get(1).get(1).set(ratingD,newCountD+1);
			}
		}
	}
	private static String[] getWordsFromComment(String comment) {
		comment = comment.toLowerCase().replaceAll("[^a-z']", " ");
		String[] words = comment.split("\\s+");
		ArrayList<String> result = new ArrayList<>();
		for (String word : words) {
			if (!word.isEmpty()) {
				result.add(word);
			}
		}return result.toArray(new String[0]);
	}
}
