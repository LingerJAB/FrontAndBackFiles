package com.Lin.FrontAndBackFiles;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        String frontName = "img1.png";
        String backName = "img2.png";
        String targetName = "result.png";

        String frontPath = getLocalFilePath(frontName);
        String backPath = getLocalFilePath(backName);
        String targetPath = getLocalFilePath(targetName);
        File frontFile = new File(frontPath), backFile = new File(backPath), targetFile = new File(targetPath);

//        FileUtil.mergeFile(frontFile, backFile, targetFile); //合并
//        FileUtil.reverseFile(targetFile); //反转
        FileUtil.divideFile(frontFile, backFile,targetFile); //分割

    }

    public static String getLocalFilePath(String name) {
        String path = "";
        try {
            path = new File(".").getCanonicalPath() + "/src/TestBox/" + name;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
