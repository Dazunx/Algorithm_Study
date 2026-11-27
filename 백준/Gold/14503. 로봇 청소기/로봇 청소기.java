import java.io.*;
import java.util.*;

public class Main
{
    /**
     * < 문제 요약 >
     * 1. N x M 크기 
     * 2. 청소기는 바라보는 방향이 있다. 
     * 3. 현재 칸 청소되지 않았으면 청소한다. 
     *      3-1. 만약 상하좌우 중 청소되지 않은 곳이 없으면 
     *          3-1-1. 방향 유지 + 한칸 후진 가능하면 후진 후 3번
     *          3-1-2. 후진 불가능하면 작동 멈춤
     *      3-2. 청소되지 않은 곳이 있으면 
     *          3-2-1. 반시계 방향으로 90도 회전 
     *          3-2-2. 청소되지 않은 곳으로 이동 후 청소
     * 
     *  [입력]
     *  - N, M 
     *  - (r,c),d
     *  - 처음 방 상태 (모든 테두리는 벽(1)이다.) 
     *      - 0이면 빈칸 
     *      - 로봇청소기가 있으면 무조건 빈칸 (청소되지 않은 상태)
     * 
     *  [출력]
     *  - 로봇이 동작을 멈출 때까지 움직이는 횟수
     * 
     * ---
     * 
     * <문제 전략>
     * 1. 청소기 클래스 생성
     *      1-1. 좌표 (r,c)
     * 
     * 2. 처음 위치에서 청소기를 움직인다.
     * 3. 현재 칸 청소하고, 주위에 청소안된칸 있으면 반시계방향으로 회전
     * 4. 큐에서 꺼낼 때마다 카운트 증가 
     * 5. 현재 방향에 있어서 후진하는 함수 구현
     *      5-1. 현재 방향이 상 -> 하로 한칸 이동
     *      5-2. 현재 방향이 우 -> 좌로 한칸 이동
     *      5-3. 현재 방향이 하 -> 상으로 한 칸 이동
     *      5-4. 현재 방향이 좌 -> 우로 한칸 이동
     */
     
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    
    public static int N,M;
    public static int[][] map;
    public static Coor cleaner;
    
    public static int count; // 정답  
    
    public static class Coor {
        int r, c;
        int dir;
        
        public Coor(int r, int c, int dir){
            this.r = r;
            this.c = c;
            this.dir = dir;
        }
    }
    
    public static void main(String[] args)  throws IOException {
        init();
        move();
        System.out.println(count);
    }
    
    public static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int dir = Integer.parseInt(st.nextToken());
        
        cleaner = new Coor(r, c, dir);
        map = new int[N][M];
        
        for(int i = 0 ; i < N ; i ++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j <  M ; j ++ ) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
    
    public static int[] dr = {-1, 0, 1, 0}; // 북 - 동 - 남 - 서
    public static int[] dc = {0, 1, 0, -1};
    
    public static void move() {
        Queue<Coor> queue = new ArrayDeque<>();
        
        queue.add(new Coor(cleaner.r, cleaner.c, cleaner.dir));

        while(!queue.isEmpty()) {
            Coor cur = queue.remove();
            
            if(map[cur.r][cur.c] !=2) {
                map[cur.r][cur.c]=2;
                count++; // 이동했으니 카운트 증가
            }
            
            // 상하좌우 다 봤는데 없으면 현재 방향에서 뒤로 후진
            if(!isExist(cur)) {
                Coor next = moveBackside(cur);
                if(next != null) queue.add(next);
            }
            // 빈칸이 존재하면 
            else {
                int nd = (cur.dir - 1 + 4) % 4;
                int nr = cur.r + dr[nd];
                int nc = cur.c + dc[nd];
                
                if(map[nr][nc] == 0) queue.add(new Coor(nr, nc, nd));
                else queue.add(new Coor(cur.r, cur.c, nd));
            }
        }
    }
    
    public static boolean isExist(Coor cur) {
        for(int d = 0 ; d < 4 ; d++ ){
            int nd = (cur.dir+d) % 4;
            int nr = cur.r + dr[nd];
            int nc = cur.c + dc[nd];
            
            if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
            if(map[nr][nc]!=0) continue; // 벽이거나 청소된 칸은 패스
            
            return true; // 청소되지 않은 곳 발견하면 true 반환 
        }
        return false;
    }
    
    public static Coor moveBackside(Coor cur) {
        int nd = (cur.dir+2) % 4;
        int nr = cur.r + dr[nd];
        int nc = cur.c + dc[nd];
        
        if(map[nr][nc] == 1) return null;
        return new Coor(nr, nc, cur.dir);
    }
}