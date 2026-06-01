import java.util.*;

class Solution {
    public String solution(String s) {
        StringTokenizer st = new StringTokenizer(s);
        StringBuilder sb = new StringBuilder();
        
        int mini = Integer.MAX_VALUE;
        int maxi = Integer.MIN_VALUE;
        
        while(st.hasMoreTokens()) {
            int num = Integer.parseInt(st.nextToken());
            
            mini = Math.min(mini, num);
            maxi = Math.max(maxi, num);
        }
        
        sb.append(mini).append(" ").append(maxi);
        
        return sb.toString();
    }
}