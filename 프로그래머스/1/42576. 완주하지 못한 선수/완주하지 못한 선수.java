import java.io.*;
import java.util.*;

class Solution {
    // 1. participant을 먼저 map에 넣는다. 만약 이미 존재하면 +1 
    // 2. completion을 순회하면서 map에서 -1씩 하고 마지막 남은 사람을 리턴한다. 
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