import java.io.*;
import java.util.*;

public class Main {

	static int R, C;
	static int max_pipe_count = 0;
	static int[] dx = {-1, 0, 1};
	static char[][] map;
	static boolean[][] visited;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   
		StringTokenizer RC_st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(RC_st.nextToken());
		C = Integer.parseInt(RC_st.nextToken());
		
		map = new char[R][C];
		visited = new boolean[R][C];
		
		for(int x=0; x<R; x++) {
			String line  = br.readLine();
			for(int y=0; y<C; y++) {
				map[x][y] = line.charAt(y);
			}
		}
		
		for(int x=0; x<R; x++) {
			find_way(x, 0);
		}
		
		System.out.println(max_pipe_count);
	}
	
	public static boolean find_way(int cur_x, int cur_y) {
		/**
		 * @see #find_way()
		 * 1. 맨 뒤 열에서 첫번째 행부터 넣는다. 
		 * 2. 왼쪽 위, 왼쪽 옆, 왼쪽 아래 방향으로 큐에 넣는다. 
		 * 3. 큐에서 하나씩 빼면서 
		 * 		3-1. 새로 이동한 자리가 X이면
		 * 
		 */
		if(cur_x<0 || cur_x>=R || cur_y<0 || cur_y >=C) return false;
		
		if(map[cur_x][cur_y] == 'x') return false;

	    map[cur_x][cur_y] = 'x';
	    
		if(cur_y==C-1) {
			max_pipe_count++;
			return true;
		}
		
		for(int i=0; i<3; i++) {
			if(find_way(cur_x+dx[i], cur_y+1)) {
				return true;
			}
		}
		return false;
	}
}
