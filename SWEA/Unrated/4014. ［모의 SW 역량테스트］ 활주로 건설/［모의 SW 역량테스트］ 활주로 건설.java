import java.io.*;
import java.util.*;

public class Solution {
	/**
	 * @author dajeongLee
	 * 
	 * <문제 요약>
	 * 1. N x N 크기 맵
	 * 2. 활주로 방향은 가로, 세로만 가능 
	 * 3. 경사로 높이는 1, 경사로 길이는 X 
	 * 4. 목표 : 건설 가능한 활주로 개수 구하기.
	 * 
	 * @see #main(String[])
	 * 1. 테
	 * 
	 * @see #input()
	 * 
	 * 
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int connectCount;
	static int N, X;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			input();
			buildConnection(map);
			buildConnection(swap(map));
			sb.append(String.format("#%d %d\n", test_case, connectCount));
		}
		System.out.println(sb);
	}
	
	public static void input() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		connectCount = 0;
		map = new int[N][N];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	static int[] dr = {0, 0}; // 좌, 우 
	static int[] dc = {-1, 1};
	
	public static int[][] swap(int[][] before){
		int[][] after = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				after[j][i] = before[i][j];
			}
		}
		return after;
	}
	/**
	 * @see #buildConnection(int[][], boolean)
	 * 1. 처음부터 끝까지 가면서 
	 * 		1-1. 현재와 다음 칸의 높이 차이가 1보다 크면 바로 넘어간다.
	 * 		1-2. 만약 현재가 다음 칸보다 1 크다면 길이가 X만큼 유지 되는지 확인한다. 
	 * 			1-2-1. 경사로가 설치된 구간이 아닌지도 확인한다.  
	 * 		1-3. 만약 현재가 이전 칸보다 1 크다면 길이가 X만큼 유지 되는지 확인한다.
	 */
	
	public static void buildConnection(int[][] checkmap) {
		for(int i=0; i<N; i++) {
			boolean[] visited = new boolean[N];

			for(int j=0; j<N; j++) {
				if(j<N-1 && Math.abs(checkmap[i][j]-checkmap[i][j+1]) > 1) break;
				if(j<N-1 && checkmap[i][j]-checkmap[i][j+1] == 1) {
					if(!canBuild(checkmap, i, j, 1, visited)) break;
				} 
				if(j>0 && checkmap[i][j]-checkmap[i][j-1] == 1) {
					if(!canBuild(checkmap, i, j, 0, visited)) break;
				}
				if(j==N-1) {
					connectCount++;
				}
			}
		}
	}
	
	public static boolean canBuild(int[][] checkmap, int r, int c, int dir, boolean[] visited) {
		int nr = r;
		int nc = c;
		int length = 0;
		
		while(true) {
			nr += dr[dir];
			nc += dc[dir];
			
			if(nr<0 || nr>=N || nc<0 || nc>=N) return false;
			
			if(checkmap[r][c]-checkmap[nr][nc]==1 && !visited[nc]) {
				visited[nc] = true;
				length++;
			}
			else return false;
			
			if(length >= X) return true;
		}
	}
}
