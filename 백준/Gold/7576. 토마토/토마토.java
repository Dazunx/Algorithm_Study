import java.io.*;
import java.util.*;

public class Main
{
    /**
     * <풀이 전략>
     * 
     * 1. 하루가 지날 때마다 격자를 순회하면서 익은 토마토는 좌우상하 방향에 토마토를 확인한다. 
     *  1-1. 만약 안 익었으면 익은 것으로. 
     *  1-2. 모든 격자가 다 익었으면 멈추고 최소 일수 반환. 
     */
     
    public static class Coor {
        
        int r, c;
        
        public Coor (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
     
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};
    
    public static int[][] map;
    public static int N, M;
    
    public static Queue<Coor> queue = new ArrayDeque<>();
    public static boolean[][] visited;
        
    public static int beforeTomato  = 0;
    
    public static int day = 0; // 반환할 값 
     
    public static void main(String[] args) throws IOException {
        // 1. 입력 받기 
        init();
        
        // 2. 날짜 세기 
        while(true) {
            // 2-1. 모두 익힐 수 없는 상태일 때 
            if(beforeTomato > 0 && queue.isEmpty()) { 
                day = -1;
                break;
            } 
            // 2-2. 끝날 수 있는 상황이면 종료
            else if(beforeTomato==0) { 
                break; 
            }
            
            // 2-3. 주위 토마토 익히기 
            tomato();
        
            // 2-4. 문제 없으면 날짜 수 증가
            day++;
            
        }
        
        // 3. 결과 출력 
        System.out.println(day);
        
    }
    
    public static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        
        map = new int[N][M];
        visited = new boolean[N][M];
        
        for(int i = 0 ; i < N; i ++) {
            
           st = new StringTokenizer(br.readLine());
           
           for(int j = 0 ; j < M; j ++) {
               
               map[i][j] = Integer.parseInt(st.nextToken());
               
               if(map[i][j] == 0) beforeTomato++;
               
           }
           
        }
        
        for(int i = 0 ; i < N; i ++) {
            
           for(int j = 0 ; j < M; j ++) {
               
               // 익은 토마토는 큐에 넣는다. 
               if(map[i][j] == 1) {
                   queue.add(new Coor(i,j));
                   visited[i][j] = true;
               }
               
           }
        }
         
    }
    
    public static void tomato() {
        
        // 모든 격자 순회하면서 익은 토마토는 좌우상하 방향에 토마토를 확인한다. 
        
        int count = queue.size();
        
        for(int q = 0; q < count; q ++ ) {
            Coor cur = queue.remove();
            
            map[cur.r][cur.c] = 1;
            
            for(int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                
                if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if(visited[nr][nc]) continue;
                if(map[nr][nc] == 1 || map[nr][nc] == -1) continue;
                
                queue.add(new Coor(nr, nc));
                visited[nr][nc] = true;
                map[nr][nc] = 1;
                beforeTomato--;
            }
            
        }
        
    }
}