import java.io.*;
import java.util.*;

public class Solution {
	
	static final int INF = (int) 1e9;

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int N;
	static int[][] dist;
	static int[] CC;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case=1; test_case<=T; test_case++) {
			input();
			floyd();
			sb.append(String.format("#%d %d\n", test_case, whoIsMin()));
		}
		System.out.println(sb);
	}
	
	public static void input() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		dist = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				dist[i][j] = INF;
			}
		}
		CC = new int[N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				int val = Integer.parseInt(st.nextToken());
				if(val != 0 ) dist[i][j] = val;
			}
			dist[i][i] = 0;
		}
	}
	
	public static void floyd() {
		for(int k=0; k<N; k++) {
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(dist[i][k] != INF && dist[k][j] != INF) {
						if(dist[i][j] > dist[i][k] + dist[k][j]) {
							dist[i][j] = dist[i][k] + dist[k][j];
						}
					}
				}
			}
		}
	}
	
	public static int whoIsMin() {
		int minCC = Integer.MAX_VALUE;
		
		for(int i=0; i<N; i++) {
			int sum = 0;
			for(int j=0; j<N; j++) {
				sum += dist[i][j];
			}
			minCC = Math.min(minCC, sum);
		}
		return minCC;
	}

}
