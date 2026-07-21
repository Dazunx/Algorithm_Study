import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 어피치가 n번 쏨. (입력으로 주어짐)
    2. 이후에 라이언이 n번 쏨 
    3. 점수 재는 법
        3-1. k과녁에 더 많은 화살 맞히면 k점 가져감. 
        3-2. 점수가 같으면 어피치가 가져감 
    4. 라이언이 가장 큰 점수 차이로 이기는 순열 구하기 
    
    <문제 전략>
    1. 어피치의 점수를 구한다.
    2. 라이언의 점수 순열을 구해서, 점수를 계산한다. 
        2-1. 만약 점수가 같으면 낮은 곳에 더 많은 경우를 리턴한다. 
            (오른쪽에 있는 수가 더 많은 경우.) 
    3. 해당하는 순열이 없으면 -1을 리턴한다. 
    */
    
    static int maxDiff = 0;
    static int[] answer = new int[11];
    
    public int[] solution(int n, int[] info) {
        makePerm(n, info, new int[11], 0, 0);
        
        if(maxDiff == 0) return new int[]{-1};
        return answer;
    }
    
    public int countJumsuDiff(int n, int[] peach, int[] ryan) {
        int ryanJumsu = 0;
        int peachJumsu = 0;
        for(int i=0; i<11; i++) {
            if(ryan[i] > peach[i]) ryanJumsu += (10-i);
            else if(peach[i] > 0) peachJumsu += (10-i);
        }
        return ryanJumsu - peachJumsu;
    }
    
    public void makePerm(int n, int[] info, int[] result, int index, int sum) {
        if(sum > n) return;
        if(index > 10 && sum != n) return;
        if(sum == n) {
            int diff = countJumsuDiff(n, info, result);
            if(diff == 0) return;
            // 점수가 더 높으면 무조건 정답으로 
            if(diff > maxDiff) {
                maxDiff = diff;
                answer = Arrays.copyOf(result, result.length);
            } 
            // 점수가 같으면 뒤에서부터 비교한다. 
            else if(diff == maxDiff) {
                for(int i=10; i>=0; i--) {
                    if(result[i] > answer[i]) {
                        answer = Arrays.copyOf(result, result.length);
                        break;
                    } else if(answer[i] > result[i]) break;
                 }
            }
            return;
        }
        
        for(int i=0; i<=n; i++) {
            result[index] = i;
            makePerm(n, info, result, index+1, sum+i);
            result[index] = 0;
        }
    }
}