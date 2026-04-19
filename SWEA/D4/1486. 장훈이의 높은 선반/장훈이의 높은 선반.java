import java.io.*;
import java.util.*;

public class Solution {
	
	/**
	 * @author dajeongLee
	 * 
	 * <문제 요약>
	 * 1. 높이가 B인 선반 
	 * 2. N명의 점원들이 선반 
	 * 3. 탑의 높이 = 점원의 키의합 . 
	 * 		3-1. 탑의 높이가 B 이상이면 되는데 그 중에서 최소값. 
	 * 
	 * 4. 점원의 키를 부분집합으로 전부 더해본다. 이때 B 이상일 때 최소인 경우를 구한다. 
	 * 
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int N, B;
	static int[] heightList;
	
	static int minHeightSum;

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			init();
			calcHeight(0,0);
			sb.append(String.format("#%d %d\n", test_case, minHeightSum-B));
		}
		System.out.println(sb);
	}
	
	public static void init() throws IOException {
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		
		heightList = new int[N];
		minHeightSum = Integer.MAX_VALUE;
		
		st= new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			heightList[i] = Integer.parseInt(st.nextToken());
		}
	}
	
	public static void calcHeight(int index, int sum) {
		if(index == N) {
			if(sum>=B) {
				minHeightSum = Math.min(minHeightSum, sum);
			}
			return;
		}
		
		calcHeight(index+1, sum+heightList[index]);
		calcHeight(index+1, sum);
	}

}
