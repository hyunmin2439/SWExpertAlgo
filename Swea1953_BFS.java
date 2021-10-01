package solved;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author hyunminKim
 * 
 * BFS 풀이 방식 선택
 * 
 * DFS보다는 BFS가 더 적절한 문제
 * 
 * DFS로 풀이시 visit 체크 조건 처리가 까다로워짐
 * 
 * 때문에 dfs방식 보다 BFS 방식이 더 간편
 * 
 * Memory:35,312KB / Time:126ms
 */
public class Swea1953_BFS {

	static int T, N, M, sY, sX, L, res;
	
	static int[][] map;
	static boolean[][] visited;
	
	static Queue<Node> queue;
	
	//				0. dummy  1.상 하 좌 우  2.상 하 3. 좌 우 4.상 우 5.하 우  6.하  좌  7.상 좌
	static int[][] dy = { {}, {-1, 1, 0, 0}, {-1, 1}, { 0, 0}, {-1, 0}, {1, 0}, {1,  0}, {-1,  0} };
	static int[][] dx = { {}, {0, 0, -1, 1}, { 0, 0}, {-1, 1}, { 0, 1}, {0, 1}, {0, -1}, { 0, -1} };
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		queue = new LinkedList<>();
		
		T =Integer.parseInt(in.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken()); // 세로 크기
			M = Integer.parseInt(st.nextToken()); // 가로 크기
			sY = Integer.parseInt(st.nextToken()); // 맨홀 세로 위치
			sX = Integer.parseInt(st.nextToken()); // 맨홀 가로 위치
			L = Integer.parseInt(st.nextToken()); // 소요된 시간
			
			// 맵 입력받기
			map = new int[N][M];
			visited = new boolean[N][M];
			
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(in.readLine());
				for (int x = 0; x < M; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			
			res = 0;
			bfs();
			
			System.out.println("#" + t + " " + res);
		}
		
		in.close();
	}

//	너비 우선 탐색을 하면서 제한 시간동안 이동할 수 있는 위치 탐색
	private static void bfs() {
		queue.clear();
		
		visited[sY][sX] = true; // 방문 체크
		res++; // 갈수 있는 장소 개수 +1
		queue.offer(new Node(sY, sX, map[sY][sX], L - 1)); // 처음 맨홀로 들어갈 때 시간 1소요
		
		while( !queue.isEmpty() ) {
			Node n = queue.poll();
			
//			시간이 다 되면 더 이상 가지 못함
			if(n.l == 0) continue;
			
//			터널 구조물 종류에 따라 반복 횟수 다름
			for (int d = 0; d < dy[n.i].length; d++) {
				int ny = n.y + dy[n.i][d];
				int nx = n.x + dx[n.i][d];

//				좌표체크 및 방문체크
				if (!(0 <= ny && ny < N && 0 <= nx && nx < M) || visited[ny][nx]) continue;

//				터널이 연결되어 있지 않다면
				if (!isConnected(n.y, n.x, ny, nx)) continue;
				
				res++; // 갈수 있는 장소 개수 +1
				visited[ny][nx] = true; // 방문 체크
				queue.offer(new Node(ny, nx, map[ny][nx], n.l - 1));
			}

		}
	}

	/** 두 터널이 연결되어 있는지 여부를 true / false로 반환
	 * @param y : 현재 터널 y좌표
	 * @param x : 현재 터널 x좌표
	 * @param ny : 이동하려는 터널 y좌표
	 * @param nx : 이동하려는 터널 x좌표
	 * @return : 연결 여부
	 */
	private static boolean isConnected(int y, int x, int ny, int nx) {
		int i = map[ny][nx];
		
		for (int d = 0; d < dy[i].length; d++) {
			int ty = ny + dy[i][d];
			int tx = nx + dx[i][d];
			
//			map[ny][nx]터널과 map[y][x]터널은 연결되어 있음
			if(y == ty && x == tx) return true;
		}
		
//		① 터널이 없는 곳 : i가 0면 dy[0].length = 0 반복문 돌지 않음
//		② 연결 안된 터널 : 반복문 안에서 return이 되지 않으면
		return false;
	}

	static class Node {
		int y, x, i, l;

		public Node(int y, int x, int i, int l) {
			this.y = y;
			this.x = x;
			this.i = i;
			this.l = l;
		}
	}
}
