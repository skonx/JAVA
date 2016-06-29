package fr.yla.tests.misc;

public class MyTestArray {

	private static String display(int[][] array){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< array.length ; i++){
			for(int j = 0 ; j < array[i].length ; j++)
				sb.append(array[i][j]+" ");
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int[][] array = new int[2][5];
		System.out.println(display(array));
	}

}
