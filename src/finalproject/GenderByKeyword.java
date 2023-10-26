package finalproject;

import java.util.ArrayList;
import java.util.LinkedList;

public class GenderByKeyword extends DataAnalyzer {
	private ArrayList<ArrayList<String[]>> info;
	public GenderByKeyword(Parser p) {super(p);	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		String[] keywords = getWordsFromComment(keyword);
		MyHashTable<String,Integer> result = new MyHashTable<String,Integer>(3);
		ArrayList<String[]> thisGender;
		ArrayList<String> unique;
		boolean notFound=true;
		int count;
		for(int i = 0;i<3;i++){
			count=0;
			thisGender = this.info.get(i);
			for(String[] comment : thisGender){
				unique = new ArrayList<String>();
				for(String word : keywords){
					if(contains(comment,word) &&!unique.contains(word)) {
						count++;
						unique.add(word);
						notFound=false;
					}
				}
			}
			if(i==0){result.put("M",count);}
			else if(i==1){result.put("F",count);}
			else if(i==2){result.put("X",count);}
		}if(notFound){return null;}
		return result;
	}

	@Override
	public void extractInformation() {
		this.info = new ArrayList<ArrayList<String[]>>(3);
		for (int i = 0; i < 3; i++) {
			ArrayList<String[]> something = new ArrayList<String[]>();
			this.info.add(something);
		}
		for (String[] d : this.parser.data) {
			String[] comment = getWordsFromComment(d[6]);
			String gender = d[7];
			if (gender.equals("M")) {
				this.info.get(0).add(comment);
			} else if (gender.equals("F")) {
				this.info.get(1).add(comment);
			} else if (gender.equals("X")) {
				this.info.get(2).add(comment);
			}
		}
	}
	private static boolean contains(String[] arr, String str) {
		for (String element : arr) {
			if (element != null && element.equals(str)) {
				return true;
			}
		}return false;
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
