package cn.wulinweb.hust.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class Convertor {
	private static final String TOKEN = "leocook";
	private static final String[] CHARSET = { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
			"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
			"T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9" };
	private final static String[] HEXDIGITS = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static List<String> longToShort(String longUrl) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		List<String> temp = null;
		List<String> list = null;
		
		temp = getSubByte(longUrl);
		list = getShortUrl(temp);

		return list;
	}

	/**
	 * 将长网址用md5算法生成32位签名串，分为4段,，每段8个字符
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	protected static List<String> getSubByte(String url)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		List<String> list = new LinkedList<String>();

		// 计算长地址的32位md5
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] md5 = messageDigest.digest((TOKEN + url).getBytes());
		String md5String = byteArrayToHexString(md5);

		// TODO: 测试使用的
		// System.out.println(md5String.getBytes().length);

		int length = md5String.length() / 8;
		// System.out.println(length);

		for (int i = 0; i < length; i++) {
			list.add(md5String.substring(i * 8, (i + 1) * 8));
		}

		return list;
	}

	/**
	 * 对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理
	 */
	protected static List<String> getShortUrl(List<String> list) {
		StringBuffer sb = null;
		List<String> shortUrl = new LinkedList<String>();

		for (String string : list) {
			sb = new StringBuffer();

			// 对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理
			long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(string, 16);

			// 将每段得到的这30位又分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串
			for (int k = 0; k < 6; k++) {
				int index = (int) (Long.valueOf("0000003D", 16) & idx);
				// outChars += chars[index];
				sb.append(CHARSET[index]);
				idx = idx >> 5;
			}

			// 这样一个md5字符串可以获得4个6位串，取里面的任意一个就可作为这个长url的短url地址
			shortUrl.add(new String(sb));
		}

		return shortUrl;
	}

	/**
	 * 将16位的md5转换为32位
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将16位的md5中的某一位字节转换为两字节
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return HEXDIGITS[d1] + HEXDIGITS[d2];
	}

}
