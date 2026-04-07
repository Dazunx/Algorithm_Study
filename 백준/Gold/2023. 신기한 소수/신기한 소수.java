import java.io.*;
import java.util.*;

public class Main {
	/**
	 * @author dajeongLee
	 * 
	 * @see #main(String[])
	 * 1. 소수를 구할 자릿수를 입력받는다. 
	 * 2. 첫 번째 자리에 올 수 있는 소수들로 시작한다.
	 * 3. 가능한 소수 결과를 출력한다. 
	 * 
	 * @see #findPrimeNum(int, int)
	 * 1. 기저조건 : N자리를 채우면 종료한다. 
	 * 2. 짝수는 제외한다. 
	 * 3. 새로 만든 숫자가 소수이면 다음 자릿수로 넘어간다.
	 * 
	 * @see #isPrime(int)
	 * 1. 만든 숫자의 제곱근까지만 반복하며 나머지가 0이면 소수가 아니므로 false를 리턴한다.
	 * 2. 그렇지 않으면 소수이므로 true를 리턴한다. 
	 */
    static int N;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 소수를 구할 자릿수를 입력받는다. 
        N = Integer.parseInt(br.readLine());

        // 2. 첫 번째 자리에 올 수 있는 소수들로 시작한다.
        findPrimeNum(2, 1);
        findPrimeNum(3, 1);
        findPrimeNum(5, 1);
        findPrimeNum(7, 1);

        // 3. 가능한 소수 결과를 출력한다. 
        System.out.println(sb);
    }

    public static void findPrimeNum(int num, int length) {
        // 1. 기저조건 : N자리를 채우면 종료한다. 
        if (length == N) {
            sb.append(num).append("\n");
            return;
        }

        for (int i = 1; i <= 9; i++) {
            if (i % 2 == 0) continue; // 2. 짝수는 제외한다. 
            
            int nextNum = num * 10 + i;
            // 3. 새로 만든 숫자가 소수이면 다음 자릿수로 넘어간다.
            if (isPrime(nextNum)) {
            	findPrimeNum(nextNum, length + 1);
            }
        }
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        // 1. 만든 숫자의 제곱근까지만 반복하며 나머지가 0이면 소수가 아니므로 false를 리턴한다.
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        // 2. 그렇지 않으면 소수이므로 true를 리턴한다. 
        return true;
    }
}