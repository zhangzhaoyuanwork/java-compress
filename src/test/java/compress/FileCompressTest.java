package compress;

import priv.dengjl.compress.util.CompressUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class FileCompressTest {

    public String fileRead() throws Exception {
        //自己定义自己的文件路径
        String FileUrl="E:\\111.xml";
        File file = new File(FileUrl);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }

//    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateString(int length,String allChar) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String allChar = null;
        try {
            allChar=new FileCompressTest().fileRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("压缩前数据：/n"+allChar);


        int Before=1024 * 1000;
        String data = generateString(Before,allChar);
        // System.out.println("压缩前数据内容：" + data);
        byte[] dataBytes = data.getBytes();
        System.out.println("压缩前数据大小：" + dataBytes.length);

        CompressUtil[] values = CompressUtil.values();
        for (CompressUtil compressUtil : values) {
            System.out.println("===================: " + compressUtil.name());
            long start = System.currentTimeMillis();
            byte[] resultBytes = compressUtil.compress(dataBytes);

            int After=resultBytes.length;
            System.out.println("压缩后数据大小:" + After);
            System.out.println("压缩时间(ms)：" + (System.currentTimeMillis() - start));


            long start2 = System.currentTimeMillis();
            byte[] uncompressBytes = compressUtil.uncompress(resultBytes);
            System.out.println("解压后数据大小：" + uncompressBytes.length);
            System.out.println("解压时间(ms)：" + (System.currentTimeMillis() - start2));

            DecimalFormat decimalFormat = new DecimalFormat("##.00%");
            System.out.println("压缩率"+decimalFormat.format((float)After/ (float)Before));


            String result = new String(uncompressBytes);
            // System.out.println("解压后数据内容：" + result);

            System.out.println("===================: " + compressUtil.name());
            System.out.println();
        }
    }

}
