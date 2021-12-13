package algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*
* Memory:27,012KB / Time:185ms
*/

public class SWEA_5658_보물상자비밀번호 {

	static int T, N, K, numLen;
	static Set<Integer> set;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		set = new TreeSet<>( (a, b) -> b - a );
		
		T = Integer.parseInt(in.readLine());
		
		for(int t = 1; t <= T; t++) {
			set.clear();
			
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			String hex = in.readLine();
			numLen = N / 4; // 한변의 길이 - 숫자의 길이
			
			hex += hex.substring(0, numLen - 1);
			// N = 8, 12345678 -> 12345678'1'
			// -> 12 23 34 45 56 67 78 81
			// -> 회전하는 처리를 간단하게 하기 위해 끝부분에 앞숫자 추가
			
			int strLen = hex.length() - (numLen - 1);
			
			// Sliding Window 알고리즘 적용
			for(int i = 0; i < strLen; i++) {
				String hexNum = hex.substring(i, numLen + i);
				
				int decNum = convertHexToDec(hexNum);
				
				set.add(decNum);
			}
			
			//System.out.println( set );
			
			System.out.println( "#" + t  + " " + getKthNumber() );
		}
		
		in.close();
	}

	private static int getKthNumber() {
		int k = 0, kthNum = 0;
		
		for (Iterator<Integer> iterator = set.iterator(); k < K; k++) {
			kthNum = iterator.next();
		}
		
		return  kthNum;
	}

	private static int convertHexToDec(String hexNum) {
		int decNum = 0;
		
		for(int j = 0; j < numLen; j++) {
			char hexChar = hexNum.charAt(j);
			
			hexChar -= hexChar >= 65 ? ('A' - 10) : '0';
			
			decNum += hexChar * Math.pow(16, numLen - 1 - j);
		}
		
		return decNum;
	}
}
