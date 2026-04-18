import java.io.*;
import java.util.*;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int N;
	static int[][] map;
	
	static boolean[] impossibleColumn;
	static boolean[] diagPlus;
	static boolean[] diagMinus;
	static int answer;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			init();
			putQueen(0);
			sb.append(String.format("#%d %d\n", test_case, answer));
		}
		System.out.println(sb);
	}
	
	public static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		answer = 0;
		impossibleColumn = new boolean[N];
		diagPlus = new boolean[N*2-1];
		diagMinus = new boolean[N*2-1];
	}
	
	public static void putQueen(int idx) {
		if(idx == N) {
			answer++;
			return;
		}							
		for(int j=0; j<N; j++) {
			if(!impossibleColumn[j] && !diagPlus[idx+j] && !diagMinus[idx-j+N-1]) {
				impossibleColumn[j] = true;				
				diagPlus[idx+j]  = true;
				diagMinus[idx-j+N-1]  = true;
				
				putQueen(idx+1);
				
				impossibleColumn[j] = false;
				diagPlus[idx+j]  = false;
				diagMinus[idx-j+N-1]  = false;
			}
		}
	}

}
