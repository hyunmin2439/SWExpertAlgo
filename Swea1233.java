package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Swea1233 {

	static int cnt, num, cal, res;
	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1233.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		for (int t = 1; t <= 10; t++) {
			res = 1;
			cnt = Integer.parseInt(br.readLine());
			
			for (int i = 1; i <= cnt; i++) {
				
				st = new StringTokenizer(br.readLine());
				num = Integer.parseInt(st.nextToken());
				cal = st.nextToken().charAt(0);
				
				// '*' 42 / '+' 43 / '-' 45 / '/' 47 / '0' 48
				// 왼쪽 자식이 있어야 오른쪽 자식도 있을 수 있음 때문에 왼쪽 자식만 체크하면 됨.
				// 자식이 있는데 연산자가 아니라면 || 자식이 없는데 숫자가 아니라면
				if( (num * 2 <= cnt && cal >= 48) || (num * 2 > cnt && cal < 48) ) { 
					// 남은 라인 읽고 버리기
					for (int j = i + 1; j <= cnt; j++) br.readLine();
					res = 0; break; 
				}
			}
			
			bw.write("#" + t + " " + res + "\n");
		}
		
		br.close();
		bw.close();
	}

}
