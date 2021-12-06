package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class SWEA_1210_Ladder1 {

	static boolean[][] ladder;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1210.txt"));
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out) );
		
		int t = 0;
		while(t < 10) {
			// init
			direc = 0;
			t = Integer.parseInt(br.readLine());
			ladder = new boolean[100][100];
			
			for (int y = 0; y < 100; y++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int x = 0; x < 100; x++) {
					String token = st.nextToken();
					if(token.equals("1")) ladder[y][x] = true;
					else if(token.equals("2")) res = x; // 당첨 위치 res에 임시 저장, 
					// 연산 손해인듯 저장해놓고 마지막 줄만 돌아서 2를 찾는 것이 더 나은 듯하다.
					// 여기서는 boolean 배열로 사용하여 수정하기가 애매함.
				}
			}
			
			moveLadder();
			
			bw.write("#" + t + " " + res + "\n");
		}
		
		br.close();
		bw.close();
	}

	static int direc, res; // 방향, 결과값
					 // 상 좌 우
	static int[] dy = { -1,  0, 0 };
	static int[] dx = {  0, -1, 1 };
	
	private static void moveLadder() {
		int y = 99, x = res;
		
		while(true) {
			// 반복문 탈출 조건
			if(y == 0) break;
			
			// 방향 변경
			switch(direc) {
			case 0: // 상
				if(x > 0 && ladder[y][x - 1]) direc = 1; // 좌로 변경
				
				if(x < 99 && ladder[y][x + 1]) direc = 2; // 우로 변경		
				break;
			case 1: case 2: // 좌, 우
				if(ladder[y - 1][x]) direc = 0; // 상으로 변경
												// y는 0이면 종료되기 때문에 y > 0 조건 체크 할 필요X
				break;
			}
			
			// 이동
			y += dy[direc];
			x += dx[direc];
		}
		
		res = x; // 당첨 -> 출발점 : 최종 도착 위치
	}

}
