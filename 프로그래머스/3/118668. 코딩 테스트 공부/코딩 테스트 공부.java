import java.io.*;
import java.util.*;

class Solution {
    /**
    <조건>
    1. problems : [alp_req, cop_req, alp_rwd, cop_rwd, cost]
        1-1. alp_req : 문제를 푸는데 필요한 알고력
        1-2. cop_req : 문제를 푸는데 필요한 코딩력
        1-3. alp_rwd : 문제를 풀었을 때 증가하는 알고력
        1-4. cop_rwd : 문제를 풀었을 때 증가하는 코딩력
        1-5. cost : 문제를 푸는데 드는 시간
        
    <접근 방법>
    1. 풀어야 하는 문제 중에서 알고력 최대, 코딩력 최대값을 저장한다.
    2. 현재 알고력과 코딩력에서 각각 뺀다. 만약 0보다 같거나 작으면 바로 0 리턴 . 
    3. DP 로 풀라는데 
    */
    
    final int MAXPOWER = 150;
    final int MAXTIME = 100;
    final int INF = (int) 1e9;
    int PCOUNT;
    
    int maxAlgo = 0;
    int maxCoding = 0;
    int maxPlusAlgo = 0;
    int maxPlusCoding = 0;
    
    int answer = Integer.MAX_VALUE;
    int[][] timeTable;
    
    public int solution(int alp, int cop, int[][] problems) {
        maxAlgo = alp;
        maxCoding = cop;
        
        init(problems);
        if(alp >= maxAlgo && cop >= maxCoding) return 0;
        
        calcTime(problems, alp, cop);
        
        for(int i = maxAlgo; i <= maxAlgo + maxPlusAlgo; i++){
            for(int j = maxCoding; j <= maxCoding + maxPlusCoding; j++) {
                answer = Math.min(answer, timeTable[i][j]);
                //System.out.print(timeTable[i][j] + " ");
            }
            //System.out.println();
        }
        return answer;
    }
    
    public void init(int[][] problems) {
        PCOUNT = problems.length;
        // 알고력 최대, 코딩력 최대값 찾기 
        for(int pIdx = 0; pIdx < PCOUNT; pIdx++) {
            maxAlgo = Math.max(maxAlgo, problems[pIdx][0]);
            maxCoding = Math.max(maxCoding, problems[pIdx][1]);
            maxPlusAlgo = Math.max(maxPlusAlgo, problems[pIdx][2]);
            maxPlusCoding = Math.max(maxPlusCoding, problems[pIdx][3]);
        }
        timeTable = new int[maxAlgo + maxPlusAlgo + 1][maxCoding + maxPlusCoding + 1];
        for(int i = 0 ; i < maxAlgo + maxPlusAlgo + 1; i++) {
            Arrays.fill(timeTable[i], INF);
        }
    }
        
    public void calcTime(int[][] problems, int nowAlp, int nowCop) {
        timeTable[nowAlp][nowCop] = 0;
        
        for(int alp = nowAlp; alp <= maxAlgo + maxPlusAlgo; alp++) {
            for(int cop = nowCop; cop <= maxCoding + maxPlusCoding; cop++) {
                if(alp==nowAlp && cop==nowCop) continue;
                
                // 알고력 + 1과 코딩력 + 1 최단 시간 비교 
                int minTime = Integer.MAX_VALUE;
                
                if(alp > nowAlp) minTime = Math.min(minTime, timeTable[alp-1][cop] + 1);
                if(cop > nowCop) minTime = Math.min(minTime, timeTable[alp][cop-1] + 1);

                // 문제를 순회하면서 그 문제들의 최단 시간 비교
                for(int pIdx = 0; pIdx < PCOUNT; pIdx++) {
                    if(alp < problems[pIdx][0] || cop < problems[pIdx][1]) continue;
                    
                    int prevAlp = (alp - problems[pIdx][2] >= nowAlp) ? alp - problems[pIdx][2] : nowAlp;
                    int prevCop = (cop - problems[pIdx][3] >= nowCop) ? cop - problems[pIdx][3] : nowCop;

                    if(prevAlp >= problems[pIdx][0] && prevCop >= problems[pIdx][1]) {
                        minTime = Math.min(minTime, timeTable[prevAlp][prevCop] + problems[pIdx][4]);
                    }
                }
                timeTable[alp][cop] = minTime;
            }
        }
    }
} 