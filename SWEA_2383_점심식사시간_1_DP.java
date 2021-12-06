package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 아이디어
 * 
 * Greedy
 * 
 * 계단까지의 거리에 따라서 사람들을 가까운 계단으로 이동시켜 내려보내는 방법을 생각해볼 수 있으나,
 * 
 * 계단을 한번에 3명씩만 내려갈 수 있는 조건, 계단의 길이가 존재함에 따라 여러가지 경우가 생길 수 있음
 * 
 * 구현이 가능한 방식일지는 모르겠으나, 실질적으로 접근 하기 어려운 해법으로 보인다.
 * 
 * 
 * BFS
 * 
 * Grid가 주어지기 때문에 BFS를 먼저 떠올릴 수 있다. 
 * 
 * 실제로도 구현이 가능해보이지만, 이동해야 하는 대상(사람)이 한명이 아닌 여러명이라는 점, 목적지가 두군데인 점.
 * 
 * 이러한 것을 고려했을 때 조합적인 측면도 포함되어야 한다. 각 사람마다 4방향이동, 최대 10명 4^10 => 2^20라는 경우의 수가 생긴다.
 * 
 * 한번의 이동에만 2^20의 경우의 수가 생기기 때문에 불가능한 해법이다.
 * 
 * 
 * DFS
 * 
 * BFS와 마찬가지로 불가능하다. 이러한 것을 따져봤을 때, Grid에서 사람을 직접 이동시키며 시뮬레이션적인 방법으로 풀기는 매우 어려워 보인다.
 * 
 * 
 * 생각한 아이디어는 Binary Counting + DP 해법
 *  
 * 1. 각 사람마다 이동해야 하는 계단을 미리 지정, binary counting 방법을 이용
 *    => 계단의 입구는 반드시 2개 조건, 최대 10명 2^10 경우의 수
 * 
 * 2. 1의 조건으로 나누어진 사람들을 계단까지의 거리순으로 정렬
 * 
 * 3. 1차원에 배열에 각각의 사람들이 이동 완료하는 시간을 기록
 *    점화식 : 계단까지 거리 + 1(대기시간) + 계단 내려가는 시간
 * 
 * 4. 단, 3번째 이후로 도착한 사람 부터는 아래 점화식을 적용
 *    점화식 : 계단까지 거리 + 계단 내려가는 시간 + Math.max(1, dp[i-3] - 계단까지의 거리)
 *    
 *    위의 점화식을 해석하자면 한번에 3명까지 밖에 못 내려가니 자신의 앞 3번째 사람이 내려가야지 자신도 내려갈 수 있다.
 *    1 2 3 자신 => 앞사람들이 내려가고 있는 상태 자신은 못 내려간다. 때문에 1번사람이 내려가야지 자신도 내려갈 수 있다.
 *    
 *    Math.max를 사용한 이유는 계단에 도착 했을 때 자신 앞 3번째 사람이 이미 내려갔을 경우는 dp[i-3] - 계단까지 거리를 하게 되면
 *    음수가 나온다. 이때는 기본적으로 대기해야 하는 시간 1을 적용하여 계산한다. 
 * 
 * 
 * 점화식 도출까지는 어렵진 않았으나, DP를 적용하기 위한 이전 단계의 구현이 굉장히 힘들었던 문제
 * 
 * 
 * Memory: 27,444KB / Time: 194ms
 */

public class SWEA_2383_점심식사시간_1_DP {

	static int T, N, flag, numOfPeople, minTime;
	
	static List<Node> peoplesInfo, stairsInfo;
	static List<Node> oneStairsGroup, twoStairsGroup;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		peoplesInfo = new ArrayList<>();
		stairsInfo = new ArrayList<>();
		oneStairsGroup = new ArrayList<>();
		twoStairsGroup = new ArrayList<>();
		
		T = Integer.parseInt(in.readLine());
		
		int t = 0;
		
