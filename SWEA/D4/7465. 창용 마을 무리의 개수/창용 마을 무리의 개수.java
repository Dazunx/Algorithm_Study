import java.io.*;
import java.util.*;

public class Solution {

	/**
	 * @author dajeongLee
	 * 
	 * <문제 요약>
	 * 1. 1~N번: N명의 사람 
	 * 2. 누군가를 거쳐서 아는 사람이면 아는 사이. 
	 * 3. 인접 리스트 사용하는 건가. 
	 * 		3-1. 인접 리스트에 양방향으로 true를 저장한다. 
	 * 
	 * @see 
	 * 1. 입력을 받는다.
	 * 2. 첫 사람 부터 순회한다. 
	 * 		2-1. 인접한 사람은 전부 무리에 넣는다.
	 * 			
	 * 
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int personCount, knowCount;
	static boolean[][] knowList;
	static boolean[] visited;
	
	static int count; // answer
	static int[] parent;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for(int test_case=1; test_case<=T; test_case++) {
			init();
			for(int i=1; i<=personCount; i++) {
				if(parent[i]==i) count++;
			}
			sb.append(String.format("#%d %d\n", test_case, count));
		}
		System.out.println(sb);		
	}
	
	public static void init() throws IOException {
		StringTokenizer st= new StringTokenizer(br.readLine());
		personCount = Integer.parseInt(st.nextToken());
		knowCount = Integer.parseInt(st.nextToken());
		
		knowList = new boolean[personCount+1][personCount+1];
		visited = new boolean[personCount+1];
		count=0;
		
		parent = new int[personCount+1];
		for(int i=1; i<=personCount; i++) parent[i] = i;
		
		for(int i=0; i<knowCount; i++) {
			st= new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			union(a,b);
		}
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
		if(parent[x]==x) return x;
		return parent[x] = find(parent[x]);
	}

}
