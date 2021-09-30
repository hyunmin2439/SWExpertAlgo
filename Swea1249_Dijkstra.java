package algo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

// Dijkstra 문제

// Memory:40,676KB / Time:206ms
public class Swea1249_Dijkstra {

	static final int INF = 100_000;
	
	static int T, N;
	static int[][] map, dp;
	
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	
	static PriorityQueue<Node> queue;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// N 최대 100, 각 Test Case 마다 새로 생성하지 않고 값을 덮어씀
		map = new int[100][100];
		dp = new int[100][100];
		queue = new PriorityQueue<>( (a, b) -> a.c - b.c );
		
		T = Integer.parseInt(in.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine());

			for (int y = 0; y < N; y++) {
				String line = in.readLine();
				for (int x = 0; x < N; x++) {
					map[y][x] = line.charAt(x) - '0';
					dp[y][x] = INF; // 전부 갈 수 없음으로 표시
				}
			}
			
			dijkstra();
			
			sb.append("#").append(t).append(" ").append(dp[N-1][N-1]).append("\n");
		}
		
		System.out.println(sb);
		in.close();
	}

	private static void dijkstra() {
		boolean[][] visit = new boolean[N][N];
		
		queue.clear();
		
		// 출발지점
		dp[0][0] = 0;
		visit[0][0] = true;
		queue.offer(new Node(0, 0, 0));
		
		while( !queue.isEmpty() ) {
			// step1. 방문하지 않은 정점 중 비용이 최소인 정점 선택
			// PriorityQueue는 Heap( O(NlogN) ) 자료구조로 이중 for문( O(N^2) )으로 탐색하는 것보다 유리 
			// Queue에서 꺼내면 값이 최소인 것을 꺼낸다.
			Node node = queue.poll();
			
			// 목표 위치에 도달했으면 더 이상 동작할 필요 없음
			if(node.y == N - 1 && node.x  == N - 1) return;
			
			// step2 : step1에서 선택된 정점을 경유지로 해서 인접정점(4방) 따져보기
			for (int d = 0; d < 4; d++) {
				int ny = node.y + dy[d];
				int nx = node.x + dx[d];
				
				// 좌표 체크 및 방문 체크
				if( !(0 <= ny && ny < N && 0 <= nx && nx < N) || visit[ny][nx] ) continue;
				
				// S : 출발지, V : 경유지, D : 도착점
				// (S -> D 최단거리) <= ( (S -> V 최단거리) + (V -> D 거리) ) 갱신할 필요 없다
				if(dp[ny][nx] <= node.c + map[ny][nx]) continue;
				
				// 방문 처리 및 최소 비용으로 갱신, 선택된 정점을 큐에 추가
				visit[ny][nx] = true;
				dp[ny][nx] = node.c + map[ny][nx];
				queue.add(new Node(ny, nx, dp[ny][nx]));
			}
		}
	}

	static class Node {
		int y, x, c;

		public Node(int y, int x, int c) {
			this.y = y;
			this.x = x;
			this.c = c;
		}
	}
}