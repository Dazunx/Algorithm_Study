import java.io.*;
import java.util.*;

class Solution {
    /**
     * <문제 요약>
     * 1. 로봇들이 routes에 따라 포인트를 순서대로 방문한다.
     * 2. 매 초마다 한 칸씩 이동하며, 이동 우선순위는 r방향 먼저, 그 다음 c방향.
     * 3. 같은 시간에 같은 위치에 2대 이상의 로봇이 있으면 충돌.
     * 4. 충돌 횟수를 반환한다.
     *
     * <문제 전략>
     * 1. 각 로봇마다 매 초의 위치를 리스트로 기록한다.
     * 2. Map<"time,r,c", count> 로 각 시간/위치에 몇 대가 있는지 센다.
     * 3. count가 2 이상인 경우를 충돌로 카운트한다.
     */

    public int solution(int[][] points, int[][] routes) {
        // key: "time,r,c" / value: 해당 위치에 있는 로봇 수
        Map<String, Integer> posMap = new HashMap<>();
        int answer = 0;

        // 각 로봇(route)마다 매 초의 위치를 계산
        for (int[] route : routes) {
            int time = 0;

            // 시작 위치 (첫 포인트)
            int r = points[route[0] - 1][0];
            int c = points[route[0] - 1][1];

            // 시작 위치도 기록 (time=0)
            String key = time + "," + r + "," + c;
            posMap.put(key, posMap.getOrDefault(key, 0) + 1);

            // 다음 포인트들로 순서대로 이동
            for (int i = 1; i < route.length; i++) {
                int tr = points[route[i] - 1][0]; // 목적지 r
                int tc = points[route[i] - 1][1]; // 목적지 c

                // 목적지에 도달할 때까지 한 칸씩 이동
                while (r != tr || c != tc) {
                    // r 방향 우선, 그 다음 c 방향
                    if (r != tr) {
                        r += (tr > r) ? 1 : -1;
                    } else {
                        c += (tc > c) ? 1 : -1;
                    }
                    time++;

                    key = time + "," + r + "," + c;
                    posMap.put(key, posMap.getOrDefault(key, 0) + 1);
                }
            }
        }

        // 같은 시간/위치에 2대 이상이면 충돌
        for (int count : posMap.values()) {
            if (count >= 2) answer++;
        }

        return answer;
    }
}