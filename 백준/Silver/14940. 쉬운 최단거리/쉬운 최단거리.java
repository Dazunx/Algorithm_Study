import java.io.*;
import java.util.*;

public class Main

{

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static StringTokenizer st;
    
    public static int N, M;
    
    public static int[][] map;
    
    public static int[][] result;
    
    public static Coor start;
    
    
    public static class Coor {
    
        int r, c;
        
        int dist;
        
        public Coor(int r, int c, int dist) {
        
            this.r = r;
            
            this.c = c;
            
            this.dist = dist;
            
        }
    
    }
    
    public static void main(String[] args) throws IOException {
    
        init();
        
        bfs();
        
        printResult();
    
    }
    
    public static void init() throws IOException {
        
        st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        
        M = Integer.parseInt(st.nextToken());
        
        map = new int[N][M];
        
        result = new int[N][M];
        
        for(int i=0; i<N; i++) {
        
            st = new StringTokenizer(br.readLine());
            
            for(int j=0; j<M; j++){
            
                map[i][j] = Integer.parseInt(st.nextToken());
                
                if(map[i][j] == 2) {
                    
                    start = new Coor(i, j, 0);
                    
                }
            
            }
            
        }
    
    }
    
    public static int[] dr = {-1, 1, 0, 0};
    
    public static int[] dc = {0, 0, -1, 1};
    
    
    public static void bfs() {
        
        boolean[][] visited = new boolean[N][M];
        Queue<Coor> queue = new ArrayDeque<>();
        
        queue.add(start);
        result[start.r][start.c] = 0;
        visited[start.r][start.c] = true;
        
        while(!queue.isEmpty()) {
        
            Coor cur = queue.remove();
            
            if(cur.r < 0 || cur.r >= N || cur.c < 0 || cur.c >= M) continue;
            
            for(int d=0; d<4; d++) {
            
                int nr = cur.r + dr[d];
                
                int nc = cur.c + dc[d];
                
                if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                
                if(map[nr][nc] == 0) continue;
                
                if(visited[nr][nc]) continue;
                
                queue.add(new Coor(nr, nc, cur.dist+1));
                
                result[nr][nc] = cur.dist+1;
                
                visited[nr][nc] = true;
                
            }
        
        }
    
    }
    
    public static void printResult() {
    
    for(int i=0; i<N; i++) {
    
        for(int j=0; j<M; j++){
            
            if(map[i][j]==1 && result[i][j]==0) result[i][j] = -1;
            
            System.out.print(result[i][j] + " ");
        
        }
        
        System.out.println();
        
        }
    
    }

}