import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * 
	 * <문제 요약>
	 * 1. 김원웅의 빵집. 재정위기 ㅠㅠ 
	 * 2. 나쁜 원웅 ㄷㄷ  가스관에 몰래 파이프 설치해서 가스비 아끼기 ㄷㄷㄷ 
	 * 
	 * 3. 첫째 열 -> 마지막 열로 이동해야함.
	 * 4. 건물이 있는 경우, 다른 파이프가 이미 설치된 경우에는 설치 불가능. 
	 * 5. 우상, 우, 우하 로 이동 가능. 
	 * 6. 파이프는 겹칠 수도, 접할 수도 없다. 
	 * 7. 목표 : 최대한 많이 설치했을 때 파이프 개수 구하기 .
	 * 
	 * <문제 접근> - 완탐 밖에 없지 않나? 
	 * 1. 우선 처음부터 파이프를 설치해본다. 
	 * 2. dfs로 그 상태에서 또 설치해보고 
	 * 3. 백트래킹 하고 
	 * 4. 또 설치하고... 
	 * 5. 최대 개수 나오면 갱신하고. 
	 */

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int H, W;
	static int[][] map;
	
	static int count;
	
	static class Coor {
		int r, c;
		
		Coor(int r, int c){
			this.r = r;
			this.c = c;
		}
	}

	public static void main(String[] args) throws IOException {
		init();
		for(int i=0; i<H; i++) {
			installPipe(i, 0);
		}
		System.out.println(count);
	}
	
	public static void init() throws IOException {
		StringTokenizer st= new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		count = 0;
		map = new int[H][W];
		
		for(int i=0; i<H; i++) {
			String row = br.readLine();
			for(int j=0; j<W; j++) {
				char cur = row.charAt(j);
				if(cur=='x') {
					map[i][j] = 8; // 건물 
				}
//				if(j==0 || j==W-1) {
//					map[i][j] = 2; // 빵집 시작/도착 지점 
//				}
			}
		}
	}
	
	static int[] dr = {-1, 0, 1};
	static int[] dc = {1, 1, 1};
	
	public static boolean installPipe(int r, int c) {
		if(c==W-1) {
			count++;
			return true;
		}
		
		for(int i=0; i<3; i++) {
			int nr = r+dr[i];
			int nc = c+1;
			
			if(nr<0 || nr>=H || nc<0 || nc>=W) continue;
			if(map[nr][nc] == 8 || map[nr][nc] == 1) continue;
			
			map[nr][nc] = 1;
			if(installPipe(r+dr[i], c+1)) return true;
		}
		return false;
	}

}
