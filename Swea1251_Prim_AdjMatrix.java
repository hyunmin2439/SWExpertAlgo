package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// time : 0.18990s ~ 0.23475s
// 간선에 대한 정보가 아닌, 정점에 대한 정보가 주어진다.
// 이럴 땐 Kruskal알고리즘 보다는 Prim알고리즘이 더 유리
// 간단하게 인접행렬을 사용, Prim 알고리즘으로 최종 결과값 계산

public class Swea1251_Prim_AdjMatrix {
	
	static int T, N;
	static double E, res;
	
	static int[] X;
	static int[] Y;
	
	static double[][] island;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1251.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());

		int t = 0;
		while (t++ < T) {
			N = Integer.parseInt(br.readLine());
			X = new int[N];
			Y = new int[N];
			
			// X 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				X[i] = Integer.parseInt(st.nextToken());
			}
			
			// Y 입력
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				Y[i] = Integer.parseInt(st.nextToken());
			}
			
			E = Double.parseDouble(br.readLine());
			
			// 섬에 대한 정보 입력
			island = new double[N][N];
			for (int i = 0; i < N - 1; i++) {
				for (int j = i + 1; j < N; j++) {
					// 양방향 그래프 반대쪽도 넣기
					island[j][i] = island[i][j] = distance(X[i], X[j], Y[i], Y[j]);
				}
			}
			
			res = prim();
			System.out.println("#" + t + " " + Math.round(E * res));
		}

		br.close();
	}
	
	private static double distance(int x1, int x2, int y1, int y2) {
		return Math.pow((double)x1 - x2, 2) + Math.pow((double)y1 - y2, 2);
	}
	
	private static long prim() {
		long res = 0;
		
		double[] weight = new double[N];
		boolean[] visit = new boolean[N];
		
		Arrays.fill(weight, Double.MAX_VALUE);
		
		// 첫번째 섬 부터 시작
		weight[0] = 0;
		
		// N번 돌리면 모든 섬 선택된 것
		for (int i = 0; i < N; i++) {
			int v = 0; // 선택된 섬
			double min = Double.MAX_VALUE; // 최단 거리
			
			// 연결 안된 섬들 중에 거리가 가장 가까운 섬 선택
			for (int j = 0; j < N; j++) {
				if( !visit[j] && min > weight[j]) {
					v = j;
					min = weight[j];
				}
			}
			
			// 섬 연결됨, 거리 더해주기
			visit[v] = true;
			res += weight[v];
			
			// 섬들의 최단 거리 갱신
			for (int j = 0; j < N; j++) {
				if( !visit[j] && weight[j] > island[v][j]) {
					weight[j] = island[v][j];
				}
			}
		}
		
		return res;
	}

}
