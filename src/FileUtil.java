import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
            targetStream.close();
            frontInputStream.close();

            targetStream = new FileOutputStream(targetFile, true);
            FileInputStream backStream = new FileInputStream(backFile);
            byte[] backAllBytes = backStream.readAllBytes();
            reverseBytes(backAllBytes);

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


}
