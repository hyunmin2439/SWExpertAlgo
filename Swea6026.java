package solved;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 중복순열
 * 
 * 비밀번호 N자리, 키보드 M개의 키
 * 
 * M개의 키를 모두 써서 N자리 비밀번호 맞춰야 한다.
 * 
 * ex) N = 3, M = 2, 2개(a, b)의 키로 N자리 비밀번호 맞출 수 있는 경우의 수
 * 
 * 2^3(전체 경우의 수) - 2(1개의 키만 사용한 경우의 수) = 6
 * 
 * aab, aba, baa, abb, bab, bba / aaa, bbb
 * 
 * 전사 개념 활용
 * 
 * m^n - (전사가 아닐 때) => m^n - (일부만 선택)
 * 
 * 전사 개념의 정리된 식 => mCm * m^n - mC(m-1) * (m-1)^n + mC(m-2) * (m-2)^n - ... mC1 * 1^n
 * 
 * 추가적으로 페르마의 소정리 개념 활용
 * 
 * 조합의 경우의 수를 구하기 위한 것
 * 
 * 다른 방법으로 조합 경우의 수를 구해도 상관 없다
 * 
 * 페르마의 소정리
 * 
 * a^(p-2) ≡ a^(-1) (mod p)
 * 
 * nCk => n! / (k! * (n-k)!) => n! / a => n! * a^(-1) (mod p) => n! * ( (n-k)! * k! )^(p-2) (mod p)
 */

public class Swea6026 {
	
	static final int MOD = 1_000_000_007;
	
	static int T, M, N;
	static long res;
	
	static long[] fact;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(in.readLine());
		
		// 팩토리얼 값 미리 다 구해놓기
		fact = new long[101];
		for (int i = 0; i <= 100; i++) {
			fact[i] = calcFactorial(i);
		}
		
		for (int t = 1; t <= T; t++) {			
			StringTokenizer st = new StringTokenizer(in.readLine());
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			
			res = 0;
			
			// sw는 + - 번갈아 가며 바꿔 줄 기호
			for (int i = 0, sw = 1; i < M; i++, sw *= -1) {
				long num = (comb(M - i) * pow(M - i, N)) % MOD;
				res = ( res + sw * num + MOD ) % MOD;
				// MOD 더해주는 이유는 모듈러 연산 때문에 결과가 음수값이 될 수도 있기 때문
			}
			
			System.out.println("#" + t + " " + res);
		}
		
		in.close();
	}
	
	private static long calcFactorial(int m) {
		long res = 1;
		
		for (int i = 2; i <= m; i++) {
			res *= i;
			res %= MOD;
		}
		
		return res;
	}
	
	private static long comb(int k) {
		// n! * ( (n-k)! * k! )^(p-2) (mod p)
		return ( fact[M] * pow((fact[M - k] * fact[k]) % MOD, MOD - 2) ) % MOD;
	}

	private static long pow(long m, long e) {
		long res = 1;
		
		while(e > 0) {
			if(e % 2 == 1)
				res = (res * m) % MOD;
			
			e >>= 1;
			
			m = (m * m) % MOD;
		}
		
		return res;
	}

}
