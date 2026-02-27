import java.io.*;
import java.util.*;

public class Main {

	static int[] parent;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		parent = new int[N+1];

		for(int i=0; i<=N; i++) {
			parent[i] = i;
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int type = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			judge(type, a, b);
		}
	}
	
	public static void judge(int type, int a, int b) {
		if(type==0) {  // union 
			union(a,b);
		} else {  // find 
			if(find(a)==find(b)) System.out.println("YES");
			else System.out.println("NO");
		}
	}
	
	public static int find(int x) {
		if(parent[x]==x) return parent[x];
		parent[x]= find(parent[x]);
		return parent[x];
	}
	
	public static boolean union(int a, int b) {
		int pa = find(a);  // find를 실행 안하면 경로 압축이 안된다. 그래서 parent배열을 직접 참조하지 말고 find를 써라. 
		int pb = find(b);
		
		if(pa==pb) return true;  // 사이클이 발생해서 union을 안한다. 
		else if (pa<pb) {
			parent[pb] = pa;
			return false;  // 사이클이 발생 안해서 union 한다. 
		}
		else {
			parent[pa] = pb;
			return false;
		}
	}

}
