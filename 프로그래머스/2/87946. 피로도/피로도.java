import java.io.*;
import java.util.*;

/**
<문제 요약>
1. 최소 필요 피로도 : 각 던전마다 탐험을 시작하기 위해 필요한 피로도
2. 소모 피로도 : 각 던점 탐험을 마쳤을 때 소모되는 피로도 
=> 유저가 탐험할 수 있는 최대 던전 수 구하기 

<문제 전략> 
1. 모든 경우의 수를 탐색해보아야 한다.
*/
class Solution {
    static int count; // 총 던전 개수 
    static int maxPossibleCount = 0;
    
    public int solution(int k, int[][] dungeons) {
        count = dungeons.length;        
        perm(k, dungeons, new boolean[count], 0, new int[count][2]);
        
        return maxPossibleCount;
    }
    
    public int calc(int k, int[][] result) {
        int possibleCount = 0; 
        
        for(int i=0; i<count; i++) {
            int needLevel = result[i][0]; // 이번 던전 최소 필요 피로도 
            int useLevel = result[i][1]; // 이번 던전 소모 피로도
                        
            if(k >= needLevel) {
                possibleCount += 1;
                k -= useLevel;
            }
        }    
        return possibleCount;
    }
    
    public void perm(int k, int[][] dungeons, boolean[] visited, int index, int[][] result) {
        if(index == count) {
            maxPossibleCount = Math.max(maxPossibleCount, calc(k, result));
            return;
        }
        
        for(int i=0; i<count; i++) {
            if(visited[i]) continue;
            
            visited[i] = true;
            result[index][0] = dungeons[i][0];
            result[index][1] = dungeons[i][1];
            perm(k, dungeons, visited, index+1, result);
            visited[i] = false;
        }
    }
}