package com.Lin.FrontAndBackFiles;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String frontName = "avatar1.jpg";
        String backName = "avatar2.jpg";
        String targetName = "result.jpg";

        String frontPath = new File(".").getCanonicalPath() + "/src/" + frontName;
        String backPath = new File(".").getCanonicalPath() + "/src/" + backName;
        String targetPath = new File(".").getCanonicalPath() + "/src/" + targetName;
        File frontFile = new File(frontPath), backFile = new File(backPath), targetFile = new File(targetPath);

//        FileUtil.mergeFile(frontFile, backFile, targetFile); //合并

        System.out.println("origin: " + FileUtil.getMd5(targetFile));

//        FileUtil.reverseFile(targetFile); //反转
        System.out.println("out.txt: " + FileUtil.getMd5(targetFile));

        FileUtil.divideFile(targetFile, frontFile, backFile); //拆分
    }

}
