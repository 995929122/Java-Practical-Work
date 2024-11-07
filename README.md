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
		2对于将其他组件置于背景面板上层的操作不熟练（见LearnToSetBackground）
		
		
		目前的问题是无法将label和botton置于正确的位置
		
		
		创建了WordIfo包的Word类做对象  目前功能仍不完整	暂时不会读文件形成封装对象的方法
