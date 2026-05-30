import java.io.*;
import java.util.*;

class Solution {
    /**
    <조건>
    1. x,y에서 출발해 r,c로 이동. 
    2. 이동 거리가 총 k 여야 하고, 중복 방문 가능하다. 
    3. 여러 경로 중에서 사전순으로 가장 빠른 경로로 채택한다. 
    <접근방법>
    1. 시작지점에서 bfs로 이동한다. 
        1-1. 이때 왔던 곳을 다시 가도 되는데.. 이러면 무한 루프 빠지지 않아? 
        1-2. 이동하면서 방향(l,r,u,d)을 문자열에 추가한다. 
    2. 도착지점에 도착하면 우선순위큐에 넣는다. 
        2-1. 우선순위 큐는 도착 문자열의 사전순 오름차순으로 정의한다. 
    */
    
    static Point start, end;
    static int h, w;
    static int length;
    
    static class Point {
        int r, c;
        int dist;
        int[] way;
        
        Point(int r, int c, int dist, int[] way) {
            this.r = r;
            this.c = c;
            this.dist = dist;
            this.way = way;
        }
    }
    
    String answer = "impossible";
    public String solution(int n, int m, int x, int y, int r, int c, int k)     {
        init(n, m, x, y, r, c, k);
        findArrivedPoint(start);
        
        return answer;
    }
    
    public void init(int n, int m, int x, int y, int r, int c, int k) {
        h = n;
        w = m;
        length = k;   
        start = new Point(x-1, y-1, 0, new int[length+1]);
        end = new Point(r-1, c-1, length, new int[length+1]);
    }
    
    static int[] dr = {1 ,0, 0, -1};
    static int[] dc = {0, -1, 1, 0};
    
    static String[] dir = {"d", "l", "r", "u"};
    
    public boolean findArrivedPoint(Point cur) {
        if(cur.dist == length && cur.r == end.r && cur.c == end.c) {
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<length; i++) {
                sb.append(dir[cur.way[i]]);
            }
            answer = sb.toString();
            return true; 
        }
        if(cur.dist > length) return false;
        int removeDist = calcDist(cur, end);
        if(removeDist > length - cur.dist) return false;
        if(removeDist%2 != (length-cur.dist)%2) return false;
        
        for(int d=0; d<4; d++) {
            int nr = cur.r + dr[d];
            int nc = cur.c + dc[d]; 
            
            if(nr < 0 || nr >= h || nc < 0 || nc >= w) continue;
            
            int[] newWay = Arrays.copyOf(cur.way, cur.way.length); 
            newWay[cur.dist] = d;       
            if(findArrivedPoint(new Point(nr, nc, cur.dist+1, newWay))) return true;
        }
        return false;
    }
    
    public int calcDist(Point p1, Point p2) {
        return Math.abs(p1.r - p2.r) + Math.abs(p1.c - p2.c);
    }
}