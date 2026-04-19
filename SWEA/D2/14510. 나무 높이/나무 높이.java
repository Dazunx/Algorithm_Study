import java.io.*;
import java.util.*;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int treeCount;
	static int[] treeHeight;	
	static int maxHeight;
	
	static int twoDays;
	static int oneDays;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			init();
			countDiff();
			sb.append(String.format("#%d %d\n", test_case, calcPeriod()));
		}
		System.out.println(sb);
	}
	
	public static void init() throws IOException {
		treeCount = Integer.parseInt(br.readLine());
		
		treeHeight = new int[treeCount];
		maxHeight = 0;
		twoDays = 0;
		oneDays = 0;
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<treeCount; i++) {
			treeHeight[i] = Integer.parseInt(st.nextToken());
			maxHeight = Math.max(maxHeight, treeHeight[i]);
		}
	}
	
	/**
	 * @see #countDiff()
	 * 1. 우선 2일과 1일의 수가 최대한 비슷해질 때까지 줄인다. (2일 수 >= 1일 수 조건은 만족해야함)
	 * 		1-1. 우선 2일 수와 1일 수를 구해야함.. 어케해 ..? 
	 * 			1-1-1. 나무를 순회하면서 최대 높이와의 차이(diff)를 구한다. 
	 * 			1-1-2. diff/2 = 2일 수, diff%2 = 1일 수 에 더한다. 
	 * 		1-2. while문으로 2일 수는 -1, 1일 수는 +2 한다. 
	 */
	public static void countDiff() {
		// 1-1-1. 나무를 순회하면서 최대 높이와의 차이(diff)를 구한다. 
		for(int i=0; i<treeCount; i++) {
			int diff = maxHeight - treeHeight[i];
			// 1-1-2. diff/2 = 2일 수, diff%2 = 1일 수 에 더한다. 
			twoDays += diff/2;
			oneDays += diff%2;
		}
		// 1-2. while문으로 2일 수는 -1, 1일 수는 +2 한다. 
		while(twoDays>1 && twoDays >= oneDays+2) {
			twoDays--;
			oneDays += 2;
		}
	}
	/**
	 * @see #countPeriod() - 나무 높이 
	 * 2. 각 상황별로 일수를 계산한다. 
	 * 		2-1. 2일 수 > 1일 수  : 1일 수 *2 +2
	 * 		2-2. 2일 수 == 1일 수 : 1일 수 *2
	 * 		2-3. 2일 수 < 1일 수  : 2일 수 *2 +3
	 */
	public static int calcPeriod() {
		if(twoDays > oneDays) return twoDays*2;
		else if(twoDays == oneDays) return twoDays*2;
		else return oneDays*2-1;
	}

}

