package solved;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author hyunminKim
 * 
 * BFS 
 * 
 * 사방탐색과 두 터널이 연결되어 있는지 여부를 판단 다른 방법으로 푼것
 * 
 * Memory:34,456KB / Time:134ms
 */
public class Swea1953_BFS2 {

	static int T, N, M, sY, sX, L, res;
	
	static int[][] map;
	static boolean[][] visited;
	
	static Queue<Node> queue;

	static int[] dy = { -1, 1,  0, 0 };
	static int[] dx = {  0, 0, -1, 1 };
	
	// 갈 수 있는 방향을 체크 하는 배열
	static boolean[][] dd = { 
			{ false, false, false, false }, // 0번 더미
			{ true, true, true, true }, 	// 1번 : 상하좌우
			{ true, true, false, false }, 	// 2번 : 상하
			{ false, false, true, true}, 	// 3번 :     좌우
			{ true, false, false, true }, 	// 4번 : 상    우
			{ false, true, false, true}, 	// 5번 :   하  우
			{ false, true, true, false }, 	// 6번 :   하좌
			{ true, false, true, false}, 	// 7번 : 상  좌
	};
	
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
			
//			4방탐색
			for (int d = 0; d < 4; d++) {
				// 갈 수 있는 방향이 아니면
				if( !dd[n.i][d] ) continue;
				
				int ny = n.y + dy[d];
				int nx = n.x + dx[d];

//				좌표체크 및 방문체크
				if (!(0 <= ny && ny < N && 0 <= nx && nx < M) || visited[ny][nx]) continue;

//				터널이 연결되어 있지 않다면
				if (!isConnected(map[ny][nx], d)) continue;
				
				res++; // 갈수 있는 장소 개수 +1
				visited[ny][nx] = true; // 방문 체크
				queue.offer(new Node(ny, nx, map[ny][nx], n.l - 1));
			}

		}
	} 

	/** 두 터널이 연결되어 있는지 여부를 판단하는 메서드
	 * @param i : 이 터널 구조물의 종류
	 * @param d : 이 터널로 이동해 온 방향
	 * @return 연결되어 있으면 true, 연결되어 있지 않으면 false
	 */
	private static boolean isConnected(int i, int d) {
		// 이 곳으로 온 방향 쪽, 즉 반대 방향
		// ex) 상 => 하, 좌 => 우
		int check = d == 0 ? 1 : ( d == 1 ? 0 : ( d == 2 ? 3 : 2) );
		
//		① 터널이 없는 곳 : 0은 dummy 배열로 전부 false
//		② 연결 안된 터널 : i에서 check 방향으로 갈 수 없으면 false
		return dd[i][check];
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
