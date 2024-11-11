package Main;
import FrameWork.MainFrame;
//import WordIfo.Word;

public class Main {
	
	
	public static void main(String[] args) {
		
		String imagePath = "D:\\Java_eclipse_workspace\\Practical_Work\\src\\FrameWork\\image.png";
		MainFrame frameWork=new MainFrame(imagePath);
		frameWork.setVisible(true);
	}
}
