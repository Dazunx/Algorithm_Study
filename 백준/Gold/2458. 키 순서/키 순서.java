import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author DajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 테스트케이스 개수 T를 입력받는다. 
	 * 2. 테스트케이스마다, 
	 * 		2-1. 입력을 받는다. 
	 * 		2-2. 
	 * 		2-3. 결과를 출력한다. 
	 * 
	 * @see #input(BufferedReader)
	 * 1. 학생의 수 M, 비교 횟수 H를 입력받는다.
	 * 2. H만큼 비교 내용을 입력받아 인접리스트에 저장한다. 
	 * 
	 * @see #compareWithMe()
	 * 1. 1~M 반복한다. 
	 * 		1-1. dfs를 한다. 
	 * 
	 */
	static int M, H;
	static int knowCount;
	static ArrayList<Integer>[] adjList;
	static boolean[] visited;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 테스트케이스 개수 T를 입력받는다. 
		//int T = Integer.parseInt(br.readLine());
		
		// 2. 테스트케이스마다, 
		//for(int test_case=1; test_case<=T; test_case++) {
			// 2-1. 입력을 받는다. 
			input(br);
			// 2-2. 
			compareWithMe();
			// 2-3. 결과를 출력한다. 
			System.out.printf("%d\n", knowCount);
		//}
		
		
	}
	
	public static void input(BufferedReader br) throws NumberFormatException, IOException {
		// 1. 학생의 수 M, 비교 횟수 H를 입력받는다.
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		// 초기화 
		knowCount = 0;
		adjList = new ArrayList[M+1];
		
		for(int i=1; i<=M; i++) {
			adjList[i] = new ArrayList<>();
		}
		
		// 2. H만큼 비교 내용을 입력받아 
		for(int i=0; i<H; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			// 인접리스트에 저장한다. 
			adjList[a].add(b);
			adjList[b].add(-a);
		}
	}
	/*
	 *  1. 1~M 반복한다. 
	 * 		1-1. 현재 노드는 방문처리한다. 
	 * 		1-2. forward dfs를 하여 개수를 더한다. 
	 * 		1-3. backward dfs를 하여 개수를 더한다. 
	 * 		1-4. 만약 개수가 M-1이면 정답 수를 더한다. 
	 * 
	 */
	public static void compareWithMe() {
		// 1. 1~M 반복한다. 
		for(int i=1; i<=M; i++) {
			int cnt = 0;
			visited = new boolean[M+1];
			// 1-1. 현재 노드는 방문처리한다. 
			visited[i] = true;
			// 1-2. forward dfs를 하여 개수를 더한다. 
			cnt += countLink(i, 1);
			// 1-3. backward dfs를 하여 개수를 더한다.
			cnt += countLink(i, 2);
			// 1-4. 만약 개수가 M-1이면 정답 수를 더한다. 
			if(cnt == M-1) knowCount++;
		}
	}
	
	/**
	 * @see #countLink(int, int)
	 * 1. 해당 노드의 arraylist를 순회한다.
	 * 		1-1. 방향이 1(forward)이면 양수의 개수만 더하여 리턴한다. 
	 * 		1-2. 방향이 2(backward)이면 음수의 개수만 더하여 리턴한다. 
	 */
	public static int countLink(int node, int dir) {
	    int count = 0;
	    
	    // 현재 노드와 연결된 모든 노드(양수/음수 섞임)를 확인
	    for (int nextVal : adjList[node]) {
	        // 1. 방향(dir)에 맞는 간선인지 필터링
	        // dir 1(정방향)일 때는 양수만, dir 2(역방향)일 때는 음수만 처리
	        if (dir == 1 && nextVal < 0) continue; 
	        if (dir == 2 && nextVal > 0) continue;

	        // 2. 실제 노드 번호(인덱스)는 항상 양수여야 하므로 절댓값 처리
	        int nextNode = Math.abs(nextVal);
	        
	        // 3. 방문하지 않은 노드라면 DFS 재귀 호출
	        if (!visited[nextNode]) {
	            visited[nextNode] = true;
	            // '현재 찾은 1명' + '그 다음 노드에서 찾은 명수'를 더함
	            count += 1 + countLink(nextNode, dir);
	        }
	    }
	    return count;
	}
}
