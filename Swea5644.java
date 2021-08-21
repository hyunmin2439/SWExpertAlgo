package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Swea5644 {
	
	static int T, M, A, res; // M : 이동시간  A : BC개수  res : 결과값
	static Node[] user;
	static Node[] charger;
	
	static int[][] move;	
	static int[] dy = { 0, -1, 0, 1,  0 };
	static int[] dx = { 0,  0, 1, 0, -1 };
	
	static BufferedReader br;
	static BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input5644.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		T = Integer.parseInt(br.readLine());
		
		int t = 0;
		while(t++ < T) {
			init();
			
			moveToCharger();
			
			bw.write("#" + t + " " + res + "\n");
		}
		
		br.close();
		bw.close();
	}
	
	private static void init() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken()); // 이동 시간
		A = Integer.parseInt(st.nextToken()); // BC 개수
		res = 0; // 최종값 초기화
		
		// 유저 두명 생성
		user = new Node[]{ new Node(1, 1), new Node(10, 10) };
		
		// 이동 동선 입력
		move = new int[2][M];
		for (int i = 0; i < 2; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				move[i][j] = Integer.parseInt(st.nextToken());
			}			
		}
		
		// BC 입력
		charger = new Node[A];
		for (int i = 0; i < A; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			
			charger[i] = new Node(y, x, c, p);
		}
	}
	
	private static void moveToCharger() {		
		// 첫 위치에서 충전
		res += charger();
		
		for (int i = 0; i < M; i++) {
			// 각 유저 이동
			user[0].y += dy[move[0][i]];	user[0].x += dx[move[0][i]];
			user[1].y += dy[move[1][i]];	user[1].x += dx[move[1][i]];
			res += charger();
		}
		
	}

	private static int charger() {
		int score = 0;
		Node one = null, two = null;
		
		// 거리가 닿는 BC를 각각 가져온다.
		PriorityQueue<Node> oneList = getBatteryCharger(user[0]);
		PriorityQueue<Node> twoList = getBatteryCharger(user[1]);
		
		// BC리스트가 비어있지 않으면 각각 담는다.
		if( !oneList.isEmpty() ) one = oneList.poll();
		if( !twoList.isEmpty() ) two = twoList.poll();
		
		// 둘다 충전이 가능한 곳이 있고, 두개가 같으면
		if(one != null && two != null && one == two) {
			int[] sum = new int[2];
			
			// 첫번째 사람이 다른 충전 가능한 곳이 있을 때
			if( !oneList.isEmpty() ) {
				sum[0] = oneList.peek().p + one.p;
			}
			// 두번째 사람이 다른 충전 가능한 곳이 있을 때
			if( !twoList.isEmpty() ) {
				sum[1] = twoList.peek().p + one.p;
			}
			
			// 첫번째 사람이 충전소 바꿈 || 두번째 사람이 충전소 바꿈 => 둘중 큰것
			score = Math.max(sum[0], sum[1]);
			// 위의 둘중 큰것과  두 사람이 같은 곳 충전 비교 큰것
			score = Math.max(score, one.p);
		}
		else {
			// 둘다 충전이 불가능하거나 둘 중 하나만 충전 가능한 경우
			if(one != null) score += one.p;
			if(two != null) score += two.p;
		}

		return score;
	}

	private static PriorityQueue<Node> getBatteryCharger(Node user) {
		PriorityQueue<Node> queue = new PriorityQueue<>();
		
		for (int i = 0; i < A; i++) {
			// user와 BC의 거리
			int dis = Math.abs(user.y - charger[i].y) + Math.abs(user.x - charger[i].x);
			// 거리가 되면 list에 추가
			if(charger[i].c >= dis) queue.offer(charger[i]); 
		}
		
		return queue;
	}

	static class Node implements Comparable<Node> {
		int y, x, c, p;
		
		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}

		public Node(int y, int x, int c, int p) {
			this.y = y;
			this.x = x;
			this.c = c;
			this.p = p;
		}
		
		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Node) {
				Node other = (Node)obj;
				if(this.y == other.y && this.x == other.x)
					return true;
			}
			return false;
		}

		@Override
		public int compareTo(Node other) {
			return other.p - this.p;
		}

	}
}
