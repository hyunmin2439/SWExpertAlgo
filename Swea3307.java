package solved;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swea3307 {

	static int T, N, max;
	static int[] arr;
	static int[] lis;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		arr = new int[1000];
		lis = new int[1000];
		
		for (int t = 1; t <= T; t++) {
			max = 0;
			N = Integer.parseInt(br.readLine());
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				lis[i] = 1;
				
				findLis(i);
			}
			
			System.out.println("#" + t + " " + max);
		}
		
		br.close();
	}

	private static void findLis(int i) {
		for (int j = 0; j < i; j++) {
			if(arr[j] < arr[i] && lis[j] + 1 > lis[i]) { // LIS[j] >= LIS[i]
				lis[i] = lis[j] + 1;
			}
		}
		
		max = Math.max(max, lis[i]);
	}

}
