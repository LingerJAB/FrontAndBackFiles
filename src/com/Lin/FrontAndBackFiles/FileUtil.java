package com.Lin.FrontAndBackFiles;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileUtil {

    //分割标记  {'|','|','|','|','|'}
    //若文件损坏请加长数组
    private static final byte[] MARK={124,124,124,124,124};


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
        long time= System.currentTimeMillis();
        try {
            if(!targetFile.exists()) targetFile.createNewFile();
            FileOutputStream targetStream = new FileOutputStream(targetFile);
            FileInputStream frontInputStream = new FileInputStream(frontFile);
            byte[] frontAllBytes = frontInputStream.readAllBytes();

            targetStream.write(frontAllBytes);
            targetStream.write(MARK); // 分隔符：|
            targetStream.close();
            frontInputStream.close();

            targetStream = new FileOutputStream(targetFile, true);
            FileInputStream backStream = new FileInputStream(backFile);
            byte[] backAllBytes = backStream.readAllBytes();

            reverseBytes(backAllBytes);

            targetStream.write(MARK); // 分隔符：|
            targetStream.write(backAllBytes);
            backStream.close();
            targetStream.close();

            System.out.printf("%s 合并成功！  用时%dms\n", targetFile.getName(), System.currentTimeMillis() - time);
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
        long time= System.currentTimeMillis();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] allBytes = inputStream.readAllBytes();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(reverseBytes(allBytes));

            outputStream.close();
            inputStream.close();
            System.out.printf("%s 转换成功！  用时%dms\n", file.getName(), System.currentTimeMillis() - time);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拆分合并后的文件到两个文件
     * 时间复杂度：O(n^2+2n) n为file的字节大小
     *
     * @param file        合并后的文件
     * @param targetFront 正面文件
     * @param targetBack  反面文件
     * @author Lin
     */
    //TODO 多线程
    public static void divideFile(File targetFront, File targetBack, File file) {
        long time= System.currentTimeMillis();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream frontStream = new FileOutputStream(targetFront);
            FileOutputStream backStream = new FileOutputStream(targetBack);

            // 文件分割解析
            byte[] allBytes = inputStream.readAllBytes();
            int markHead = kmpSearch(allBytes, MARK);
            int markTail=markHead+2*MARK.length-1;

            // 结果写入
            for(int i = 0; i<markHead; i++) frontStream.write(allBytes[i]);
            for(int i = allBytes.length-1; i>markTail; i--) backStream.write(allBytes[i]);

            backStream.close();
            frontStream.close();
            inputStream.close();

            System.out.printf("%s 分离成功！  用时%dms\n", file.getName(), System.currentTimeMillis() - time);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * KMP搜索子数组算法
     * @param bytes 原数组
     * @param pattern 搜寻数组
     * @return 子数组索引
     */
    private static int kmpSearch(byte[] bytes,byte[] pattern){
        //初始化next数组
        int[] next1 = new int[pattern.length];
        next1[0] = -1;
        int i1 = 0;
        int j1 = -1;
        //获取匹配表
        while(i1< pattern.length - 1){
            if(j1== -1 || pattern[i1] == pattern[j1]){
                i1++;
                j1++;
                //如果当前字符相同，则next[i] = next[j]
                if(pattern[i1] == pattern[j1]){
                    next1[i1] = next1[j1];
                }else{
                    next1[i1] = j1;
                }
            }else{
                j1 = next1[j1];
            }
        }
        int i = 0;
        int j = 0;
        //遍历查找
        while(i < bytes.length && j < pattern.length){
            if(j == -1 || bytes[i] == pattern[j]){
                i++;
                j++;
            }else{
                //查找失败，查找匹配表
                j = next1[j];
            }
        }
        //匹配成功
        if(j == pattern.length){
            return i - j;
        }else{
            return -1;
        }
    }

    /**
     * 打印字节符号
     * @param file 字节文件
     */
    public static void printBytes(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            byte[] allBytes = stream.readAllBytes();
            stream.close();
            System.out.println(Arrays.toString(allBytes));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
