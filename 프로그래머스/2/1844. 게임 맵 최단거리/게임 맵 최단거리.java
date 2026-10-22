import java.io.*;
import java.util.*;

class Solution {
    
    static class Coor {
        int r, c;
        int dist;
        
        public Coor(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }
    
    static int minDist = Integer.MAX_VALUE;
    public int solution(int[][] maps) {
        bfs(maps);
        
        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }
    
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    
    public void bfs(int[][] maps) {
        int N = maps.length;
        int M = maps[0].length;
        Queue<Coor> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];
        
        queue.add(new Coor(0, 0, 1));
        visited[0][0] = true;
        
        while(!queue.isEmpty()) {
            Coor cur = queue.remove();
            
            if(cur.r == N-1 && cur.c == M-1 && maps[cur.r][cur.c] == 1) {
                minDist = Math.min(minDist, cur.dist);
                continue;
            }
            
            for(int d=0; d<4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                
                if(nr < 0 || nr >= N || nc < 0 ||  nc >= M) continue;
                if(maps[nr][nc] == 0) continue;
                if(visited[nr][nc]) continue;
                
                queue.add(new Coor(nr, nc, cur.dist + 1));
                visited[nr][nc] = true;
            }
        }
    }
}