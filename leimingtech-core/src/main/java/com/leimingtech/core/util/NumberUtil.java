package com.leimingtech.core.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isInteger(String str) {
		return true;
	}

	public static boolean isLong(String str) {
		return true;
	}

	public static double round(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private static long Seed = System.currentTimeMillis();

	private static Random rand = new Random();

	public static int getRandomInt(int max) {
		rand.setSeed(Seed);
		Seed++;
		return rand.nextInt(max);
	}

	public static int toInt(byte[] bs) {
		return toInt(bs, 0);
	}

	public static int toInt(byte[] bs, int start) {
		int i = 0;
		i += (bs[start] & 255) << 24;
		i += (bs[start + 1] & 255) << 16;
		i += (bs[start + 2] & 255) << 8;
		i += (bs[start + 3] & 255);
		return i;
	}

	public static byte[] toBytes(int i) {
		byte[] bs = new byte[4];
		bs[0] = (byte) (i >> 24);
		bs[1] = (byte) (i >> 16);
		bs[2] = (byte) (i >> 8);
		bs[3] = (byte) (i & 255);
		return bs;
	}

	public static void toBytes(int i, byte[] bs, int start) {
		bs[start] = (byte) (i >> 24);
		bs[start + 1] = (byte) (i >> 16);
		bs[start + 2] = (byte) (i >> 8);
		bs[start + 3] = (byte) (i & 255);
	}

	public static short toShort(byte[] bs) {
		return toShort(bs, 0);
	}

	public static short toShort(byte[] bs, int start) {
		short i = 0;
		i += (bs[start + 0] & 255) << 8;
		i += (bs[start + 1] & 255);
		return i;
	}

	public static byte[] toBytes(short i) {
		byte[] bs = new byte[2];
		bs[0] = (byte) (i >> 8);
		bs[1] = (byte) (i & 255);
		return bs;
	}

	public static void toBytes(short i, byte[] bs, int start) {
		bs[start + 0] = (byte) (i >> 8);
		bs[start + 1] = (byte) (i & 255);
	}

	public static byte[] toBytes(long l) {
		return Long.toString(l).getBytes();
	}

	public static void toBytes(long l, byte[] bs, int start) {
		byte[] arr = Long.toString(l).getBytes();
		for (int i = 0; i < 8; i++) {
			bs[start + i] = arr[i];
		}
	}

	public static long toLong(byte[] bs) {
		return toLong(bs, 0);
	}

	public static long toLong(byte[] bs, int index) {
		return ((((long) bs[index] & 0xff) << 56) | (((long) bs[index + 1] & 0xff) << 48) | (((long) bs[index + 2] & 0xff) << 40) | (((long) bs[index + 3] & 0xff) << 32) | (((long) bs[index + 4] & 0xff) << 24) | (((long) bs[index + 5] & 0xff) << 16) | (((long) bs[index + 6] & 0xff) << 8) | (((long) bs[index + 7] & 0xff) << 0));

	}
	/**
	 * ��������
	 */
	public static String mathS(float value,int weishu){
		String sm="";
		for(int i=0;i<=weishu;i++){
			if(i==1) sm +=".0";
			else sm +="0";
		}
		DecimalFormat df = new DecimalFormat(sm);
		return df.format(value);
	}
	/**
	 * ���ָ��λ�������
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length)     
	{     
	   String val = "";     
	            
	   Random random = new Random();     
	    for(int i = 0; i < length; i++)     
	    {     
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // �����ĸ��������      
	                 
	        if("char".equalsIgnoreCase(charOrNum)) // �ַ�      
	        {     
	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //ȡ�ô�д��ĸ����Сд��ĸ      
	            val += (char) (choice + random.nextInt(26));     
	        }     
	        else if("num".equalsIgnoreCase(charOrNum)) // ����      
	        {     
	            val += String.valueOf(random.nextInt(10));     
	        }     
	    }     
	             
	    return val;     
	}
	@Test
	public void test() {
		System.out.println(NumberUtil.mathS(10.1234f, 2));
		System.out.println(NumberUtil.getRandomInt(99999));
	}
}
