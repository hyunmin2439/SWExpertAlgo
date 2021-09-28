package solved;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Swea1767 {

	static final int CELL = 0;
	static final int CORE = 1;
	static final int CABLE = 2;
	
	static int T, N, coreCnt, maxCnt, minLen;
	static int[][] map;
	
	static List<Core> list;
	
	static int[] dy = { -1, 1,  0, 0 };
	static int[] dx = {  0, 0, -1, 1 };
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
		list = new ArrayList<>();
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			// input
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			list.clear();
			
			for (int y = 0; y < N; y++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int x = 0; x < N; x++) {
					if(Integer.parseInt(st.nextToken()) == CORE) {
						map[y][x] = CORE;
						// 연결되어 있는 코어 list 추가 불필요
						if(y == 0 || y == N - 1 || x == 0 || x == N - 1) continue;
						
						list.add(new Core(y, x));
					}
				}
			}
			
			// init
			coreCnt = list.size();
			maxCnt = Integer.MIN_VALUE;
			minLen = Integer.MAX_VALUE;
			
			// 각 코어 마다 사방 갈 수 있는 곳인지 체크 및 좌표 기록
			check4direct();
			
			recur(0, 0, 0);
			
			minLen = (minLen == Integer.MAX_VALUE) ? 0 : minLen;
			System.out.println("#" + t + " " + minLen);
		}
		
		br.close();
	}
	
	private static void check4direct() {
		for (int i = 0; i < coreCnt; i++) {
			Core core = list.get(i);
			
			// 연결 좌표들 기록
			for (int d = 0; d < 4; d++) {
				List<Pos> dl = core.direct.get(d);
				int ny = core.y;
				int nx = core.x;
				
				while(true) {
					ny += dy[d];
					nx += dx[d];
					
					// 전원부에 닿으면 종료
					if( !(0 <= ny && ny < N && 0 <= nx && nx < N) ) break;
					
					// 연결 길목에 코어가 있으면 연결 불가능
					if(map[ny][nx] == CORE) {
						dl.clear();
						break;
					}
					
					// 좌표 기록
					dl.add(new Pos(ny, nx));
				}		
			}
		}
	}
	
	// 인덱스, 연결된 코어 개수, 전선 총 길이
	private static void recur(int idx, int cnt, int len) {

		// 기저 조건 : 남은 코어를 다 연결해도 최대 연결개수 보다 작으면
		if (maxCnt > coreCnt - idx + cnt)
			return;

		// 기저 조건
		if (idx == coreCnt) {
			// 디버깅용
//			System.out.println("cnt:" + cnt + " len:" + len);
//			printMap();

			if (maxCnt == cnt) {
				minLen = Math.min(minLen, len);
			} else if (maxCnt < cnt) {
				maxCnt = cnt; minLen = len;
			}
			return;
		}

		Core core = list.get(idx);

		for (int d = 0; d < 4; d++) {
			List<Pos> dl = core.direct.get(d);

			// 비어 있으면 연결할 수 없다는 것
			if (dl.isEmpty())
				continue;

			boolean check = setCable(dl); // 케이블 연결
			
			// 연결 되었으면 재귀, 돌아와서 해제
			if(check) recur(idx + 1, cnt + 1, len + dl.size());
			if(check) delCable(dl);
		}

		// 연결 안하고 다음 것
		recur(idx + 1, cnt, len);

	}
	
	private static boolean setCable( List<Pos> dl ) {
		
		// 먼저 체크 다른 전선이 있다면 설치 불가
		for ( int i = 0; i < dl.size(); i++ ) {
			Pos pos = dl.get(i);
			if(map[pos.y][pos.x] == CABLE) return false;
		}
		
		// 앞에서 return되지 않으면 설치 가능, 설치
		for ( int i = 0; i < dl.size(); i++ ) {
			Pos pos = dl.get(i);				
			map[pos.y][pos.x] = CABLE;
		}
		
		return true;
	}
	
	private static void delCable( List<Pos> dl ) {
		for (int i = 0; i < dl.size(); i++ ) {
			Pos pos = dl.get(i);
			map[pos.y][pos.x] = CELL;
		}
	}
	
	private static void printMap() {
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				System.out.print(map[y][x] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static class Pos {
		int y, x;

		public Pos(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return "Pos [y=" + y + ", x=" + x + "]";
		}
	}

	static class Core extends Pos {
		List< List<Pos> > direct; // 사방 갈수 있는 방향

		public Core(int y, int x) {
			super(y, x);
			
			direct = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				direct.add(new ArrayList<>());
			}
		}

		@Override
		public String toString() {
			return "Core [y=" + y + ", x=" + x + ", direct=" + direct + "]";
		}

	}

}
