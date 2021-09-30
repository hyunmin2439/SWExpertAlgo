package swea5643;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 네번째 풀이

// 최적화를 고민한 방법이 아닌, 중복된 코드 줄이는 방법

// 또한, 역 인접행렬을 만들어 해결하는 다른 아이디어를 제시

// Memory:102,480KB / Time:1,488ms
public class Swea5643_4_DFS_Refect1 {
	
	static int N, M;
	static int[][] adj, radj; // radj : 역 인접행렬
	static int cnt;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(in.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine());
			M = Integer.parseInt(in.readLine());
			adj = new int[N + 1][N + 1];
			radj = new int[N + 1][N + 1];
			
			StringTokenizer st = null;
			for (int m = 1; m <= M; m++) {
				st = new StringTokenizer(in.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				radj[to][from] = adj[from][to] = 1;
				// radj : to보다 from의 키가 작다.
				// adj : from보다 to의 키가 크다.
			}

			int ans = 0;
			for (int i = 1; i <= N; i++) {
				cnt = 0;
				dfs(i, adj, new boolean[N + 1]); // 자신보다 큰 학생 탐색
				dfs(i, radj, new boolean[N + 1]); // 자신보다 작은 학생 탐색
				
				if(cnt == N - 1) ++ans;
			}
			System.out.println("#" + t + " " + ans);
		}
		
		in.close();
	}
	
	// 자신보다 큰 학생따라 탐색
	private static void dfs(int cur, int[][] adj, boolean[] visited) {
		// 자기 자신 방문 처리
		visited[cur] = true;
		
		for (int i = 1; i <= N; i++) {
			if(!visited[i] && adj[cur][i] == 1) {
				++cnt; // 자신보다 큰 학생
				dfs(i, adj, visited);
			}
		}
	}
	
}
