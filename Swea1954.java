package notSolved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// SW Expert 1954 달팽이 숫자
public class Swea1954 {
	static int n;
	static int[][] grid;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input1954.txt"));
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out) );
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for (int i = 1; i <= T; i++) {
			n = Integer.parseInt(br.readLine());
			grid = new int[n][n];
			snailGrid();
			sb.append("#").append(String.valueOf(i)).append("\n");
			printGrid(sb);
		}
		
		bw.write(sb.toString());
		br.close();
		bw.close();
	}
	
	//			   right down left up
	static int[] dy = { 0, 1,  0, -1 };
	static int[] dx = { 1, 0, -1,  0 };
	
	private static void snailGrid() {
		int num = 1; 			    // 채울 숫자
		int circle = n * 2 - 1;     // 전체 회전 수
		int depth = n;				// 한 방향 반복 수
		int y = 0, x = -1, dir = 0; // 시작 더해짐
		
		// 전체 회전
		for (int i = 1; i <= circle; i++) {
			
			// 한방향 채우기
			for (int j = 0; j < depth; j++) {
				y += dy[dir]; x += dx[dir];
				grid[y][x] = num;
				num++;
				// System.out.println("y : " + y + " x : " + x + " grid[y][x] : " + grid[y][x]);
			}
			
			dir++; // 방향 바꾸기
			if(dir > 3) dir = 0;
			
			if(i % 2 == 1) depth--; // 홀수번 끝나고 감소
		}
	}

	private static void printGrid(StringBuilder sb) {
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				sb.append(String.valueOf(grid[y][x])).append(" ");
			}
			sb.append("\n");
		}
	}

}
