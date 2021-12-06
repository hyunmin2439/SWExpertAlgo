package uploaded;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class SWEA_1204_최빈수구하기 {

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out) );

		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
				
		int T = Integer.parseInt(br.readLine().trim());
		int t = 1, scores[], maxCnt = Integer.MIN_VALUE, fre = 0;
		
		while(t < T) {
			scores = new int[101]; maxCnt = 0; fre = 0;
			t = Integer.parseInt(br.readLine().trim());
			st = new StringTokenizer(br.readLine().trim());
			
			for (int i = 0; i < 1000; i++) {
				scores[Integer.parseInt(st.nextToken().trim())]++;				
			}
			
			for (int i = 0; i < scores.length; i++) {
				if(scores[i] >= maxCnt) {
					maxCnt = scores[i];
					fre = i;
				}
			}
			
			sb.append("#" + t + " " + fre + "\n");
		}
		
		bw.write(sb.toString());
		br.close();
		bw.close();
	}
	
}
