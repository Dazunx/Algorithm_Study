import java.io.*;
import java.util.*;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int H, W;
	static int[][] map;
	static int[][] numMap;
	
	static int landNum;
	static ArrayList<Bridge> bridgeList;
	
	static int minLength; // answer
	
	static class Coor {
		int r, c;
		
		Coor(int r, int c){
			this.r = r;
			this.c = c;
		}
	}
	
	static class Bridge implements Comparable<Bridge> {
		int start, end;
		int length;
		
		Bridge(int start, int end, int length){
			this.start = start;
			this.end = end;
			this.length = length;
		}
		
		@Override
		public int compareTo(Bridge o) {
			return this.length - o.length;
		}
	}

	public static void main(String[] args) throws IOException {
		//1. 입력을 받는다.
		init();
		//2. 땅을 찾아서 섬 번호를 붙인다.
		findLand();
		//3. 땅인 부분에서 상하좌우로 다리를 뻗어본다. 연결되면 다리리스트에 추가한다.
		makeBridge();
		//4. 다리리스트를 오름차순 정렬하고, 순회하면서 크루스칼 알고리즘으로 최단 거리를 구한다. 
		kruskal();
		System.out.println(minLength);
	}
	
	public static void init() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		map = new int[H][W];
		numMap = new int[H][W];
		landNum = 0;
		bridgeList = new ArrayList<>();
		
		for(int i=0; i<H; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<W; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	public static void findLand() {
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				if(map[i][j]==1 && numMap[i][j]==0) {
					++landNum;
					findAdjLand(i, j);
				}
			}
		}
	}
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void findAdjLand(int r, int c) {
		Queue<Coor> queue = new ArrayDeque<>();
		queue.add(new Coor(r,c));
		
		while(!queue.isEmpty()) {
			Coor cur = queue.remove();
			numMap[cur.r][cur.c] = landNum;
			
			for(int i=0; i<4; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nr>=H || nc<0 || nc>=W) continue;
				if(numMap[nr][nc]!=0) continue;
				
				if(map[nr][nc]==1) {
					queue.add(new Coor(nr,nc));
				}
			}
		}
	}
	
	public static void makeBridge() {
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				if(numMap[i][j]!=0) {
					for(int d=0; d<4; d++) connectBridge(i, j, d);
				}
			}
		}
	}
	
	public static void connectBridge(int r, int c, int dir) {
		int nr = r;
		int nc = c;
		int length = 0;
		
		while(true) {
			nr += dr[dir];
			nc += dc[dir];
			
			if(nr<0 || nr>=H || nc<0 || nc>=W) return;
			if(numMap[nr][nc]==numMap[r][c]) return;
			
			
			if(numMap[nr][nc]==0) length++;
			else {
				if(length >= 2) {
					bridgeList.add(new Bridge(numMap[r][c], numMap[nr][nc],length));
				}
				break;
			}
		}
	}
	
	static int[] parent; 
	public static void kruskal() {
		parent = new int[landNum+1];
		for(int i=1; i<=landNum; i++) {
			parent[i] = i;
		}
		
		Collections.sort(bridgeList);
		
		int count = 0;
		for(Bridge bridge : bridgeList) {
			if(union(bridge.start, bridge.end)) {
				minLength += bridge.length;
				count++; 
			}
		}
		if(count!=landNum-1) minLength = -1;
	}
	
	public static boolean union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		if(rootA == rootB) return false;
		if(rootA > rootB) parent[rootA] = rootB;
		else parent[rootB] = rootA;
		return true;
	}
	
	public static int find(int x) {
		if(parent[x] == x) return x;
		return parent[x] = find(parent[x]);
	}

}
