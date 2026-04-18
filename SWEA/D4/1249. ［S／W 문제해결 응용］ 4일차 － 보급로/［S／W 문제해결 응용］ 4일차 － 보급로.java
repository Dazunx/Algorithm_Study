import java.io.*;
import java.util.*;

public class Solution {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static final int INF = (int) 1e9;
	static int N;
	static int[][] map;
	
	static int[][] dist;
	
	static class Coor {
		int r, c;
		int time;
		
		Coor(int r, int c, int time){
			this.r = r;
			this.c = c;
			this.time = time;
		}
	}

	public static void main(String[] args) throws IOException {
		int T= Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			init();
			findMinDist();
			sb.append(String.format("#%d %d\n", test_case, dist[N-1][N-1]));
		}
		System.out.println(sb);
	}
	
	public static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		dist = new int[N][N];
		
		for(int i=0; i<N; i++) {
			String row = br.readLine();
			for(int j=0; j<N; j++) {
				map[i][j] = row.charAt(j)-'0';
				dist[i][j] = INF;
			}
		}
	}
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void findMinDist() {
		PriorityQueue<Coor> pq = new PriorityQueue<>((a,b) -> (a.time - b.time));
		pq.add(new Coor(0,0,0));
		dist[0][0] = 0;
		
		while(!pq.isEmpty()) {
			Coor cur = pq.remove();
			
			if(cur.time > dist[cur.r][cur.c]) continue;
			
			for(int i=0; i<4; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nr>=N || nc<0 || nc>=N) continue;
				
				int nxtTime = map[nr][nc];
				if(dist[cur.r][cur.c] + nxtTime < dist[nr][nc]) {
					dist[nr][nc] = dist[cur.r][cur.c] + nxtTime;
					pq.add(new Coor(nr, nc, dist[nr][nc]));
				}
			}
		}
	}

}
