import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. cost가 주어졌을 때 가장 적은 비용으로 모든 정점을 갈 수 있는 cost를 찾는다. 
    
    <문제 전략>
    1. 크루스칼 알고리즘으로 최단 경로를 찾는다. 
    */
    
    static ArrayList<Edge> edgeList = new ArrayList<>();
    static int[] parent;
    static int answer = 0;
    
    static class Edge implements Comparable<Edge> {
        int start, end;
        int cost; 
        
        public Edge(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
    
    public int solution(int n, int[][] costs) {        
        init(n, costs);
        
        kruskal(n);
        
        return answer;
    }
    
    public void init(int n, int[][] costs) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        for(int i = 0; i < costs.length; i++) {
            edgeList.add(new Edge(costs[i][0], costs[i][1], costs[i][2]));
        }
        Collections.sort(edgeList);
    }
    
    public void kruskal(int n) {
        int edgeCount = 0;
        for(Edge edge : edgeList) {
            if(union(edge.start, edge.end)) {
                answer += edge.cost;
                edgeCount++;
                if (edgeCount == n - 1) break;
            }
        }
        
    }
    
    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        
        if(find(a) == find(b)) return false;
        
        if (rootA < rootB) parent[rootB] = rootA;
        else parent[rootA] = rootB;
        
        return true;
    }
    
    public int find(int node) {
        if(parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }
       
}
