import java.io.*;
import java.util.*;

class Solution {
    /**
    <조건>
    1. survey에서 앞에 있는 알파벳이 동의, 뒤에 있는 알파벳이 비동의임. 
    2. 각 choices 별로 점수를 매긴다. 
    3. 각 지표별로 더 많은 점수를 가지고 있는 알파벳을 뽑는다. 만약 점수가 같으면 사전순으로 빠른 애가 선정됨.
    
    <문제 접근>
    1. 각 choices를 순회한다.
        1-1. 점수를 보고 4점 초과이면 survey의 0번째 문자에 점수를 추가한다.
        1-2. 4점 미만이면 1번째 문자에 점수를 추가한다. 
    2. dict에 각 알파벳별로 점수를 계산해놓았으니, 그 점수를 가지고 지표별로 점수가 높은 알파벳을 찾는다. 만약 같으면 사전순으로 더 크기가 작은 알파벳을 뽑는다.
    */
    public String solution(String[] survey, int[] choices) {
        StringBuilder answer = new StringBuilder();
        Map<Character, Integer> jumsuCount = new HashMap<>();
        
        for(int i=0; i<choices.length; i++) {
            int choice = choices[i];
            int jumsu = 0;
            
            // 점수 계산 
            switch(choice) {
                case 1, 7:
                    jumsu = 3;
                    break;
                case 2, 6:
                    jumsu = 2;
                    break;
                case 3, 5:
                    jumsu = 1;
                    break;
            }
            
            // 어떤 문자에 점수를 추가할 것인지 
            if(choice == 4) continue;
            
            char cIdx = ' ';
            
            if(choice < 4) {
                cIdx = survey[i].charAt(0);
            } else {
                cIdx = survey[i].charAt(1);
            } 
            
            // 점수 추가 
            if(!jumsuCount.containsKey(cIdx)) {
                jumsuCount.put(cIdx, jumsu);
            } else {
                jumsuCount.put(cIdx, jumsuCount.get(cIdx)+jumsu);
            }
        }
        
        char[][] types = {{'R', 'T'}, {'C', 'F'},{'J', 'M'}, {'A', 'N'}};
        
        for(int typeIdx=0; typeIdx<4; typeIdx++) {
            char ch1 = types[typeIdx][0];
            char ch2 = types[typeIdx][1];
            
            int jumsu1 = jumsuCount.containsKey(ch1) ? jumsuCount.get(ch1) : 0;
            int jumsu2 = jumsuCount.containsKey(ch2) ? jumsuCount.get(ch2) : 0;
            
            if(jumsu1 >= jumsu2) {
                answer.append(ch1);
            } else {
                answer.append(ch2);
            }
        }
        
        System.out.println(jumsuCount);
        
        
        return answer.toString();
    }
}