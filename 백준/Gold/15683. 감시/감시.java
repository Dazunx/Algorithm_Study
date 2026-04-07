import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author DajeongLee
	 * 
	 * <감시>
	 * - K개의 CCTV (최대 8개)
	 * 		1번 CCTV는 한 쪽 방향만 감시할 수 있다. 
	 * 		2번과 3번은 두 방향을 감시할 수 있는데, 
	 * 			2번은 감시하는 방향이 서로 반대방향이어야 하고, 
	 * 			3번은 직각 방향이어야 한다. 
	 * 		4번은 세 방향,
	 * 		5번은 네 방향을 감시할 수 있다.
	 * - 감시 방향에 있는 칸 전체 감시 가능 
	 *		- 벽은 통과 불가
	 *		- 감시 불가능 지역은 사각지대
	 * - 90도 회전 가능 
	 * 
	 * @see #main(String[])
	 * 1. 입력을 받는다. 
	 * 2. 완전탐색으로 최소 사각지대 수를 찾는다. (함수 호출)
	 * 3. 결과를 출력한다. 
	 * 
	 * @see #input(BufferedReader)
	 * 1. 사무실의 세로 크기 N, 가로 크기 M을 입력받는다. 
	 * 2. 사무실 정보를 입력받는다.
	 * 		2-1. 0은 빈칸 
	 * 		2-2. 6은 벽 
	 * 		2-3. 1~5는 CCTV : CCTV 리스트에 집어넣는다. 
	 * 
	 * @see #findMinHideCount(int, int)
	 * 1. 기저조건 
	 * 		1-1. 사각지대 개수 세서 최소값 업데이트  (함수 호출)
	 * 2. cctv를 뽑는다. 
	 * 3. 방향을 4방향을 순회한다.
	 * 		3-1. 넘겨줄 새로운 맵을 복사해서 만든다. 
	 * 		3-2. 선택된 cctv와 방향으로 영역을 표시한다. 
	 * 		3-3. 재귀를 호출한다.  
	 * 
	 *  * @see #draw(CCTV, int, int[][])
	 * 1. map에서 cctv의 방향을 시작으로 한다.
	 * 2. cctv번호에 따라, 현재 정해진 방향을 설정한다.
	 * 3. 정해진 방향대로 끝까지 이동하며 #으로 표시한다. 
	 * 
	 * 		1번 CCTV는 한 쪽 방향만 감시할 수 있다. 
	 * 			- 상 0 / 하 1 / 좌 2 / 우 3 
	 * 		2번과 3번은 두 방향을 감시할 수 있는데, 
	 * 			2번은 감시하는 방향이 서로 반대방향이어야 하고, 
	 * 				-> 상+하(0,1) / 좌+우(2,3) / *2
	 * 			3번은 직각 방향이어야 한다. 
	 * 				-> 상+우(0,3) / 우+하(3,2) / 좌+하(2,1) / 좌+상(2,0)
	 * 		4번은 세 방향,
	 * 				-> 좌+상+우(2,0,3) / 상+우+하(0,3,1) / 우+하+좌(1,3,2) / 하+좌+상(1,2,0)
	 * 		5번은 네 방향을 감시할 수 있다.
	 * 				-> 상+하+좌+우(0,1,2,3) / *4
	 * 
	 */

	static int N, M;
	static int[][] map;
	static ArrayList<CCTV> cctvList;
	
	static int minHideCount;
	
	static class CCTV {
		int r, c;
		int type;
		
		CCTV(int r, int c, int type){
			this.r = r;
			this.c = c;
			this.type = type;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 입력을 받는다. 
		input(br);
		// 2. 완전탐색으로 최소 사각지대 수를 찾는다. (함수 호출)
		findMinHideCount(0, cctvList.size(), map);
		// 3. 결과를 출력한다. 
		System.out.println(minHideCount);
	}
	
	public static void input(BufferedReader br) throws IOException {
		// 1. 사무실의 세로 크기 N, 가로 크기 M을 입력받는다. 
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 초기화
		map = new int[N][M];
		cctvList = new ArrayList<>();
		minHideCount = Integer.MAX_VALUE;
		
		// 2. 사무실 정보를 입력받는다.
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 2-3. 1~5는 CCTV : CCTV 리스트에 집어넣는다. 
				if(map[i][j]!=0 && map[i][j]!=6) cctvList.add(new CCTV(i,j,map[i][j]));
			}
		}
	}
	
	
	static int[] dr = {-1, 1, 0, 0}; // 상, 하, 좌, 우
	static int[] dc = {0, 0, -1, 1};
	
	public static void findMinHideCount(int depth, int count, int[][] map) {
		// 1. 기저조건 
		if(depth==count) {
			// 1-1. 사각지대 개수 세서 최소값 업데이트  (함수 호출)
			minHideCount = Math.min(minHideCount, counting(map));
			return;
		}
		// 2. cctv를 뽑는다. 
		CCTV cctv = cctvList.get(depth);
		
		// 3. 방향을 4방향을 순회한다.
		for(int i=0; i<4; i++) {
			// 3-1. 넘겨줄 새로운 맵을 복사해서 만든다. 
			int[][] tempMap = new int[N][M];
			for(int r=0; r<N; r++) {
				for(int c=0; c<M; c++) {
					tempMap[r][c] = map[r][c];
				}
			}
			// 3-2. 선택된 cctv와 방향으로 영역을 표시한다. 
			draw(cctv, i, tempMap);
			// 3-3. 재귀를 호출한다.  
			findMinHideCount(depth+1, count, tempMap);
		}
	}

	static int[][][] cctvDirs = {
		    {}, // 0번 무시
		    {{0}, {1}, {2}, {3}}, // 1번: 상, 하, 좌, 우
		    {{0, 1}, {2, 3}, {0, 1}, {2, 3}}, // 2번: 상+하, 좌+우 (반대 방향)
		    {{0, 3}, {3, 1}, {1, 2}, {2, 0}}, // 3번: 상+우, 우+하, 하+좌, 좌+상 (직각)
		    {{2, 0, 3}, {0, 3, 1}, {3, 1, 2}, {1, 2, 0}}, // 4번: 좌+상+우, 상+우+하, 우+하+좌, 하+좌+상 (세 방향)
		    {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}} // 5번: 상+하+좌+우 (항상 네 방향)
		};
	public static void draw (CCTV cctv, int rotation, int[][] tempMap) {
		int type = cctv.type;
	    
		// 1. map에서 cctv의 방향을 시작으로 한다.
	    int[] directions = cctvDirs[type][rotation];
	    
	    // 2. cctv번호에 따라, 현재 정해진 방향을 설정한다.
	    for (int d : directions) {
			int nr = cctv.r;
			int nc = cctv.c;
			
			while(true) {
				nr = nr + dr[d];
				nc = nc + dc[d];
				
				if(nr<0 || nr>=N || nc<0 || nc>=M) break;
				if(tempMap[nr][nc]==6) break;
				
				//  3. 정해진 방향대로 끝까지 이동하며 -1 로 표시한다. 
				if (tempMap[nr][nc] == 0) tempMap[nr][nc] = -1;
			}
	    }
	}
	
	public static int counting(int[][] tempMap) {
		// 1. 0의 개수를 센다. 
		int count = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(tempMap[i][j]==0) count++;
			}
		}
		return count;
	}
	

}
