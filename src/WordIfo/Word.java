package WordIfo;
import java.lang.*;

import FrameWork.FrameWork;

public class Word {
	private StringBuffer English;
	private StringBuffer Chinese;
	
	public  Word(String english,String chinese) {
		this.English=new StringBuffer(english);
		this.Chinese=new StringBuffer(chinese);
		
	}
	public String getEnglish() {
		return this.English.toString();
	}
	public String getChinese() {
		return this.Chinese.toString();
	}
	
	public char getFirst() {
		return English.charAt(0);
	}
	public char getLast() {
		return English.charAt(English.length()-1);
	}
	
	//public static void main(String[] args) {
	//	Word word=new Word("Apple", "苹果");
	//	System.out.println(word.getChinese()+word.getEnglish()+word.getFirst()+word.getLast());
	//}
	
}

class GetWord{
	
}