import java.io.*;
import java.util.*;

class Solution {
    /**
    1. 구해야하는 가로 길이를 w, 세로 길이를 h라고 하면, 각각 2씩 뺀 값을 nw, nh라고 하자.
    2. yellow칸 수는 nw * nh 이고, brown 칸 수는 2*(nw+nh) + 4 이다. 
    3. yellow의 약수를 순회하면서 이 조건을 성립하는 nw와 nh를 구하여 최종적으로 w와 h를 구한다. 
    */
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        
        // yellow의 약수를 구한다. 
        List<Integer> numList = getDivisors(yellow);
        
        // 구한 약수리스트를 순회한다. 
        for(int num : numList) {
            // 구해야 하는 가로 길이와 세로 길이에서 각각 2씩 뺀 값을 nw, nh라고 하자. 
            int nw = num;
            int nh = yellow/nw;
            
            // 테두리 수가 brown과 같아지면 종료한다.
            if(2*(nw+nh)+4 == brown) {
                answer[0] = nw + 2;
                answer[1] = nh + 2;
                break;
            }
        }
        return answer;
    }
    
    public List<Integer> getDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();

        // 1부터 n의 제곱근까지만 반복
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i); // i가 약수라면
                if (i * i != n) {
                    divisors.add(n / i); // n/i도 약수 (중복 제거)
                }
            }
        }
        // 내림차순 정렬 (추후 순서대로 탐색하는데 가로 길이가 더 길어야 하기 때문에)
        Collections.sort(divisors, Collections.reverseOrder());
        return divisors;
    }
}