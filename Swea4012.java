package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Swea4012 {
	
	static int T, N, dif;
	static int[][] table;
	static int[] ingred;
	
	static BufferedReader br;
	static BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input4012.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		T = Integer.parseInt(br.readLine());
		int t = 0;
		while(t++ < T) {
			// input & init
			dif = Integer.MAX_VALUE;
			N = Integer.parseInt(br.readLine());
			table = new int[N][N];
			ingred = new int[N];
			ingredInit();
			tableInput();
			
			do {
				cook();
				
			} while(np());
			
			bw.write("#" + t + " " + dif + "\n");
		}
		
		br.close();
		bw.close();
	}

	private static void ingredInit() {
		for (int i = N - 1; i >= N / 2; i--) {
			ingred[i] = 1;
		}
	}

	private static void tableInput() throws IOException {
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				// 같을 경우 어짜피 0이라서 입력 받을 필요 없지만 번거롭고 코드 복잡해지기 때문에 <=로 통합
				if(i <= j) table[i][j] = Integer.parseInt(st.nextToken());
				else table[j][i] += Integer.parseInt(st.nextToken());
			}
		}
	}
	
	private static void cook() {
		int[] idx = new int[2];
		int[] sum = new int[2];
		int[][] foods = new int[2][N / 2];
		
		for (int i = 0; i < N; i++) {
			if(ingred[i] == 0) foods[0][idx[0]++] = i;
			else foods[1][idx[1]++] = i;
		}
		
		for (int i = 0; i < 2; i++) { // 두 요리
			for (int f = 0; f < N / 2; f++) { // 첫번째 음식
				for (int s = f + 1; s < N / 2; s++) { // 두번째 음식
					sum[i] += table[foods[i][f]][foods[i][s]];
				}
			}	
		}
		
		dif = Math.min(dif, Math.abs(sum[0] - sum[1]));
	}

	private static boolean np() {
		
		int i = N - 1;
		while(i > 0 && ingred[i - 1] >= ingred[i]) i--;
		
		if(i == 0) return false;
		
		int j = N - 1;
		while(ingred[i - 1] >= ingred[j]) j--;
		swap(i - 1, j);
		
		int k = N - 1;
		while(i < k) {
			swap(i++, k--);
		}
		
		return true;
	}

	private static void swap(int i, int j) {
		int temp = ingred[i];
		ingred[i] = ingred[j];
		ingred[j] = temp;
	}

}
