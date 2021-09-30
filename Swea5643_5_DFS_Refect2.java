package swea5643;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 다섯번째 풀이

// 가장 최적화를 한 방법

// 시간복잡도 대폭 단축됐다.

// i < j, j < k => i < k 이러한 것들을 인접행렬에 전부 기록

// 즉, 나와 직접관계인 학생을 기준으로 탐색하여 그 결과를 나에게 반영한다

// 0번째 열에 값을 -1로 두고, 탐색 후 다른 값 바꿈 -> 자신보다 큰 학생 수

// dfs 탐색부분이 조금 복잡하게 짜여져 있다.

// 맨 아래 예시를 적어놓았다.

// Memory:103,796KB / Time:470ms
public class Swea5643_5_DFS_Refect2 {
	
	static int N, M;
	static int[][] adj;
	static int cnt;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(in.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine()); // 학생 수
			M = Integer.parseInt(in.readLine()); // 간선 수
			adj = new int[N + 1][N + 1];
			
			StringTokenizer st = null;
			for (int m = 1; m <= M; m++) {
				st = new StringTokenizer(in.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				adj[from][to] = 1; // adj : from보다 to의 키가 크다.
			}

			// N + 1 : 0번 행, 열 남는 공간, Memoization 기법을 위한 공간으로 사용
			for (int i = 1; i <= N; i++) {
				adj[i][0] = -1; // 탐색 전이라는 의미
			}
			
			int ans = 0;
			for (int i = 1; i <= N; i++) {
				if(adj[i][0] == -1) dfs(i); // 자신보다 큰 학생 탐색(아직 탐색이 안된 학생만
			}
			
			// 위에서 탐색된 결과를 토대로 자신보다 작은 학생수 카운팅 
			// 0행에 나보다 작은 학생 수 더하기
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					adj[0][j] += adj[i][j];
				}
			}
			
			for (int i = 1; i <= N; i++) {
				if(adj[i][0] + adj[0][i] == N - 1) ans++;
			}
			
			System.out.println("#" + t + " " + ans);
		}
		
		in.close();
	}
	
	// 자신보다 큰 학생따라 탐색
	private static void dfs(int cur) {
		
		for (int i = 1; i <= N; i++) {
			if(adj[cur][i] == 1) { // 자신보다 큰 학생이면
				if(adj[i][0] == -1) { // 탐색 전
					dfs(i);
				}
				// 자신보다 큰 학생들을 탐색을 완료한 상태(메모가 되어있으면 탐색안하고 바로 내려옴)
				if(adj[i][0] > 0) { // i보다 큰 학생이 존재, cur < i < 1 ~ (i보다 큰 학생들의 수)
					// i의 인접행렬의 상태를 cur에 반영
					for (int j = 1; j <= N; j++) {
						// cur < i < j => cur < j
						if(adj[i][j] == 1) adj[cur][j] = 1;
					}
				}
			}
			
		}
		
		// 탐색 끝난 후 기록
		int cnt = 0;
		for (int j = 1; j <= N; j++) {
			cnt += adj[cur][j]; // 0 1 0 1 1 ... 이렇게 되어 있으니 다 더해도 상관X
		}
		adj[cur][0] = cnt;
	}
	
}

/*
input\
1
6
6
1 5
3 4
5 4
4 2
4 6
5 2

adj : dfs(1) 첫 회전
   0 1 2 3 4 5 6
0 
1  4   1   1 1 1
2  0
3 -1   1   1   1
4  2   1       1
5  3   1   1   1
6  0

adj : dfs(3) 두번째 회전
   0 1 2 3 4 5 6
0 
1  4   1   1 1 1
2  0
3  3   1   1   1
4  2   1       1
5  3   1   1   1
6  0

   0 1 2 3 4 5 6
0    0 4 0 3 1 4
1  4   1   1 1 1
2  0
3  3   1   1   1
4  2   1       1
5  3   1   1   1
6  0

1번 학생 4 + 0 => X
2번 학생 0 + 4 => X
3번 학생 3 + 0 => X
4번 학생 2 + 3 => O (N-1 => 5)
5번 학생 3 + 1 => X
6번 학생 0 + 4 => X
*/
