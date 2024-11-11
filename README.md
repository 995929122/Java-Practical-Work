# Java-Practical-Work
《Java语言与程序设计》课程实践作业

11.8更新:
-
用
		
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
	
方法将背景面板作为窗口的内容面板 实现了图片背景  
缺点：
1不能平铺等比缩放 （看后续能不能给改好）
2对于将其他组件置于背景面板上层的操作不熟练（见LearnToSetBackground  搞了半天才让label透明显示在背景上层 暂时不明白原理）
		
目前的问题是无法将label和botton置于正确的位置
		
创建了WordIfo包的Word类做对象  目前功能仍不完整	暂时不会读文件形成封装对象的方法


11.8更新2：
-
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
	

11.9更新：
在ai帮助下在LBA类中实现了按钮切换窗口（CardLayout方法实现）对代码理解不够深刻 
接下来着重理解这部分代码
-
决定将每个按钮的按钮事件单独分装一个类 感觉太麻烦 应该有更好的解决方案 但是在代码能力增长之前暂时不敢尝试

课本要快点啃完 看看能不能找个Java开源项目学学试试（不大很抱希望 时间比较紧张）
-
面板还是比较粗糙（见11.8--2）
WordIfo包等待完善（见11.8--2）
 11.8--2的问题仍然没能解决 
考虑回头看看课本布局管理的部分找找思路
-
关于对主面板的修改 暂时的思路是保持背景不变 将不同的Card显示在背景上层 觉得很有可能实现


11.10更新：
-
添加了按钮事件
在AI的帮助下推到了原先主面板的设计，理解了保持背景显示的方法（设置上层面板透明） 成功实现了平埔显示整个图片（利用getWidth和getHeight），稍微熟悉了一点cardlayout的用法

：CardLayout 是一个LayoutManager 规定一次显示一个组件 内部的public void show(Container parent,String name)方法将parent中的不同组件（Container）show出来
先将不同组件加到一个用CardLayout的Parent Panel中（用parent.add.(组件，"name")方法）之后就可以用show方法显示不同的Container 

Label和Button的布局非常抽象 属于面向结果编程 不停调参之后面板差不多能看了 暂时不敢乱动  （也就是说布局管理自定义的能力不足）
目前采用一个统一的创造次要面板的函数方法 不同面板完全同质 回头大概要拆分成不同的函数创建面板 但是感觉这么做会让Frame主类非常臃肿
问了一嘴Ai ai的设计思路也是用不同的创建面板函数 使各自面板有不同组件  -----回头实装一下试试


11.11更新
-
赢麻了 借鉴课本的学生管理系统的设计 用另一种方法实现了面板切换
		将Main Start Wrong Set 等拆分成不同的Frame类
		利用按钮事件	
			this.dispose();
			new anotherFrame();
		方法实现了面板（准确说是窗口）切换
		由于将不同面板拆成了不同类 现在对不同面板进行布局管理变得简单很多 思路清晰很多
现在的代码设计思路更清晰 利用了统一的封装的面板初始化函数方法（没能封装到类中是一大遗憾点）
自认为比原先简洁很多
算是熟悉了各种布局管理方法 基本能将组件控制在目标位置 对事件的运用能力也得到了增强

下一步的计划是
	1搞出定时器	
	2网络编程	 	
	3文件读写 	这个可以参考课本的学生管理系统对文件的读写 最后应该不会用这个方法 但是可以学到思路嘛


			