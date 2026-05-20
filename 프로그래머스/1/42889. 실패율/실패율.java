import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 신규, 기존 사용자의 스테이지 차이 너무 큼 .
    2. 목표 : 실패율을 구하는 것. 
    3. 실패율이 높은 stage부터 내림차순. 
    
    <문제 전략>
    1. 각 스테이지의 실패율을 구해서 내림차순 정렬한다. 
    */
    public int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        HashMap<Integer, Integer> peopleCountPerStage = new HashMap<>();
        HashMap<Integer, Integer> peopleCountOnStage = new HashMap<>();
        HashMap<Integer, Double> failureRatio = new HashMap<>();
        
        // 어느 스테이지까지 도달했는지 파악하기
        for(int i=0; i<stages.length; i++) {
            int sIdx = stages[i];
            
            if(!peopleCountOnStage.containsKey(sIdx)) {
                peopleCountOnStage.put(sIdx, 1);
            } else {
                peopleCountOnStage.put(sIdx, peopleCountOnStage.get(sIdx)+1);
            }
            
            for(int j=1; j<=sIdx; j++) { // sIdx까지 포함해야됨 (인덱스 헷갈리는 군)
                if(j > N) continue;
                if(!peopleCountPerStage.containsKey(j)) {
                    peopleCountPerStage.put(j, 1);
                } else {
                    peopleCountPerStage.put(j, peopleCountPerStage.get(j)+1);
                }
            }
        }
        System.out.println(peopleCountPerStage);
        
        // 실패율 구하기 
        for(int sIdx=1; sIdx<=N; sIdx++) {
            if(!peopleCountPerStage.containsKey(sIdx)) {
                failureRatio.put(sIdx, 0.0); // 여기서 0으로 하는 것 놓침. 
                continue;
            }
            
            if(!peopleCountOnStage.containsKey(sIdx)) {
                failureRatio.put(sIdx, 0.0);
            } else {
                Double failure = (double) peopleCountOnStage.get(sIdx) / peopleCountPerStage.get(sIdx);
                failureRatio.put(sIdx, failure);
            }
        }
        
        System.out.print(failureRatio);
        
        // 실패율 dict 정렬 (value 기준으로 내림차순)
        List<Integer> keySet = new ArrayList<>(failureRatio.keySet());
        keySet.sort((o1, o2) -> failureRatio.get(o2).compareTo(failureRatio.get(o1)));
        
        // key 를 뽑아내어 반환 
        int i = 0;
        for(Integer stageNum : keySet) {
            answer[i++] = stageNum;
        }
        
        return answer;
    }
}