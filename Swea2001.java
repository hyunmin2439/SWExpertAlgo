package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// 2001. 파리 퇴치
public class Swea2001 {

	static int T, N, M, maxFlyCnt;
	static int[][] area;
	
	public static void main(String[] args) throws IOException {
		// System.setIn( new FileInputStream("res/input2001.txt"));
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(System.out) );
		
		int t = 0;
		T = Integer.parseInt(br.readLine());
		
		while(t++ < T) {
			// init
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			maxFlyCnt = 0;
			area = new int[M][N]; // M라인 만큼의 메모리만 생성
			
			// 입력 받으면서 계산
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine());
				
				// 입력
				for (int x = 0; x < N; x++) {
					area[y % M][x] = Integer.parseInt(st.nextToken());
				}
				
				swing(); // max값 계산
			}
			
			bw.write("#" + t + " " + maxFlyCnt + "\n");
		}
		
		br.close();
		bw.close();
	}

	private static void swing() {
		int s = 0, e = M - 1;
		
		for (int i = 0; i <= N - M; i++) {
			int temp = 0;
			for (int y = 0; y <= M -1; y++) { // y축은 고정
				for (int x = s; x <= e; x++) { // x축은 가변
					temp += area[y][x];
				}
			}
			
			maxFlyCnt = Math.max(maxFlyCnt, temp);
			s++; e++; // x축 값 증가
		}
	}

}
