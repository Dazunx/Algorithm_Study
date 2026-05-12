import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        
        Deque<Integer> pickedDeque = new ArrayDeque<>();
        
        // 1. 우선 board의 세로줄 순서대로 queue에 넣는다. 
        Queue<Integer>[] boardQueueList = new ArrayDeque[board.length];
        
        for(int j=0; j<board.length; j++) {
            boardQueueList[j] = new ArrayDeque<>();
            for(int i=0; i<board.length; i++){
                if(board[i][j]!=0) boardQueueList[j].add(board[i][j]);
            }
        }
        
        //  2. moves의 순서대로 값을 빼낸다.
        for(int pickIdx : moves) {
            pickIdx--; //  (moves[?]-1이 인덱스가 되어야 한다.)
            
            if(boardQueueList[pickIdx].isEmpty()) continue;
            
            int addInt = boardQueueList[pickIdx].remove();
            if(pickedDeque.isEmpty()) {
                pickedDeque.addLast(addInt);
                continue;
            }
            int last = pickedDeque.peekLast();
            
            // 3-1. 만약 맨 뒤의 값과 같은 값이 들어가 있으면, 맨 뒤의 값을 빼내고 answer을 +1 해준다. 
            if(last == addInt) {
                pickedDeque.removeLast();
                answer+=2;
            } 
            // 3-2. 그렇지 않으면 그냥 맨 뒤에 추가한다. 
            else {
                pickedDeque.addLast(addInt);
            }
        }
        
        return answer;
    }
}