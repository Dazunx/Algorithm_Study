import java.io.*;
import java.util.*;

public class Main {
    /**
    <문제 요약>
    * 첫번째 턴에서는 탐사 시작 전에 유물 획득 불가
    
    [1] 탐사 진행
        1. 5x5격자 
        2. 유물 조각 1~7 (7가지)
        3. 5x5 안에서 3x3을 선택하여 회전 -> 90, 180, 270도 회전 -> 무조건 회전 해야함
        4. 회전 방법 중 선택 기준
            4-1. 유물 1차 획득 가치 최대화
            4-2. 회전한 각도가 가장 작은 방법
            4-3. 회전 중심 좌표 열이 가장 작은 구간
            4-4. 회전 중심 좌표 행이 가장 작은 구간

    [2] 유물 획득
        1. 상하좌우로 인접한 같은 종류의 조각이 3개 이상 연결된 경우
            합쳐져서 하나의 조각이 되고, 사라진다. 
                1-1. 유물의 가치 = 모인 조각의 개수 
        2. 조각이 사라지면 새로 생긴다. 
            2-1. 유적의 벽면에 써있는 숫자 순서대로 새로운 조각이 생겨난다. 
            2-2. (1) 열 번호가 작은 순으로 조각이 생겨난다.
                 (2) 행 번호가 큰 순으로 조각이 생겨난다. 
        3. 새로운 조각 생겨난 이후에도 조각들이 3개 이상 연결 되면 똑같이 1번부터 반복.
            (더이상 사라지는 조각이 없을 때까지) 

    [3] 탐사 반복 
        1. 탐사 진행 ~ 유물 연쇄 획득 : 1턴  
        2. 총 k번의 턴 진행 
            2-1. 각 턴마다 획득한 조각의 가치의 총합 출력. 
            2-2. 만약 한 턴에서 조각을 획득할 수 없었으면  바로 종료. 
                종료할 때 아무것도 출력하지않는다. 

    [입출력]
    - 입력 
        - 첫째줄 : 탐사 반복 횟수 K, 유물 조각 개수 M (1 <= K <= 10)(10 <= M <= 300)
        - 둘째줄부터 5줄만큼 : 5 x 5 격자 정보 
        - 마지막 줄 : 벽면에 적힌 M개의 유물 조각 번호 (1 <= 유물조각번호 <= 7)
    - 출력
        - 각 턴이 끝날 때마다 각 턴에서 획득한 총 조각의 가치의 합을 
          공백을 사이에 두고 한 줄로 출력

    ----------------------------------------------
    <문제 전략>
    1. K번 반복한다. 
        1-1. 내부 3x3에 대해서 탐색을 진행한다.
            1-1-1. 90, 180, 270도 회전시킨다.
            1-1-2. 각각 유물 획득 수를 구한다.
            1-1-3. 새로운 유물로 채워넣는다.
            1-1-4. 1-1-2에서 0이 될 때까지 1-1-2와 1-1-3을 반복한다.
            1-1-5. 각 좌표와 회전각도에 따라 유물 수를 구하고 center 우선순위 큐에 넣는다.
        1-2. 우선순위 큐에서 가장 우선인 center를 뽑아 map 상태를 그대로 변화시킨다.
        1-3. 우선순위 큐는 clear 해준다. 
        1-4. 만약 획득 수가 0이면 종료하고, 그렇지 않으면 stringBuilder에 유물 수를 넣는다.
    2. stringBuilder 값을 출력한다.
    */

    static final int SIZE = 5;
    static final int KERNEL_SIZE = 3;

    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int K, M;

    static int[][] map = new int[SIZE][SIZE];

    static PriorityQueue<NextCoor> nextCoorPQ = new PriorityQueue<>();
    static Queue<Integer> nextNumQueue = new ArrayDeque<>();
    static PriorityQueue<Center> centerPQ = new PriorityQueue<>();

    static class Center implements Comparable<Center> {
        int r, c;
        int rotateAngle;
        int benefit;
        int[][] tempMap = new int[SIZE][SIZE];

        public Center(int r, int c, int rotateAngle, int benefit, int[][] tempMap) {
            this.r = r;
            this.c = c;
            this.rotateAngle = rotateAngle;
            this.benefit = benefit;
            this.tempMap = tempMap;
        }

