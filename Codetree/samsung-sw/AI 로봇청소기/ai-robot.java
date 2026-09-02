// 코드
import java.io.*;
import java.util.*;

/**
<문제 요약>
1. N x N 격자 ( (1,1) ~ (N, N))
2. 격자의 상태 
    2-1. 먼지가 있음 (1~100 먼지)
    2-2. 아무런 먼지가 없음 (0)
    2-3. 물건이 위치 (-1)
3. 로봇 청소기 초기 위치에는 먼지 없음.  

아래 과정을 L번 반복한다. 
    [1] 청소기 이동
        1.오염 격자로 이동
            1-1. 이동거리가 가장 가까운 격자
            1-2. 행 번호가 가장 작은 격자
            1-3. 열 번호가 가장 작은 격자
        2. 물건이나 청소기가 있는 격자로는 이동 불가능

    [2] 청소
        1. 각 방향별로 청소량을 구하여 청소 방향을 정한다. 
            1-1. 합이 같으면 오른쪽 -> 아래 -> 왼쪽 -> 위쪽 순서로 선택
        2. 청소기 번호 순서대로 청소 진행
        3. 먼지량이 가장 큰 방향에서 청소 시작
            3-1. 각 격자마다 최대 청소량은 20 
            3-2. 청소기 방향 기준으로 현재 격자, 왼쪽, 위쪽, 오른쪽 격자 청소 

    [3] 먼지 축적 - 먼지 있는 모든 격자에 동시에 5씩 추가 

    [4] 먼지 확산 
        1. 깨끗한 격자에 (주위 4방향 격자의 먼지량 합 / 10) 만큼 먼지 확산
            1-1. 소수점 아래 수 버림
        2. 모든 격자 동시에 확산 
    [5] 출력
        1. 전체 공간의 총 먼지량 출력
            1-1. 먼지가 아예 없으면 자연스럽게 0 출력 

<입출력>
입력 
    - 첫줄 : 격자의 크기 (N), 로봇청소기 개수 K, 테스트 횟수 L
    - 두번째줄 : N줄에 걸쳐 격자 정보 (먼지의 양 / -1인 경우 물건 위치)
    - N+1 줄 : K줄에 걸쳐 로봇청소기 위치 정보 
            - i번째 줄에는 i번째로 추가되는 로봇청소기 좌표 (r,c) 
----------------------------------------------------------------

<문제 전략>
1. 입력 받기 
    1-1. 모든 좌표는 -1 해서 입력받기 (0부터 시작으로 통일) (0~N-1)
    1-2. 청소기는 청소기 큐에 넣는다. 
        1-2-1. 청소기 class : r,c,dir(청소기가 바라보고 있는 방향)
2. 전체 L번 반복 
    2-1. 청소기 이동
        2-1-1. 로봇청소기 큐에서 하나씩 뺀다. 
            2-1-1-1. 4방향의 격자를 nextMoving우선순위큐에 넣는다. 
                2-1-1-1-1. nextMoving우선순위큐는 [1]-1번 조건대로 compareTo 메소드 구현
                2-1-1-1-2. 물건이나 청소기가 있는 격자는 continue
            2-1-1-2. 첫번째 좌표를 빼고, 우선순위큐는 clear해준다. 
            2-1-1-3. 해당 좌표로 청소기의 좌표를 이동시키고, 방향을 업데이트하여 다시 청소기 큐에 넣는다. 
                2-1-1-3-1. 방향은 어떻게 정해지는 거지? => 아마도 이동한 방향으로.. 
    2-2. 청소 
        2-2-1. 청소기 큐에서 하나씩 뺀다.
            2-2-1-1. 각 4 방향에 따라 청소할 수 있는 먼지량을 구해 방향과 함께 nextClean 우선순위 큐에 넣는다.
                2-2-1-1-1. 우선순위큐는 [2]-2의 조건대로 compareTo 메소드를 구현한다. 
            2-2-1-2. 먼지량이 가장 큰 방향을 뽑고, 우선순위큐는 clear한다. 
                2-2-1-2-1. 합이 같으면 오른쪽 -> 아래 -> 왼쪽 -> 위쪽 순서로 선택된다. (우선순위큐)
        2-2-2. 먼지량이 가장 큰 방향에서 청소 시작
            2-2-2-1. 청소기 방향 기준으로 현재, 왼쪽, 위쪽, 오른쪽 격자 청소  
            2-2-2-2. 각 격자마다 최대 청소량은 20 
    2-3. 먼지 축적
        2-3-1. 격자를 전부 돌면서 모든 먼지있는 칸(>0)의 값을 +5 한다. 
    2-4. 먼지 확산
        2-4-1. 격자를 돌면서 깨끗한 먼지의 좌표를 (주위 4방향 먼지량 합 / 10) 값과 함께 큐에 넣는다.
        2-4-2. 큐에서 하나씩 빼면서 해당 좌표값을 변환한다. 
    2-5. 출력
        2-5-1. 전체 격자를 돌면서 전체 먼지량을 구한다. 
        2-5-2. 구한 먼지량을 출력한다. 
*/

