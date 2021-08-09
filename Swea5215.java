package uploaded;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Swea5215 {
	
	static class Ingred {
		int satis;
		int calory;
		
		public Ingred(String satis, String calory) {
			this.satis = Integer.parseInt(satis);
			this.calory = Integer.parseInt(calory);
		}
	}
	
	static int T, N, L, maxSat;
	static Ingred[] inList;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input5215.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int t = 0;
		T = Integer.parseInt(br.readLine());
		while(t++ < T) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			inList = new Ingred[N];
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				inList[i] = new Ingred(st.nextToken(), st.nextToken());
			}
	
			maxSat = 0;
			find(0, 0, 0);
			
			bw.write("#" + t + " " + maxSat + "\n");
		}
		
		br.close();
		bw.close();
	}
	
	private static void find(int idx, int sat, int cal) {
		if(cal > L) return;
		
		if(idx == N) {
			maxSat = Math.max(maxSat, sat);
			return;
		}
		
		find(idx + 1, sat + inList[idx].satis, cal + inList[idx].calory); // 선택함
		find(idx + 1, sat, cal); // 선택 안함
	}
}
