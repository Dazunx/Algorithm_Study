import java.io.*;
import java.util.*;

public class Main

{
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static StringTokenizer st;
    
    public static final int MAX_SIZE = 10000;
    
    public static int[] dp = new int[MAX_SIZE + 1];

    
    public static void main(String[] args) throws IOException {
        
        // 1. 사전에 미리 경우의 수를 계산하여 저장한다. 
        
        dp[0] = 1;
        
        for (int k = 1; k <= 3; k++) {       // 바깥: 쓸 수
        
            for (int i = k; i <= MAX_SIZE; i++) { // 안쪽: 만들 합
            
                dp[i] += dp[i - k];
                
            }
            
        }
            
        // 2. 숫자 입력 받아서 결과 출력 
        
        int T = Integer.parseInt(br.readLine());
        
        for(int tc = 1; tc <= T ; tc++) {
            
            int N = Integer.parseInt(br.readLine());
            
            System.out.println(dp[N]);
            
        }
    
    }

}