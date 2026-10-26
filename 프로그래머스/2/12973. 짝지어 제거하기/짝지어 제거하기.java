import java.io.*;
import java.util.*;

class Solution
{
    public int solution(String s)
    {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        
        for(int i=0; i<s.length(); i++) {
            if(!stack.isEmpty() && stack.peekFirst() == s.charAt(i)) stack.removeFirst();
            else stack.addFirst(s.charAt(i));
        }
        
        return stack.isEmpty() ? 1 : 0;
    }
}