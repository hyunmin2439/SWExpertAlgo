package myAlgo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 규영이와 인영이의 카드게임
public class Swea6808 {

	static int T, win, lose;
	static int[] guyung = new int[9];
	static int[] inyung = new int[9]; // 변화
	static int[] oriInyung	= new int[9];
	static boolean[] counting = new boolean[18];
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input6808.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int t = 0;
		T = Integer.parseInt(br.readLine());
		
		while(t++ < T) {
			// input & init
			win = 0; lose = 0;
			Arrays.fill(counting, false); // false로 값 초기화
			
			// guyung 카드 배열 초기화
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 9; i++) {
				guyung[i] = Integer.parseInt(st.nextToken());
				counting[ guyung[i] - 1 ] = true;
			}
			// inyung 카드 배열 초기화
			int idx = 0;
			for (int i = 0; i < 18; i++) {
				if( !counting[i] ) {
					oriInyung[idx] = i + 1; 
					idx++;
				}
			}
			permu(0, 0);
			System.out.println("#" + t + " " + win + " " + lose);
		}
		
		br.close();
	}

	private static void permu(int idx, int flag) {
		if(idx == 9) {
			checkWinner();
			return;
		}
		
		for (int i = 0; i < 9; i++) {
			if( (flag & 1 << i) != 0 ) continue;
			inyung[idx] = oriInyung[i];
			permu(idx + 1, flag | 1 << i);
		}
	}
	
	private static void checkWinner() {
		int guScore = 0, inScore = 0;
		
		// 각 카드 마다 이기는 쪽에 점수 더해주기
		for (int i = 0; i < 9; i++) {
			int sum = guyung[i] + inyung[i];
			if( guyung[i] > inyung[i] ) guScore += sum;
			else inScore += sum;
		}
		
		if(guScore > inScore) win++;
		else if(guScore < inScore) lose++;
	}
}
