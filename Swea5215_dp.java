package solved;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swea5215_dp {
	
	static int T;
	static int N, L; // 재료 수, 제한 칼로리
	static int[][] ingred; // 0 : 맛점수 1 : 칼로리
	static int[][] dp;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			ingred = new int[N + 1][2];
			dp = new int[N + 1][L + 1];
			
			for (int i = 1; i <= N; i++) {
				st = new StringTokenizer(br.readLine());
				ingred[i][0] = Integer.parseInt(st.nextToken());
				ingred[i][1] = Integer.parseInt(st.nextToken());
			}
			
			for (int i = 1; i <= N; i++) {
				for (int w = 1; w <= L; w++) {
					if(ingred[i][1] > w) dp[i][w] = dp[i - 1][w];
					else dp[i][w] = Math.max(ingred[i][0] + dp[i - 1][ w - ingred[i][1] ], dp[i - 1][w]);
				}
			}
			
			System.out.println("#" + t + " " + dp[N][L]);
		}

		br.close();
	}

}
