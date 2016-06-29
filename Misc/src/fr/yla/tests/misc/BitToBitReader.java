package fr.yla.tests.misc;

import java.util.Scanner;

public class BitToBitReader {

	private BitToBitReader() {
		//Garbage constructor : class cannot be instanciated
	}

	public static String displayInt(int value){

		StringBuilder sb = new StringBuilder();

		for (int i = Integer.SIZE-1; i >= 0 ; i--)
			sb.append(((value>>i)&0x01)+""+((i%Byte.SIZE == 0)?" ":""));
		return sb.toString();

	}

	public static byte[] convertToBytes(int value){
		long start = System.nanoTime();

		byte[] bytes = new byte[Integer.BYTES];

		for (int i = Integer.BYTES-1 ; i >= 0  ; i--)
			bytes[i] = (byte) ((value >> i*Byte.SIZE) & 0xFF);

		System.out.println(System.nanoTime()-start+" ns");

		return bytes;
	}

	public static byte[] convertToBytesReadingBits(int value){
		long start = System.nanoTime();
		byte[] bytes = new byte[Integer.BYTES];
		byte buffer1 = 0;
		byte buffer2 = 0;

		for (int i = Integer.BYTES-1 ; i >= 0  ; i--){
			buffer1 = (byte) ((value >> i*Byte.SIZE) & 0xFF);
			for(int j = 0 ; j < Byte.SIZE ; j++){
				buffer2 |= (byte) (buffer1 & (0x01<<j));
			}
			bytes[i] = buffer2;
			buffer2 = 0;
			buffer1 = 0;
		}
		System.out.println(System.nanoTime()-start+" ns");
		return bytes;
	}

	public static String displayBytes(byte[ ] bytes){

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i<bytes.length; i++){
			sb.append("\n\t"+i+"-[");
			for(int j = Byte.SIZE-1 ; j >=0 ; j--)
				sb.append((bytes[i]>>j)&0x01);
			sb.append("] ");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		String message = "Enter a value to translate in range ["+Integer.MAX_VALUE+" / "+Integer.MIN_VALUE+"] :";

		System.out.print(message);

		try(Scanner scanner = new Scanner(System.in)){
			while(scanner.hasNext()){
				String value = scanner.nextLine();
				System.out.println("Normal representation : "+displayInt(Integer.parseInt((value))));
				System.out.println("Byte Array representation using convertToBytesReadingBits method: "+displayBytes(convertToBytesReadingBits(Integer.parseInt((value)))));
				System.out.println("Byte Array representation using convertToBytes method: "+displayBytes(convertToBytes(Integer.parseInt((value)))));
				System.out.print(message);
			}

		}catch(Exception e){
			System.out.println("\n-- Bye --");
		}
	}

}
