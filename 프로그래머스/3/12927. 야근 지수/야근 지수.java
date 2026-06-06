import java.io.*;
import java.util.*;

class Solution {
    /**
    <접근 방법> 
    1. works를 내림차순 정렬한다. 
    2. 우선순위 큐를 활용한다. 2번을 반복한다. 
        2-1. 내림차순 우선순위큐에서 맨 앞의 값을 뺀다. 
        2-2. n만큼 반복하면서.. 가장 높은 값을 1 줄이고 다시 넣는다. 
    */ 
    
    PriorityQueue<Integer> pq = new PriorityQueue(Collections.reverseOrder());
    long answer = 0;
    
    public long solution(int n, int[] works) {
        
        for(int t : works) {
            pq.add(t);
        }
        
        for(int i = 0; i < n; i++){
            if(pq.isEmpty()) break;
            int high = pq.remove();
            if(high-1 > 0) pq.add(high - 1);
        }
        
        while(!pq.isEmpty()) {
            int t = pq.remove();
            answer += t*t;
        }
        return answer;
    }
    
}