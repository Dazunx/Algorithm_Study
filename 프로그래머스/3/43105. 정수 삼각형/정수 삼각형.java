
import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        int h = triangle.length;
        int[][] maxSum = new int[h+1][h+1];
        
        for(int i=1; i<=h; i++) {
            for(int j=1; j<=i; j++) {
                if(j==0) maxSum[i][j] = maxSum[i-1][0];
                else if(j==i+1) maxSum[i][j] = maxSum[i-1][j-1];
                else maxSum[i][j] = Math.max(maxSum[i-1][j-1], maxSum[i-1][j]);
                
                maxSum[i][j] += triangle[i-1][j-1];
            }
        }
        
        for(int idx = 1; idx <= h; idx++) {
            answer = Math.max(answer, maxSum[h][idx]);
        }
        return answer;
    }
}