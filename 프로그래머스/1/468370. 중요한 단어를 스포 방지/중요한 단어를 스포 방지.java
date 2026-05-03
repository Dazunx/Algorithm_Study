import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    정답 :  중요한 단어의 개수를 찾는다. 
    1. 스포일러를 방지하기 위한 범위가 주어진다. 
    2. 그 부분이 공개되면, 중요한 단어인지 확인하고, 중요한 단어이면 개수를 추가시킨다.

    <문제 전략>
    0. 스포일러 범위를 boolean배열을 만들어서 spolier방지되었다는 표시로 true로 표시한다.
    1. 처음에 입력받은 문장을 공백 기준으로 나눈다. 
    2. 나누어진 단어가 
        2-1. 스포일러 range에 포함되면(boolean 배열 확인) hidden 배열에 추가한다. 
        2-2. 포함 안되면 hashSet에 넣는다. 
    3. hidden 배열을 순회한다. 
        3-1. 이때 HashSet에 이미 존재하는 단어이면, 중요한 단어가 아니다. 
        3-2. 만약 HashSet에 추가하였는데 size가 1 증가했다면, 중요한 단어 개수를 추가한다. 
    */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    
    static HashSet nonHiddenSet = new HashSet<>();
    static ArrayList<String> hiddenList = new ArrayList<>();
    
    static boolean[] isHidden;
    static int answer = 0;

    public int solution(String message, int[][] spoiler_ranges) {        
        isHidden = new boolean[message.length()];
        // 0. 스포일러 범위를 boolean배열을 만들어서 spolier방지되었다는 표시로 true로 표시한다.
        hiddenCheck(message, spoiler_ranges);
        // 1. 처음에 입력받은 문장을 공백 기준으로 나눈다. 
        cutString(message);
        findImportWord();
        return answer;
    }
    
    public void findImportWord(){
        // 3. hidden 배열을 순회한다. 
        for(String s : hiddenList){
            int before = nonHiddenSet.size();
            nonHiddenSet.add(s);
            int after = nonHiddenSet.size();
            // 3-1. 이때 HashSet에 이미 존재하는 단어이면, 중요한 단어가 아니다. 
            // 3-2. 만약 HashSet에 추가하였는데 size가 1 증가했다면, 중요한 단어 개수를 추가한다. 
            if(before != after) answer++;
        }
    }
    
    public void cutString(String message){
        String[] cutting = message.split(" ");
        int checkIdx = 0;
        
        // 2. 나누어진 단어가 
        for(int wordIdx=0; wordIdx<cutting.length; wordIdx++){
            String cut = cutting[wordIdx];
            boolean hidden = false;
            
            // 2-1. 스포일러 range에 포함되면(boolean 배열 확인) hidden 배열에 추가한다. 
            for(int idx=0; idx<cut.length(); idx++){
                if(isHidden[checkIdx+idx]){
                    hidden = true;
                    break;
                }
            }
            checkIdx += cut.length();
            checkIdx++;
            
            // 2-2. 포함 안되면 hashSet에 넣는다. 
            if(!hidden) {
                nonHiddenSet.add(cut);
            } else {
                hiddenList.add(cut);
            }
        }
    }
    
    
    public void hiddenCheck(String message, int[][] spoiler_ranges){
        for(int spoIdx=0; spoIdx<spoiler_ranges.length; spoIdx++){
            int start = spoiler_ranges[spoIdx][0];
            int end = spoiler_ranges[spoIdx][1];
            
            for(int hiddenIdx=start; hiddenIdx<=end; hiddenIdx++){
                isHidden[hiddenIdx] = true;
            }
        }
    }

}