import java.util.Arrays;

class Solution {
    /**
    <문제 요약>
    1. 바위 n개를 제거한 뒤 지점 간 최소 거리의 최댓값을 구하는 문제

    <문제 전략>
    1. 이분 탐색 활용: 최소 거리가 mid일 때 제거할 바위가 n개 이하인지 판별
    2. rocks 정렬 후 이전 위치와의 거리가 mid 미만이면 바위 제거
    3. 제거한 바위 수가 n개 이하이면 거리 증가, 초과면 거리 감소
    */
    public int solution(int distance, int[] rocks, int n) {
        // 1. 바위 위치 정렬
        Arrays.sort(rocks);

        int left = 1;
        int right = distance;
        int answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2; // 각 지점 사이의 최소 거리 기준값
            int removedRocks = 0; // 제거된 바위 개수
            int prev = 0; // 이전 바위 위치

            for (int rock : rocks) {
                // 현재 바위와 이전 바위 사이의 거리가 mid보다 작으면 바위 제거
                if (rock - prev < mid) {
                    removedRocks++;
                } else {
                    // mid 이상이면 바위를 남겨두고 이전 바위 위치 갱신
                    prev = rock;
                }
            }

            // 마지막 바위와 도착지점 사이의 거리 검사
            if (distance - prev < mid) {
                removedRocks++;
            }

            // 제거한 바위 수가 n개 이하인 경우: 더 큰 최소 거리를 시도 가능
            if (removedRocks <= n) {
                answer = mid;
                left = mid + 1;
            } else { // 제거한 바위 수가 n개를 초과한 경우: 기준 거리가 너무 큼
                right = mid - 1;
            }
        }

        return answer;
    }
}