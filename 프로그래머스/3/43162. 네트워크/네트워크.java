import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 조건>
    1. 덩어리의 개수를 찾는다. 
    
    <접근 방법>
    1. dfs 로 1일 때 
    
    */
    
    ArrayList<Integer>[] adjList;
    boolean[] visited;
    
    public int solution(int n, int[][] computers) {   
        int answer = 0;
        
        init(n, computers);
        
        for(int num = 0; num < n; num++) {
            if(visited[num]) continue;
            findNetworks(num);
            answer++;
        }
        
        return answer;
    }
    
    public void init(int n, int[][] computers) {
        visited = new boolean[n];
        adjList = new ArrayList[n];
        for(int i=0; i<n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for(int i=0; i<computers.length; i++) {
            for(int j=0; j<computers[i].length; j++) {
                if(computers[i][j]==1) adjList[i].add(j);
            }
        }
    }
    
    public void findNetworks(int cur) {
        
        for(int next : adjList[cur]) {
            if(visited[next]) continue;
            visited[next] = true;
            findNetworks(next);
        }
    }
}