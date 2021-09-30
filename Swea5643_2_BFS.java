package swea5643;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 두번째 풀이 : BFS

// 1보다 큰 학생따라 탐색 => 1보다 작은 학생따라 탐색
// 	카운팅		    카운팅
// 1보다 큰 학생수     +    1보다 작은학생수 = N - 1  자신의 키가 몇번째인지 알 수 있다.
// 	진출차수               진입차수

// Memory:107,492KB / Time:2,293ms
public class Swea5643_2_BFS {
	
	static int N, M;
	static int[][] adj;
	
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
				// 자신보다 큰 학생수 + 자신보다 작은 학생수 == N - 1
				if(gtBFS(i) + ltBFS(i) == N - 1) ++ans;
			}
			System.out.println("#" + t + " " + ans);
		}
		
		in.close();
	}
	
	// 자신보다 큰 학생따라 탐색
	private static int gtBFS(int start) {
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[N + 1];
		
		queue.offer(start);
		visited[start] = true;
		
		int cnt = 0;
		while( !queue.isEmpty() ) {
			int cur = queue.poll();
			
			for (int i = 1; i <= N; i++) {
				if(!visited[i] && adj[cur][i] == 1) {
					queue.offer(i);
					visited[i] = true;
					++cnt;
				}
			}
		}
		return cnt;
	}
	
	// 자신보다 작은 학생을 따라 탐색
	private static int ltBFS(int start) {
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[N + 1];
		
		queue.offer(start);
		visited[start] = true;
		
		int cnt = 0;
		while( !queue.isEmpty() ) {
			int cur = queue.poll();
			
			for (int i = 1; i <= N; i++) {
				if(!visited[i] && adj[i][cur] == 1) {
					queue.offer(i);
					visited[i] = true;
					++cnt;
				}
			}
		}
		return cnt;
	}
}
