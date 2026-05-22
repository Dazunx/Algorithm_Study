import java.io.*;
import java.util.*;

class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {        
        int[] curTime = new int[2];
        curTime[0] = Integer.parseInt(pos.substring(0,2));
        curTime[1] = Integer.parseInt(pos.substring(3,5));
        
        int[] endTime = new int[2];
        endTime[0] = Integer.parseInt(video_len.substring(0,2));
        endTime[1] = Integer.parseInt(video_len.substring(3,5));
        
        int[] opStart = new int[2];
        opStart[0] = Integer.parseInt(op_start.substring(0,2));
        opStart[1] = Integer.parseInt(op_start.substring(3,5));
        
        int[] opEnd = new int[2];
        opEnd[0] = Integer.parseInt(op_end.substring(0,2));
        opEnd[1] = Integer.parseInt(op_end.substring(3,5));
        
        if (isOpening(curTime, opStart, opEnd)) {
            curTime[0] = opEnd[0];
            curTime[1] = opEnd[1];
        }
        
        for(String command : commands) {
            switch(command) {
                case "prev":
                    curTime[1] -= 10;
                    
                    if(curTime[1] < 0){
                        curTime[1] += 60;
                        curTime[0]--;
                    }
                    if(curTime[0] < 0) {
                        curTime[0] = 0;
                        curTime[1] = 0;
                    }
                    break;
                    
                case "next":
                    curTime[1] += 10;
                    
                    if (curTime[1] >= 60) {
                        curTime[1] -= 60;
                        curTime[0]++;
                    }
                    
                    if((curTime[0] == endTime[0] && curTime[1] > endTime[1]) || curTime[0] > endTime[0]) {
                        curTime[0] = endTime[0];
                        curTime[1] = endTime[1];
                    }
                    
                    break;
            }
            
            if (isOpening(curTime, opStart, opEnd)) {
                curTime[0] = opEnd[0];
                curTime[1] = opEnd[1];
            }
        }
        
        return String.format("%2s:%2s", curTime[0]+"", curTime[1]+"").replace(" ", "0");
    }
    
    private boolean isOpening(int[] cur, int[] start, int[] end) {
        int curSec = cur[0] * 60 + cur[1];
        int startSec = start[0] * 60 + start[1];
        int endSec = end[0] * 60 + end[1];
        
        return curSec >= startSec && curSec <= endSec;
    }
}