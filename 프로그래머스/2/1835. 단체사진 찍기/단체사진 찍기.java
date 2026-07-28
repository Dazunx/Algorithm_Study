import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 일렬로 줄 세우기 조건n개를 모두 만족하는 경우의 수를 구한다. 
    2. 각 조건은 A~B=(두 캐릭터 사이에 있는 다른 프렌즈의 수) 형태로 주어진다. 
    
    <문제 전략>
    1. 순열을 구한다. 
        1-1. 순열을 만들면서 조건에 맞지 않으면 바로 탈락시킨다. 
    */
    
    static final int MEMCOUNT = 8;
    static char[] friends = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
    static int answer = 0;
    
    public int solution(int dataCount, String[] data) {
        answer = 0;
        makeLine(dataCount, data, new boolean[MEMCOUNT], new HashMap<>(), new char[MEMCOUNT], 0);
        return answer;
    }
    
    public void makeLine(int dataCount, String[] data, boolean[] visited, Map<Character, Integer> idxMap, char[] result, int index) {
        if(index == MEMCOUNT) {
            if(isPossible(dataCount, data, idxMap, result, index)) answer++;
            return;
        }
        
        for(int i=0; i<MEMCOUNT; i++) {
            if(visited[i]) continue;
            
            result[index] = friends[i];
            idxMap.put(result[index], index);
            visited[i] = true;
            makeLine(dataCount, data, visited, idxMap, result, index+1);
            visited[i] = false; 
            idxMap.remove(result[index]);
        }
    }
    
    public boolean isPossible(int dataCount, String[] data, Map<Character, Integer> idxMap, char[] result, int index) {               
        for(String condition : data) {
            char a = condition.charAt(0);
            char b = condition.charAt(2);
            char sign = condition.charAt(3);
            int diff = condition.charAt(4)-'0';
            
            switch(sign) {
                case '>' :
                    if(Math.abs(idxMap.get(a) - idxMap.get(b)) <= diff + 1) return false;
                    break;
                case '<':
                    if(Math.abs(idxMap.get(a) - idxMap.get(b)) >= diff + 1) return false;
                    break;
                case '=':
                    if(Math.abs(idxMap.get(a) - idxMap.get(b)) != diff + 1) return false;
                    break;
            }
        }
        return true;
    }
}