public class Main {

    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int[] dr = {0, 1, 0, -1}; // 우, 하, 좌, 상
    static int[] dc = {1, 0, -1, 0};

    static int N, K, L;
    static int[][] map;
    static boolean[][] cleanerMap;

    static Queue<Cleaner> cleanerQueue = new ArrayDeque<>(); // 청소기 번호 순서대로 뽑아서 청소한다. 
    static PriorityQueue<MovingDirection> nextMovingPQ = new PriorityQueue<>(); // 로봇 청소기가 이동할 네 방향 중 하나 결정하기 위한 용도
    static PriorityQueue<CleanDirection> cleanDirPQ = new PriorityQueue<>(); // 청소할 방향 정하는 우선순위큐

    static class Cleaner{
        int r, c;
        int dir;

        public Cleaner(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }
    }

    static class MovingDirection implements Comparable<MovingDirection> {
        int r, c;
        int dist;
        int dir;

        public MovingDirection(int r, int c, int dist, int dir) {
            this.r = r;
            this.c = c;
            this.dist = dist;
            this.dir = dir;
        }

        @Override
        public int compareTo(MovingDirection o) {
            // 1-1. 이동거리가 가장 가까운 격자
            if(this.dist != o.dist) return Integer.compare(this.dist, o.dist);
            // 1-2. 행 번호가 가장 작은 격자
            if(this.r != o.r) return Integer.compare(this.r, o.r);
            // 1-3. 열 번호가 가장 작은 격자
            return Integer.compare(this.c, o.c);
        }
    }

    static class CleanDirection implements Comparable<CleanDirection> {
        int dir; 
        int dust; 
        
        public CleanDirection(int dir, int dust) {
            this.dir = dir;
            this.dust = dust;
        }

        @Override
        public int compareTo(CleanDirection o) {
            if(this.dust != o.dust) return Integer.compare(o.dust, this.dust);
            return Integer.compare(this.dir, o.dir);
        }
    }

    static class Coor {
        int r, c;
        int dust = 0;

        public Coor(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Coor(int r, int c, int dust) {
            this.r = r;
            this.c = c;
            this.dust = dust;
        }
    }
    
