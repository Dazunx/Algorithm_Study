import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 배열의 크기 N, M과 수행해야 하는 회전의 수 R를 입력받는다.
	 * 2. 돌릴 배열을 입력받는다.
	 * 3. 숫자 하나씩 회전해야하는 횟수만큼 돌린다.
	 * 4. 돌려진 배열을 출력한다. 
	 * 
	 * @see #operate(int)
	 * 1. 각 명령어에 맞는 함수를 실행한다. 
	 * 
	 * @see #op1()
	 * 1. 상하 반전: 행의 인덱스를 대칭으로 바꿈
	 * @see #op2()
	 * 2. 좌우 반전: 열의 인덱스를 대칭으로 바꿈
	 * @see #op3()
	 * 3. 오른쪽 90도 회전: (i, j) -> (j, N-1-i), N과 M이 바뀜
	 * @see #op4()
	 * 4. 왼쪽 90도 회전: (i, j) -> (M-1-j, i), N과 M이 바뀜
	 * @see #op5()
	 * 5. 4분할 시계방향 이동: 1->2, 2->3, 3->4, 4->1
	 * @see #op6()
	 * 6. 4분할 반시계방향 이동: 1->4, 4->3, 3->2, 2->1
	 * 
	 */
    static int N, M, R;
    static int[][] mat;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1. 배열의 크기 N, M과 수행해야 하는 회전의 수 R를 입력받는다.
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        // 2. 돌릴 배열을 입력받는다.
        mat = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                mat[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        // 3. 숫자 하나씩 회전해야하는 횟수만큼 돌린다.
        for (int i = 0; i < R; i++) {
            int cmd = Integer.parseInt(st.nextToken());
            operate(cmd);
        }

        // 4. 돌려진 배열을 출력한다. 
        StringBuilder sb = new StringBuilder();
        for (int[] row : mat) {
            for (int val : row) sb.append(val).append(" ");
            sb.append("\n");
        }
        System.out.print(sb);
    }

    public static void operate(int cmd) {
    	// 1. 각 명령어에 맞는 함수를 실행한다. 
        switch (cmd) {
            case 1: op1(); break; // 상하 반전
            case 2: op2(); break; // 좌우 반전
            case 3: op3(); break; // 오른쪽 90도 회전
            case 4: op4(); break; // 왼쪽 90도 회전
            case 5: op5(); break; // 4분할 시계방향 이동
            case 6: op6(); break; // 4분할 반시계방향 이동
        }
    }

    // 1. 상하 반전: 행의 인덱스를 대칭으로 바꿈
    static void op1() {
        int[][] next = new int[N][M];
        for (int i = 0; i < N; i++) next[i] = mat[N - 1 - i];
        mat = next;
    }

    // 2. 좌우 반전: 열의 인덱스를 대칭으로 바꿈
    static void op2() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M / 2; j++) {
                int temp = mat[i][j];
                mat[i][j] = mat[i][M - 1 - j];
                mat[i][M - 1 - j] = temp;
            }
        }
    }

    // 3. 오른쪽 90도 회전: (i, j) -> (j, N-1-i), N과 M이 바뀜
    static void op3() {
        int[][] next = new int[M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) next[j][N - 1 - i] = mat[i][j];
        }
        int temp = N; N = M; M = temp;
        mat = next;
    }

    // 4. 왼쪽 90도 회전: (i, j) -> (M-1-j, i), N과 M이 바뀜
    static void op4() {
        int[][] next = new int[M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) next[M - 1 - j][i] = mat[i][j];
        }
        int temp = N; N = M; M = temp;
        mat = next;
    }

    // 5. 4분할 시계방향 이동: 1->2, 2->3, 3->4, 4->1
    static void op5() {
        int[][] next = new int[N][M];
        int n = N / 2, m = M / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                next[i][j + m] = mat[i][j];           // 1 -> 2
                next[i + n][j + m] = mat[i][j + m];   // 2 -> 3
                next[i + n][j] = mat[i + n][j + m];   // 3 -> 4
                next[i][j] = mat[i + n][j];           // 4 -> 1
            }
        }
        mat = next;
    }

    // 6. 4분할 반시계방향 이동: 1->4, 4->3, 3->2, 2->1
    static void op6() {
        int[][] next = new int[N][M];
        int n = N / 2, m = M / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                next[i + n][j] = mat[i][j];           // 1 -> 4
                next[i + n][j + m] = mat[i + n][j];   // 4 -> 3
                next[i][j + m] = mat[i + n][j + m];   // 3 -> 2
                next[i][j] = mat[i][j + m];           // 2 -> 1
            }
        }
        mat = next;
    }
}