package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// time : 2.28728s ~ 2.40728s
// 굉장히 느리다. 
// Collection 프레임워크를 많이 써서 오버헤드가 많이 걸리는 듯 하다.
// 쓰기 편하고 이해하기 쉽지만 시간초 제한 있을 때 사용하면 안됨.

public class Swea1251_Prim_AdjList {
	
	static int T, N;
	static double E, res;
	
	static int[] X;
	static int[] Y;
	
	static ArrayList<ArrayList<Node>> list;
	
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
			
			// 섬 init
			list = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				list.add(new ArrayList<Node>());				
			}
			
			// 섬에 대한 정보 입력
			for (int i = 0; i < N - 1; i++) {
				for (int j = i + 1; j < N; j++) {					
					Double d = distance(X[i], X[j], Y[i], Y[j]);
					list.get(i).add(new Node(j, d)); // i -> j // 양방향
					list.get(j).add(new Node(i, d)); // j -> i
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
		long res = 0; // 결과값
		boolean[] visit = new boolean[N];
		PriorityQueue<Node> queue = new PriorityQueue<>( (v1, v2) -> Double.compare(v1.d, v2.d));
		
		// 0번 섬을 시작으로 갈 수 있는 모든 섬 큐에 추가
		visit[0] = true;
		queue.addAll(list.get(0));
		
		int cnt = 0; // 선택된 정점
		while( !queue.isEmpty() && cnt < N ) {
			Node node = queue.poll();
			
			// 방문했다면 다음으로
			if( visit[node.v] ) continue;
			
			// 방문 처리 / 결과 += 거리 / 연결한 섬++
			visit[node.v] = true;
			res += node.d;
			cnt++;
			
			// 현재 섬에서 갈 수 있는 다른 섬 큐에 전부 추가
			queue.addAll(list.get(node.v));			
		}
		
		
		return res;
	}

	static class Node {
		int v; // 도착 정점
		double d; // 거리
		
		public Node(int v, double d) {
			super();
			this.v = v;
			this.d = d;
		}
		
	}
}
