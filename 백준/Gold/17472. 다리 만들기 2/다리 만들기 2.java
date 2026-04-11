import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * <문제 요약> 
	 * - N x M 이차원맵 
	 * - 모든 섬을 연결할 수 있는 가장 최소 길이의 다리를 구한다.
	 * - 다리 만드는 조건 
	 * 		1. 다리의 양 끝은 섬과 인접한 바다 위에 있어야 한다. (양 끝이 섬에 연결되어야 한다.)
	 * 		2. 다리의 길이는 2이상 이어야 한다. 
	 * 		3. 다리의 방향이 중간에 바뀌면 안됨. (일직선이어야 하므로 가로/세로만 가능) 
	 * 		4. 다리가 교차할 수 있음. 대신 다리 길이에 둘 다 포함되어야 한다. 
	 * 			 
	 * 
	 * @see #main(String[])
	 * 1. 입력을 받는다. 
	 * 2. 최소 다리 길이를 구한다. 
	 * 		2-1. BFS로 섬마다 번호를 붙인다.
	 * 		2-2. 섬을 잇는 다리를 전부 찾아서 다리리스트에 넣는다. 
	 * 		2-3. 다리리스트를 가지고 크루스칼 알고리즘을 적용하여 모든 섬을 잇는 최소 다리 길이를 구한다. 
	 * 3. 결과를 출력한다. 
	 * 
	 * @see #input(BufferedReader)
	 * 1. 세로 크기 N과 가로 크기 M을 입력받는다. 
	 * 2. N개의 줄에 M개의 칸을 입력받는다. 
	 * 
	 * @see #numberingLand()
	 * 1. BFS를 통하여 각 섬마다 번호를 붙인다. 
	 * 
	 * @see #makeBridge()
	 * 1. 섬을 만나면 
	 * 		1-1. 상하좌우로 이동해본다. 
	 * 			1-1-1. 바다이면 계속 이동 
	 * 			1-1-2. 다른 섬을 만나면 멈춘다.
	 * 				1-1-2-1. 길이가 2이상이면, 다리 리스트에 추가한다. 
	 * 
	 * @see #kruskal()
	 * 1. parent 배열 초기화
	 * 2. 다리 리스트를 길이 기준으로 오름차순 정렬한다.
	 * 3. 짧은 다리부터 하나씩 꺼내서 연결 시도한다. 
	 * 		3-1. 두 섬이 아직 연결 안 됐으면 (사이클 아니면) 연결한다. 
	 * 		3-2. 이미 연결된 섬이면 (사이클) 그냥 버린다. 
	 * 4. 모든 섬이 연결됐는지 확인한다. (간선 수 = 섬 수 - 1)
	 * 		4-1. 연결 불가능하면 -1 
	 * 
	 * @see #union(int, int)
	 * 1. 각각의 최상위 루트를 찾는다.
	 * 2. 루트가 같으면 이미 같은 그룹이므로 연결 안 한다.
	 * 3. 루트가 다르면 한쪽 루트를 다른 쪽 밑으로 합친다. 
	 * 
	 * @see #find(int)
	 * 1. 자기 자신이 루트면 반환한다. 
	 * 2. 아니면 부모를 타고 올라가며 루트 탐색한다. 
	 */
	
	static int N, M;
	static int[][] map;
	static int[][] landMap;
	static int minBridgeLength; 
	static boolean[][] visited;
	static int landNum;
	
	static ArrayList<Bridge> bridgeList;
	
	static class Coor {
		int r, c;
		
		Coor(int r, int c){
			this.r = r;
			this.c = c;
		}
	}
	
	static class Bridge implements Comparable<Bridge>{
		int start, end;
		int length;
		
		Bridge(int start, int end, int length){
			this.start = start;
			this.end = end;
			this.length = length;
		}
		
		@Override
		public int compareTo(Bridge o) {
			return Integer.compare(this.length, o.length);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 입력을 받는다. 
		input(br);
		// 2. 최소 다리 길이를 구한다. 
		// 2-1. BFS로 섬마다 번호를 붙인다.
		landNum = 1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {				
				if(map[i][j]==1 && !visited[i][j]) numberingLand(i, j, landNum++);
			}
		}
		// 2-2. 섬을 잇는 다리를 전부 찾아서 다리리스트에 넣는다. 
		makeBridge();
		// 2-3. 다리리스트를 가지고 크루스칼 알고리즘을 적용하여 모든 섬을 잇는 최소 다리 길이를 구한다. 
		kruskal();
		// 3. 결과를 출력한다. 
		System.out.println(minBridgeLength);
	}
	
	public static void input(BufferedReader br) throws IOException {
		// 1. 세로 크기 N과 가로 크기 M을 입력받는다. 
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 초기화
		map = new int[N][M];
		landMap = new int[N][M];
		minBridgeLength = Integer.MAX_VALUE; 
		visited = new boolean[N][M];
		bridgeList = new ArrayList<>();
		
		// 2. N개의 줄에 M개의 칸을 입력받는다. 
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void numberingLand(int r, int c, int landNum) {
		// 1. BFS를 통하여 각 섬마다 번호를 붙인다. 
		Queue<Coor> queue = new ArrayDeque<>();
		queue.add(new Coor(r,c));
		visited[r][c] = true;
		landMap[r][c] = landNum;
		
		while(!queue.isEmpty()) {
			Coor cur = queue.remove();
			
			for(int i=0; i<4; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nr>=N || nc<0 || nc>=M) continue;
				
				if(map[nr][nc]==1 && !visited[nr][nc]) {
					visited[nr][nc] = true;
					landMap[nr][nc] = landNum;
					queue.add(new Coor(nr, nc));
				}
			}
		}
	}

	public static void makeBridge() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				// 섬을 만나면 
				if(landMap[i][j] > 0) {
					int start = landMap[i][j];
					// 상하좌우로 이동해본다. 
					for(int d=0; d<4; d++) {
						int nr = i;
						int nc = j;
						int length = 0;
						
						while(true) {
							// 바다이면 계속 이동 
							nr += dr[d];
							nc += dc[d];
							
							if(nr<0 || nr>=N || nc<0 || nc>=M) break;
							if(landMap[nr][nc]==start) break; 
							
							int end = landMap[nr][nc];
							// 다른 섬을 만나면 멈춘다.
							if(end > 0) {								
								// 길이가 2이상이면, 다리 리스트에 추가 
								if(length>=2) bridgeList.add(new Bridge(start, end, length));
								break;
							}
							length++;
						}
					}
				}
			}
		}
	}
	
	static int[] parent;
	
	public static void kruskal() {
		// 1. parent 배열 초기화
		parent = new int[landNum];
		for(int i=0; i<landNum; i++) parent[i] = i;
		
		// 2. 다리 리스트를 길이 기준으로 오름차순 정렬한다.
		Collections.sort(bridgeList);
		
		int bridgeLength = 0;
		int bridgeCount = 0;
		
		//  3. 짧은 다리부터 하나씩 꺼내서 연결 시도한다. 
		for(Bridge b : bridgeList) {
			// 3-1. 두 섬이 아직 연결 안 됐으면 (사이클 아니면) 연결한다. 
			if(find(b.start) != find(b.end)) {
				union(b.start, b.end);
				bridgeLength += b.length;
				bridgeCount++;
			}
			// 3-2. 이미 연결된 섬이면 (사이클) 그냥 버린다. 
		}
		
		// 4. 모든 섬이 연결됐는지 확인한다.
		if(bridgeCount == landNum-2) minBridgeLength = bridgeLength;
		// 4-1. 연결 불가능하면 -1 
		else minBridgeLength = -1;
	}
	
	public static boolean union(int a, int b) {
		// 1. 각각의 최상위 루트를 찾는다.
		int rootA = find(a);
		int rootB = find(b);
		
		// 2. 루트가 같으면 이미 같은 그룹이므로 연결 안 한다.
		if(rootA == rootB) return false;
		
		// 3. 루트가 다르면 한쪽 루트를 다른 쪽 밑으로 합친다. 
		if(rootA > rootB) parent[rootA] = rootB;
		else parent[rootB] = rootA;
		return true;
	}
	
	public static int find(int x) {
		// 1. 자기 자신이 루트면 반환한다. 
		if(parent[x]==x) return x;
		// 2. 아니면 부모를 타고 올라가며 루트 탐색한다. 
		parent[x] = find(parent[x]);
		return parent[x];
	}

}
