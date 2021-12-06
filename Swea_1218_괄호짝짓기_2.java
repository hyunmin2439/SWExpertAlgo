package notSolved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

// SW Expert Academy, nanoTime으로 재봤으나 차이없고 오히려 느릴 때 많음
// 즉, br.read()로 읽어서 시간을 줄이기보다 코드를 알기 쉽게 다듬는 것이 나아보임.
public class Swea_1218_괄호짝짓기_2 {
//							0    1    2    3    4    5    6    7
	static char[] list = { '(', '[', '{', '<', ')', ']', '}', '>' };
	
	public static void main(String[] args) throws IOException {
		long start = System.nanoTime();
		System.setIn( new FileInputStream("res/input1218.txt") );
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(System.out) );
		
		Stack<Character> stack = new Stack<>();
		
		int t = 0;
		while(++t <= 10) {
			stack.clear();
			int cnt = Integer.parseInt(br.readLine());
			int idx = 0, check = 1;
			
			while(cnt-- > 0) {
				char paren = (char) br.read();
				// 어느 괄호인지 판단
				for (int i = 0; i < 8; i++) {
					if(paren == list[i]) { idx = i; break; }
				}

				// idx 4 미만 여는 괄호, 4 이상 닫는 괄호 따로 처리
				if (idx < 4) stack.push(paren);
				else if (stack.pop() != list[idx - 4]) { // -4를 할 경우 자기 자신
					// 제일 마지막에 나온 여는 괄호가 먼저 닫혀야 함.
					// 먼저 닫히지 않으면 그건 맞지 않는 괄호 라인
					check = 0; break;
				}
			}
			
			if( !stack.isEmpty() ) check = 0;
			
			br.readLine(); // 중간에 break를 해서 나오든 다 읽어서 나오든 
						   // \n을 읽어야 하기 때문에 readLine을 해줘야 한다.
			
			bw.write("#" + t + " " + check + "\n");
		}
		
		System.out.println( ((double)System.nanoTime() - start) / 1000000000 );
		
		br.close();
		bw.close();
	}

}
