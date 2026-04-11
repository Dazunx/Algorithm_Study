import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 숫자X 를 입력받는다.
	 * 2. 1부터 X까지 순회한다. 
	 * 		2-1. 3으로 나누어 떨어질 때, 2로 나누어 떨어질 때, -1 했을 때 연산 횟수의 최솟값을 구하여 저장한다. 
	 */
	
	static int[] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int X = Integer.parseInt(br.readLine());
		
		dp = new int[X+1];
		dp[1] = 0;
		
		for(int num=2; num<=X; num++) {		
			dp[num] = Integer.MAX_VALUE;
			
			if(num%3==0) dp[num] = Math.min(dp[num/3]+1, dp[num]);
			if(num%2==0) dp[num] = Math.min(dp[num/2]+1, dp[num]);
			dp[num] = Math.min(dp[num-1]+1, dp[num]);
		}
		
		System.out.println(dp[X]);
	}

}
