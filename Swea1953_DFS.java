package solved;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author hyunminKim
 * 
 * DFS로 풀이시 visit 체크 조건 처리가 까다로워짐
 * 
 * 아래와 같은 예시에서 S에서 시작하여 L이 8이 주어진다면
 * 
 *   O O O
 *   S   O
 * X O O O
 * 
 * 위쪽으로 먼저 탐색한다고 가정했을 때, S포함 O는 전부 방문이 가능하나
 * 
 * 아래쪽으로 가려고 하니 이미 방문한 곳이라 X위치는 가지 못하는 경우가 발생
 * 
 * 따라서 DFS로 풀려고 하면 visit 체크 배열을 두개를 두고
 * 
 * 하나는 탈주범이 있을 수 있는 위치를 체크하는 용도(reached)
 * 
 * 하나는 탐색하는 용도(visited)로 두고, 이 배열은 한쪽 방향 탐색 후 해제를 해야 한다.
 * 
 * 그리고 dfs 탐색이 끝난 후 탈주범이 있을 수 있는 위치를 체크 해둔 배열을 순회하며 count를 해야한다.
 * 
 * 시간이 크게 차이 날 것 같았으나, 큰 차이 없음
 * 
 * 크기가 작아서 큰 차이가 없는 듯 함 (5 ≤ N, M ≤ 50)
 * 
 * 크기가 커지면 더 큰 차이를 보일 것으로 생각된다. 또한 코드상 BFS가 더 간편하다.
 * 
 * Memory:38,328KB / Time:125ms
 */
public class Swea1953_DFS {

	static int T, N, M, sY, sX, L, res;
	
	static int[][] map;
	static boolean[][] visited, reached;
	
	//				0. dummy  1.상 하 좌 우  2.상 하 3. 좌 우 4.상 우 5.하 우  6.하  좌  7.상 좌
	static int[][] dy = { {}, {-1, 1, 0, 0}, {-1, 1}, { 0, 0}, {-1, 0}, {1, 0}, {1,  0}, {-1,  0} };
	static int[][] dx = { {}, {0, 0, -1, 1}, { 0, 0}, {-1, 1}, { 0, 1}, {0, 1}, {0, -1}, { 0, -1} };
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
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
			reached = new boolean[N][M];
			
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(in.readLine());
				for (int x = 0; x < M; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			
			res = 0;
			reached[sY][sX] = true; // 시작위치
			dfs(sY, sX, L - 1); // 맨홀로 들어 갔을 때 시간 1 소요
			
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					if(reached[y][x]) res++;
				}
			}
			
			System.out.println("#" + t + " " + res);
		}
		
		in.close();
	}

	/** 깊이 우선 탐색을 하면서 제한 시간동안 이동할 수 있는 위치 탐색
	 * @param y : 현재 터널 y좌표
	 * @param x : 현재 터널 x좌표
	 * @param l : 남은 시간
	 */
	private static void dfs(int y, int x, int l) {
//		시간이 다 되면 더 이상 가지 못함
		if(l == 0) return;
		
		int i = map[y][x]; // 터널 구조물 종류
		
//		터널 구조물 종류에 따라 반복 횟수 다름
		for (int d = 0; d < dy[i].length; d++) {
			int ny = y + dy[i][d];
			int nx = x + dx[i][d];
			
//			좌표체크 및 방문체크
			if( !(0 <= ny && ny < N && 0 <= nx && nx < M) || visited[ny][nx] ) continue;
			
//			터널이 연결되어 있지 않다면
			if( !isConnected(y, x, ny, nx) ) continue;
			
			visited[ny][nx] = true; // 방문 체크
			reached[ny][nx] = true; // 탈주범이 있을 수 있는 위치 체크
			dfs(ny, nx, l - 1);
			visited[ny][nx] = false; // 방문 해제
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

}
