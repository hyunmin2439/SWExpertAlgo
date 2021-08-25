package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

// memory:20,804KB / time : 0.11459s ~ 0.13258s

// 부분 방향 그래프
// 모든 방향에 간선이 존재하지 않고
// 이어져 있지 않는 정점들도 존재
// 또한 중복된 from to가 입력으로 들어옴
// 이러한 것들을 해결하기 위해 인접 리스트를 사용
// map을 사용하여 공간, 시간 복잡도 낮추려고 했으나 큰 의미는 없었다.
// set을 사용하여 중복된 값들이 포함되지 않게 하였다.

public class Swea1238 {
	
	static int len, start, ans;
	
	// 인접 리스트 개념, 중복된 입력 때문에 set을 사용
	static Map<Integer, HashSet<Integer>> map; 
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1238.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		map = new HashMap<Integer, HashSet<Integer>>();
		
		int t = 0;
		while( t++ < 10 ) {
			// init
			
			map.clear();
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			len = Integer.parseInt(st.nextToken());
			start = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < len / 2; i++) {
				Integer from = Integer.valueOf(st.nextToken());
				Integer to = Integer.valueOf(st.nextToken());
				
				// 첫 입력시 비어 있으면 생성
				if( map.get(from) == null ) map.put(from, new HashSet<Integer>());
				map.get(from).add(to);
			}
			
			bfs(start);

			System.out.println("#" + t + " " + ans);
		}
		
		br.close();
	}
	
	private static void bfs(int num) {
		// 정답 가능 리스트
		List<Node> ansList = new ArrayList<>();
		ansList.add(new Node(0, 0)); // 초기 에러(IndexOut~) 방지용 dummy
		
		Queue<Node> queue = new LinkedList<>();
		boolean[] visit = new boolean[101];
		
		// 초기 연락 시작
		queue.offer(new Node(num, 0));
		visit[num] = true;
		
		while( !queue.isEmpty() ) {
			Node curr = queue.poll();
			num = curr.num;
			
			// 정답 리스트 추가
			addAns(ansList, curr);
			
			// 연락 가능한 사람이 없다.
			if(map.get(num) == null) continue;
			
			for (int i : map.get(num)) {
				// 연락 받지 않았으면
				if( !visit[i] ) {
					visit[i] = true;
					queue.offer(new Node(i, curr.depth + 1));
				}
			}
		}
		
		// 정답 리스트 중 가장 큰 것
		Collections.sort(ansList);
		ans = ansList.get(0).num;
	}

	private static void addAns(List<Node> ansList, Node n) {
		int maxDepth = ansList.get(0).depth;
		if( maxDepth > n.depth ) return; // 현재 depth가 작으면 추가X
		
		if( maxDepth < n.depth ) ansList.clear(); // 현재 depth가 크면 클리어
		ansList.add(new Node(n.num, n.depth)); 
	}

	static class Node implements Comparable<Node> {
		int num, depth;

		public Node(int num, int depth) {
			super();
			this.num = num;
			this.depth = depth;
		}
		
		@Override
		public int compareTo(Node other) {
			return other.num - this.num;
		}
				
	}

}
