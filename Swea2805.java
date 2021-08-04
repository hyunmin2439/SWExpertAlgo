package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// 2805. 농작물 수확하기
public class Swea2805 {
	
	static int T, N;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input2805.txt"));
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(System.out) );
		
		int t = 0;
		T = Integer.parseInt(br.readLine());
		
		while(t++ < T) {
			N = Integer.parseInt(br.readLine());
			
			int res = 0;
			int mid = N / 2;
			int start = mid;
			int end = mid;
			int sw = 1;
			
			for (int y = 0; y < N; y++) {
				// 2차원 배열에 저장하지 말고 한줄씩 읽고 바로 처리하면 메모리 절약
				char[] area = br.readLine().toCharArray();
				
				for (int x = start; x <= end; x++) {
					res += area[x] - '0';
				}
				
				if(y == mid) sw *= -1;
				start -= sw; end += sw;
			}
			
			bw.write("#" + t + " " + res + "\n");
		}
		
		
		br.close();
		bw.close();
	}
}
