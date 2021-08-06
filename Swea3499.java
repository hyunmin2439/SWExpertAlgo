package uploaded;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Swea3499 {
	static String[] 	   line;
	static BufferedReader 	 br;
	static BufferedWriter 	 bw;
	static StringBuilder 	 sb;

	public static void main(String[] args) throws IOException {
		System.setIn( new FileInputStream("res/input3499.txt") );
		br = new BufferedReader( new InputStreamReader(System.in) );
		bw = new BufferedWriter( new OutputStreamWriter(System.out) );
		sb = new StringBuilder();
		
		int t = 0;
		int T = Integer.parseInt(br.readLine());
		
		while(t++ < T) {
			// input & init
			
			// 숫자 개수 읽고 버리기
			br.readLine();
			// 각 단어 읽고 쪼개기
			line = br.readLine().trim().split(" ");
			
			// test case
			sb.append("#").append(t).append(" ");
			// shuffle
			perfectShuffle();
			// end case, next line
			sb.append("\n");	
		}
		
		bw.write(sb.toString());
		
		br.close();
		bw.close();		
	}
	
	private static void perfectShuffle() {
		boolean isEven = line.length % 2 == 0;
		int mid = line.length / 2 + (isEven ? 0 : 1);
		int end = mid - (isEven ? 0 : 1);
		
		// 결과 StringBuilder 바로 추가
		for (int i = 0; i < end; i++) {
			sb.append(line[i]).append(" ");
			sb.append(line[i + mid]).append(" ");
		}
		
		if( !isEven ) sb.append(line[end]); // 홀수일 때 원소 하나더 추가
		else sb.setLength(sb.length() - 1); // 짝수일 때 마지막 공백 제거
	}
}