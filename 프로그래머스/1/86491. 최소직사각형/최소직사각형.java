import java.io.*;
import java.util.*;

class Solution {
    static int answer = Integer.MAX_VALUE;
    
    public int solution(int[][] sizes) {
        int maxI = 0;
        int maxJ = 0;
        
        for(int[] size : sizes) {
            if(size[1] > size[0]) {
                maxI = Math.max(maxI, size[1]);
                maxJ = Math.max(maxJ, size[0]);
            } else {
                maxI = Math.max(maxI, size[0]);
                maxJ = Math.max(maxJ, size[1]);
            }
        }
        
        return maxI * maxJ;
    }
}