        @Override
        public int compareTo(Center o) {
            // 4-1. 유물 1차 획득 가치 최대화
            if(this.benefit != o.benefit) return Integer.compare(o.benefit, this.benefit);
            // 4-2. 회전한 각도가 가장 작은 방법
            if(this.rotateAngle != o.rotateAngle) return Integer.compare(this.rotateAngle, o.rotateAngle);
            // 4-3. 회전 중심 좌표 열이 가장 작은 구간
            if(this.c != o.c) return Integer.compare(this.c, o.c);
            // 4-4. 회전 중심 좌표 행이 가장 작은 구간
            return Integer.compare(this.r, o.r);
        }
    }

    static class NextCoor implements Comparable<NextCoor> {
        int r, c;
        
        public NextCoor(int r, int c) {
            this.r = r;
            this.c = c;
        }
      
        @Override
        public int compareTo(NextCoor o) {
            // (1) 열 번호가 작은 순으로 조각이 생겨난다.
            if(this.c != o.c) return Integer.compare(this.c, o.c);
            // (2) 행 번호가 큰 순으로 조각이 생겨난다. 
            return Integer.compare(o.r, this.r);
        }
    }

    /**
    <문제전략>
    1. K번 반복한다. 
        1-1. 내부 3x3에 대해서 탐색을 진행한다.
            1-1-1. 90, 180, 270도 회전시킨다.
            1-1-2. 각각 유물 획득 수를 구한다.
            1-1-3. 새로운 유물로 채워넣는다.
            1-1-4. 1-1-2에서 0이 될 때까지 1-1-2와 1-1-3을 반복한다.
            1-1-5. 각 좌표와 회전각도에 따라 유물 수를 구하고 center 우선순위 큐에 넣는다.
        1-2. 우선순위 큐에서 가장 우선인 center를 뽑아 map 상태를 그대로 변화시킨다.
        1-3. 우선순위 큐는 clear 해준다. 
        1-4. 만약 획득 수가 0이면 종료하고, 그렇지 않으면 stringBuilder에 유물 수를 넣는다.
    2. stringBuilder 값을 출력한다.
    */
    public static void main(String[] args) throws IOException {
        init();
        
        // 총 k번의 턴 진행 
        for(int turn=1; turn<=K; turn++) {
            // 탐색하면서 유물 획득 수 다 구해놓는다.
            explore();

            if(!centerPQ.isEmpty()) {
                // 우선순위 조건에 의거하여 첫번째 경우를 뽑고 나머지는 정리한다.
                Center center = centerPQ.remove();
                centerPQ.clear();

                // 만약 한 턴에서 조각을 획득할 수 없었으면  바로 종료. 
                if(center.benefit == 0) break;

                // map 상태 바꾸기
                for(int i=0; i<SIZE; i++) {
                    for(int j=0; j<SIZE; j++) {
                        map[i][j] = center.tempMap[i][j];
                    }
                }
                
                // 연쇄적으로 유물 획득하기 
                int benefitTotal = 0;
                int benefit = -1;

                benefit = findTreasure(map, true);
                benefitTotal += benefit;

                while(benefit!=0) {
                    // 새로운 유물로 채우기
                    fillNewTreasure();
                    // 채운 상태에서 다시 유물 획득 수 구하기
                    benefit = findTreasure(map, true);
                    // 연쇄적으로 획득된 유물 더해주기 
                    benefitTotal += benefit;
                }
                // 각 턴마다 획득한 조각의 가치의 총합 출력. 
                sb.append(benefitTotal).append(" ");
            }
        }
        System.out.println(sb.toString());
    }

    public static void init() throws IOException {
        // 첫째줄 : 탐사 반복 횟수 K, 유물 조각 개수 M (1 <= K <= 10)(10 <= M <= 300)
        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 둘째줄부터 5줄만큼 : 5 x 5 격자 정보 
        for(int i=0; i<SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<SIZE; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 마지막 줄 : 벽면에 적힌 M개의 유물 조각 번호 (1 <= 유물조각번호 <= 7)
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<M; i++) {
            nextNumQueue.add(Integer.parseInt(st.nextToken()));
        }
    }

