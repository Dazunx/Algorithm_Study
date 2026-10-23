import java.io.*;
import java.util.*;

/**
<문제 요약>
1. 발표논문 n편 중, 
    h번 이상 인용된 논문이 h편 이상, 
        나머지 논문 h번 이하 인용                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
2. 가능한 h의 최댓값을 구해라. 

<문제전략>

*/
class Solution {
    public int solution(int[] citations) {        
        // 1. 일단 오름차순 정렬한다.
        Arrays.sort(citations);
                
        // 2. 뒤에서부터 하나씩 보면서 인덱스+1 == 현재 값인지 확인한다.
        int idx = 0;
        
        while(idx < citations.length-1) {
            int num = citations[idx];
            
            // 2-1. 만약 같은 숫자가 있으면 더 내려간다. 
            if(num >= citations.length - idx) {
                return citations.length - idx;
            }
            idx++;
        }
        return 0;
    }
}