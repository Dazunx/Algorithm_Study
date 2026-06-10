import java.io.*;
import java.util.*;

public class Solution {
	/**
	 * @author DajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 테스트케이스 개수(T)를 입력받는다.
	 * 
	 * 각 테스트케이스마다, 
	 * 2. 입력받기
	 * 		2-1. N과 M을 입력받는다.	
	 * 			2-1-1. 한 줄을 입력받는다.
	 * 			2-1-2. 공백 기준으로 분리하여 N과 M을 저장한다. 
	 * 		2-2. N x N 배열을 입력받는다. 
	 * 			2-2-1. N번 반복하여 입력받는다.
	 * 			2-2-2. 입력받은 줄을 공백으로 분리하여 2차원 배열(num_list)에 저장한다.
	 * 3. Max 합계 구하기
	 * 		3-1. 누적합 구하기
	 * 			-> S[y][x]: (0,0)부터 (y,x)까지의 사각형 영역 총합
	 * 			-> 누적합 = S[i][j] = 현재값 + S[i-1][j] + S[i][j-1] - S[i-1][j-1]
	 * 		3-2. 구간합 구하기
	 * 			-> 구간합 = S[i][j] - S[i-M][j] - S[i][j-M] + S[i-M][j-M]
	 * 4. 결과 출력 
	 * 		4-1. max 합계 출력
	 * 		
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 1. 테스트케이스 개수(T)를 입력받는다.
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			// 2. 입력받기
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int[][] sum_list = new int[N+1][N+1];
			
			for(int y=1; y<=N; y++) {
				StringTokenizer num_st = new StringTokenizer(br.readLine());
				
				for(int x=1; x<=N; x++) {
					int num = Integer.parseInt(num_st.nextToken());
                    // 현재값 + 위쪽합 + 왼쪽합 - 대각선중복합
					sum_list[y][x] = num + sum_list[y-1][x] + sum_list[y][x-1] - sum_list[y-1][x-1];
				}
			}
			
			int max_sum = 0;
			
			for(int i=M; i<=N; i++) {
                for(int j=M; j<=N; j++) {
                    // (i, j)에서 M x M 크기 사각형의 합 공식
                    int current_sum = sum_list[i][j] - sum_list[i-M][j] - sum_list[i][j-M] + sum_list[i-M][j-M];
                    max_sum = Math.max(max_sum, current_sum);
                }
            }
			
			
			// 4. 결과 출력 
			System.out.printf("#%d %d\n", test_case, max_sum);
		}
	}
}
