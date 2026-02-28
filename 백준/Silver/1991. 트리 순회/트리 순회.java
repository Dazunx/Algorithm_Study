import java.io.*;
import java.util.*;

public class Main {
	static int[][] tree = new int[26][2];
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int parent = st.nextToken().charAt(0) - 'A';
			char left = st.nextToken().charAt(0);
			char right = st.nextToken().charAt(0);
			
			if(left=='.') tree[parent][0] = -1;
			else tree[parent][0] = left - 'A';
			
			if(right=='.') tree[parent][1] = -1;
			else tree[parent][1] = right - 'A';
			
		}
		
		preorder(0);
		System.out.println();
		inorder(0);
		System.out.println();
		postorder(0);
	}
	
	public static void preorder(int cur) {
		if(cur==-1) return;
		
		System.out.print((char)(cur+'A'));
		preorder(tree[cur][0]);
		preorder(tree[cur][1]);
	}
	public static void inorder(int cur) {
		if(cur==-1) return;
		
		inorder(tree[cur][0]);
		System.out.print((char)(cur+'A'));
		inorder(tree[cur][1]);
	}
	public static void postorder(int cur) {
		if(cur==-1) return;
		
		postorder(tree[cur][0]);
		postorder(tree[cur][1]);
		System.out.print((char)(cur+'A'));
	}

}
