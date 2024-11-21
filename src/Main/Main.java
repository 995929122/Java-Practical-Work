package Main;
import FrameWork.MainFrame;
import ImagePath.imagePath;




public class Main {
	
	
	public static void main(String[] args) {
		
		imagePath imagePath=new imagePath();
		MainFrame frameWork=new MainFrame(imagePath.getImagePath());
		frameWork.setVisible(true);
	}
}
