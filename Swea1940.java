package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swea1940 {

	static int T, N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= 10; t++) {
			int speed = 0, dist = 0;
			N = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int type = Integer.parseInt(st.nextToken());
				switch(type) {
				case 1: speed += Integer.parseInt(st.nextToken()); break;
				case 2: speed -= Integer.parseInt(st.nextToken()); break;
				}
				if(speed < 0) speed = 0;
				
				dist += speed;
			}
			
			System.out.println("#" + t + " " + dist);
		}
		
		
		br.close();
	}

}
