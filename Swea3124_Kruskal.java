package uploaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// memory:113,292KB / time:1,915ms
public class Swea3124_Kruskal {

	static int T, V, E;
	static int[] parent;
	static Edge[] edges;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			edges = new Edge[E];
			
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				edges[i] = new Edge(Integer.parseInt(st.nextToken()),
									Integer.parseInt(st.nextToken()),
									Integer.parseInt(st.nextToken()));
			}
			
			makeSet();
			System.out.println("#" + t + " " + kruskal());
		}
		
		br.close();
	}
	
	private static long kruskal() {
		int cnt = 0;  // cnt 간선의 수
		long res = 0; // res 결과값
		
		Arrays.sort(edges);
		
		for (int i = 0; i < E; i++) {
			if(cnt >= V - 1) break;
			
			Edge e = edges[i];
			
			if(union(e.v1, e.v2)) {
				res += e.c;
				cnt++;
			}
		}
		
		return res;
	}

	private static void makeSet() {
		parent = new int[V + 1]; // 0 : dummy
		
		for (int i = 1; i <= V; i++) {
			parent[i] = i;
		}
	}
	
	private static int find(int v) {
		if(v == parent[v]) return v;
		return parent[v] = find(parent[v]);
	}
	
	private static boolean union(int v1, int v2) {
		int onep = find(v1);
		int twop = find(v2);
		
		if(onep == twop) return false;
		
		if(onep > twop) parent[twop] = onep;
		else parent[onep] = twop;
		return true;
	}
	
	static class Edge implements Comparable<Edge> {
		int v1, v2, c;

		public Edge(int v1, int v2, int c) {
			super();
			this.v1 = v1;
			this.v2 = v2;
			this.c = c;
		}

		@Override
		public int compareTo(Edge other) {
			// c가 음수 일 수도 있기 때문에 compare사용
			return Integer.compare(this.c, other.c);
		}
		
	}
}
