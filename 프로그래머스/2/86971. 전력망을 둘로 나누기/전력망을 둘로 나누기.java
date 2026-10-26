import java.io.*;
import java.util.*;

class Solution {
    static ArrayList<Integer>[] adjList;
    
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        init(n, wires);
        
        for(int[] wire : wires) {
            int a = wire[0];
            int b = wire[1];
            
            boolean[] visited = new boolean[n+1];
            visited[a] = true;
            visited[b] = true;             
            
            int count = dfs(visited, a);
            
            int diff = n - count;
            answer = Math.min(answer, Math.abs(count - diff));
        }
        
        return answer;
    }
    
    
    public void init(int n, int[][] wires) {
        adjList = new ArrayList[n+1];
        
        for(int i=1; i<=n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for(int i=0; i<wires.length; i++) {
            adjList[wires[i][0]].add(wires[i][1]);
            adjList[wires[i][1]].add(wires[i][0]);
        }
    }
    
    public int dfs(boolean[] visited, int cur) {
        int count = 1; 
        
        for(int next : adjList[cur]) {
            if(visited[next]) continue;
            
            visited[cur] = true;
            count += dfs(visited, next);
        }
        
        return count;
    }
}