package com.cti.vpx.controls;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Convert {

	public Convert() {

	}

	public static void convertIntoLong() {

		String str = "3.4585365E19";

		String val = String.format("%04X", Float.floatToIntBits(Float.parseFloat(str)));

		byte[] bArr = new byte[4];

		bArr[0] = (byte) Integer.parseInt(val.substring(0, 2), 16);

		bArr[1] = (byte) Integer.parseInt(val.substring(2, 4), 16);

		bArr[2] = (byte) Integer.parseInt(val.substring(4, 6), 16);

		bArr[3] = (byte) Integer.parseInt(val.substring(6, 8), 16);

		for (int i = 0; i < bArr.length; i++) {
			printBytes(bArr[i], i);
		}

		System.out.println();

		long s = bytesToLong(bArr);

		System.out.println(s);

		System.out.format("%04x\n", s);

	}
	
	public static void convertIntoByte() {

	/*	String str = "F4";
		
		byte b = (byte) 244;
		
		byte t = (byte) Integer.parseInt(str, 16);
		
		

		System.out.println((t == b));*/
		
		String str = "3.4585365E19";

		System.out.println(Float. parseFloat(str));
	}

	public static long bytesToLong(byte[] b) {
		
		long result = 0;
		
		for (int i = 0; i < 4; i++) {
		
			result <<= 8;
			
			result |= (b[i] & 0xFF);
		}
		
		return result;
	}

	public static void main(String[] args) {
		// byte[] b = readFileToByteArray("D:/array/array.txt", 0, 0);

		// for (int i = 0; i < b.length; i++) {

		// printBytes(b[i], i);
		// }

		convertIntoByte();

	//	readFile("D:/array/array.txt");
	}

	public static String readFile(String filename) {

		try {

			String content = new String(
					Files.readAllBytes(Paths.get(URI.create("file:///" + getPathAsLinuxStandard(filename)))));

			System.out.println(content);

			return content;

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
	}

	public static byte[] readFileToByteArray(String filename, int format, int delimiter) {

		String content = readFile(filename);

		String seperator = (delimiter == 0) ? System.lineSeparator() : ",";

		String[] arr = content.split(seperator);

		return getByteArray(arr, format);
	}

	private static byte[] getByteArray(String[] arr, int format) {

		ByteBuffer bb = null;

		byte[] b = new byte[arr.length * 4];

		bb = ByteBuffer.allocate(b.length);

		for (int i = 0; i < arr.length; i++) {

			String val = String.format("%08X", Float.floatToIntBits(Float.parseFloat(arr[i])));

			byte[] bArr = new byte[4];

			bArr[3] = (byte) Integer.parseInt(val.substring(0, 2), 16);

			bArr[2] = (byte) Integer.parseInt(val.substring(2, 4), 16);

			bArr[1] = (byte) Integer.parseInt(val.substring(4, 6), 16);

			bArr[0] = (byte) Integer.parseInt(val.substring(6, 8), 16);

			bb.put(bArr);
		}

		b = bb.array();

		return b;
	}

	private static void printBytes(byte byteVal, int i) {

		System.out.print(String.format("%02x ", byteVal));

		if (((i + 1) % 16) == 0) {

			System.out.println();
		}

	}

	public static String getPathAsLinuxStandard(String path) {

		return path.replaceAll("\\\\", "/");
	}
}
