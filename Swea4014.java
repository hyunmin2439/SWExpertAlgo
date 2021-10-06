package algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 활주로 건설
 * 
 * 2차원 배열을 입력받아 행 열을 1차원 배열로 옮긴 뒤 각각 조건체크
 * 
 * 문제 자체는 단순하나, 조건들이 까다로워 헷갈리기 쉬운 문제
 * 
 * 문제에 주어진 제약사항 중 주의해야 하는 제약 사항 아래 두가지
 * 
 * 3. 경사로의 높이는 항상 1 이고, 길이 X 는 2 이상 4 이하의 정수이다. ( 2 ≤ X ≤ 4 )
 * 
 * 5. 동일한 셀에 두 개 이상의 경사로를 겹쳐서 사용할 수 없다.
 * 
 * 높이는 1로 고정이기 때문에 높이 차이가 2이상 나면 조건 체크를 그만두면 된다.
 * 
 * 또한, 경사로 설치시 길이가 2 ~ 4이기 때문에 이 부분을 신경써서 체크하면 된다.
 * 
 * 문제 풀때 5번 조건을 신경쓰지 못하고 풀었다가 Test Case 50중 35개를 맞고 fail을 맞았다.
 * 
 * 5번 조건을 신경 쓰지 않은체 단순히 조건 체크를 하다가는 경사로를 겹쳐서 짓는다고 판단하여 오류가 생길 수 있다.
 * 
 * Memory:22,888KB / Time:144ms
 */

public class Swea4014 {

	static int T, N, X, cnt;
	static int[][] map;
	static int[][] arr;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(in.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			arr = new int[2][N];
			
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(in.readLine());
				for (int x = 0; x < N; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			
			cnt = 0;
			
			// 활주로 건설 가능한지 확인
			for (int i = 0; i < N; i++) {
				// 행 열 각각 옮겨서
				for (int j = 0; j < N; j++) {
					arr[0][j] = map[i][j]; // 열
					arr[1][j] = map[j][i]; // 행
				}
				
				// 활주로를 건설할 수 있으면 cnt++
				if( check(arr[0]) ) cnt++;
				if( check(arr[1]) ) cnt++;
			}
			
			System.out.println("#" + t + " " + cnt);
		}
		
		in.close();
	}

	private static boolean check(int[] line) {
		boolean[] visit = new boolean[N]; // 경사로가 겹치지 않게 설치한곳 체크
		
		for (int i = 0; i < N - 1; i++) {
			int diff = Math.abs(line[i] - line[i + 1]); // 높이차

			if(diff == 0) continue; // 같은 높이면 아래 체크 할 필요 없음
			
			if(diff > 1) return false; // 높이가 2이상 차이면 불가
			
			// 차이가 1이면 경사로 설치 가능한지 확인
			
			// 오른쪽이 더 높으면 올라가는 경사로
			if(line[i] < line[i + 1]) {
				// i - X + 1 ~ i
				int height = line[i];
				visit[i] = true;
				for (int j = 1; j < X; j++) {
					// 남은 구간이 경사로 길이보다 짧거나 || 높이가 맞지 않거나 || 경사로가 이미 설치되어 있으면 설치불가
					if(i - j < 0 || height != line[i - j] || visit[i - j]) return false;
					visit[i - j] = true;
				}
				
				// 올라가는 경사로는 지나온 경로에 설치하기 때문에 X - 1만큼 건너뛰지 않음
			}
			// 왼쪽이 더 높으면 내려가는 경사로
			else {
				// i + 1 ~ i + X
				int height = line[i + 1];
				visit[i + 1] = true;
				for (int j = 2; j <= X; j++) {
					// 남은 구간이 경사로 길이보다 짧거나 || 높이가 맞지 않거나 || 경사로가 이미 설치되어 있으면 설치불가
					if(i + j >= N || height != line[i + j] || visit[i + j]) return false;
					visit[i + j] = true;
				}
				
				i += X - 1; // 경사로 설치한 부분은 건너뛰기
			}
			
		}
		
		return true; // 앞에서 return되지 않았으면 설치 가능한것
	}

}
