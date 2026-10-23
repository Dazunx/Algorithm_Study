import java.io.*;
import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        
        // 1. 명령어별로 실행
        for(int cIdx = 0; cIdx < commands.length; cIdx++) {
            int start = commands[cIdx][0]-1;
            int end = commands[cIdx][1]-1;
            int K = commands[cIdx][2]-1;
            
            // 1-1. 자르기
            int[] newArray = new int[end-start+1];
            int newIdx = 0;
            for(int i = start; i <= end; i++) {
                newArray[newIdx++] = array[i];
            }
            
            // 1-2. 정렬
            Arrays.sort(newArray);
            
            // 1-3. 인덱싱
            answer[cIdx] = newArray[K];
        }
        
        return answer;
    }
}