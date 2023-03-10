import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String frontName = "avatar.jpg";
        String backName = "fake_avatar.jpg";
        String targetName = "result.jpg";

        String frontPath = new File(".").getCanonicalPath() + "/src/" + frontName;
        String backPath = new File(".").getCanonicalPath() + "/src/" + backName;
        String targetPath = new File(".").getCanonicalPath() + "/src/" + targetName;
        File frontFile = new File(frontPath), backFile = new File(backPath), targetFile = new File(targetPath);

//        FileUtil.mergeFile(frontFile,backFile,targetFile);

        String frontMd5 = FileUtil.getMd5(targetFile);
        System.out.println("origin: " + frontMd5);

        FileUtil.reverseFile(targetFile);
        System.out.println("out: " + FileUtil.getMd5(targetFile));

    }

}
