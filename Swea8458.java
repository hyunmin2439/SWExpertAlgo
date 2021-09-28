package algo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Swea8458 {
	
	static int T, N;
	static int[] arr; 
	static boolean[] isEven;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(in.readLine());
		
		// 최대 10개의 점
		arr = new int[10]; 
		isEven = new boolean[10];
		
		for (int t = 1; t <= T; t++) {
			long res = 0;
			
			N = Integer.parseInt(in.readLine());
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(in.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				// 거리 계산
				arr[i] = dist(x, y);
				
				// 짝수 홀수 판단
				isEven[i] = checkEven(arr[i]); 
			}
			
			// 전부 홀수거나 짝수가 아닌 경우
			if( !isAllSameOddEven() ) {
				System.out.println("#" + t + " " + -1);
				continue;
			}
			
			// 정렬 : 0부터 N-1까지
			Arrays.sort(arr, 0, N);
			
			// 몇번 움직이면 원점으로 돌아올 수 있는지 체크
			res = checkMoveCnt(arr[N - 1]);
			
			System.out.println("#" + t + " " + res);
		}
		
		in.close();
	}

	// 원점과의 거리, 최대 2 * 10^9 = 20억 => int
	private static int dist(int x, int y) {
		return Math.abs(x) + Math.abs(y);
	}
	
	// 짝수인지 판별
	private static boolean checkEven(int num) {
		return (num % 2 == 0) ? true : false;
	}

	// 전부 동일한 true false 값을 가지는지 체크
	private static boolean isAllSameOddEven() {
		for (int i = 1; i < N; i++) {
			if(isEven[i - 1] != isEven[i]) return false;
		}
		
		return true;
	}
	
	// 몇번 움직이면 원점으로 돌아올 수 있는지 체크
	private static long checkMoveCnt(int num) {
		// 연산 조금이라도 줄이기 위해
		long cnt = (int) Math.sqrt(num);
		
		while( true ) {
			// 가우스 공식, i번 움직임으로 최대 이동할 수 있는 거리
			// long으로 하지 않으면 계산하다가 오버플로우 발생
			long ld = cnt * (cnt + 1) / 2;

			// 홀수이면 홀수만큼, 짝수이면 짝수만큼 이동 가능
			boolean ldIsEven = ld % 2 == 0;
			
			// 조건 만족하면 빠져나감
			if( ld >= num && ldIsEven == isEven[0] ) break;
			
			cnt++;
		}
		
		return cnt;
	}
}