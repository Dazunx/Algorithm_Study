import java.io.*;
import java.util.*;

class Solution {
    /**
        <문제 요약>
        1. 도로에 신호등이 n개 있다. 
        2. 신호등은 초록 - 노랑 - 빨강 순서로 반복된다. 지속 시간은 다르다. 
        3. 모든 신호등이 노란불일 대 정전이 발생하는 것. 
        4. [목표] : 모든 신호등이 노란불이 되는 시점을 찾아 반환한다. 
            4-1. 그런 경우가 아예 안 생기면 -1을 반환한다. 
            
        <문제 접근 전략> - 클로드와 함께 
        1. 모든 신호등의 초+노+빨 합(주기) 의 최소공배수까지만 확인한다.
            1-1. 이때까지 전부 겹치는 구간이 없으면 -1을 반환한다. 
            1-2. 최악의 경우 =  모든 신호등 주기의 곱 
        2. 최소공배수까지 가면서 
                2-1. 모든 신호등이 그 시점에 노란불인지 확인한다.
                        2-1-1. 해당 신호등이 그 시점에 노란불인지 확인하는 방법은 해당 시점을 주기로 나눈 나머지의 값으로 확인한다. 
                2-2. 만약 모든 신호등이 노란불이면 바로 멈추고 그 시점의 값을 반환한다. 
                2-3. 만약 최소공배수까지 갔는데 모든 신호등이 노란불이 되는 시점이 존재하지 않는다면 -1을 반환한다.
        
    */
        
    public int solution(int[][] signals) {        
        int LCM = calcLCM(signals);
        for(int time = 1; time <= LCM; time++){
            if(isAllYellow(signals, time)) {
                return time;
            }
        }
        return -1; 
    }
    
    public boolean isAllYellow(int[][] signals, int time){        
        for(int i=0; i<signals.length; i++){
            int sum = 0; 
            for(int j=0; j<3; j++){
                sum += signals[i][j];
            }
            
            int remainingTime = time % sum - signals[i][0];
            if(remainingTime < 0) return false;
            if(!(remainingTime <= signals[i][1] && remainingTime > 0)) {
                return false;
            }
        }
        return true;
    }
    
    public int calcLCM(int[][] signals){
        int LCM = 1;
        
        for(int i=0; i<signals.length; i++){
            int sum = 0; 
            
            for(int j=0; j<3; j++){
                sum += signals[i][j];
            }
            
            LCM = (LCM * sum) / GCD(LCM, sum);
        }
        return LCM;
    }
    
    public int GCD(int a, int b){
        if (b == 0) return a;
        return GCD(b, a % b);
    }
}