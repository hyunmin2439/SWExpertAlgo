package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// time : 0.79415s ~ 0.86290s
// Kruskal로 풀면 간선들에 대한 정보를 입력받고 해야해서 느림
// 이 문제는 prim으로 푸는 것이 조금 더 빠름
public class Swea1251_Kruskal {
	static int T, N, edgeCnt;
	static double E, res;
	static int[] parent;
	static Node[] island;
	static Node[] edges;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1251.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		int t = 0;
		while(t++ < T) {
			// init
			N = Integer.parseInt(br.readLine());
			edgeCnt = N * (N - 1) / 2;
			island = new Node[N];
			edges = new Node[edgeCnt];
			
			// X 좌표 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				island[i] = new Node(Integer.parseInt(st.nextToken()));
			}
			
			// Y 좌표 입력
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				island[i].y = Integer.parseInt(st.nextToken());
			}
			
			// 환경 부담 세율 입력
			E = Double.parseDouble(br.readLine());
			
			// 간선 추가
			int cnt = 0;
			for (int i = 0; i < N - 1; i++) {
				for (int j = i + 1; j < N; j++) {
					Double d = distance(island[i].y, island[j].y, island[i].x, island[j].x);
					edges[cnt++] = new Node(i, j, d);
				}
			}
			
			// 간선 거리 기준 정렬
			Arrays.sort(edges);
	
			// 서로소 집합 생성
			make();
			
			res = 0; // 결과값
			cnt = 0; // 간선 개수
			for (int i = 0; i < edgeCnt; i++) {
				// 간선수 == 정점수 - 1 => 모든섬 이어진 것
				if(cnt == N - 1) break;
				
				if( union(edges[i].x, edges[i].y) ) {
					res += edges[i].d; // 결과값 더하기
					cnt++;
				}
			}
			
			System.out.println("#" + t + " " + Math.round(E * res));
		}
		
		br.close();
	}
	
	private static double distance(int y1, int y2, int x1, int x2) {
		return Math.pow((long)y1 - y2, 2) + Math.pow((long)x1 - x2, 2);
	}
	
	private static void make() {
		parent = new int[N];
		
		for (int i = 0; i < N; i++) {
			parent[i] = i;
		}
	}
	
	private static int find(int a) {
		if(a == parent[a]) return a;
		return parent[a] = find(parent[a]);
	}
	
	private static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if(aRoot == bRoot) return false;
		
		if(aRoot > bRoot) parent[bRoot] = aRoot;
		else parent[aRoot] = bRoot;
		return true;
	}

	static class Node implements Comparable<Node> {
		int x, y;
		double d;
		
		public Node(int x) {
			super();
			this.x = x;
		}
		
		public Node(int x, int y, double d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", d=" + d + "]";
		}

		@Override
		public int compareTo(Node other) {
			return Double.compare(this.d, other.d);
		}
	}
}
