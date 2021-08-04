package solved;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//.	평지(전차가 들어갈 수 있다.)
//*	벽돌로 만들어진 벽
//#	강철로 만들어진 벽
//-	물(전차는 들어갈 수 없다.)
//^	위쪽을 바라보는 전차(아래는 평지이다.)
//v	아래쪽을 바라보는 전차(아래는 평지이다.)
//<	왼쪽을 바라보는 전차(아래는 평지이다.)
//>	오른쪽을 바라보는 전차(아래는 평지이다.)

//U	Up : 전차가 바라보는 방향을 위쪽으로 바꾸고, 한 칸 위의 칸이 평지라면 위 그 칸으로 이동한다.
//D	Down : 전차가 바라보는 방향을 아래쪽으로 바꾸고, 한 칸 아래의 칸이 평지라면 그 칸으로 이동한다.
//L	Left : 전차가 바라보는 방향을 왼쪽으로 바꾸고, 한 칸 왼쪽의 칸이 평지라면 그 칸으로 이동한다.
//R	Right : 전차가 바라보는 방향을 오른쪽으로 바꾸고, 한 칸 오른쪽의 칸이 평지라면 그 칸으로 이동한다.
//S	Shoot : 전차가 현재 바라보고 있는 방향으로 포탄을 발사한다.


// 1873. 상호의 배틀필드 // 시뮬레이션 문제 => 급하게 풀지 말고 꼼꼼히 풀어야 한다.
public class Swea1873 {
	
	static int H, W, Y, X, dir, comCnt;
						// 상   하   좌   우
	static char[] tank = { '^', 'v', '<', '>' };
	static int[] 	dy = { -1,   1,   0,   0 };
	static int[] 	dx = {  0,   0,  -1,   1 };
	
	static char[][] area;
	static char[] command;
	
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1873.txt"));
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(System.out) );
		
		int t = 0;
		int T = Integer.parseInt(br.readLine());

		while(t++ < T) {
			// input & init
			String[] line = br.readLine().split(" ");
			H = Integer.parseInt(line[0]);
			W = Integer.parseInt(line[1]);
			area = new char[H][];
			
			for (int y = 0; y < H; y++) {
				area[y] = br.readLine().toCharArray();				
			}
			
			comCnt = Integer.parseInt(br.readLine());
			command = br.readLine().toCharArray();
			
			// inputPrint();
			findTank();
			executeCommand();
			printArea(t);
		}
		
		bw.write(sb.toString());
		br.close();
		bw.close();
	}

	private static void findTank() {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				for (int i = 0; i < tank.length; i++) {
					if(area[y][x] == tank[i]) {
						Y = y; X = x; dir = i; return;
					}
				}
			}
		}
	}

	private static void executeCommand() {	
		for (char com : command) {
			if(com == 'S') shoot();
			else move(com);
		}
	}
	
	private static void shoot() {
		int ty = Y, tx = X; // 포탄의 위치
		
		while(true) {
			// 포탄 이동
			ty += dy[dir]; tx += dx[dir];
			
			// 범위 벗어나거나 강철벽이면 탈출
			if( ty < 0 || ty >= H || 
				tx < 0 || tx >= W || 
				area[ty][tx] == '#' ) break;
			
			// 벽돌이면 평지로
			if( area[ty][tx] == '*' ) {
				area[ty][tx] = '.'; break;
			}
		}
	}

	private static void move(char com) {
		// 방향 값 변경
		switch(com) {
		case 'U': dir = 0; break;
		case 'D': dir = 1; break;
		case 'L': dir = 2; break;
		case 'R': dir = 3; break;
		}
		// 전차 방향바꾸기
		area[Y][X] = tank[dir];
		
		// 전차 이동할 자리
		int ty = Y + dy[dir], tx = X + dx[dir];
		
		// 범위 벗어나면 탈출
		if( ty < 0 || ty >= H || tx < 0 || tx >= W ) return;
		
		// 전차 이동
		if(area[ty][tx] == '.') {
			area[ty][tx] = area[Y][X];
			area[Y][X] = '.';
			Y = ty; X = tx;
		}
	}
	
	// 최종 출력
	private static void printArea(int t) {
		sb.append("#").append(t).append(" ");
		
		for (int y = 0; y < H; y++) {	
			sb.append(area[y]).append("\n");
		}
	}
	
	// 입력값 테스트
	private static void inputPrint() {
		System.out.println("H : " + H + " / W : " + W);
		for (int y = 0; y < H; y++) {
			System.out.println(area[y]);
		}
		System.out.println(comCnt);
		System.out.println(command);
		System.out.println();
	}

}
