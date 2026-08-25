import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 기억한 멜로드로 방금 재생한 음악을 찾는다. 
    2. 한 음악을 반복 재생할 때도 있어서 끝-처음이 연결될 수도 있다. 
    3. 음악을 중간에 끊을 경우, 그 곡이 아닐 수도 있다. 
    4. 재생 시간과 악보를 직접 비교한다. 
        4-1. 음악제목, 재생 - 끝난 시각, 악보 제공
        4-2. 각 음은 1분에 1개씩 재생, 처음부터 재생됨, 재생 시간이 음악보다 길면 처음부터 재반복, 그렇지 않으면 재생시간만큼만 재생. 
        4-3. 조건이 일치하는 음악 => 재생 시간이 제일 긴 음악, 
                재생 시간도 같으면 => 먼저 입력된 음악 
        4-4. 조건이 일치하지않는 경우 => "(None)" 반환 
        
    5. 문제 조건 
        5-1. 1 <= 기억하는 음 m <= 1439
        5-2. 음악정보는 100개 이하 
            5-2-1. 음악 정보 : 음익시작시각, 끝시각, 제목
            5-2-2. 시각 HH:MM 형식 
            5-2-3. 1자 <= 음악 제목 <= 64자
            5-2-4. 1개 <= 악보 정보 <= 1439개
        5-3. 음 종류 12개 : C, C#, D, D#, E, F, F#, G, G#, A, A#, B
        
        
    <문제 전략>
    1. 악보와 m(멜로디)을 #이 들어간 음을 변환한다. 
    2. 
    3. 
    
    */
    
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    
    static class Music implements Comparable<Music> {
        int recordTime;
        String title;
        String parts;
        int index; // 순서 기록용 
        
        public Music(int index, int recordTime, String title, String parts) {
            this.index = index;
            this.recordTime = recordTime;
            this.title = title;
            this.parts = parts;
        }
        
        @Override
        public int compareTo(Music o) {
            if(this.recordTime == o.recordTime) {
                return Integer.compare(this.index, o.index);
            }
            return Integer.compare(o.recordTime, this.recordTime);
        }
    }
    
    static PriorityQueue<Music> pq = new PriorityQueue<>();
    
    public String solution(String m, String[] musicinfos) {
        String convertedM = replaceSharp(m);
        // 음악정보를 파싱하여 저장한다. 
        
        for(int i=0; i<musicinfos.length; i++) {
            String info = musicinfos[i];
            st = new StringTokenizer(info, ",");
            // 재생시간 계산 
            int recordTime = calcRecordTime(st.nextToken(), st.nextToken());
            // 제목 저장 
            String title = st.nextToken();
            // 우선순위큐에 음악정보 넣기
            pq.add(new Music(i, recordTime, title, replaceSharp(st.nextToken())));
        }
        
        while(!pq.isEmpty()) {
            Music music = pq.remove();
            makeParts(music);
            
            sb.setLength(0);
            sb.append(music.parts);

            if(sb.toString().contains(convertedM)) {
                return music.title;
            }
        }
        
        return "(None)";
    }
    
    public int calcRecordTime(String start, String end) {
        String[] startTime = start.split(":");
        String[] endTime = end.split(":");

        int startHour = Integer.parseInt(startTime[0]);
        int startMin = Integer.parseInt(startTime[1]);
        int endHour = Integer.parseInt(endTime[0]);
        int endMin = Integer.parseInt(endTime[1]);

        // 같은 시간대인 경우
        if (startHour == endHour) {
            return endMin - startMin;
        } 

        // 시간이 다른 경우
        return (endHour - startHour - 1) * 60 + (60 - startMin) + endMin;
    }
    
    public String replaceSharp(String str) {
        return str.replaceAll("C#", "c")
                  .replaceAll("D#", "d")
                  .replaceAll("F#", "f")
                  .replaceAll("G#", "g")
                  .replaceAll("A#", "a");
    }
    
    public void makeParts(Music music) {
        // 재생시간만큼 늘린다.
        // (재생시간을 parts의 길이로 나눈 몫 + 1)만큼 parts를 곱하여 늘린다. 
        sb.setLength(0);
        for(int i=0; i<(music.recordTime / music.parts.length()) + 1; i++) {
            sb.append(music.parts);
        }
        // 재생시간만큼 자른다. 
        music.parts = sb.toString().substring(0, music.recordTime);
    
    }
}