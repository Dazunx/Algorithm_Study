import java.io.*;
import java.util.*;

class Solution {
    /**
    <접근방법>
    1. target이 words에 없으면 바로 0을 리턴한다. 
    2. dfs로 접근해본다. 
        2-1. 한 자리만 변경했을 때 words안에 있으면 재귀 호출 
            2-1-1. count를 늘린다. 
            2-1-2. 한 자리만 변경했는지 확인하는 방법
                2-1-2-1. 한 글자씩 이동하면서 해당 인덱스 앞, 뒤로 같은 글자이고 완전히 같은 단어가 아니면 그 단어로 이동한다. 
        2-2. 그렇지 않으면 종료한다. 
    */
    int answer = Integer.MAX_VALUE;
    
    public int solution(String begin, String target, String[] words) {
        boolean isExist = false; 
        for(String w : words) {
            if(target.equals(w)) isExist = true;
        }
        if(!isExist) return 0;
        
        findWord(target, words, begin, 0, new boolean[words.length]);
        return answer;
    }
    
    public void findWord(String target, String[] words, String strs, int count, boolean[] visited) {
        // 종료조건 
        if(strs.equals(target)) {
            answer = Math.min(answer, count);
            return;
        }
        // 이미 모든 단어를 순회하였는데 정답이랑 같은 문자가 되지 않았다면 종료 
        if(count > words.length) return;
                
        // 다음 이동할 수 있는 단어 탐색 (한 자리만 달라야 한다.)
        for(int wIdx = 0 ; wIdx < words.length; wIdx++) {
            String w = words[wIdx];
            
            // 같은 단어는 패스 
            if(strs.equals(w)) continue;
            if(visited[wIdx]) continue;
            
            for(int i = 0; i < w.length(); i++) {
                //System.out.print(strs.substring(0, i) + " " + strs.substring(i+1, w.length()));
                //System.out.println(" / " + w.substring(0, i) + " " + w.substring(i+1, w.length()));
                if(strs.substring(0, i).equals(w.substring(0, i)) && strs.substring(i+1, w.length()).equals(w.substring(i+1, w.length()))) {
                    //System.out.println(" => !! " + w);
                    visited[wIdx] = true;
                    findWord(target, words, w, count + 1, visited);
                    visited[wIdx] = false;
                } 
            }
        }
    }
}