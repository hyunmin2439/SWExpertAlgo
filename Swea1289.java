package notSolved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//Second Solve
public class Swea1289 {
	static char memory[];

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int T = Integer.parseInt(br.readLine().trim());
		int t = 0, cnt = 0;

		while (t++ < T) {
			char check = '0';
			cnt = 0;
			memory = br.readLine().trim().toCharArray();
			for (int i = 0; i < memory.length; i++) {
				if (memory[i] != check) {
					check = memory[i];
					cnt++;
				}
			}

			bw.write("#" + t + " " + cnt + "\n");

		}

		br.close();
		bw.close();
	}
}

// first Solve
//public class Swea1289 {
//	static char memory[], preState[];
//
//	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("res/input.txt"));
//		BufferedReader br = new BufferedReader( new InputStreamReader( System.in) );
//		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out) );
//
//		StringBuilder sb = new StringBuilder();
//				
//		int T = Integer.parseInt(br.readLine().trim());
//		int t = 0, cnt = 0;
//		
//		while(t++ < T) {
//			cnt = 0;
//			memory = br.readLine().trim().toCharArray();
//			preState = new char[memory.length];
//			for(int i = 0; i < preState.length; i++) 
//				preState[i] = '0';
//			
//			for (int idx = 0; idx < memory.length; idx++) {
//				if(memory[idx] != preState[idx]) {
//					changeBit(idx, memory[idx]);
//					cnt++;
//				}
//			}
//			
//			// sb.append("#" + t + " " + cnt + "\n"); // 아래처럼 하는 것이 좋다.
//			sb.append("#").append(t).append(" ").append(cnt).append("\n");
//			// StringBuilder를 사용하는 것이 메모리를 계속 잡고 있기 때문에 더 손해이다.
//			// 문제별로 이득인 부분이 있고 이득이 아닌 부분이 있다. 잘 판단해서 사용할 것
//		}
//		
//		
//		bw.write(sb.toString());
//		br.close();
//		bw.close();
//	}
//
//	private static void changeBit(int idx, char bit) {
//		for(int i = idx; i < preState.length; i++) 
//			preState[i] = bit;
//	}
//	
//}