    /**
    탐사 진행 
    1. 1~3을 중심좌표로 해서 center-1 ~ center+1 을 순회하며 우선순위 큐에 저장한다.  
    2. 각각 90도 회전 , 180도 회전, 270도 회전한다.
        2-1. centerPQ
    */
    public static void explore() {
        for(int i=1; i<=KERNEL_SIZE; i++) {
            for(int j=1; j<=KERNEL_SIZE; j++) {
                // 90도 회전
                rotate(90, i, j);
                // 180도 회전
                rotate(180, i, j);
                // 270도 회전
                rotate(270, i, j);
            }
        }
    }

    public static void rotate(int angle, int centerR, int centerC) {
        // temp에 값 저장해두기 
        int[][] tempMap = new int[SIZE][SIZE];

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                tempMap[i][j] = map[i][j];
            }
        }

        for(int i = centerR - 1; i <= centerR + 1; i++) {
            for(int j = centerC - 1; j <= centerC + 1; j++) {
                int dr = i - centerR;
                int dc = j - centerC;
                
                int nx = centerR, ny = centerC;
                if(angle == 90) {
                    nx = centerR + dc;
                    ny = centerC - dr;
                } else if(angle == 180) {
                    nx = centerR - dr;
                    ny = centerC - dc;
                } else if(angle == 270) {
                    nx = centerR - dc;
                    ny = centerC + dr;
                }
                tempMap[nx][ny] = map[i][j];
            }
        }

        int benefit = findTreasure(tempMap, false);
        // center 우선순위 큐에 넣기
        centerPQ.add(new Center(centerR, centerC, angle, benefit, tempMap));
    }

    /**
    [2] 유물 획득
            1. 상하좌우로 인접한 같은 종류의 조각이 3개 이상 연결된 경우
                합쳐져서 하나의 조각이 되고, 사라진다. 
                    1-1. 유물의 가치 = 모인 조각의 개수 
            2. 조각이 사라지면 새로 생긴다. 
                2-1. 유적의 벽면에 써있는 숫자 순서대로 새로운 조각이 생겨난다. 
                2-2. (1) 열 번호가 작은 순으로 조각이 생겨난다.
                    (2) 행 번호가 큰 순으로 조각이 생겨난다. 
            3. 새로운 조각 생겨난 이후에도 조각들이 3개 이상 연결 되면 똑같이 1번부터 반복.
                (더이상 사라지는 조각이 없을 때까지) 
    */

    public static int findTreasure(int[][] tempMap, boolean isReal) {
        boolean[][] visited = new boolean[SIZE][SIZE];
        int count = 0;

        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                if(visited[i][j]) continue;
                count += bfs(visited, tempMap, i, j, isReal);
            }
        }
        return count;
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static int bfs(boolean[][] visited, int[][] tempMap, int r, int c, boolean isReal) {
        Queue<NextCoor> goneQueue = new ArrayDeque<>();
        int count = 0;

        Queue<NextCoor> queue = new ArrayDeque<>();
        queue.add(new NextCoor(r, c));
        visited[r][c] = true;

        while(!queue.isEmpty()) {
            NextCoor next = queue.remove();
            goneQueue.add(next);
            count++;
            
            for(int d=0; d<4; d++) {
                int nr = next.r + dr[d];
                int nc = next.c + dc[d];

                if(nr < 0  || nr >= SIZE || nc < 0 || nc >= SIZE) continue;
                if(visited[nr][nc]) continue;
                if(tempMap[next.r][next.c] != tempMap[nr][nc]) continue;

                queue.add(new NextCoor(nr, nc));
                visited[nr][nc] = true;
            }
        }
        if(count >= 3) {
            if(isReal) {
                while(!goneQueue.isEmpty()) {
                    nextCoorPQ.add(goneQueue.remove());
                }
            }
            return count;
        }
        return 0;
    }

    public static void fillNewTreasure() {
        // 사라진 유물 칸에서 우선순위대로 하나씩 뽑아서 채우기
        while(!nextCoorPQ.isEmpty()) {
            NextCoor next = nextCoorPQ.remove();
            map[next.r][next.c] = nextNumQueue.remove();
        }
    }
}
