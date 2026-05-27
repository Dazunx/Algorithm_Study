import java.io.*;
import java.util.*;

class Solution {
    /**
    <접근방법>
    1.  튜플의 길이로 정렬을 해야되는데.... 어떻게 해야되지 
    2. {, } 으로 split 하고  그 다음에 , 으로 split 하면 
        2-1. 숫자만 남음. 그러면 그 개수로 정렬해서 가장 많은 것부터 넣는다. 
    
    */
    public int[] solution(String s) {
        Map<String, Integer> map = new HashMap<>();
        
        String[] tuples = s.split("\\{|\\}");
        int idx = 0;
        
        for(String tup : tuples) {
            String[] numbers = tup.split(",");
            
            for(String num : numbers) {  
                if(num.equals("")) continue;
                if(!map.containsKey(num)) {
                    map.put(num, 1);
                } else {
                    map.put(num, map.get(num)+1);
                }
            }
        }
        
        int[] answer = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .mapToInt(e -> Integer.parseInt(e.getKey()))
                    .toArray();
            
        return answer;
    }
}