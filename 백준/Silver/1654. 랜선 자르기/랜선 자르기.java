import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int[] lenLine;
	static int K, N;
	static long end;
	
	public static void main(String[] args) throws IOException {
		input();
		binarySearch();
	}
	
	public static void input() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		K = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		lenLine = new int[K];
		
		for(int i=0; i<K; i++) {
			lenLine[i] = Integer.parseInt(br.readLine());
			end = Math.max(end, lenLine[i]);
		}
	}
	
	public static void binarySearch() {
		long s = 1;
		long e = end;
		long mid, result = 0;
		
		while(s<=e) {
			mid = (s+e)/2;
			
			if(isPossible(mid)) {
				result = Math.max(mid, result);
				s = mid+1;
			} else {
				e = mid -1;
			}
		}
		System.out.println(result);
	}
	
	/**
	 * @see #isPossible(int)
	 * 1. 랜선을 순회한다.
	 * 		1-1. 현재 mid로 나눈 몫을 count에 더한다. 
	 * 2.만약 count가 N과 같으면 true를 리턴하고 그렇지 않으면 false를 리턴한다. 
	 */
	public static boolean isPossible(long M) {
		long count = 0;
		for(int i=0; i<K; i++) {
			count += (lenLine[i]/M);
		}
		if(count>=N) return true;
		else return false;
	}

}
