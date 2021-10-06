import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/* 벽돌깨기
 * 
 * 중복순열을 완전탐색해야 하는 시뮬레이션 문제
 * 
 * 공 떨어트리고 BFS든, DFS든 탐색을 통해 블럭 제거
 * 
 * 블럭 제거 후 중력 작용처럼 블럭을 아래로 다 정렬을 해내려야 한다.
 * 
 * 추가로, 순열을 만들어 놓고 시뮬레이션을 돌리는 것이 아닌 각 순열마다
 * 
 * 시뮬레이션을 하고 다음 순열을 만드는 것이 시간적으로 더 유리하다.
 * 
 * ex) N = 3 -> 1, 3, 5열 순열 완성 -> 공 떨어트리는 시뮬레이션 시간 느림
 * 
 *     N = 3 -> 1 공 떨어트리는 시뮬레이션 -> 0 ~ W 공 떨어트리는 시뮬레이션 -> 시간 절약
 *     
 * 폭발범위라는 것까지 있기 때문에 시뮬레이션이 생각보다 복잡해서 많이 헤맸던 문제이다.
 * 
 * 실수가 잦을 수 밖에 없는 문제임으로 차근차근 꼼꼼히 풀어보는 것이 좋다.
 * 
 * Memory:91,660KB / Time:334ms // 일반 중력작용 코드
 * 
 * Memory:96,604KB / Time:376ms // 리스트를 사용한 중력작용 코드
 */

public class Swea5656 {
	
	static final int EMPTY = 0;
	
	static int T, N, W, H, min;
	
	static List<Integer> list; // 블럭을 아래로 떨어트릴때 사용하는 리스트
	
	//					상 하  좌 우
	static int[] dy = { -1, 1,  0, 0 };
	static int[] dx = {  0, 0, -1, 1 };
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		list = new ArrayList<>();
		
		T = Integer.parseInt(in.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			int[][] map = new int[H][W];
			
			// 맵 입력받기
			for (int y = 0; y < H; y++) {
				st = new StringTokenizer(in.readLine());
				for (int x = 0; x < W; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			
			min = Integer.MAX_VALUE;
			
			simul(map, 0);
			
			System.out.println("#" + t + " " + min);
		}
		
		in.close();
	}

	private static void simul(int[][] map, int idx) {
		if(idx == N) {
			// 남은 벽돌개수 계산 min값 갱신
			min = Math.min(min, countBlock(map));
			return;
		}
		
		// idx번째 구슬 x열에 떨어뜨리기
		for (int x = 0; x < W; x++) {
			int[][] copy = copyMap(map); // 맵 복사본 만들어 사용
			
			boom(copy, x); // bfs로 벽돌 깨트리기
			
			gravity(copy); // 중력 작용으로 벽돌 밑으로 내리기
			
			simul(copy, idx + 1); // 다음 구슬 떨어트리기
		}
	}

	private static int countBlock(int[][] map) {
		int cnt = 0;
		
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if(map[y][x] != EMPTY) cnt++;
			}
		}
		
		return cnt;
	}

	private static int[][] copyMap(int[][] map) {
		int[][] copy = new int[H][W];
		
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				copy[y][x] = map[y][x];
			}
		}
		
		return copy;
	}

	private static void boom(int[][] map, int x) {
		Queue<Block> queue = new LinkedList<>();
		boolean[][] visit = new boolean[H][W];
		
		int y = 0;
		// 구슬 떨어트린 열의 첫 블록과 만날 때까지 행값 증가
		while(y < H && map[y][x] == EMPTY) y++; 
		
		if(y == H) return; // 블록이 없는 열, 폭발 시킬 블럭이 없다
		
		// 구슬과 닿은 첫 블럭 처리
		queue.offer(new Block(y, x, map[y][x])); // 큐 삽입
		map[y][x] = EMPTY; // 해당 블럭 폭발
		visit[y][x] = true; // 방문 처리
		
		while( !queue.isEmpty() ) {
			Block block = queue.poll();
			
			// 블록의 범위만큼 폭발 확장(1 제외)
			for (int i = 1; i < block.n; i++) {
				
				// 사방 탐색
				for (int d = 0; d < dy.length; d++) {
					int ny = block.y + dy[d] * i;
					int nx = block.x + dx[d] * i;
					
					// 범위 체크 || 방문 체크
					if( !(0 <= ny && ny < H && 0 <= nx && nx < W) || visit[ny][nx] ) continue;
					
					// 폭발에 닿은 블럭 추가 연쇄처리
					// 1이면 자기 자신만 폭발, 주변에 영향주지 못하니 큐에 넣지 않기
					if(map[ny][nx] > 1) queue.offer(new Block(ny, nx, map[ny][nx]));
					map[ny][nx] = EMPTY;
					visit[ny][nx] = true;
				}
			}
			
		}
		
	}
	
//	일반적인 중력작용 코드
//	private static void gravity(int[][] map) {
//		// 맨 아래 행 윗 줄 부터 시작
//		for (int y = H - 2; y >= 0; y--) {
//			for (int x = 0; x < W; x++) {
//				// 빈칸이면 다음으로
//				if(map[y][x] == EMPTY) continue;
//				
//				// 벽돌이 있으면 내리기
//				int ny = y + 1;
//				while(ny < H && map[ny][x] == EMPTY) ny++;
//				ny--; // ny가 범위를 벗어나거나, 블록을 만나면 그 바로 위로 블록을 옮기기 위해 -1
//				
//				// 자기 자신 위치 건너뛰기
//				if(y == ny) continue;
//				
//				map[ny][x] = map[y][x];
//				map[y][x] = EMPTY;
//			}
//		}
//	}
	
	// List를 이용한 중력작용
	// 시간, 공간적으로 조금 손해가 있으나, 단순하고 직관적인 코드라 헷갈리지 않음
	private static void gravity(int[][] map) {
		
		for (int x = 0; x < W; x++) {
			// 행 아래쪽 부터 차례차례 리스트에 담기
			for (int y = H - 1; y >= 0; y--) {
				if(map[y][x] != EMPTY) {
					list.add(map[y][x]);
					map[y][x] = EMPTY;
				}
			}
			
			int y = H;
			// 아래쪽 부터 차례차례 넣기
			for (int num : list) map[--y][x] = num;
			
			list.clear();
		}

		
	}
	
	static class Block {
		int y, x, n;

		public Block(int y, int x, int n) {
			this.y = y;
			this.x = x;
			this.n = n;
		}
	}

}