    public static void main(String[] args) throws IOException {
        // 1. 입력 받기 
        init();

        // 2. 전체 L번 반복 
        for(int turn=1; turn<=L; turn++) {
            // 2-1. 청소기 이동
            move();
            // 2-2. 청소 
            clean();
            // 2-3. 먼지 축적
            upgradeDust();
            // 2-4. 먼지 확산
            shareDust();
            // 2-5. 출력
                //  2-5-1. 전체 격자를 돌면서 전체 먼지량을 구한다. 
            int dustSum = 0;
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    if(map[i][j] > 0) dustSum += map[i][j];
                }
            }
                //  2-5-2. 구한 먼지량을 출력한다.
            System.out.println(dustSum);
        }
    }

    /**
    init : 입력 받는 메소드
        - 첫줄 : 격자의 크기 (N), 로봇청소기 개수 K, 테스트 횟수 L
        - 두번째줄 : N줄에 걸쳐 격자 정보 (먼지의 양 / -1인 경우 물건 위치)
        - N+1 줄 : K줄에 걸쳐 로봇청소기 위치 정보 
        - i번째 줄에는 i번째로 추가되는 로봇청소기 좌표 (r,c) 
    */
    public static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        cleanerMap = new boolean[N][N];

        cleanerQueue = new ArrayDeque<>(); 
        nextMovingPQ = new PriorityQueue<>(); 
        cleanDirPQ = new PriorityQueue<>(); 

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
  
        for(int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            // 1-1. 모든 좌표는 -1 해서 입력받기 (0부터 시작으로 통일) (0~N-1)
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            // 1-2. 청소기는 청소기 큐에 넣는다. 
            // 1-2-1. 청소기 class : r,c,dir(청소기가 바라보고 있는 방향)
            cleanerQueue.add(new Cleaner(r, c, 0));
            cleanerMap[r][c] = true;
        }
    }

    /**
    move : 청소기 이동 
        2-1-1. 로봇청소기 큐에서 하나씩 뺀다. 
            2-1-1-1. 가장 가까운 먼지 격자를 nextMovingPQ 우선순위큐에 넣는다. 
                2-1-1-1-1. nextMovingPQ 우선순위큐는 [1]-1번 조건대로 compareTo 메소드 구현
                2-1-1-1-2. 물건이나 청소기가 있는 격자는 continue
            2-1-1-2. 첫번째 좌표를 빼고, 우선순위큐는 clear해준다. 
            2-1-1-3. 해당 좌표로 청소기의 좌표를 이동시키고, 방향을 업데이트하여 다시 청소기 큐에 넣는다. 
                2-1-1-3-1. 방향은 어떻게 정해지는 거지? => 아마도 이동한 방향으로.. 
    */
    public static void move() {
        for(int i=0; i<K; i++) {
            Cleaner cleaner = cleanerQueue.remove();
            cleanerMap[cleaner.r][cleaner.c] = false;
            
            // bfs로 가장 가까운 오염 격자를 찾아나선다. 만약 최단 거리를 넘어가면 종료한다. 
            bfs(new MovingDirection(cleaner.r, cleaner.c, 0, cleaner.dir));

            // 2-1-1-2. 첫번째 좌표를 빼고, 우선순위큐는 clear해준다. 
            if(nextMovingPQ.isEmpty()) {
                cleanerQueue.add(cleaner);
                cleanerMap[cleaner.r][cleaner.c] = true;
                continue;
            }
            MovingDirection next = nextMovingPQ.remove();
            nextMovingPQ.clear();

            // 2-1-1-3. 해당 좌표로 청소기의 좌표를 이동시키고, 방향을 업데이트하여 다시 청소기 큐에 넣는다. 
            cleaner.r = next.r;
            cleaner.c = next.c;
            cleaner.dir = next.dir;
            cleanerQueue.add(cleaner);

            cleanerMap[cleaner.r][cleaner.c] = true;
        }
    }

    public static void bfs(MovingDirection start) {
        int minDist = Integer.MAX_VALUE;

        boolean[][] visited = new boolean[N][N];
        Queue<MovingDirection> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start.r][start.c] = true;

        while(!queue.isEmpty()) {
            MovingDirection next = queue.remove(); 

            if(next.dist > minDist) break;

            if(map[next.r][next.c] > 0) {
                nextMovingPQ.add(new MovingDirection(next.r, next.c, next.dist, next.dir));
                minDist = next.dist;
            }

            for(int d=0; d<4; d++) {
                int nr = next.r + dr[d];
                int nc = next.c + dc[d];

                if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if(visited[nr][nc]) continue;
                if(map[nr][nc] == -1) continue; // 물건 있을 때 패스
                if(cleanerMap[nr][nc]) continue; // 청소기 있을 때 패스
                // 최단 거리 넘어가면 좋료 
                if((map[next.r][next.c] > 0) && (next.dist + 1 > minDist)) continue;

                queue.add(new MovingDirection(nr, nc, next.dist + 1, d));
                visited[nr][nc] = true;
            }
        }
     }

    /**
    clean : 청소하는 메소드 
        2-2-1. 청소기 큐에서 하나씩 뺀다.
            2-2-1-1. 각 4 방향에 따라 청소할 수 있는 먼지량을 구해 방향과 함께 cleanDirPQ 우선순위 큐에 넣는다.
                2-2-1-1-1. 우선순위큐는 [2]-2의 조건대로 compareTo 메소드를 구현한다. 
            2-2-1-2. 먼지량이 가장 큰 방향을 뽑고, 우선순위큐는 clear한다. 
                2-2-1-2-1. 합이 같으면 오른쪽 -> 아래 -> 왼쪽 -> 위쪽 순서로 선택된다. (우선순위큐)
        2-2-2. 먼지량이 가장 큰 방향에서 청소 시작
            2-2-2-1. 청소기 방향 기준으로 현재, 왼쪽, 위쪽, 오른쪽 격자 청소  
            2-2-2-2. 각 격자마다 최대 청소량은 20 
    */
    public static void clean() {
        for(int i=0; i<K; i++) {
            Cleaner cleaner = cleanerQueue.remove();

            // 2-2-1-1. 각 4 방향에 따라 청소할 수 있는 먼지량을 구해 방향과 함께 cleanDirPQ 우선순위 큐에 넣는다.
            for(int d=0; d<4; d++) {
                cleanDirPQ.add(new CleanDirection(d, calcDustSize(cleaner, d)));
            }
            // 2-2-1-2. 먼지량이 가장 큰 방향을 뽑고, 우선순위큐는 clear한다. 
            CleanDirection cleanDir = cleanDirPQ.remove();
            cleanDirPQ.clear();

            // 2-2-2. 먼지량이 가장 큰 방향에서 청소 시작
            realClean(cleaner, cleanDir.dir);
            cleaner.dir = cleanDir.dir;

            // 다음 테스트를 위해 다시 넣어준다. 
            cleanerQueue.add(cleaner);
        }
    }

    public static int calcDustSize(Cleaner cleaner, int dir) {
        int dust = 0;
        int exceptDir = -1;

        dust += Math.min(map[cleaner.r][cleaner.c], 20);

        switch(dir) {
            case 0: // 오른쪽 : 우, 하, 현재, 상 (좌(2)가 빠짐 )
                exceptDir = 2;
                break;
            case 1: // 아래 : 우, 하, 현재, 좌 (상(3)이 빠짐)
                exceptDir = 3;
                break;
            case 2: // 왼쪽 : 좌, 하, 현재, 상 (우(0)이 빠짐)
                exceptDir = 0;
                break; 
            case 3: // 위쪽 : 좌, 상, 우, 현재  (하(1)이 빠짐)
                exceptDir = 1;
                break;
        }

        for(int d=0; d<4; d++) {
            if(d == exceptDir) continue;
            
            int nr = cleaner.r + dr[d];
            int nc = cleaner.c + dc[d];

            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
            if(map[nr][nc] < 0) continue;

            dust += Math.min(map[nr][nc], 20);
        }

        return dust;
    }

    public static void realClean(Cleaner cleaner, int dir) {
        int exceptDir = -1;

        switch(dir) {
            case 0: // 오른쪽 : 우, 하, 현재, 상 (좌(2)가 빠짐 )
                exceptDir = 2;
                break;
            case 1: // 아래 : 우, 하, 현재, 좌 (상(3)이 빠짐)
                exceptDir = 3;
                break;
            case 2: // 왼쪽 : 좌, 하, 현재, 상 (우(0)이 빠짐)
                exceptDir = 0;
                break; 
            case 3: // 위쪽 : 좌, 상, 우, 현재  (하(1)이 빠짐)
                exceptDir = 1;
                break;
        }

        for(int d=0; d<4; d++) {
            if(d == exceptDir) continue;
            
            int nr = cleaner.r + dr[d];
            int nc = cleaner.c + dc[d];

            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
            if(map[nr][nc] < 0) continue;

            //  2-2-2-1. 청소기 방향 기준으로 현재, 왼쪽, 위쪽, 오른쪽 격자 청소 (각각 최대 20을 뺀다)
            map[nr][nc] = Math.max(0, map[nr][nc] - 20);
        }

        // 현재는 전부 포함되므로 무조건 청소
        map[cleaner.r][cleaner.c] = Math.max(0, map[cleaner.r][cleaner.c] - 20);
    }

    /**
    upgradeDust : 먼지 축적 
        2-3-1. 격자를 전부 돌면서 모든 먼지있는 칸(>0)의 값을 +5 한다. 
    */
    public static void upgradeDust() {
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(map[i][j] > 0) map[i][j] += 5;
            }
        }
    }

    /**
    shareDust : 먼지 확산
        2-4-1. 격자를 돌면서 깨끗한 좌표를 (주위 4방향 먼지량 합 / 10) 값과 함께 큐에 넣는다.
        2-4-2. 큐에서 하나씩 빼면서 해당 좌표값을 변환한다. 
    */
    public static void shareDust() {
        Queue<Coor> cleanCoorQueue = new ArrayDeque<>();

        // 2-4-1. 격자를 돌면서 깨끗한 먼지의 좌표를 (주위 4방향 먼지량 합 / 10) 값과 함께 큐에 넣는다.
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(map[i][j] != 0) continue;
                int dust = 0;
                for(int d=0; d<4; d++) {
                    int nr = i + dr[d];
                    int nc = j + dc[d];

                    if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue; // 격자를 넘어가면 패스 
                    if(map[nr][nc] <= 0) continue; // 먼지 있는 칸이 아니면 패스 

                    dust += map[nr][nc];
                }
                cleanCoorQueue.add(new Coor(i, j, dust / 10));
            }
        }

        // 2-4-2. 큐에서 하나씩 빼면서 해당 좌표값을 변환한다. 
        while(!cleanCoorQueue.isEmpty()) {
            Coor cleanCoor = cleanCoorQueue.remove();
            
            map[cleanCoor.r][cleanCoor.c] = cleanCoor.dust;
        }
    }
}