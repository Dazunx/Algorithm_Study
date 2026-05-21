import java.io.*;
import java.util.*;

class Solution {
    /**
    <조건>
    1. 3번의 기회가 있다. 각 기회별로 0~10 점 점수를 얻을 수 잇따. 
    2. S영역은 1제곱, D영역은 2제곱, T영역은 3제곱으로 계산하여 점수를 계산한다. 
    3. * 는 해당 점수, 바로 직전 점수를 2배로 만든다. 
        3-1. 첫번째에도 나올 수 있음. 그러면 해당 점수만 2배로. 
        3-2. # 와 중첩 가능. 
    4. # 는 해당 점수를 - 로 바꾼다.
        4-1. 만약 *도 나오면 2배 후 - 를 붙이는 것임. 
    5. *와 #은 점수마다 존재하지 않거나 하나만 존재한다. 
    
    입력 : "점수|보너스|[옵션]"으로 이루어진 문자열 3세트
    
    <접근 방법>
    1. 입력 받는 것부터 막히네. 
        1-1. 점수는 0~10 이므로 
    */
    
    int[] jumsuList = new int[3];
    String[] inputs = new String[3];
    
    public int solution(String dartResult) {
        int answer = 0;
        
        // 문자열을 분리해서 점수를 계산한다.
        splitInput(dartResult);

        // 계산된 점수를 전부 더한다. 
        for(int i=0; i<3; i++) {
            answer += jumsuList[i];
        }
        
        return answer;
    }
    
    public void splitInput(String dartResult) {
        int idx = 0;
        
        StringBuilder sb = new StringBuilder();
        // 알파벳에 해당하면 멈추고 다음 문자를 확인한다. 
        for(int i=0; i<dartResult.length(); i++) {
            
            if(dartResult.charAt(i) >= 'A' && dartResult.charAt(i) <= 'Z') {
                // 숫자를 변환하여서 그것을 점수로 넣어준다. 
                int jumsu = Integer.parseInt(sb.toString());
                char bonus = dartResult.charAt(i);
                char op =  ' ';
                
                // 옵션이 있는지 확인하고, 있으면 저장한다. 
                if(i<dartResult.length()-1 && (dartResult.charAt(i+1)=='*' || dartResult.charAt(i+1)=='#')) {
                    op = dartResult.charAt(i+1);
                    i++;
                }
                // 분리한 숫자, 보너스, 옵션을 사용하여 점수를 계산한다. 
                decode(idx, jumsu, bonus, op);
                
                // 다음 점수로 넘어가기 
                sb.setLength(0);
                idx++;
                
            } else {
                // 숫자인 경우에 stringbuilder에 더한다. (나중에 한꺼번에 숫자로 변환한다)
                sb.append(dartResult.charAt(i));
            }
        }
    }
    
    public void decode(int idx, int jumsu, char bonus, char op) {
        int decodedJumsu = jumsu;
        
        // S영역은 1제곱, D영역은 2제곱, T영역은 3제곱으로 계산하여 점수를 계산한다. 
        switch(bonus) {
            case 'D':
                decodedJumsu = (int) Math.pow(decodedJumsu, 2);
                break;
            case 'T':
                decodedJumsu = (int) Math.pow(decodedJumsu, 3);
                break;
        }
        
        switch(op) {
            // * 는 해당 점수, 바로 직전 점수를 2배로 만든다
            case '*':
                decodedJumsu *= 2;
                if(idx>0) jumsuList[idx-1] *=2;
                break;
            // # 는 해당 점수를 - 로 바꾼다.
            case '#':
                decodedJumsu *= (-1);
                break;
        }
        
        // 계산한 점수를 저장한다. 
        jumsuList[idx] = decodedJumsu;
    }
}