package solved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

// 후위 표기식 계산 알고리즘
public class Swea1223 {

	static int N, res;
	static char[] arr;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int t = 0;
		while(t++ < 10) {
			res = 0;
			N = Integer.parseInt(br.readLine());
			arr = new char[N];

			changePost(br.readLine());
			
			calc();
			
			System.out.println("#" + t + " " + res);
		}
		
		br.close();
	}

	private static void changePost(String line) {
		int idx = 0; // 배열에 담을 인덱스
		Stack<Character> stack = new Stack<>();
		
		for (int i = 0; i < N; i++) {
			// ASCII * : 74 / + : 75 / 0 : 80
			char c = line.charAt(i);
			// 숫자면 배열에 담음
			if(c >= '0') arr[idx++] = c;
			// 스택의 연산자의 우선 순위가 높거나 같다면 스택에서 빼고 현재 연산자 넣기
			else if( !stack.isEmpty() && stack.peek() <= c ) {
				arr[idx++] = stack.pop();				
				stack.push(c);
			}
			// 스택이 비어있거나 || 스택의 연산자 보다 현재 연산자 우선순위가 높다면 그냥 담음
			else stack.push(c);
		}
		
		// 남은 연산자 배열에 담기
		while(!stack.isEmpty()){
			arr[idx++] = stack.pop();
		}
	}

	private static void calc() {
		int[] num = new int[3];
		Stack<Integer> stack = new Stack<>();
		
		for (int i = 0; i < N ; i++) {
			// ASCII * : 74 / + : 75 / 0 : 80
			char c = arr[i];
			// 숫자면 담기
			if(c >= '0') stack.push((int)c - '0'); 
			// + *면 연산
			else {
				// + *는 상관없지만 추후 빼기 나누기면 순서를 중요시해야 함
				num[1] = stack.pop();
				num[0] = stack.pop();
				num[2] = c == '+' ? num[0] + num[1] : num[0] * num[1];
				stack.push(num[2]);
			}
		}
		
		res = stack.pop();
	}

}
