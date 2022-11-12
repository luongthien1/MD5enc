package MD5Package;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

public class md5 {
	public static String byteToBits(byte b) {
		StringBuilder binary = new StringBuilder("");
		if (b==0) return "00000000";
		
		for (int i=0; i<8; i++) {
			if ((b & 128) == 0) 
				binary.append(0);
			else 
				binary.append(1);
			b <<= 1;
		}
		return binary.toString();
	}
	public static String MD5(String mess) {
		var g = mess.getBytes();
		g = Padding(g);
		StringBuilder[] MessBinary = new StringBuilder[(int) Math.ceil(g.length / 64)];
		
		for (int i = 0; i < MessBinary.length; i++) {
			String temp = "";
			for (int j=i*64; j<(i+1)*64; j++) {
				temp+=byteToBits(g[j]);
			}
			MessBinary[i] = new StringBuilder(temp);
		}
		
		int[] s = new int[64];
		s = inputS();
		int[] k = new int[64];
		k = inputK();
		
		int a0 = 0x67452301;   // A
		int b0 = 0xefcdab89;   // B
		int c0 = 0x98badcfe;   // C
		int d0 = 0x10325476;   // D
		
		for (int i=0; i<MessBinary.length; i++) {
			int[] M = new int[16];
			M = plitM(MessBinary[i]);
			int A = a0;
			int B = b0;
			int C = c0;
			int D = d0;
			
			for (int j=0; j< 64; j++) {
				int F = 0,G = 0;
				if (0<=j && j<16) {
					F = (B & C) | ((~B) & D);
					G = j;
				}
				else if (16<=j && j<32) {
					F = (D & B) | ((~D) & C);
					G = (5*j+1)%16;
				}
				else if (32<=j && j<48) {
					F = B ^ C ^ D;
		            G = (3*j + 5) % 16;
				}
				else if (48<=j && j<64) {
					F = C ^ (B | (~ D));
		            G = (7*j) % 16;
				}

				F = (int) (F + A + k[j] + M[G]); // M[g] must be a 32-bits block
		        A = D;
		        D = C;
		        C = B;
		        B = B + leftrotate(F, s[j]);
			}
			a0 = a0 + A;
		    b0 = b0 + B;
		    c0 = c0 + C;
		    d0 = d0 + D;
		}
		
		String rs = toHexDigit(a0) + toHexDigit(b0)+toHexDigit(c0)+toHexDigit(d0);
		return rs;
	}
	public static String toHexDigit(int num) {
		String a = Integer.toHexString(num);
		while (a.length()<8) a = "0"+a;
		return a;
	}
	public static int leftrotate(int F, int shift) {
		return F<<shift;
	}
	public static int[] plitM(StringBuilder messBinary) {
		int[] m = new int[16];
		for (int i=0; i<16; i++) {
			String binary32 = messBinary.substring(i*32, (i+1)*32);
			m[i] =(int) Long.parseLong(binary32,2);
		}
		return m;
	}
	public static int[] inputS() {
		int[] s = {7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
				5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
				4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
				6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21};
		return s;
	}
	public static int[] inputK() {
		int[] k = { 0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
				0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
				0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
				0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
				0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
				0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
				0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
				0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
				0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
				0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
				0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
				0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
				0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
				0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
				0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
				0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391};
		return k;
	}
	public static byte[] Padding(byte[] b) {
		byte[] newb;
		if (b.length%64 < 55) 
			newb = new byte[(int) Math.ceil((double)b.length/64)*64];
		else newb = new byte[(int) Math.ceil(b.length/64)*64 +64];

		for (int i = 0; i < b.length; i++)
	           newb[i] = b[i];
	   
		
		newb[b.length] = 1;
		for (int i=b.length+1; i<newb.length-8;i++) {
			newb[i] = 0;
		}
		int blen = (int) (b.length % Math.pow(10, 9)); 
		int j = 0;
		while (Math.pow(10,j)<blen) j++; 
		for (int i=newb.length-1; i>=newb.length-8; i--) {
			if (i<newb.length-j)
				newb[i] =  0;
			else {
				newb[i] = (byte) ((blen %10) +48); 
				blen /= 10;
			}
		}
		
		return newb;
		
	}
}
