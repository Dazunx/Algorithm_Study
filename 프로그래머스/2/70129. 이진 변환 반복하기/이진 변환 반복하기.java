import java.io.*;
import java.util.*;

class Solution {
    /**
    <조건> 
    1. 문자열 s를 이진 변환한다. 
    2. 우선 x에서 0을 제거한다.
    3. x의 길이를 이진법으로 표현해라 
    */
    
    int convertedCount = 0;
    int deletedZeroCount = 0;
    
    public int[] solution(String s) {
        int[] answer = new int[2];
        String newStr = s;
        
        while(true) {
            if(newStr.equals("1")) break;
            newStr = convert(newStr);
            convertedCount++;
        }
        answer[0] = convertedCount;
        answer[1] = deletedZeroCount;
        
        return answer;
    }
    
    public String convert(String s) {
        StringBuilder sb = new StringBuilder();
        // x에서 0을 제거한다.
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if(c!='0') {
                sb.append(c);
            } else {
                deletedZeroCount++;
            }
        }
        //System.out.print(sb.toString() + " ");
        // x의 길이를 이진법으로 표현해라 
        return convertToBinary(sb.length());
    }
    
    public String convertToBinary(int num) {  
        StringBuilder sb = new StringBuilder();
        //System.out.print(num + " ");
        while(num > 0) {
            sb.append((char)(num%2 + '0'));
            num /= 2;
        }
        //System.out.println(sb.reverse().toString() + " ");
        return sb.reverse().toString();
    }
}