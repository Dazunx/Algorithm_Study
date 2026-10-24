import java.io.*;
import java.util.*;

class Solution {
    // 1. completion을 먼저 map에 넣는다. 만약 이미 존재하면 +1 
    // 2. participant도 map에 똑같이 넣는다. 
    // 3. 둘을 비교하면서 인원 수가 다르면 멈추고 리턴한다. 
    public String solution(String[] participant, String[] completion) {
        HashMap<String, Integer> map = new HashMap<>();
        
        for(String person : participant) {
            if(map.containsKey(person)) {
                map.put(person, map.get(person)+1);
            } else {
                map.put(person, 1);
            }
        }
        
        for(String person : completion) {
            if(map.containsKey(person)) {
                if(map.get(person)-1 == 0) {
                    map.remove(person);
                    continue;
                }
                map.put(person, map.get(person)-1);
            }
        }
        
        for(String answer : map.keySet()) {
            return answer;
        }
        return "";
    }
}