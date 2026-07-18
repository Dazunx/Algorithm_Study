import java.io.*;

public class Solution {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		int tc = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=tc; t++) {
			String numStr = br.readLine();
			
			int count = 0;
			
			if(numStr.charAt(0) == '1') count++;
			
			for (int i=1; i<numStr.length(); i++) {
				if(numStr.charAt(i) !=  numStr.charAt(i-1)) {
					count++;
				}
			}
			
			sb.append("#").append(t).append(" ").append(count).append("\n");
		}
		System.out.print(sb);
	}

}
