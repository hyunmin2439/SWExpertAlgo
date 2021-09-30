package swea5643;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 여섯번째 풀이

// Floyd_Warsall 알고리즘

// 모든쌍 최단경로 구하는 알고리즘

// 시간 복잡도는 O(N^3)으로

// 이 문제에 적용 시 시간적 이점은 없으나

// 코드의 간결함 => 빠른시간 내에 풀 수 있는 장점

// Memory:99,808KB / Time:1,252ms
public class Swea5643_6_Floyd {
	
	static int T, N, M;
	
	static int[][] adjMatrix;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(in.readLine());

		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(in.readLine());
			M = Integer.parseInt(in.readLine());
			
			adjMatrix = new int[N + 1][N + 1];
			
			for (int m = 1; m <= M; m++) {
				StringTokenizer st = new StringTokenizer(in.readLine());
				int i = Integer.parseInt(st.nextToken());
				int j = Integer.parseInt(st.nextToken());
				
				adjMatrix[i][j] = 1; // i는 j보다 작다
			}
			
			floyd();

			System.out.println("#" + t + " " + count());
		}

		in.close();
	}

	private static void floyd() {
		for (int k = 1; k <= N; k++) {
			for (int i = 1; i <= N; i++) {
				// i와 k가 같은 학생일때
				if(i == k) continue; 
				
				for (int j = 1; j <= N; j++) {
					// i와 j가 같은 학생일때, k가 j가 같은 학생일때
					if(i == j || k == j) continue;
					
					// i < k, k < j 키가 크면 i < j 키가 크다
					// 문제의 주어진 조건대로 구현, 코드가 간결 명확해서 헷갈리지 않음
					if(adjMatrix[i][k] == 1 && adjMatrix[k][j] == 1)
						adjMatrix[i][j] = 1;
				}
			}
		}
	}
	
	// 자신의 키가 몇 번째인지 알 수 있는 학생의 수 계산하는 메소드
	private static int count() {
		int res = 0; // 결과
		
		for (int i = 1; i <= N; i++) {
			int cnt = 0; // 자신과 키를 비교할 수 있는 학생 수
			for (int j = 1; j <= N; j++) {
				// i보다 큰 학생수 + i보다 작은 학생 수
				cnt += adjMatrix[i][j] + adjMatrix[j][i];
			}
			
			// 자신 빼고 나머지 다 비교 가능하면
			if(cnt == N - 1) res++;
		}
		
		return res;
	}
	
}
