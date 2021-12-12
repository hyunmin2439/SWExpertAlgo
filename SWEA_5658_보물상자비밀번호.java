package algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class SWEA_5658_보물상자비밀번호 {

	static int T, N, M;
	static Set<Integer> set;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		set = new TreeSet<>();
		
		T = Integer.parseInt(in.readLine());
		
		for(int t = 1; t <= T; t++) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			String hexa = in.readLine();
			int numLen = N / 4;
			
			hexa += hexa.substring(0, numLen - 1);
			
			int strLen = hexa.length() - numLen - 1;
			for(int i = 0; i < strLen; i++) {
				String hexaNum = hexa.substring(i, numLen + i);
				
				int decNum = 0;
				for(int j = numLen - 1; j >= 0; j--) {
					
				}
			}
		}
		
		
		System.out.println(set);
		
		in.close();
	}

}
