import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    ** 목표 : 캐시 크기에 따라서 실행시간이 어떻게 달라지는지 구한다. **
    1. 캐시 크기만큼 Deque을 만든다. 
    2. 도시를 하나씩 읽으면서, 
        2-1. 캐시 deque에 있으면 
            2-1-1. 실행시간 += 1
            2-1-2. 해당 도시를 remove하고 다시 추가한다. (최신 업데이트)
        2-2. 없으면 
            2-2-1. 실행시간 += 5 하고, 
            2-2-2. 캐시 deque에 넣은 후 
            2-2-3. 제일 처음 값은 뺀다.
    
    - 최대 도시 수는 100,000개
    - 0 ≦ cacheSize ≦ 30
    */
    
    public int solution(int cacheSize, String[] cities) {
        ArrayDeque<String> cache = new ArrayDeque<>();
        int time = 0;
        
        for(String city : cities) {
            city = city.toLowerCase();
            if(cache.contains(city)){
                time++;
                cache.remove(city);
                cache.addLast(city);
            } else {
                time += 5;
                cache.addLast(city);
                if(cache.size()>cacheSize) cache.removeFirst();
            }
        }
        return time;
    }
}