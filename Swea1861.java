package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// Depth First Search
public class Swea1861 {
	// num, max 출력 결과값
	static int T, N, Y, X, num, max;
	static int[][] area;
	
	static BufferedReader  br;
	static BufferedWriter  bw;
	static StringTokenizer st;
	static StringBuilder   sb;
	
	//			        상 하  좌 우
	static int[] dy = { -1, 1,  0, 0 };
	static int[] dx = {  0, 0, -1, 1 };
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1861.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		
		int t = 0;
		while(t++ < T) {
			N = Integer.parseInt(br.readLine());
			area = new int[N][N];

			num = Integer.MAX_VALUE; 
			max = Integer.MIN_VALUE;
			
			input();
			
			dest();
			
			sb.append("#" + t + " " + num + " " + max + "\n");
		}
		
		bw.write(sb.toString());
		br.close();
		bw.close();
	}
	
	private static void input() throws IOException {
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine());
			for (int x = 0; x < N; x++) {
				area[y][x] = Integer.parseInt(st.nextToken());
			}
		}
	}

	private static void dest() {
		for ( Y = 0 ; Y < N; Y++) {
			for ( X = 0; X < N; X++) {
				find(Y, X, 1);
			}
		}
	}
	
	private static void find(int y, int x, int cnt) {
		// delta값 활용 사방향 탐색
		for (int i = 0; i < 4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];
			if(ny < 0 || ny >= N || nx < 0 || nx >= N ) continue;
			
			// 현재 숫자보다 1크면 재귀 호출
			if(area[y][x] + 1 == area[ny][nx]) 
				find(ny, nx, cnt + 1);
		}
		
		// 기저 조건
		if(max == cnt) {
			num = Math.min(num, area[Y][X]); // num 작은값으로선택
		}
		else if(max < cnt) {
			// 파라미터의 y, x가 아닌 find함수가 시작 되는 좌표값
			num = area[Y][X];
			max = cnt;
		}
		
	}

}
