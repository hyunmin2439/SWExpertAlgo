package algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* 
 * DFS를 이용한 풀이 방법
 * 
 * 가지치기 조건을 찾지 못해 DFS로 못 풀 것 같았으나,
 * 
 * 확률 계산은 진행할 수록 값이 작아질 수 밖에 없다.
 * 
 * 때문에 가지치기 조건을 찾으니 시간안에 풀린다.
 * 
 * Memory:22,640KB / Time:1,931ms
 */

public class SWEA_1865_동철이의일분배_1_DFS {

	static int T, N;
	static double maxProbOfSuccess;
	static int[][] jobTable;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		T = Integer.parseInt(in.readLine());
		
		for(int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine());
			
			jobTable = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(in.readLine());
				for(int j = 0; j < N; j++) {
					jobTable[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			maxProbOfSuccess = 0.0;
			
			dfs(0, 0, 1.0);
			
			// 출력값은 이렇게 고정할 것.
			maxProbOfSuccess = Math.round(maxProbOfSuccess * 100_000_000) / 1_000_000.0; // 소수점 6자리까지 표현 + 백분률 => 10^8 / 10^6
			
			System.out.println("#" + t + " " + String.format("%.6f", maxProbOfSuccess));
		}
		
		in.close();
	}

	private static void dfs(int idx, int flag, double probOfSuccess) {
		if(maxProbOfSuccess >= probOfSuccess) return; // 이후 계속 진행하더라도 현재 값보다 더 큰 값이 나올 수 없음
													  // 소수점 연산. 최대 곱하는 값 1.0 => 점점 더 값이 작아짐
		
		if(idx == N) {
			maxProbOfSuccess = Math.max(maxProbOfSuccess, probOfSuccess);
			return;
		}
		
		for(int i = 0; i < N; i++) {
			if( (flag & 1 << i) != 0 ) continue;
			
			dfs(idx + 1, flag | 1 << i, probOfSuccess * 0.01 * jobTable[idx][i]);
		}
	}

}
