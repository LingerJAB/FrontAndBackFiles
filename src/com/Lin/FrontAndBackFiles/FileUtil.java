package com.Lin.FrontAndBackFiles;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileUtil {

    /**
     * 将 Byte[] 字节数组反转过来,且返回新的数组
     *
     * @param bytes 原字节数组
     * @author Lin
     */
    public static byte[] reverseBytes(byte[] bytes) {
        int front = 0;
        int back = bytes.length - 1;
        while(back - front>1) {
            bytes[front] ^= bytes[back];
            bytes[back] ^= bytes[front];
            bytes[front++] ^= bytes[back--];
        }
        return bytes;
    }

    /**
     * 获取文件的MD5值
     *
     * @param file 需要获取的文件
     * @return 文件的md5
     * @author Apache
     */
    public static String getMd5(File file) {
        try {
            return DigestUtils.md5Hex(new FileInputStream(file));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 合并两个文件为一个文件
     *
     * @param targetFile 合并后的目标文件
     * @param frontFile  正面文件
     * @param backFile   背面文件
     * @author Lin
     */
    public static void mergeFile(File frontFile, File backFile, File targetFile) {
        try {
            if(!targetFile.exists()) targetFile.createNewFile();
            FileOutputStream targetStream = new FileOutputStream(targetFile);
            FileInputStream frontInputStream = new FileInputStream(frontFile);
            byte[] frontAllBytes = frontInputStream.readAllBytes();

            targetStream.write(frontAllBytes);

//            System.out.print("front:");
//            System.out.println(Arrays.toString(frontAllBytes));

            targetStream.write(124); // 分隔符：|
            targetStream.close();
            frontInputStream.close();

            targetStream = new FileOutputStream(targetFile, true);
            FileInputStream backStream = new FileInputStream(backFile);
            byte[] backAllBytes = backStream.readAllBytes();

//            System.out.print("back:");
//            System.out.println(Arrays.toString(backAllBytes));

            reverseBytes(backAllBytes);

            targetStream.write(124); // 分隔符：|
            targetStream.write(backAllBytes);
            backStream.close();
            targetStream.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反转文件所有字节
     *
     * @param file 需要被反转的文件
     * @author Lin
     */
    public static void reverseFile(File file) {

        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] allBytes = inputStream.readAllBytes();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(reverseBytes(allBytes));

            outputStream.close();
            inputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拆分合并后的文件到两个文件
     * 时间复杂度：O(2n) n为file的字节大小
     *
     * @param file        合并后的文件
     * @param targetFront 正面文件
     * @param targetBack  反面文件
     * @author Lin
     */
    public static void divideFile(File targetFront, File targetBack,File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream frontStream = new FileOutputStream(targetFront);
            FileOutputStream backStream = new FileOutputStream(targetBack);

            byte[] allBytes = inputStream.readAllBytes();
            inputStream.close();

            int head = 0;
            int back = allBytes.length - 1;
            byte headByte = allBytes[head];
            byte backByte = allBytes[back];
            System.out.println("len:"+allBytes.length);
            while(back>head) {
                if(headByte==124) {
                    while(backByte!=124) {
                        System.out.println("head:"+head);
                        backStream.write(backByte);
                        backByte = allBytes[--back];
                    }
                    break;
                } else if(backByte==124) { // 124 为 “|”
                    System.out.println("back:"+back);
                    while(headByte!=124) {
                        frontStream.write(headByte);
                        headByte = allBytes[++head];
                    }
                    break;
                } else {
                    frontStream.write(headByte);
                    backStream.write(backByte);
                }
                headByte = allBytes[++head];
                backByte = allBytes[--back];
            }
            frontStream.close();
            backStream.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public static void divideFileRe(File file, File targetFront, File targetBack) {
//        try {
//            FileInputStream inputStream = new FileInputStream(file);
//            List list = Arrays.asList(inputStream.readAllBytes());
//
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }

}
