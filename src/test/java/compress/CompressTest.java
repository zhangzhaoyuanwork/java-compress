package compress;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import priv.dengjl.compress.util.CompressUtil;

public class CompressTest {

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		String data = generateString(1024 * 10);
		// System.out.println("压缩前数据内容：" + data);
		byte[] dataBytes = data.getBytes();
		int After=dataBytes.length;
		System.out.println("压缩前数据大小：" + dataBytes.length);

		CompressUtil[] values = CompressUtil.values();
		for (CompressUtil compressUtil : values) {
			System.out.println("===================: " + compressUtil.name());
			long start = System.currentTimeMillis();
			byte[] resultBytes = compressUtil.compress(dataBytes);
			int Before=dataBytes.length;
			System.out.println("压缩后数据大小:" + resultBytes.length);
			System.out.println("压缩时间(ms)：" + (System.currentTimeMillis() - start));


			long start2 = System.currentTimeMillis();
			byte[] uncompressBytes = compressUtil.uncompress(resultBytes);
			System.out.println("解压后数据大小：" + uncompressBytes.length);
			String result = new String(uncompressBytes);
			// System.out.println("解压后数据内容：" + result);
			System.out.println("解压时间(ms)：" + (System.currentTimeMillis() - start2));

			DecimalFormat decimalFormat = new DecimalFormat("##.00%");
			System.out.println("压缩率"+decimalFormat.format((float)After/ (float)Before));
			System.out.println("===================: " + compressUtil.name());
			System.out.println();
		}
	}

}
