# FrontAndBackFiles

> 2023 高中科创比赛作品  

通过IO读写文件，将两个文件合并，
实现文件隐藏加密等效果，且可绕过文件类型检测  
~~因此被称作 “正反面文件”~~

在Java源码中提供了一些方法作为调用

## 使用方法

`FileUtil`类 提供了以下静态方法  

**主要方法**

- `mergeFile(File, File, File)` 合并两个文件到一个文件
- `divideFile(File, File, File)` 分割文件到两个文件
- `reverseFile(File)` 翻转文件

具体形参含义参见源码

---

**其它方法**

- `printBytes(File)` 打印字节
- `getMd5(File)` 获取文件MD5值
- `reverseBytes(byte[])` 反转字节

## 效果

将`图片A.jpg`和`图片B.jpg`合并到`图片R.jpg`后，
`图片R.jpg`的体积约为`图片A.jpg`和`图片B.jpg`之和。  
windows的资源管理器里的缩略图只会显示`图片A`，  
对`图片R.jpg`进行翻转后，则变成了`图片B`，且仍可以正常打开

## 注意

**文件合并依赖于文件尾，因此只支持以下文件合并**

- `JPG` : *FF D9*
- `PNG` : *AE 42 60 82*
- `GIF` : *00 3B*
- `ZIP` : *50 4B*

对于zip文件可能会出现损坏问题，但不影响使用(浏览，加密，解压等操作)
