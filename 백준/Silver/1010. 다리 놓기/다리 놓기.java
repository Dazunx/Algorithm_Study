import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author DajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 테스트케이스 개수를 입력받는다.
	 * 2. dp 배열을 계산한다. 파스칼의 삼각형에서 쓰이는 공식을 점화식으로 하여 DP로 풀어낸다. 
	 * 3. 테스트케이스마다, 
	 * 		3-1. 서쪽의 사이트 N, 동쪽의 사이트 M을 입력받는다. 
	 * 		3-2. dp 배열에 저장해둔 결과를 출력한다. 
	 * 
	 */
	
	static int leftSiteCount, rightSiteCount;
	public static void main(String[] args) throws IOException {
		// 1. 테스트케이스 개수를 입력받는다.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		// 2. dp 배열을 계산한다. 파스칼의 삼각형에서 쓰이는 공식을 점화식으로 하여 DP로 풀어낸다. 
		int[][] dp = new int[31][31];
		
		for(int i=0; i<=30; i++) {
			for(int j=0; j<=i; j++) {
				if(j==0 || j==i) dp[i][j] = 1;
				else dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
			}
		}
		// 3. 테스트케이스마다, 
		for(int test_case=1; test_case<=T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 3-1. 서쪽의 사이트 N, 동쪽의 사이트 M을 입력받는다. 
			leftSiteCount = Integer.parseInt(st.nextToken());
			rightSiteCount = Integer.parseInt(st.nextToken());
			
			// 3-2. dp 배열에 저장해둔 결과를 출력한다. 
			System.out.println(dp[rightSiteCount][leftSiteCount]);
		}
	}

}
