package swea_algo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Swea1228 {
	
	static int N, X, Y;
	static char inst;
	static List<String> list = new LinkedList<>();
	
	static StringTokenizer st;
	static StringBuilder   sb = new StringBuilder(); 
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1228.txt"));
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));   
		BufferedWriter  bw  = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int t = 0;
		while(++t <= 10) {
			// init
			list.clear();
			
			// 원본 암호문
			N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) list.add(st.nextToken());
			
			// 명령어
			N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				inst = st.nextToken().charAt(0);
				X = Integer.parseInt(st.nextToken());
				Y = Integer.parseInt(st.nextToken());
				changeCipTxt();				
			}
			
			sb.append("#").append(t).append(" ");
			for (int i = 0; i < 10; i++) {
				sb.append(list.get(i)).append(" ");
			}
			sb.setLength(sb.length() - 1);
			sb.append("\n");
		}
		
		bw.write(sb.toString());
		
		br.close();
		bw.close();
	}

	private static void changeCipTxt() {
		switch(inst) {
		case 'I': 
			List<String> temp = new LinkedList<>();
			for (int i = 0; i < Y; i++)
				temp.add(st.nextToken());
			list.addAll(X, temp);
		}
	}
}
