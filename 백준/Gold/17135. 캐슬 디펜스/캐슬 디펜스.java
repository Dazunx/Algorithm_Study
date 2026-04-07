import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author DajeongLee
	 * 1. N + 1번 행의 모든 칸은  전부 성(1)이다.  
	 * 2. N x M 인 격자판 
	 * 3. 궁수 3명 -> 성이 있는 칸에만 가능. (각 성에 1명)(궁수의 위치는 변함없음) 
	 * 		- 한 턴마다 궁수가 적 한명을 공격 가능 
	 * 		- 모든 궁수가 동시에 공격 
	 * 			- 거리가 D 이하인 적 중에서 가장 가까운 적 공격 
	 * 				- 거리는 맨해튼 거리 : 격자판의 두 위치 (r1, c1), (r2, c2)의 거리는 |r1-r2| + |c1-c2|
	 * 			- 여럿일 경우 가장 왼쪽에 있는 적 공격 
	 * 			- 같은 적이 여러 궁수에게 공격당하기 가능 . (공격당하면 게임에서 사라짐) 
	 * 4. 궁수의 공격 타임이 끝나면 적 이동 
	 * 		- 적은 아래로 1칸 이동 
	 * 		- 성이 있는 칸에 도착하면 끝. 
	 * 		- 적이 모두 성에 도착하면 게임 끝 . 
	 * 
	 * <로직 플랜>
	 * 1. 궁수가 위치할 조합을 구한다. 
	 * 2. 조합의 기저조건에서 적을 죽여본다.
	 * 		2-1. 궁수가 동시에 공격
	 * 			2-1-1. 현재 궁수와 모든 적의 거리를 구해 가장 가까운 & 가장 왼쪽의 적을 찾아내어 맵에서 제거한다. 
	 * 		2-2. 적 이동 (한칸 아래로)
	 * 3. 모든 적이 맵에서 사라질 때까지 2번을 반복한다.
	 * 4. 가장 많은 적을 죽인 수를 업데이트한다. 
	 * 
	 * @see #main(String[])
	 * 1. 입력을 받는다. 
	 * 2. 궁수의 조합을 구하여 죽일 수 있는 최대 적수를 구한다. 
	 * 3. 최대 적수를 출력한다. 
	 *  
	 * @see #input(BufferedReader)
	 *  1. 격자판 행의 수 N, 열의 수 M, 궁수의 공격 거리 제한 D를 입력받는다.
	 *  2. N개의 줄에는 격자판의 상태(0은 빈 칸, 1은 적이 있는 칸)를 입력받는다.
	 *  
	 * @see #combi(int)
	 * 1. 기저조건
	 * 		1-1. 궁수가 적을 죽이는 함수를 실행한다. 
	 * 		1-2. 가장 많이 죽인 적 수를 업데이트한다. 
	 * 2. 조합 만들기 
	 * 
	 * @see #kill(int[])
	 * 1. 모든 적이 맵에서 사라질 때까지 반복한다.
	 * 		1-1. 궁수가 동시에 공격
	 * 			1-1-1. 현재 궁수와 모든 적의 거리를 구해 가장 가까운 & 가장 왼쪽의 적을 찾아내어 맵에서 제거한다. 
	 * 		1-2. 적 이동 (한칸 아래로)
	 */
	
	static int N, M, D;
	static int[][] originMap;
	static int maxKilledCount;
	static ArrayList<Enemy> originEnemyList;
	
	static class Enemy {
		int x, y;
		boolean alive = true;
		
		Enemy(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public void killed() {
			this.alive = false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 입력을 받는다. 
		input(br);
		// 2. 궁수의 조합을 구하여 죽일 수 있는 최대 적수를 구한다. 
		combi(0, 0, new int[3]);
		// 3. 최대 적수를 출력한다. 
		System.out.println(maxKilledCount);
	}
	
	public static void input(BufferedReader br) throws IOException {
		// 1. 격자판 행의 수 N, 열의 수 M, 궁수의 공격 거리 제한 D를 입력받는다.
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		// 초기화 
		originMap = new int[N][M];
		maxKilledCount = 0;
		originEnemyList = new ArrayList<>();
		
		// 2. N개의 줄에는 격자판의 상태(0은 빈 칸, 1은 적이 있는 칸)를 입력받는다. 
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				originMap[i][j] = Integer.parseInt(st.nextToken());
				if(originMap[i][j]==1) originEnemyList.add(new Enemy(i, j));
			}
		}
	}
	
	public static void combi(int start, int depth, int[] selected) {
		// 1. 기저조건 
		if(depth == 3) {
			int[][] map = new int[N][M];
			for(int i=0; i<N; i++) {
				for(int j=0; j<M; j++) {
					map[i][j] = originMap[i][j];
				}
			}
			Enemy[] enemyList = new Enemy[originEnemyList.size()];
			for(int i=0; i<originEnemyList.size(); i++){
				Enemy e = originEnemyList.get(i);
		        enemyList[i] = new Enemy(e.x, e.y);
			}
			// 1-1. 궁수가 적을 죽이는 함수를 실행한다. 
			maxKilledCount = Math.max(maxKilledCount, kill(selected, map, enemyList));
			return;
		}
		
		// 2. 조합 만들기
		for(int i=start; i<M; i++) {
			selected[depth] = i;
			combi(i+1, depth+1, selected);
		}
	}
	
	static HashSet<Enemy> killList = new HashSet<>();
	
	public static int kill(int[] selected, int[][] map, Enemy[] enemyList) {
		int killedCount = 0;
		
		// 1. 모든 적이 맵에서 사라질 때까지 반복한다.
		while(true) {
			if(!isThereEnemy(enemyList)) break;
			
			// 1-1. 궁수가 동시에 공격
			for(int i=0; i<3; i++) {
				int me = selected[i];
				
				// 1-1-1. 현재 궁수와 모든 적의 거리를 구해 가장 가까운 & 가장 왼쪽의 적을 찾아낸다.
				Enemy target = killCloseEnemy(me, enemyList);
			    if(target != null) {
			        killList.add(target);
			    }
			}
			// 1-1-2. 맵에서 제거한다. 
			for(Enemy e : killList) {
				if(!e.alive) continue;
				map[e.x][e.y] = 0;
				e.killed();
				killedCount++;
			}
			// 1-2. 적 이동 (한칸 아래로)
			moveEnemy(enemyList);
			killList.clear();
		}
		return killedCount;
	}
	
	public static Enemy killCloseEnemy(int me, Enemy[] enemyList) {
		Enemy closeEnemy = null;
		int closeDist = Integer.MAX_VALUE;
		
		// 1. 적 리스트를 순회한다.  
		for(Enemy e : enemyList) {
			if(!e.alive) continue;
			
			// 1-1. 현재 궁수와 모든 적의 거리를 구한다. 
			int dist = getDist(e.x, e.y, N, me);
			// 1-2. 가장 가까운 & 가장 왼쪽의 적을 찾아낸다.
			if(dist <= D) {
				if(closeEnemy == null || closeDist > dist ||  (closeDist == dist && closeEnemy.y > e.y)) {
					closeEnemy = e; 
					closeDist = dist;
				}
			}
		}
		return closeEnemy;
	}
	
	public static int getDist(int ar, int ac, int br, int bc) {
		return Math.abs(ar-br) + Math.abs(ac-bc);
	}
	
	public static void moveEnemy(Enemy[] enemyList) {
		for(Enemy e : enemyList) {
			if(!e.alive) continue;
			e.x++;
			if(e.x >= N) e.killed();
		}
	}
	
	public static boolean isThereEnemy(Enemy[] enemyList) {
		for(Enemy e : enemyList) {
			if(e.alive) return true;
		}
		return false;
	}
}
