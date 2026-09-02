//코드
import java.io.*;
import java.util.*;

class Solution {
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
            // 재생시간만큼 악보를 늘린다.
            makeParts(music);
          
            if(music.parts.contains(convertedM)) {
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