import java.io.*;
import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];
        TreeSet<Integer> ts = new TreeSet<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        StringTokenizer st;
        
        for(String op : operations) {
            st = new StringTokenizer(op);
            String command = st.nextToken();
            int num = Integer.parseInt(st.nextToken());
            
            switch(command) {
                case "I":
                    ts.add(num);
                    if(map.containsKey(num)) {
                        map.put(num, map.get(num) + 1);
                    } else {
                        map.put(num, 1);
                    }
                    break;
                case "D":
                    if(ts.isEmpty()) continue;
                    if(num==1){
                        int maxi = ts.last();
                        if(map.get(maxi) > 1) {
                            map.put(maxi, map.get(maxi)-1);
                        } else {
                            map.put(maxi, 0);
                            ts.removeLast();
                        }
                    } else {
                        int mini = ts.first();
                        if(map.get(mini) > 1) {
                            map.put(mini, map.get(mini)-1);
                        } else {
                            map.put(mini, 0);
                            ts.removeFirst();
                        }
                    }
                    break;
            }
        }
        
        if(ts.isEmpty()) return answer;
        else {
            answer[0] = ts.last();
            answer[1] = ts.first();
        }
        
        return answer;
    }
}