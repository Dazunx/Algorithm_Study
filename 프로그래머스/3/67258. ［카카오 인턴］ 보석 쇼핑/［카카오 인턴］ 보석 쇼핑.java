import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 전략 : 투포인터> - 혼자 삽질하다가 AI 도움 받음 
    1. 먼저 gems안에 보석 개수를 구한다. 
    2. start와 end를 처음부터 시작한다. 
        2-1. end를 늘리면서 hashmap에 보석별 개수를 저장한다. 
        2-2. hashmap의 사이즈가 전체 보석 개수와 같아지면 해당 길이를 우선 저장한다.
        2-3. end는 고정된 상태에서 start를 이동하면서 map의 사이즈가 보석 개수보다 작아지면 바로 멈춘다. 
        2-4. end를 늘린다. 
    3. 2번을 끝까지 반복해서 끝까지 탐색하여 가장 짧은 구간을 구한다. 
    */
    
    static int gemCount;
    static int start, end; 
    static int minLength = Integer.MAX_VALUE;
    
    public int[] solution(String[] gems) {
        // 1. 먼저 gems안에 보석 개수를 구한다. 
        HashSet<String> gemSet = new HashSet<>(Arrays.asList(gems));
        gemCount = gemSet.size();
        
        // 3. 2번을 끝까지 반복해서 끝까지 탐색하여 가장 짧은 구간을 구한다. 
        findShortest(gems);
        
        return new int[]{start+1, end+1};
    }
    
    public void findShortest(String[] gems) {
        // 2. start와 end를 처음부터 시작한다. 
        int left = 0;
        int right = 0;
        
        
        HashMap<String, Integer> eachGemCountMap = new HashMap<>();  
        
        while(right < gems.length) {

            // 2-1. end를 늘리면서 hashmap에 보석별 개수를 추가한다.
            eachGemCountMap.put(gems[right], eachGemCountMap.getOrDefault(gems[right], 0) + 1);
            right++; 
            
            // 2-2. hashmap의 사이즈가 전체 보석 개수와 같아지면 해당 길이를 우선 저장한다.
            while(eachGemCountMap.size() == gemCount) {

                if(right-left < minLength) {
                    minLength = right - left;
                    start = left;
                    end = right-1;
                }
                
                
                // 왼쪽 인덱스 늘리기 
                eachGemCountMap.put(gems[left], eachGemCountMap.get(gems[left]) - 1);
                if(eachGemCountMap.get(gems[left]) == 0) eachGemCountMap.remove(gems[left]);
                left++; // map에서 지우고 늘려야됨 
            }
            
        }
    }
}