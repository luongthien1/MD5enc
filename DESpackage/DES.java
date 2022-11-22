package DESpackage;

import java.math.BigInteger;
import java.util.Arrays;

public class DES {
	public static String key = "0133457799bbcdff";
	String Ci;
	String Di;
	int[][] S;
	int[] IIP;
	String[] Roundkey;
	
	public static void main(String[] args) {
		try {
			System.out.println(DES.Encryption("00123456789abcde"));
			System.out.println(DES.Decryption("1abff69d5a93e80b"));
			while(true) {
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String Encryption(String text) throws Exception {
		DES Aen = new DES();
		return Aen.Encrypt(text);
	}
	public static String Decryption(String Hextext) throws Exception {
		return (new DES()).Decrypt(Hextext);
	}
	public DES() throws Exception {
		S =new int[8][];
		S[0] = new int[]{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
						0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
						4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
						15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13};
		S[1] = new int[]{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
						3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
						0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
						13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9};
		S[2] = new int[]{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
						13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
						13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
						1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12};
		S[3] = new int[]{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
						13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
						10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
						3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14};
		S[4] = new int[]{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
						14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
						4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
						11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3};
		S[5] = new int[]{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
						10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
						9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
						4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13};
		S[6] = new int[]{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
						13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
						1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
						6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12,};
		S[7] = new int[]{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
						1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
						7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
						2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11,};
		IIP = new int[]{40,8,48,16,56,24,64,32,
						39,7,47,15,55,23,63,31,
						38,6,46,14,54,22,62,30,
						37,5,45,13,53,21,61,29,
						36,4,44,12,52,20,60,28,
						35,3,43,11,51,19,59,27,
						34,2,42,10,50,18,58,26,
						33,1,41,9,49,17,57,25};
		
		String PC1key = PermutedChoice1(key);
		Ci = PC1key.substring(0,28);
		Di = PC1key.substring(28,56);
		Roundkey = new String[16];
		for (int i=0; i<Roundkey.length; i++)
			Roundkey[i] = RoundKey(i);
	}
	public String Decrypt(String HexText) throws Exception {
		int[] IP = inputIP();

		String permutedText = permuteWithIP(IP, new BigInteger(HexText,16));
		HexText = "";
		String L = permutedText.substring(0, 32);
		String R = permutedText.substring(32, 64);

		BigInteger BR = new BigInteger(R,2);
		BigInteger BL = new BigInteger(L,2);

		for (int j=0; j<16; j++) {
			BigInteger Ki = new BigInteger(Roundkey[15-j],2);
			BigInteger temp = BL;
			BL = BR;
			BR = temp.xor(EncrytionF(BR,Ki));
		}
		L = BL.toString(2);
		while (L.length()<32) L="0"+L;
		R = BR.toString(2);
		while (R.length()<32) R="0"+R;
		BigInteger toHec = new BigInteger(permuteWithInverseIP(IIP, R+L),2);
		String temp = toHec.toString(16);
		while (temp.length()<16) temp = "0" + temp;
		HexText = temp;
		return HexText;
		
	}
	public String Encrypt(String text) throws Exception {
		int[] IP = inputIP();
		

		String permutedText = permuteWithIP(IP, new BigInteger(text,16));
		text = "";
		String L = permutedText.substring(0, 32);
		String R = permutedText.substring(32, 64);

		BigInteger BR = new BigInteger(R,2);
		BigInteger BL = new BigInteger(L,2);

		for (int j=0; j<16; j++) {
			BigInteger Ki = new BigInteger(Roundkey[j],2);
			BigInteger temp = BL;
			BL = BR;
			BR = temp.xor(EncrytionF(BR,Ki));
		}
		L = BL.toString(2);
		while (L.length()<32) L="0"+L;
		R = BR.toString(2);
		while (R.length()<32) R="0"+R;
		BigInteger toHec = new BigInteger(permuteWithInverseIP(IIP, R+L),2);
		String temp = toHec.toString(16);
		while (temp.length()<16) temp = "0" + temp;
		text = temp;
		return text;
	}
	public String permuteWithInverseIP(int[] IIP, String LR) {
		String result = "";
		for (int i=0; i<IIP.length; i++) {
			result += LR.charAt(IIP[i]-1);
		}
		return result;
	}
	public BigInteger EncrytionF(BigInteger R, BigInteger K) throws Exception {
		String Binary = R.toString(2);
		while (Binary.length()<32) Binary = "0"+Binary;
		String a = ESelector(Binary);
		R = new BigInteger(a,2);
		BigInteger RExK = R.xor(K);
		Binary = RExK.toString(2);
		while (Binary.length()<48) Binary = "0"+Binary;
		String result = "";
		for (int i=0; i<8; i++) {
			String subi = Binary.substring(i*6,(i+1)*6);
			int row = Integer.parseInt(String.valueOf(subi.charAt(0))+subi.charAt(5),2);
			int column = Integer.parseInt(subi.substring(1, 5),2);
			
			String temp = Integer.toBinaryString(S[i][row*16+column]);
			while (temp.length()<4) temp = "0" + temp;
			result += temp;
		}
		result = PermuteP(result);
		return new BigInteger(result,2);
		
	}
	public String PermutedChoice1(String key) throws Exception {
		String text = "";
		text = (new BigInteger(key, 16)).toString(2);
		while (text.length()<64) text = "0"+text;
		int[] PC1table = {57,49,41,33,25,17,9 ,
						1 ,58,50,42,34,26,18,
						10,2 ,59,51,43,35,27,
						19,11,3 ,60,52,44,36,
						63,55,47,39,31,23,15,
						7 ,62,54,46,38,30,22,
						14,6 ,61,53,45,37,29,
						21,13,5 ,28,20,12,4 };
		String result = "";
		for (int i=0; i<56; i++) {
			result += text.charAt(PC1table[i]-1);
		}
		return result;
	}
	public String PermuteP(String Binary) {
		int[] Ptable = {16,7,20,21,29,12,28,17,
						1,15,23,26,5,18,31,10,
						2,8,24,14,32,27,3,9,
						19,13,30,6,22,11,4,25};
		String result="";
		for (int i=0; i<Ptable.length; i++) {
			result += Binary.charAt(Ptable[i]-1);
		}
		return result;
	}
	public String ESelector (String bits) throws Exception {
		if (bits.length()!=32) throw new Exception("Error in ESelector function: input string ("+bits.length()+") must be 32");
		int[] E = {	32,1 ,2 ,3 ,4 ,5 ,4 ,5 ,6 ,7 ,8 ,9 ,
					8 ,9 ,10,11,12,13,12,13,14,15,16,17,
					16,17,18,19,20,21,20,21,22,23,24,25,
					24,25,26,27,28,29,28,29,30,31,32,1};
		String result = "";
		for (int i=0; i<48;i++) {
			result += bits.charAt(E[i]-1);
		}
		return result;
	}
	public String permuteWithIP(int[] IP, BigInteger text) {
		String textToBit = text.toString(2);
		while (textToBit.length()<64) textToBit = "0" + textToBit;
		String result="";
		for (int i=0; i<textToBit.length(); i++) {
			result += textToBit.charAt(IP[i]-1);
		}
		return result;
	}
	public int[] inputIP() {
		int[] IP = {58, 50, 42, 34, 26, 18, 10, 2,	
					60, 52, 44, 36, 28, 20, 12, 4,
					62, 54, 46, 38, 30, 22, 14, 6,
					64, 56, 48, 40, 32, 24, 16, 8,
					57, 49, 41, 33, 25, 17, 9, 1,
					59, 51, 43, 35, 27, 19, 11, 3,
					61, 53, 45, 37, 29, 21, 13, 5,
					63, 55, 47, 39, 31, 23, 15, 7};
		return IP;
	}
	public String RoundKey(int index) {
		int[] ShiftTable = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

		Ci = Ci.substring(ShiftTable[index]) + Ci.substring(0,ShiftTable[index]);
		Di = Di.substring(ShiftTable[index]) + Di.substring(0,ShiftTable[index]);

		return PermutedChoice2(Ci+Di);
	}
	public String PermutedChoice2(String CiDi) {
		int[] PC2table = {14,17,11,24,1 ,5 ,
						3 ,28,15,6 ,21,10,
						23,19,12,4 ,26,8 ,
						16,7 ,27,20,13,2 ,
						41,52,31,37,47,55,
						30,40,51,45,33,48,
						44,49,39,56,34,53,
						46,42,50,36,29,32};
		String result = "";
		for (int i=0; i<PC2table.length; i++) {
			result += CiDi.charAt(PC2table[i]-1);
		}
		return result;
	}
	public String byteToBits(byte b) {
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
	public static void Println(Object a) {
		System.out.println(a);
	}
}
