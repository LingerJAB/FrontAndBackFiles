# FrontAndBackFiles

> 2023 高中科创比赛作品  

通过IO读写文件，将两个文件合并，
实现文件隐藏加密等效果，且可绕过文件类型检测  
~~因此被称作 “正反面文件”~~

在Java源码中提供了一些方法方便调用

## 使用方法
`FileUtil`类 提供了以下静态方法  

**主要方法**
- `mergeFile(File, File, File)` 合并两个文件到一个文件
- `reverseFile(File)` 反转文件字节
- `divideFile(File, File, File)` 分割文件到两个文件

**其它方法**
- `printBytes(File)` 打印字节
- `getMd5(File)` 获取文件MD5值
- `reverseBytes(byte[])` 反转字符串