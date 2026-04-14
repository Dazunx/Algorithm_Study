import java.io.*;
import java.util.*;

public class Solution {
	/**
	 * @author DajeongLee
	 * 
	 * <문제 요약>
	 * 1. 최대한 많은 Core에 연결하였을 때, 전선 길이의 합을 구한다.
	 * 2. 최대로 연결되었을 때, 같은 Core의 수를 연결한 경우가 있으면 길이가 최소가 되는 경우로 구한다. 
	 * 
	 * 3. core에 전원이 연결되는 경우 
	 * 		3-1. 가장자리에 있는 경우
	 * 		3-2. core에서 상 하 좌 우로만 뻗어나갈 수 있음.
	 * 		3-3. 전선이 교차되면 안됨. 
	 * 
	 * @see #main(String[])
	 * 1. 테스트 케이스 개수를 입력받는다.
	 * 2. 테스트 케이스마다, 
	 * 		2-1. 입력을 받는다. 
	 * 		2-2. 
	 * 		2-3. 결과를 출력한다. 
	 * 
	 * @see #init()
	 * 1. 맵의 크기 N을 입력받는다. 
	 * 2. 맵의 정보를 입력받는다.
	 * 		2-1. 1이 있으면 core의 위치이므로, coreList에 추가한다. 
	 * 
	 * @see #connectCore()
	 * 1. coreList를 순회한다.
	 * 		1-1. core를 전부 순회하면, 
	 * 			1-1-1. 만약 최대 연결 core개수보다 많이 연결되면, 전선 길이를 바로 업데이트한다.
	 * 			1-1-2. 만약 최대 연결 core개수와 같다면, 
	 * 				1-1-2-1. 전선 길이가 최소 길이보다 작으면 업데이트한다. 
	 * 		1-2. 현재 코어에서 상하좌우로 순서대로 뻗어나간다.
	 * 			1-2-1. 현재 방향으로 뻗어지는지 확인한다.
	 * 			1-2-2. 현재 방향에서 뻗어지면, 뻗으면서 표시한다. (2로)
	 * 			1-2-3. 현재 길이를 더해서 다음 코어로 넘어간다. 
	 * 			1-2-4. 백트래킹 - 뻗으면서 표시한 것을 다시 원상복귀한다. (0으로) 
	 * 
	 */
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int N;
	static int[][] map;
	static ArrayList<Core> coreList;
	static int maxConnectedCoreCount;
	static int minWireLength; // answer
	
	static class Core {
		int r, c;
		
		Core(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			init();
			connectCore(0, 0, 0);
			sb.append(String.format("#%d %d\n", test_case, minWireLength));
		}
		System.out.println(sb);
	}
	
	public static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		coreList = new ArrayList<>();
		maxConnectedCoreCount = 0;
		minWireLength = Integer.MAX_VALUE;
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]!=0) {
					if(i==0 || i==N-1 || j==0 || j==N-1) continue;
					coreList.add(new Core(i,j));
				}
			}
		}
	}
	
	/**
	 * @see #connectCore()
	 * 1. coreList를 순회한다.
	 * 		1-1. core를 전부 순회하면, 
	 * 			1-1-1. 만약 최대 연결 core개수보다 많이 연결되면, 전선 길이를 바로 업데이트한다.
	 * 			1-1-2. 만약 최대 연결 core개수와 같다면, 
	 * 				1-1-2-1. 전선 길이가 최소 길이보다 작으면 업데이트한다. 
	 * 		1-2. 현재 코어에서 상하좌우로 순서대로 뻗어나간다.
	 * 			1-2-1. 현재 방향으로 뻗어지는지 확인한다.
	 * 			1-2-2. 현재 방향에서 뻗어지면, 뻗으면서 표시한다. (2로)
	 * 			1-2-3. 현재 길이를 더해서 다음 코어로 넘어간다. 
	 * 			1-2-4. 백트래킹 - 뻗으면서 표시한 것을 다시 원상복귀한다. (0으로) 
	 */
	
	static int[] dr = {-1, 1, 0, 0}; // 상 하 좌 우 
	static int[] dc = {0, 0, -1, 1};
	public static void connectCore(int index, int connectedCoreCount, int connectedWireLength) {
		// 1-1. core를 전부 순회하면, 
		if(index==coreList.size()) {
			// 1-1-1. 만약 최대 연결 core개수보다 많이 연결되면, 전선 길이를 바로 업데이트한다.
			if(maxConnectedCoreCount < connectedCoreCount) {
				minWireLength = connectedWireLength;
				maxConnectedCoreCount = connectedCoreCount;
			}
			// 1-1-2. 만약 최대 연결 core개수와 같다면, 
			else if(maxConnectedCoreCount == connectedCoreCount) {
				minWireLength = Math.min(minWireLength, connectedWireLength);
			}
			return;
		}
		Core cur = coreList.get(index);
		// 1-2. 현재 코어에서 상하좌우로 순서대로 뻗어나간다.
		for(int i=0; i<4; i++) {
			// 1-2-1. 현재 방향으로 뻗어지는지 확인한다.
			int length = checkMap(cur.r, cur.c, i);
			if(length > 0) {				
				// 1-2-2. 현재 방향에서 뻗어지면, 뻗으면서 표시한다. (2로)
				markMap(cur.r, cur.c, i, 2);
				// 1-2-3. 현재 길이를 더해서 다음 코어로 넘어간다. 
				connectCore(index + 1, connectedCoreCount+1, connectedWireLength+length);
				// 1-2-4. 백트래킹 - 뻗으면서 표시한 것을 다시 원상복귀한다. (0으로) 
				markMap(cur.r, cur.c, i, 0);
			}
		}
		connectCore(index + 1, connectedCoreCount, connectedWireLength);
	}
	
	/**
	 * @see #checkMap(int, int, int)
	 * 1. 만약에 방향이 0이면 
	 */
	public static int checkMap(int r, int c, int dir) {
		int length = 0;
		int nr = r;
		int nc = c;
		
		while(true) {
			nr += dr[dir];
			nc += dc[dir];
			
			if(nr<0 || nr>=N || nc<0 || nc>=N) break;
			if(map[nr][nc]!=0) return 0;
			
			length++;
		}
		return length;
	}
	
	public static void markMap(int r, int c, int dir, int value) {
		int nr = r;
		int nc = c;
		
		while(true) {
			nr += dr[dir];
			nc += dc[dir];
			
			if(nr<0 || nr>=N || nc<0 || nc>=N) break;
			
			map[nr][nc] = value;
		}
	}

}
