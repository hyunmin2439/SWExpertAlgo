package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Swea3289 {

	// T(테스트 케이스) N(집합 수) M(연산의 수)
	static int T, N, M;
	static int[] parent;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		
		int t = 0;
		while(t++ < T) {
			sb.append("#").append(t).append(" ");
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			make();
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int c = Integer.parseInt(st.nextToken()); // 연산
				int a = Integer.parseInt(st.nextToken()); // a집합
				int b = Integer.parseInt(st.nextToken()); // b집합
				
				if(c == 0) union(a, b);
				else sb.append( find(a) == find(b) ? 1 : 0 );
			}
			
			sb.append("\n");
		}
		bw.write(sb.toString());
		br.close();
		bw.close();
	}

	private static void make() {
		parent = new int[N + 1];
		
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}
	}
	
	private static int find(int num) {
		if(num == parent[num]) return num;
		else return parent[num] = find(parent[num]);
	}
	
	private static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		if(aRoot == bRoot) return false;
		parent[bRoot] = aRoot;
		return true;
		
	}
}
