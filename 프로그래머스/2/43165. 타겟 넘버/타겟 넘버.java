import java.io.*;
import java.util.*;

/**
<문제 전략>
1. 숫자들 사이에 +, - 만 넣어서 계산
2. 타겟 숫자에 도달하면 카운트 증가 후 종료 
*/

class Solution {
    
    static public int answer = 0;
    
    public void dfs(int[] numbers, int[] newNumbers, int target, int index) {
        // 타겟 숫자에 도달하면 카운트 증가 후 종료 
        if(index == numbers.length) {
            int sum = 0;
            for(int i=0; i<index; i++) {
                sum += newNumbers[i];
            }
            if(sum == target) answer++;
            return;
        }
        
        newNumbers[index] = numbers[index];
        dfs(numbers, newNumbers, target, index+1);
        newNumbers[index] = numbers[index] * (-1);
        dfs(numbers, newNumbers, target, index+1);
    }
    
    public int solution(int[] numbers, int target) {
        dfs(numbers, new int[numbers.length], target, 0);
        
        return answer;
    }
}