import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 입력을 받는다. 
	 * 2. 학생들을 위상정렬한다. 
	 * 
	 * @see #input(BufferedReader)
	 * 1. 학생 수 N과 비교 수 M을 입력받는다. 
	 * 2. M번만큼 비교관계를 입력받는다. 
	 * 		2-1. 입력받으면서 인접리스트에 저장한다. 
	 * 		2-2. 뒤에 오는 숫자들은 inDegree 배열에 해당 인덱스의 값을 하나씩 증가시킨다. 
	 * 
	 * @see #sort_students()
	 * 1. 위상정렬을 한다.
	 * 		1-1. inDegree가 0인 학생을 찾아 모두 큐에 넣는다. 
	 * 		1-2. 큐에서 하나씩 빼면서,
	 * 			1-2-1. 더 작은 키의 학생들이 다 출력되었으니 자신을 출력한다. 
	 * 			1-2-2. 해당 인접리스트를 찾아 인접리스트에 들어있는 학생 번호들을 순회한다. 
	 * 				1-2-2-1. inDegree를 1 감소시킨다. 
	 * 				1-2-2-2. 만약 감소된 값이 0이 되면 큐에 넣는다. 
	 * 		1-3. 모든 학생들을 출력할 때까지 1-2를 반복한다. 
	 * 		1-4. 결과를 한꺼번에 출력한다. 
	 * 
	 */
	
	static int[] inDegree;
	static int N;
	static ArrayList<Integer>[] orderList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 입력을 받는다. 
		input(br);
		// 2. 학생들을 위상정렬한다. 
		sort_students();
	}
	
	public static void input(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 1. 학생 수 N과 비교 수 M을 입력받는다. 
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		// 초기화 
		inDegree = new int[N];
		orderList = new ArrayList[N];
		for(int i = 0; i < N; i++) {
		    orderList[i] = new ArrayList<>();
		}
		
		// 2. M번만큼 비교관계를 입력받는다. 
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int first = Integer.parseInt(st.nextToken())-1;
			int second = Integer.parseInt(st.nextToken())-1;
			
			// 2-1. 입력받으면서 인접리스트에 저장한다. 
			orderList[first].add(second);
			// 2-2. 뒤에 오는 숫자들은 inDegree 배열에 해당 인덱스의 값을 하나씩 증가시킨다. 
			inDegree[second]++;
		}
	}
	
	public static void sort_students() {
		// 1. 위상정렬을 한다.
		Queue<Integer> queue = new ArrayDeque<>();
		StringBuilder sb = new StringBuilder();
		
		// 1-1. inDegree가 0인 학생을 찾아 모두 큐에 넣는다. 
		for(int i=0; i<N; i++) {
			if(inDegree[i]==0) queue.add(i);
		}
		
		while(!queue.isEmpty()) {
			// 1-2. 큐에서 하나씩 빼면서,
			int next = queue.poll();
			// 1-2-1. 더 작은 키의 학생들이 다 출력되었으니 자신을 출력한다. 
			sb.append(next+1).append(" ");

			// 1-2-2. 해당 인접리스트를 찾아 인접리스트에 들어있는 학생 번호들을 순회한다. 
			for(int i=0; i<orderList[next].size(); i++) {
				int cur = orderList[next].get(i);
				// 1-2-2-1. inDegree를 1 감소시킨다. 
				inDegree[cur]--;
				// 1-2-2-2. 만약 감소된 값이 0이 되면 큐에 넣는다. 
				if(inDegree[cur]==0) queue.add(cur);
			}
		}
		// 1-4. 결과를 한꺼번에 출력한다. 
		System.out.println(sb.toString());
	}

}
