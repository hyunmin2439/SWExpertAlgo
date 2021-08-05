package notSolved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Swea1225 {

	static int t;
	static Queue<Integer> queue;
	static StringTokenizer st;
	static StringBuilder sb;
	static BufferedReader br;
	static BufferedWriter bw;
	
	public static void main(String[] args) throws IOException {
		// init
		System.setIn( new FileInputStream("res/input1225.txt") );
		br = new BufferedReader( new InputStreamReader(System.in) );
		bw = new BufferedWriter( new OutputStreamWriter(System.out) );
		sb = new StringBuilder();
		queue = new LinkedList<>();
		
		// 여기서는 각각 8개의 원소라고 고정되어 있기 때문에 queue.clear 필요 없다.
		// 만약 초기화를 해야 한다고 하면 
		// = new LinkedList로 생성하는 것보다 queue.clear();가 조금 더 유리
		
		while(t++ < 10) {
			input(); // 입력
			
			createPw(); // 암호 생성
			
			printQueue(); // 출력문 담기
		}
		
		bw.write(sb.toString());
		br.close();
		bw.close();
	}

	private static void input() throws IOException {
		br.readLine(); // t입력 버리기
		st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < 8; i++) {
			queue.offer(Integer.parseInt(st.nextToken()));				
		}
	}

	private static void createPw() {
		int num = 0;
		
		while(true) {
			for (int i = 1; i <= 5; i++) {
				num = queue.poll() - i;
				
				if( num <= 0 ) {
					queue.offer(0);
					return; // 종료
				}
				
				queue.offer(num);
			}	
		}
		
	}
	
	private static void printQueue() {
		sb.append("#").append(t).append(" ");
		for (int i = 0; i < 8; i++) {
			sb.append(queue.poll()).append(" ");
		}
		sb.append("\n");
	}
}