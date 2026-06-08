import java.io.*;
import java.util.*;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			N = Integer.parseInt(br.readLine());
			
			int moveDist = 0;
			int speed = 0; 
			
			for(int j=0; j<N; j++) {				
				st  = new StringTokenizer(br.readLine());
				
				moveDist += speed;
				int sec = Integer.parseInt(st.nextToken());
				if(sec != 0) {
					int change = Integer.parseInt(st.nextToken());
					
					if(sec==1) {
						speed += change;
					} else {
						speed -= change;
						if(speed < 0) speed = 0;
					}
 				} 
			}
			moveDist += speed;
			
			
			System.out.println("#" + i + " " + moveDist);
		}
	}

}
