import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 9:00시부터 n회 t분 간격으로 역에 도착한다. 
    2. 셔틀에 최대 m 명 탑승 가능
    3. 셔틀이 도착했을 때 도착한 순간에 대기열에 선 크루까지 대기 순서대로 태움
        3-1. 9:00에 도착한 셔틀에 9:00에 줄 선 크루 탑승 가능
    4. 23:59에 모두 집으로 돌아감
    
    [입력]
    - 셔틀 운행 횟수 n 
    - 셔틀 운행 간격 t
    - 한 셔틀에 탈 수 있는 최대 크루 수 m
    - 크루들이 대기열에 도착하는 시간 비열 timetable (00:01 ~ 23:59)
    
    [출력]
    - 콘이 셔틀을 타고 사무실로 갈 수 있는 도착 시간 중 가장 늦은 시각 
    
    [예제 이해]
    1. "08:00", "08:01", "08:02", "08:03"
        1-1. 9시에 4명 다 탐  -> 5명 까지 가능하므로 9시가 가장 늦음.
    (?)2. "08:00", "09:09", "09:10" (2명 탑승 / 10분 간격 / 2대)
        2-1. 9시에 2명 타고(08:00, 09:09) 한명 남음 (09:10)
        2-2.  9:09에 도착하면 콘과 나머지 한명 더 탐..? 
        - 근데 10분 간격인데 어떻게 .. 아 0시부터 10분 ..? 
    3. "09:00", "09:00", "09:00", "09:00" (2명 탑승 / 1분 간격 / 2대)
        3-1. 9:00 에 바로 타야됨. -> 08:59
    4. "00:01", "00:01", "00:01", "00:01", "00:01" (5명 탑승 / 1분 간격 / 1대)
        4-1. 대기열 5명 보다 빨리 와야 하므로 00:00
    5. ["23:59"], 1명 탑승 / 1분 간격 / 1대
        5-1. 9시에 바로 타야하므로 9:00.. 
    6. ["23:59" * 16명] , 45명 탑승 / 60분 간격 / 10대 
        6-1. 9시부터 10시간 뒤인 16시에 타야함. 
    
    -----
    <문제 전략>
    1. 시간 순으로 우선순위큐에 넣는다.  
    2. 9시부터 t분 간격으로 늘리면서 현재 시각보다 빨리 도착하는지 확인 후, 
        우선순위큐에서 m 명을 제거한다. 
    3. 만약 마지막 버스이면 마지막 m명을 검사한다. 
        3-1. m명을 시간 내림차순으로 콘이 들어갔을 때 m명에 속하기 가능한지 확인한다.
        3-2. 만약 가장 늦는 사람보다 1분 앞서야함. 
        3-3. 근데 마지막 버스인데 m명이 다 안 채워지면 그냥 마지막 버스 도착 시간에 맞춰 와도 됨. 
    */
    
    static PriorityQueue<Time> pq = new PriorityQueue<>();
    
    static class Time implements Comparable<Time> {
        int hour;
        int min;
        
        public Time(String time) {
            convertTimeToInt(time);
        }
        
        public Time(int hour, int min) {
            this.hour = hour;
            this.min = min;
        }
        
        void convertTimeToInt(String time) {
            StringTokenizer st = new StringTokenizer(time, ":");
            hour = Integer.parseInt(st.nextToken());
            min = Integer.parseInt(st.nextToken());
        }
        
        @Override 
        public int compareTo(Time o) {
            if(this.hour == o.hour) return Integer.compare(this.min, o.min);
            return Integer.compare(this.hour, o.hour);
        }
        
        @Override
        public String toString() {
            return String.format("%02d:%02d", hour, min);
        }
    }
    
    public String solution(int n, int t, int m, String[] timetable) {
        // 1. 시간 순으로 우선순위큐에 넣는다.  
        for(String time : timetable) {
            pq.add(new Time(time));
        }
        
        int busTime = 9*60;
        Time lastCrew = null;
        Time answer = null;
        int count = 0;
        
        // 2. 9시부터 t분 간격으로 늘리면서 현재 시각보다 빨리 도착하는지 확인 후, 우선순위큐에서 m 명을 제거한다. 
        // 가장 앞의 것 peek로 제거하지 않고 확인한다. 
        for(int i=0; i<n; i++) {
            count = 0;
            
            while (!pq.isEmpty() && count < m) {
                Time crew = pq.peek();
                
                if((crew.hour*60 + crew.min) <= busTime) {
                    lastCrew = pq.remove();
                    count++;
                } else {
                    break;
                }
            }
            
            if(i < n-1) {
                busTime += t;
            } 
        }
        
        /**
        3. 만약 마지막 버스이면 마지막 m명을 검사한다. 
            3-1. m명을 시간 내림차순으로 콘이 들어갔을 때 m명에 속하기 가능한지 확인한다.
            3-2. 만약 가장 늦는 사람보다 1분 앞서야함. 
            3-3. 근데 마지막 버스인데 m명이 다 안 채워지면 그냥 마지막 버스 도착 시간에 맞춰 와도 됨. 
        */
        if(count < m) {
            answer = new Time(busTime/60, busTime%60);
        } else {
            int lastCrewMin = lastCrew.hour*60 + lastCrew.min - 1;
            answer = new Time(lastCrewMin/60, lastCrewMin%60);
        }
        
        return answer.toString();
    }

}