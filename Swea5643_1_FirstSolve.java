package swea5643;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 첫번째 풀이

// 자신(a) 보다 큰 학생(b)이 있고

// 그 학생(b)보다 큰 학생(c)가 있다면

// a < b < c => a < c를 adjMatrix에 전부 기록

// 그래프를 순회 하는 방법을 BFS로 선택

// 마지막에 자신의 행 = 나보다 큰 학생, 자신의 열 = 나보다 작은 학생

// 둘 다 false가 존재하면 나와 비교할 수 없는 학생이 존재

// 자신이 키가 몇번째인지 알 수 없음

// 전부 다 비교할 수 있으면 결과값++

// Memory:101,428KB / Time:1,385ms
public class Swea5643_1_FirstSolve {

	static int T, N, M;
	static boolean[][] adjMatrix, visit;
	// adjMatrix[a][b]는 a는 b보다 작다 라는 의미
	
	static Queue<Integer> queue;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(in.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine());
			M = Integer.parseInt(in.readLine());
			
			adjMatrix = new boolean[N + 1][N + 1];
			visit = new boolean[N + 1][N + 1];
			queue = new LinkedList<>();
			
			for (int i = 1; i <= M; i++) {
				StringTokenizer st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				adjMatrix[a][b] = true; // a는 b보다 키가 작다
			}
			
			// bfs 방문으로 자신보다 큰 학생 전부 체크
			for (int a = 1; a <= N; a++) {
				for (int b = 1; b <= N; b++) {
					// 처음 입력으로 들어온 것들만 따져보면 된다.
					// 방문 했다는 것은 이미 다 따져보고 체크 된 것.
					if( !visit[a][b] && adjMatrix[a][b] ) bfs(a, b);
				}
			}
			
			int cnt = 0;
			for (int a = 1; a <= N; a++) {				
				// a학생이 자신의 키가 몇 번째인지 알 수 있는지 체크
				if( check(a) ) cnt++;
			}
			
			System.out.println("#" + t + " " + cnt);
		}
		
		in.close();
	}
	
	// bfs의 목적 : a학생보다 큰 학생 체크
	private static void bfs(int a, int b) {
		queue.clear(); // 에러 방지 초기화
		
		visit[a][b] = true; // 방문체크
		queue.offer(b); // b는 a보다 크다
		
		while( !queue.isEmpty() ) {
			int c = queue.poll(); // a보다 큰 학생
			
			for (int d = 1; d <= N; d++) {
				// c보다 큰 d가 있고 이미 체크한 곳이 아니라면
				// a < c < d  =>  a < d
				if( adjMatrix[c][d] && !visit[a][d] ) {
					visit[a][d] = true;
					adjMatrix[a][d] = true;
					
					// d보다 큰 학생 찾음, a < d < e => a < e
					queue.offer(d);
				}
			}
		}
	}
	
	private static boolean check(int a) {
		for (int b = 1; b <= N; b++) {
			if(a == b) continue;
			
			// !(a는 b보다 작다 || b는 a보다 작다)
			// 둘 다 false면 키를 비교할 수 없어 자신이 몇 번 째인지 알 수 없다.
			if( !(adjMatrix[a][b] || adjMatrix[b][a]) ) return false;
		}
		
		return true;
	}

}
