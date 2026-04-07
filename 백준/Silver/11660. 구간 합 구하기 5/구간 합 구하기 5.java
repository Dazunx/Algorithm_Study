import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 숫자 개수N과 범위 개수 M을 입력받는다. 
	 * 2. 누적 합을 계산한다. -> 현재까지의 합 = 위쪽 합 + 왼쪽 합 - 대각선 합(중복 제거) + 현재값
	 * 		2-2. 범위 끝지점까지의 합 - 범위 시작지점까지의 합을 구하여 출력한다. 
	 * 3. 2차원 구간 합을 구한다. 
	 * 4. 결과를 출력한다. 
	 * 
	 */
	
	
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1. 숫자 개수 N과 범위 개수 M을 입력받는다. 
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        long[][] sum = new long[N + 1][N + 1];
        
        // 2. 누적 합을 계산한다.
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int value = Integer.parseInt(st.nextToken());
                // 현재까지의 합 = 위쪽 합 + 왼쪽 합 - 대각선 합(중복 제거) + 현재값
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + value;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < M; k++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            // 3. 2차원 구간 합을 구한다. 
            long result = sum[x2][y2] - sum[x1 - 1][y2] - sum[x2][y1 - 1] + sum[x1 - 1][y1 - 1];
            sb.append(result).append("\n");
        }

        // 4. 결과를 출력한다. 
        System.out.print(sb);
    }

}
