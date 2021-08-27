package uploaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// memory:259,328kb / time:4,451ms
public class Swea3124_Prim {

	static int T, V, E;
	static ArrayList<ArrayList<Edge>> edges;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			edges = new ArrayList<>();
			
			for (int i = 0; i < E + 1; i++) {
				edges.add(new ArrayList<>());
			}
			
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				int v1 = Integer.parseInt(st.nextToken());
				int v2 = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				
				edges.get(v1).add(new Edge(v2, d));
				edges.get(v2).add(new Edge(v1, d));
			}
			
			System.out.println("#" + t + " " + prim());
		}
		
		
		br.close();
	}
	
	private static long prim() {
		long res = 0; // 결과 값
		
		int cnt = 1; // 방문한 정점 개수
		boolean[] visit = new boolean[V + 1];
		int[] distance = new int[V + 1];
		PriorityQueue<Edge> queue = new PriorityQueue<>();
		
		Arrays.fill(distance, Integer.MAX_VALUE);
		
		visit[1] = true;
		distance[1] = 0;
		queue.addAll(edges.get(1));
		
		while( !queue.isEmpty() ) {
			if(cnt == V) break;
			
			Edge e = queue.poll();

			// 방문한 정점이면 continue
			if (visit[e.v])
				continue;

			visit[e.v] = true;
			res += e.c;
			cnt++;

			queue.addAll(edges.get(e.v));
		}
		
		return res;
	}

	static class Edge implements Comparable<Edge> {
		int v, c;

		public Edge(int v, int c) {
			super();
			this.v = v;
			this.c = c;
		}

		@Override
		public int compareTo(Edge other) {
			// c가 음수 일 수도 있기 때문에 compare사용
			return Integer.compare(this.c, other.c);
		}
		
	}
}
