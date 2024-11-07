package WordIfo;
import java.lang.*;

public class Word {
	private String English;
	private String Chinese;
	StringBuffer englishBuffer;
	public  Word(String english,String chinese) {
		this.English=english;
		this.Chinese=chinese;
		englishBuffer=new StringBuffer(english);
	}
	public String getEnglish() {
		return this.English;
	}
	public String getChinese() {
		return this.Chinese;
	}
	
	public char getFirst() {
		return englishBuffer.charAt(0);
	}
	public char getLast() {
		return englishBuffer.charAt(englishBuffer.length()-1);
	}
	
}
