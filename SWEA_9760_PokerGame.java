package solved;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
1. Straight Flush : 모두 동일한 슈트에 랭크(값)가 모두 연속적이다(여기서는 로얄 플러쉬를 포함한다. 로얄 플러쉬는 모두 동일한 슈트에 T, J, Q, K, A를 갖는다).
2. Four of a Kind : 5장 중 4개의 랭크(값)가 모두 같다.
3. Full House : 3장의 동일한 랭크(값)와 다른 랭크(값)의 동일한 2장으로 구성된다.
4. Flush : 5장이 모두 동일한 슈트다.
5. Straight : 다른 슈트가 섞여있으며 랭크(값)가 모두 연속적이다.
6. Three of a kind : 동일한 랭크(값)가 3장이다(1,2,3,3,3).
7. Two pair : 동일한 랭크(값) 2장씩 두개의 랭크다(2,6,6,3,3).
8. One pair : 동일한 랭크가 2장이다(2,4,5,5,7).
9. High card : 1~8번에 해당하지 않는다.  
*/

public class SWEA_9760_PokerGame {

	static int T;
	static char[] suit = {'S', 'D', 'H', 'C'};
	static char[] rank = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
	static int[] suitCnt, rankCnt;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(in.readLine());
		
		for (int t = 1; t <= T; t++) {
			suitCnt = new int[4];
			rankCnt = new int[14]; // 0 dummy
			
			String[] card = in.readLine().split(" ");
			
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < suitCnt.length; j++) {
					// 카드 모양이 일치하면
					if( suit[j] == card[i].charAt(0) ) suitCnt[j]++;
				}
				
				for (int j = 0; j < rank.length; j++) {
					// 숫자가 일치하면
					if( rank[j] == card[i].charAt(1) ) rankCnt[j + 1]++;
				}
			}
			
			boolean Flush = false;
			boolean Straight = false;
			boolean FourOfaKind = false;
			boolean ThreeOfaKind = false;
			boolean TwoPair = false;
			boolean OnePair = false;
			
			// 플러시 체크
			for (int i = 0; i < 4; i++) {
				if( suitCnt[i] == 5 ) {
					Flush = true; 
					break;
				}
			}
			
			// 스트레이트 체크
			// 3, 4, 5, 6, 7
			for (int i = 1; i <= 9; i++) {
				// rankCnt[3]... 4 5 6 7
				if( rankCnt[i] * rankCnt[i + 1] * rankCnt[i + 2] * rankCnt[i + 3] * rankCnt[i + 4] == 1) {
					Straight = true;
					break;
				}
			}
			
			// 4 ~ 1 카드 체크
			for (int i = 1; i <= 13; i++) {
				if( rankCnt[i] == 4 ) FourOfaKind = true;
				else if( rankCnt[i] == 3 ) ThreeOfaKind = true;
				else if( rankCnt[i] == 2 ) {
					if( !OnePair ) OnePair = true;
					else TwoPair = true;
				}
			}
			
			String res = "";
			if( Flush && Straight ) res = "Straight Flush";
			else if( FourOfaKind ) res = "Four of a Kind";
			else if( ThreeOfaKind && OnePair ) res = "Full House";
			else if( Flush ) res = "Flush";
			else if( Straight ) res = "Straight";
			else if( ThreeOfaKind ) res = "Three of a kind";
			else if( TwoPair ) res = "Two pair";
			else if( OnePair ) res = "One pair";
			else res = "High Card";
			
			System.out.println("#" + t + " " + res);
		}
		in.close();
	}

}
