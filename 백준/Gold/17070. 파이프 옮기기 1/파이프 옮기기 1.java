import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author DajeongLee
	 * 
	 * <문제 요약>
	 * 1. N x N 격자판 (빈칸 or 벽)
	 * 2. 파이프는 2개의 연속된 칸 차지 
	 * 3. 파이프를 밀 수 있는 방향은 총 3가지가 있으며, →, ↘, ↓ 방향
	 * 4. 파이프 이동방법
	 * 		4-1. 가로로 놓여진 경우 : 2가지
	 * 		4-2. 세로로 놓여진 경우 : 2가지
	 * 		4-3. 대각선 방향으로 놓여진 경우 : 3가지 
	 * 5. 가장 처음에 파이프 (1,1)(1,2), 방향 가로 
	 * 6. 한쪽 끝이 (N,N)에 도착하는 방법의 개수. 
	 * 
	 * <문제 접근>
	 * 1. dp 배열을 선언한다. (크기는 맵 크기 N x N)(가로 : 0, 세로 : 1, 대각선 : 2) 
	 * 		1-1. 시작위치인 (0,0), (0,1) 은 가로  1로 초기화 한다. 
	 * 2. 왼쪽 대각선 위, 위, 왼쪽에서 오는 경우를 더한다. 
	 * 		2-1. 가로 : dp[r][c][0] = dp[r][c-1][0] + dp[r][c-1][2]
	 * 		2-2. 세로 : dp[r][c][1] = dp[r-1][c][1] + dp[r-1][c][2]
	 * 		2-3. 대각선 : dp[r][c][2] = dp[r-1][c-1][0] + dp[r-1][c-1][1] + dp[r-1][c-1][2]
	 * 3. dp[N-1][N-1]의 3가지 방향을 더한 값을 출력한다. 
	 * 
	 */
	
	static int N;
	static int[][] map; 
	static int[][][] dp;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력을 받는다. 
		input(br);
		// DP 배열을 생성한다. 
		calcDP();
		// 결과를 출력한다. 
		System.out.println(dp[N-1][N-1][0] + dp[N-1][N-1][1] + dp[N-1][N-1][2]);
	}
	
	public static void input(BufferedReader br) throws IOException{
		// 맵의 크기 N을 입력받는다. 
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		dp = new int[N][N][3];
		
		//맵의 정보를 입력받는다. 
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	public static void calcDP() {
		// DP 배열 생성 
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				// 벽일 때 패스 
				if(map[r][c]==1)  continue;
				if(r==0 && c==1) dp[r][c][0] = 1; // 초깃값 설정 
				else {
					// 가로일 때 : 왼쪽 부분의 가로(0), 대각선(2) 경우의 수를 더한다. 
					if(c-1>=0) dp[r][c][0] = dp[r][c-1][0] + dp[r][c-1][2];
					// 세로일 때  : 위부분의 세로(1) , 대각선(2) 경우의 수를 더한다. 
					if(r-1>=0 && c-1>=0) dp[r][c][1] = dp[r-1][c][1] + dp[r-1][c][2];
					// 대각선일 때 : 위, 왼쪽, 현재가 빈칸이면 => 왼쪽 대각선 위의 값을 모두 더한다. 
					if(r-1>=0 && c-1>=0 && (map[r-1][c]!=1 && map[r][c-1]!=1)) dp[r][c][2] = dp[r-1][c-1][0] + dp[r-1][c-1][1] + dp[r-1][c-1][2];
				}
			}
		}
	}
}
