package solved;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swea1263 {
	
	static final int infinity = 100_000;

	static int T, N, min;
	static int[][] adjMatrix;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			min = infinity;
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			adjMatrix = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int val = Integer.parseInt(st.nextToken());
					
					// i == j일 때도 infinity를 넣어버리면 나중에 total 계산할 때 에러
					if(val == 0 && i != j) adjMatrix[i][j] = infinity;
					else adjMatrix[i][j] = val;
				}
			}
			
			floyd();
			findMinValue();
			
			System.out.println("#" + t + " " + min);
		}
		br.close();
	}

	private static void floyd() {
		for (int k = 0; k < N; k++) { // 거쳐가는
			for (int i = 0; i < N; i++) { // from -> to
				if(k == i) continue;
				for (int j = 0; j < N; j++) {
					if(k == j || i == j) continue;
					adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
				}
			}
		}
	}
	
	private static void findMinValue() {
		int line;
		
		for (int i = 0; i < N; i++) {
			line = 0;
			
			for (int j = 0; j < N; j++) {
				line += adjMatrix[i][j];
			}
			
			min = Math.min(min, line);
		}
	}

}