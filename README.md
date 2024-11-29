Practical_Work/  

├── src/  
│   ├── FrameWork/  
│   │   ├── SettingsFrame.java  
│   │   ├── MainFrame.java  
│   │   ├── StartFrame.java  
│   │   └── Words.java  
│   ├── ImagePath/  
│   │   └── imagePath.java  
│   ├── Modes/  
│   │   ├── Mode1_Panel.java  
│   │   └── Mode2_Panel.java  
│   └── fakeServer/  
│       ├── fakeServer.java  
│       ├── sentLines.txt  
│       ├── WrongWords.txt  
│       ├── mastered.txt  
│       └── sorted.txt  
└── README.md  





## 文件说明

### FrameWork 文件夹

- **MainFrame.java**: 主框架类，负责初始化和显示主窗口。
- **MainFrame.java**: 主界面类，负责显示主界面内容。
- **StartFrame.java**: 启动界面类，负责显示启动界面内容。
- **Words.java**: 处理单词相关操作的类，包括已掌握和未掌握单词的处理。

### ImagePath 文件夹

- **imagePath.java**: 处理图片路径的类，提供图片路径的获取方法。

### Modes 文件夹

- **Mode1_Panel.java**: 模式1的面板类，负责显示和处理模式1的内容。
- **Mode2_Panel.java**: 模式2的面板类，负责显示和处理模式2的内容。

### fakeServer 文件夹

- **fakeServer.java**: 模拟服务器类，负责处理客户端请求并发送数据。
- **sentLines.txt**: 已发送的行记录文件。
- **WrongWords.txt**: 错误单词记录文件。
- **mastered.txt**: 已掌握单词记录文件。
- **sorted.txt**: 排序后的单词记录文件。

## 使用说明

1. **启动服务器**:
   - 运行 `fakeServer.java` 启动模拟服务器，服务器将监听多个端口以处理不同的请求。

2. **启动客户端**:
   - 运行 `FrameWork.java` 启动客户端应用程序，显示主窗口。

3. **操作说明**:
   - 在主窗口中，可以选择不同的模式（Mode1 或 Mode2）进行单词练习。
   - 在单词练习过程中，可以查看已掌握和未掌握的单词。

## 注意事项

- 确保所有文件路径正确，特别是 `fakeServer` 文件夹中的数据文件路径。
- 确保服务器在客户端尝试连接之前已经启动。
- 如果遇到任何问题，请检查控制台输出的调试信息。
- myServer和myApp是经maven重构后的项目文件 包含整个项目执行逻辑 但受本人能力限制未能构建出可执行jar（本地测试失败 主要是不会跑jar的cmd）
