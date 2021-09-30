package swea5643;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 세번째 풀이 : DFS

// Memory:100,732KB / Time:1,711ms
public class Swea5643_3_DFS {
	
	static int N, M;
	static int[][] adj;
	static int gtCnt, ltCnt; // 정확히 세는지 보기 위해 count 변수 따로 둠
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(in.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine());
			M = Integer.parseInt(in.readLine());
			adj = new int[N + 1][N + 1];
			
			StringTokenizer st = null;
			for (int m = 1; m <= M; m++) {
				st = new StringTokenizer(in.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				adj[from][to] = 1; // from보다 to의 키가 크다.
			}

			int ans = 0;
			for (int i = 1; i <= N; i++) {
				gtCnt = ltCnt = 0;
				gtDFS(i, new boolean[N + 1]);
				ltDFS(i, new boolean[N + 1]);
				if(gtCnt + ltCnt == N - 1) ++ans;
			}
			System.out.println("#" + t + " " + ans);
		}
		
		in.close();
	}
	
	// 자신보다 큰 학생따라 탐색
	private static void gtDFS(int cur, boolean[] visited) {
		// 자기 자신 방문 처리
		visited[cur] = true;
		
		for (int i = 1; i <= N; i++) {
			if(!visited[i] && adj[cur][i] == 1) {
				++gtCnt; // 자신보다 큰 학생
				gtDFS(i, visited);
			}
		}
	}
	
	// 자신보다 작은 학생을 따라 탐색
	private static void ltDFS(int cur, boolean[] visited) {
		// 자기 자신 방문 처리
		visited[cur] = true;
		
		for (int i = 1; i <= N; i++) {
			if(!visited[i] && adj[i][cur] == 1) {
				++ltCnt; // 자신보다 큰 학생
				ltDFS(i, visited);
			}
		}
	}
	
}
