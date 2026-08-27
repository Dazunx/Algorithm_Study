import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 레스토랑의 구조는 동그란 모양 
        1-1. 총 둘레 n미터 
        1-2. 취약 지점 위치 : 시계방향으로 떨어진 거리
    2. 점검 시간 1시간 
        2-1. 각 사람별로 1시간 동안 이동할 수 있는 거리 다 다름. 
        2-2. 최소한의 친구들로 점검하려고 함. 
        2-3. 이동은 출발지점부터 시계/반시계 방향으로 이동 가능
    3. 입출력
        3-1. 입력 
            3-1-1. 외벽의 길이 N (1 <= N <= 200)
            3-1-2. 취약 지점 위치 배열 weak (1<= 취약지점수 <= 15)
            3-1-3. 각 친구가 1시간 동안 이동 가능한 거리 배열 dist (1<= 친구수 <=8)
        3-2. 출력
            3-2-1. 모든 취약 지점을 점검하기 위한 최소 친구 수
            3-2-2. 친구들 모두 투입해도 취약 지점 모두 점검 안되는 경우 -1 반환 
    
    <문제 전략> 
    1. 순열로 친구들 순서를 정하고, 모든 곳을 점검할 수 있는 가장 적은 수를 찾는다.
    
    */
    
    static int minPeople = Integer.MAX_VALUE;
    static int[] doubleWeak;
    
    public int solution(int N, int[] weak, int[] dist) {
        minPeople = Integer.MAX_VALUE; 
        
        int len = weak.length;
        doubleWeak = new int[len * 2];
        for (int i = 0; i < len * 2; i++) {
            if (i < len) {
                doubleWeak[i] = weak[i];
            } else {
                doubleWeak[i] = weak[i - len] + N; 
            }
        }
        
        // 순열 크기는 친구 수(dist.length)만큼 지정
        perm(N, weak, dist, new boolean[dist.length], new int[dist.length], 0);
        
        return minPeople == Integer.MAX_VALUE ? -1 : minPeople;
    }
    
    public void perm(int N, int[] weak, int[] dist, boolean[] visited, int[] result, int index) {
        if (index == dist.length) {
            check(N, weak, result);
            return;
        }
        
        for (int i = 0; i < dist.length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            result[index] = dist[i];
            perm(N, weak, dist, visited, result, index + 1);
            visited[i] = false;
        }
    }
    
    public void check(int N, int[] weak, int[] result) {
        int totalWeak = weak.length;

        for (int start = 0; start < totalWeak; start++) {
            int friendIdx = 0;
            int coverage = doubleWeak[start] + result[friendIdx];

            for (int i = start; i < start + totalWeak; i++) {
                if (doubleWeak[i] > coverage) {
                    friendIdx++;

                    if (friendIdx == result.length) break;

                    coverage = doubleWeak[i] + result[friendIdx];
                }
            }

            int usedFriends = friendIdx + 1;

            if (usedFriends <= result.length) {
                minPeople = Math.min(minPeople, usedFriends);
            }
        }
    }
}