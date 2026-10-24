import java.io.*;
import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        String answer = "";
        String[] stringArray = new String[numbers.length];
        
        for(int i=0; i<numbers.length; i++) {
            stringArray[i] = String.valueOf(numbers[i]);
        }
        
        // 합쳤을 때 더 큰 수가 앞에 오도록 정렬
        Arrays.sort(stringArray, (a,b) -> ((b+a).compareTo(a+b)));
        
        if(stringArray[0].equals("0")) {
            return "0";
        }
        
        for(String strs : stringArray) {
            answer += strs;
        }
        
        return answer;
    }
}