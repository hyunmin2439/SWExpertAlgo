package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Swea1233_2 {

	static int cnt, num, cal, res;
	static int[] bTree;
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1233.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		for (int t = 1; t <= 10; t++) {
			cnt = Integer.parseInt(br.readLine());
			bTree = new int[cnt + 1];
			res = 1;
			
			for (int i = 1; i <= cnt; i++) {
				st = new StringTokenizer(br.readLine());
				num = Integer.parseInt(st.nextToken());
				cal = st.nextToken().charAt(0);
				
				bTree[num] = cal;
			}
			
			checkVaild(1);
			
			bw.write("#" + t + " " + res + "\n");
		}
		
		br.close();
		bw.close();
	}

	private static void checkVaild(int idx) {
		if(res == 0 && idx > cnt) return;
		
		boolean leftCheck = idx * 2 <= cnt;
		boolean rightCheck = idx * 2 + 1 <= cnt;
		
		if( (leftCheck && bTree[idx] >= 48) || (!leftCheck && bTree[idx] < 48) ) {
			res = 0; return;
		}
		
		if(leftCheck) checkVaild(idx * 2);
		if(rightCheck) checkVaild(idx * 2 + 1);
	}
}