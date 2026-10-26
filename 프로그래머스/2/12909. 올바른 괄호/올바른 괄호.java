import java.io.*;
import java.util.*;

class Solution {
    boolean solution(String s) {
        boolean answer = true;
        
        Deque<Character> stack = new ArrayDeque<>();
        
        for(int i=0; i<s.length(); i++) {
            if(s.charAt(i) == ')') {
                if(stack.size() > 0 && stack.peek() == '(') stack.pop();
                else return false;
            } else {
                stack.push(s.charAt(i));
            }
        }
        if(!stack.isEmpty()) answer = false;
        
        return answer;
    }
}