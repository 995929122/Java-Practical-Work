# Java-Practical-Work
《Java语言与程序设计》课程实践作业

11.8更新：用
		ImagePanel imagePanel = new ImagePanel(image);
        this.setContentPane(imagePanel)
        imagePanel.setOpaque(false);
		
		class ImagePanel extends JPanel {
		private Image image;
		
		public ImagePanel(Image image) {
			this.image = image;
		}
		// 重写paintComponent方法，在面板上绘制图像
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 将图像绘制到面板上，图像位于(0, 0)位置
			g.drawImage(image, 0, 0, this);
		}
	}
		方法将背景面板作为窗口的内容面板 实现了图片背景  
		缺点：
		1不能平铺等比缩放 （看后续能不能给改好）
		2对于将其他组件置于背景面板上层的操作不熟练（见LearnToSetBackground  搞了半天才让label透明显示在背景上层 暂时不明白原理）
		
		目前的问题是无法将label和botton置于正确的位置
		
		创建了WordIfo包的Word类做对象  目前功能仍不完整	暂时不会读文件形成封装对象的方法


11.8更新2：
	修改了代码布局 现在的思路比凌晨的版本清晰很多
	改换了窗口设计思路 将label button都封装在不同的panel里 通过设置各自panel调整各组件FrameWork上主面板的显示
	用ai添加了一个按钮事件 但是暂时不会这方面的东西 得快点把课本内容啃完
	
	不知为何设置LabelPanel位置的时候设置BorderLayout为NORTH 就会使得label文本居中显示 改为WEST才正确显示在左上方 但是早期的代码用NORTH也能正确显示 在给添LabelPanel添加了
		int labeltopMargin = 80; 
        int labelleftMargin = 30; 
        labelPanel.setBorder(BorderFactory.createEmptyBorder(labeltopMargin, labelleftMargin, 0, 0)); // 设置边距
		//setBorder和BorderFactory.createEmptyBorder
	功能使其能控制和窗口左上边界的距离之后 上述问题出现 
	回退到之前的代码也出现同样问题 暂时理解不了问题何在 恐怕是什么别的地方的被我忽略的改动导致了这个问题
	
	目前主页面面板基本成型 但是
	1各个按钮事件还未设置 虚有其表
	2面板比较粗糙 不能调整大小 背景不能平铺等。。。
	3WordIfo包对文件的读写等操作暂时没有思路 目前打算随机读一行 切出两个参数（中、英）来实例化Word 毫无头绪
	
	在Learn包中记录了下一个学习项目和目标
	