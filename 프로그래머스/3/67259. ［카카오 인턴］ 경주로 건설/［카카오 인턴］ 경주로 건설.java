import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
     1. 0은 경주로, 1은 벽 
     2. 건설비용 : 직선100원, 코너 500원 
     3. 끝에 도착했을 때 최소비용을 출력한다. 
     
     <문제 전략>
     1. 맵을 입력받는다. 
     2. 다익스트라 알고리즘 적용 
     3. (N-1, N-1)칸의 최소 비용 리턴 
    */
    
    static int[][][] minCostMap;
    static int N; 
    
    static class Car {
        int r, c;
        int cost;
        int dir;
        
        public Car(int r, int c, int cost, int dir) {
            this.r = r;
            this.c = c;
            this.cost = cost;
            this.dir = dir;
        }
    }
    
    public int solution(int[][] board) {
        // 최소 비용을 저장할 맵을 만든다. 
        N = board.length;
        minCostMap = new int[N][N][4];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                for(int d=0; d<4; d++) {
                    minCostMap[i][j][d] = Integer.MAX_VALUE;
                }
            }
        }
        
        // 다익스트라 알고리즘으로 최소 비용을 구한다. 
        dilkstra(board);
        
        // (N-1, N-1)칸의 최소 비용 리턴 
        int answer = Integer.MAX_VALUE;
        for(int d=0; d<4; d++) {
            answer = Math.min(answer, minCostMap[N-1][N-1][d]);
        }
        return answer;
    }
    
    static int[] dr = {-1, 0, 1, 0}; // 위, 왼, 아래, 오
    static int[] dc = {0, -1, 0, 1};
    
    public void dilkstra(int[][] board) {
        PriorityQueue<Car> pq = new PriorityQueue<>((a,b) -> (a.cost - b.cost));
        
        for(int d=0; d<4; d++) {
            pq.add(new Car(0,0,0,d));
            minCostMap[0][0][d] = 0;
        }
        
        while(!pq.isEmpty()) {
            Car cur = pq.remove();
            
            if(cur.cost > minCostMap[cur.r][cur.c][cur.dir]) continue;
            if(cur.r == N-1 && cur.c == N-1) continue;
            
            for(int i=0; i<4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                
                if(nr<0 || nr>=N || nc<0 || nc>=N) continue;
                if(board[nr][nc] == 1) continue;
                
                int newCost = calcCost(cur, nr, nc, i);
                if(cur.cost + newCost < minCostMap[nr][nc][i]) {
                    pq.add(new Car(nr, nc, cur.cost + newCost, i));
                    minCostMap[nr][nc][i] = cur.cost + newCost;
                }
            }   
        }
    }
    
    public int calcCost(Car cur, int nr, int nc, int ndir) {
        if(cur.dir == ndir) return 100;
        else return 600;
    }
}