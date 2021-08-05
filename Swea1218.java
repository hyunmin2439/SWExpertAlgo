package notSolved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

public class Swea1218 {
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
			br.readLine(); // 괄호 개수 버리기
			int idx = 0, check = 1;
			
			for (char paren : br.readLine().toCharArray()) {
				
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
			
			// 다 걸리지 않았어도, stack이 비어있지 않으면 맞지 않는것.
			if( !stack.isEmpty() ) check = 0; // 안 해도 정답, 하지만 논리에 맞기 하기 위해 추가
			
			bw.write("#" + t + " " + check + "\n");
		}
		
		System.out.println( ((double)System.nanoTime() - start) / 1000000000 );
		br.close();
		bw.close();
	}

}
