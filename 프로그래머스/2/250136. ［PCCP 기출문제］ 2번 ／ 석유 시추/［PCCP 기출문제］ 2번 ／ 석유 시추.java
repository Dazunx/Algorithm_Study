import java.io.*;
import java.util.*;

class Solution {
    /**
    @see #bfs (효율성)
    0. 세로 n(=land.length) / 가로 m(=land[0].length) 맵 
    1.  각 row를 set에 추가한다. 
    2. count 계산 전부 다 하면(인접한 땅 다 찾으면) 그 set을 순회하면서 oilCount 인덱스를  row 값으로 해서 count를 올려준다. 
    3. 마지막에 가장 많은 row 를 answer로 하면 됨. 
    */
    
    class Coor {
        int r, c;
        
        Coor(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    
    int answer = 0;
    
    int h, w;
    int[] oilCount;
    boolean[][] visited;
    
    public int solution(int[][] land) {
        h = land.length;
        w = land[0].length; 
        oilCount = new int[w];
        visited = new boolean[h][w];
        
        for(int i=0; i<h; i++) {  
            for(int j=0; j<w; j++) {
                if(!visited[i][j]) {
                    bfs(land, new Coor(i, j));
                }
            }
            
         }

        for(int count : oilCount) {
            answer = Math.max(answer, count);
        }
         return answer;
    }
    
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public void bfs(int[][] land, Coor start) {
        int h = land.length;
        int w = land[0].length;
        int count = 0;
        
        Queue<Coor> queue = new ArrayDeque<>();
        Set<Integer> set = new HashSet<>();
        
        queue.add(start);
        visited[start.r][start.c] = true;
        
        while(!queue.isEmpty()) {
            Coor cur = queue.remove();
            
            if(land[cur.r][cur.c] == 0) continue;
            
            count++;
            set.add(cur.c);
            
            for(int d=0; d<4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                
                if(nr<0 || nr>=h || nc<0 || nc>=w) continue;
                if(visited[nr][nc] || land[nr][nc] == 0) continue;
                
                queue.add(new Coor(nr, nc));
                visited[nr][nc] = true;
            }
        }
        
        for(int row : set) {
            oilCount[row] += count;
        }
    }
}