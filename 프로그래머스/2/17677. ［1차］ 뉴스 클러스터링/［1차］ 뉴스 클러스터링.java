import java.io.*;
import java.util.*;

class Solution {
    /**
    <접근 방법>
    1. 두 글자씩 묶어서 배열을 저장한다.
        1-1. 만약에 글자가 아닌 숫자나 특수문자가 들어가면 패스한다. 
        1-2. 패스할 때 그 문자랑 뒤 글자가 영문자인 경우에만 추가한다.
    2. str1과 str2의 모든 다중집합을 리스트에 넣어 구한다. 
    3. 각각 map에 넣어서 같은 sub문자열의 개수를 구한다.
    4. 교집합 개수 = 맵에서 더 작은 개수로 구한다. 
    5. 공집합 개수 = 각 리스트 개수를 더해서 교집합 개수를 뺀다. 
    6. 자카드 유사도를 계산하여(0~1범위) 65536을 곱하여 정수부만 출력한다. 
    */
    public int solution(String str1, String str2) {                
        ArrayList<String> subStr1 = new ArrayList<>();
        ArrayList<String> subStr2 = new ArrayList<>();
        
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
                
        // str의 다중집합 구하기 
        for(int i=0; i<str1.length()-1; i++) {
            char cur = str1.charAt(i);
            char next = str1.charAt(i+1);
            if(!(cur >= 'a' && cur <= 'z' && next >= 'a' && next <= 'z')) continue; 
            
            subStr1.add(str1.substring(i, i+2));
        }        
        for(int i=0; i<str2.length()-1; i++) {
            char cur = str2.charAt(i);
            char next = str2.charAt(i+1);
            if(!(cur >= 'a' && cur <= 'z' && next >= 'a' && next <= 'z')) continue; 
            
            subStr2.add(str2.substring(i, i+2));
        }
        
        // 각각 맵에 등록 
        for(String s : subStr1){
            if(!map1.containsKey(s)) map1.put(s, 1);
            else map1.put(s, map1.get(s)+1);
        }
        for(String s : subStr2){
            if(!map2.containsKey(s)) map2.put(s, 1);
            else map2.put(s, map2.get(s)+1);
        }
        
        double similarity;
        
        // 만약 모두 공집합이면 1로 
        if(subStr1.size() == 0 && subStr2.size() == 0) similarity = 1;
        else {
             // 교집합 원소 개수 구하기 
            int sameCount = 0;
            
            for(String s : map1.keySet()) {
                if(map2.containsKey(s)) {
                    sameCount += Math.min(map1.get(s), map2.get(s));
                }
            }

            // 합집합 원소 개수 구하기 
            int sumCount = subStr1.size() + subStr2.size() - sameCount;

            similarity = (double) sameCount / sumCount;
        
            //System.out.println(sameCount + " / " + sumCount + " = " + similarity);
        }
        
        
        return (int) (similarity * 65536);
    }
}