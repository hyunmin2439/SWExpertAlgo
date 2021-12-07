package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 첫번째 테스트 케이스는 맞았으나 나머지는 다 틀림
 * 
 * 원인을 분석해보니 각 미생물 군집들을 하나씩 이동시키며
 * 
 * 처리할 부분들을 처리하고 하나로 합치는 것도 계산을 함.
 * 
 * 하지만, 두개의 미생물 군집들이 한군데 모이는 것은 문제 없이 처리되나, 
 * 
 * 세 개의 미생물 군집들이 한군데 모이는 것은 문제가 발생한다.
 * 
 * 아래 예시로 설명을 하자면, 
 * 
 * A < B < C (미생물 수) => C의 미생물 수가 가장 많으므로 C의 이동방향을 따라야 하나,
 * 
 * A, B 미생물 군집이 먼저 이동 후 합쳐짐 => A + B > C (미생물 수)
 * 
 * 이럴 경우 B의 이동 방향을 따르게 되니 전혀 다른 케이스가 발생한다.
 * 
 * isExsitMicroOrg 배열을 단순히 int가 아닌 ArrayList<ArrayList<Node>>로 구상하는 것이 좋아보인다.
 * 
 */

public class SWEA_2382_미생물격리_1_오답 {

	static int N, M, K; // N : 한변 셀의 개수, M : 격리시간, K : 군집의 개수
	
	static int[][] isExsitMicroOrg; // 0이면 존재하지 않음
	
	static List<Node> microOrgList;
	
	static int[] dy = { 0, -1, 1, 0, 0 }; // 0: dummy
	static int[] dx = { 0, 0, 0, -1, 1 };
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		microOrgList = new LinkedList<>();
		
		int t = 0;
		int T = Integer.parseInt(in.readLine());
		
		while(t++ < T) {
			inputInfo(in);
			
			isolateMicroOrg();
		
			System.out.println( "#" + t + " " + getRemainNumOfMicroOrg() );
		}
		
		in.close();
	}
	
	private static void inputInfo(BufferedReader in) throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		isExsitMicroOrg = new int[N][N];
		microOrgList.clear();
		
		for(int i = 1; i <= K; i++) {
			st = new StringTokenizer(in.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int cnt = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			isExsitMicroOrg[y][x] = i;
			
			microOrgList.add(new Node(i, y, x, cnt, d, -1));
		}
	}

	private static void isolateMicroOrg() {
		Node microOrg, otherMicroOrg;
		
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < K; j++) {
				microOrg = microOrgList.get(j);
				
				// 없어진 개체
				if( !microOrg.isExist ) continue;
				
				int ny = microOrg.y + dy[microOrg.d];
				int nx = microOrg.x + dx[microOrg.d];
				microOrg.flag = i; // 0, 1, 2, ... 번째 이동
				
				if(ny == 0 || ny == N - 1 ||
						nx == 0 || nx == N - 1 ) {
					microOrg.cnt /= 2; // 미생물 반으로 감소
					microOrg.d += microOrg.d % 2 == 0 ? -1 : 1; // 반대방향: 짝수면 -1, 홀수면 +1
				}
				
				// 이전 위치에 적혀있는 번호가 자신과 같으면 이전 위치 지우기. -> 다른 미생물이 자신의 자리 차지했을 수 있음
				if(isExsitMicroOrg[microOrg.y][microOrg.x] == microOrg.num) {
					isExsitMicroOrg[microOrg.y][microOrg.x] = 0;
				}
				
				// 남은 미생물 수가 없으면 사라짐 처리
				if(microOrg.cnt == 0) {
					microOrg.isExist = false;
					continue;
				}
				
				microOrg.y = ny;
				microOrg.x = nx;
				
				// 이동하려는 위치에 다른 미생물이 존재하면
				if(isExsitMicroOrg[ny][nx] != 0) {
					otherMicroOrg = microOrgList.get( isExsitMicroOrg[ny][nx] - 1 );
					
					if( otherMicroOrg.flag < microOrg.flag ) {
						isExsitMicroOrg[ny][nx] = microOrg.num;					
					}
					else if(otherMicroOrg.cnt > microOrg.cnt) {
						microOrg.isExist = false;
						otherMicroOrg.cnt += microOrg.cnt;
					}
					else {
						otherMicroOrg.isExist = false;
						isExsitMicroOrg[ny][nx] = microOrg.num;
						microOrg.cnt += otherMicroOrg.cnt;
					}
				}
				
			}
		}
	}

	private static int getRemainNumOfMicroOrg() {
		int totalNum = 0;
		
		for (Node mo : microOrgList) {
			totalNum += mo.cnt;
		}
		
		return totalNum;
	}

	static class Node {
		boolean isExist;
		int num, y, x, cnt, d, flag; // num:미생물 번호, y x : 좌표, cnt : 미생물 수, d : 방향, flag: 이동여부 체크

		public Node(int num, int y, int x, int cnt, int d, int flag) {
			this.isExist = true;
			this.num = num;
			this.y = y;
			this.x = x;
			this.cnt = cnt;
			this.d = d;
			this.flag = flag;
		}

		@Override
		public String toString() {
			return "Node [num=" + num + ", y=" + y + ", x=" + x + ", cnt=" + cnt + ", d=" + d + ", flag=" + flag + "]";
		}
		
	}
}
