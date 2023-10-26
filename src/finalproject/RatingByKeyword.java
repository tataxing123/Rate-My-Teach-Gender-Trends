package finalproject;

import java.util.ArrayList;

public class RatingByKeyword extends DataAnalyzer {
	private MyHashTable<Integer,ArrayList<String[]>> info;
	
    public RatingByKeyword(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		String[] keywords = getWordsFromComment(keyword);
		MyHashTable<String,Integer> result = new MyHashTable<String,Integer>(5);
		ArrayList<String[]> thisRating;
		int count;
		boolean notFound=true;
		for(int i = 0;i<5;i++){
			count=0;
			thisRating = this.info.get(i);
			for(String[] comment : thisRating){
				for(String word : keywords){
					if(contains(comment,word)){
						count++;
						notFound=false;
					}
				}
			}result.put(Integer.toString(i+1),count);
		}
		if(notFound){return null;}
		return result;
	}

	@Override
	public void extractInformation() {
		this.info = new MyHashTable<Integer,ArrayList<String[]>>(5);
		for (int i = 0; i < 5; i++) {
			ArrayList<String[]> something = new ArrayList<String[]>();
			this.info.put(i,something);
		}
		for (String[] d : this.parser.data) {
			String[] comment = getWordsFromComment(d[6]);
			double rate =Double.parseDouble(d[4]);
			int rating = (int) Math.floor(rate);
			String[] uniqueArr = new String[comment.length];
			int index = 0;
			for (String str : comment) {
				if (!contains(uniqueArr, str)) {
					uniqueArr[index] = str;
					index++;
				}
			}this.info.get(rating-1).add(comment);
		}
	}
	private static boolean contains(String[] arr, String str) {
		for (String element : arr) {
			if (element != null && element.equals(str)) {
				return true;
			}
		}
		return false;
	}
	private static String[] getWordsFromComment(String comment) {
		comment = comment.toLowerCase().replaceAll("[^a-z']", " ");
		String[] words = comment.split("\\s+");
		ArrayList<String> result = new ArrayList<>();
		for (String word : words) {
			if (!word.isEmpty()) {
				result.add(word);
			}
		}
		return result.toArray(new String[0]);
	}
}