		while(++t <= T) {
			
			N = Integer.parseInt(in.readLine());
			minTime = Integer.MAX_VALUE;
			
			// 초기화
			peoplesInfo.clear();
			stairsInfo.clear();
			
			inputInfo(in); // 사람, 계단 정보 입력
			
			calcStairsDistance(); // 각 계단에 대한 거리 계산
			
			numOfPeople = peoplesInfo.size(); // 사람 총 인원 수 계산
			
			flag = -1;
			int maxFlag = 1 << numOfPeople;
			
			while(++flag < maxFlag) {
				
				// 초기화
				oneStairsGroup.clear();
				twoStairsGroup.clear();
				
				divideTwoGroup(); // flag 변수에 따라 내려갈 계단 결정
				
				// 정렬
				oneStairsGroup.sort( (el1, el2) -> el1.d[0] - el2.d[0] );
				twoStairsGroup.sort( (el1, el2) -> el1.d[1] - el2.d[1] );
				
				minTime = Math.min(minTime, getTimeAllPeoplesGoesDown());
			}
			
			System.out.println("#" + t + " " + minTime);
		}
		
		in.close();
	}

	private static int getTimeAllPeoplesGoesDown() {
		int time = 0, goDownTime = 0, size = 0;
		
		int[] timeTable;
		
		goDownTime = stairsInfo.get(0).t; // 계단 내려가는데 걸리는 시간
		size = oneStairsGroup.size(); // 현재 그룹인원수
		timeTable = new int[size];
		
		for(int i = 0; i < size; i++) {
			Node people = oneStairsGroup.get(i);
			
			if(i < 3)
				// 계단까지 거리 + 내려가기전 기다리는 시간 + 내려가는 시간
				timeTable[i] = people.d[0] + 1 + goDownTime; 
			else
				// 계단까지 거리 + 내려가는 시간 + 이전 사람이 내려가기 까지 기다리는 시간
				timeTable[i] = people.d[0] + goDownTime + Math.max(1, timeTable[i - 3] - people.d[0]);
		}
		
		// 한쪽이 비어있는 경우도 있음
		if(size > 0) time = timeTable[size - 1];
		
		goDownTime = stairsInfo.get(1).t;
		size = twoStairsGroup.size();
		timeTable = new int[size];
		
		for(int i = 0; i < size; i++) {
			Node people = twoStairsGroup.get(i);
			
			if(i < 3)
				timeTable[i] = people.d[1] + 1 + goDownTime; 
			else
				timeTable[i] = people.d[1] + goDownTime + Math.max(1, timeTable[i - 3] - people.d[1]);
		}
		
		if(size > 0) time = Math.max(time, timeTable[size - 1]); // 마지막 사람 내려간 시간
		
		return time;
	}

	private static void inputInfo(BufferedReader in) throws IOException {
		StringTokenizer st = null;
		int tmp = 0;
		
		for(int y = 0; y < N; y++) {
			st = new StringTokenizer(in.readLine());
			
			for(int x = 0; x < N; x++) {
				tmp = Integer.parseInt(st.nextToken());
				
				if(tmp == 1)
					peoplesInfo.add(new Node(y, x));
				else if(tmp > 1)
					stairsInfo.add(new Node(y, x, tmp));
			}
		}
	}

	private static void calcStairsDistance() {
		Node stairs;
		
		for (Node people : peoplesInfo) {
			
			for(int i = 0; i < 2; i++) {
				stairs = stairsInfo.get(i);
				
				people.d[i] = Math.abs(people.y - stairs.y) + Math.abs(people.x - stairs.x);
			}
			
		}
	}
	
	private static void divideTwoGroup() {
		
		for(int i = 0; i < numOfPeople; i++) {
			if( (flag & 1 << i) == 0 )
				oneStairsGroup.add( peoplesInfo.get(i) );
			else 
				twoStairsGroup.add( peoplesInfo.get(i) );
		}
		
	}

	static class Node {
		int y, x; // y, x 좌표
		int t; // 계단 : 내려가는 시간
		int[] d;  // 사람 : 각 계단까지 거리

		public Node(int y, int x) {
			this.y = y;
			this.x = x;
			this.d = new int[2];
		}
		
		public Node(int y, int x, int t) {
			this.y = y;
			this.x = x;
			this.t = t;
		}
		
	}
}
