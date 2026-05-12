import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 어느 시간대 이용자가 m 명 미만 -> 서버 증설 X 
    2. 이용자가 nXm명 ~ (n+1)*m명 미만 -> 최소 n대의 증설된 서버 운영 중이어야 함. 
        2-1. 증설된 서버는 k 시간동안 운영하고 반납. 
        
    <문제 접근>
    0. 길이 24인 이용가능 수 리스트를 만든다. (m으로 초기화)
    1. 게임이용자 수 리스트를 순회한다.
    2. 리스트에 저장된 이용가능자 수보다 미만이면 패스한다.
    3. 게임 이용자 수 / m을 n이라고 하자. 증설 횟수를 n만큼 늘린다. 
        3-1. 현재 시간부터 k 만큼 이용 가능 수 리스트를 업데이트한다.
        3-2. 이용 가능 수를 (n+1)*m으로 증가시킨다.
    */
    
    int ONEDAY = 24;
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        //  0. 길이 24인 이용가능 수 리스트를 만든다. (m으로 초기화)
        int[] available = new int[ONEDAY];
        Arrays.fill(available, m);
        
        // 1. 게임이용자 수 리스트를 순회한다.
        for(int i = 0; i < ONEDAY; i++) {
            int curPlayers = players[i];
            // 2. 리스트에 저장된 이용가능자 수보다 미만이면 패스한다.
            if(curPlayers < available[i]) continue; 
            
            // 3. 게임 이용자 수 / m을 n이라고 하자. 증설 횟수를 n만큼 늘린다. 
            int n  = (curPlayers-available[i])/m+1;
            answer += n;
            
            // 3-1. 현재 시간부터 k 만큼 이용 가능 수 리스트를 업데이트한다.
            for(int kIdx = i+1; kIdx < i+k; kIdx++) {
                if(kIdx >= ONEDAY) break;
                // 3-2. 이용 가능 수를 (n+1)*m으로 증가시킨다. 
                available[kIdx] += n*m;
            }
        }
        
        return answer;
    }
}