import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 조건이 주어지고, 지원서 작성 항목 4가지에 맞는 지원자의 수를 구한다. 
    
    <문제 전략>
    1. 입력 : 지원자 1명으로 나올 수 있는 조건 16개를 다 만들어 맵에 점수와 함께 넣는다.
    2. 이진탐색을 위해 HashMap의 점수 리스트를 정렬한다.
    3. 쿼리 조건에 맞는 점수 배열 가져와서 이진탐색으로 기준 점수 넘는 사람의 수를 샌다.
    */
    
    static HashMap<String, List<Integer>> infoMap = new HashMap<>();
    
    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        init(info);
        
        for(int i = 0 ; i < query.length; i ++ ) {
            String[] parts = query[i].replaceAll(" and ", " ").split(" ");
    
            String searchQuery = parts[0] + parts[1] + parts[2] + parts[3];
            int searchScore = Integer.parseInt(parts[4]);
            
            // 해당 쿼리로 개수 검색
            answer[i] = search(searchQuery, searchScore);   
        }
        
        return answer;
    }
    
    public void init(String[] info) {
        for(int i = 0 ; i < info.length ; i++) {
            saveInfo(info[i]);
        }
        // 이진탐색을 위해 HashMap의 점수 리스트를 정렬한다.
        for (List<Integer> scoreList : infoMap.values()) {
            Collections.sort(scoreList);
        }
    }
    
    public void saveInfo(String inf) {
        String[] parts = inf.split(" ");
        int score = Integer.parseInt(parts[4]);

        String[][] options = {
            {parts[0], "-"},
            {parts[1], "-"},
            {parts[2], "-"},
            {parts[3], "-"}
        };

        // 지원자 1명으로 나올 수 있는 조건 16개를 다 만들어 맵에 점수와 함께 넣는다.
        for (String lang : options[0]) {
            for (String job : options[1]) {
                for (String career : options[2]) {
                    for (String food : options[3]) {
                        String key = lang + job + career + food;

                        // 이미 존재하지 않으면 새로 key를 만들고, 
                        if (!infoMap.containsKey(key)) {
                            infoMap.put(key, new ArrayList<>());
                        }
                        // 이미 존재하면 list에 더한다. 
                        infoMap.get(key).add(score);
                    }
                }
            }
        }
    }
    
    public int search(String searchQuery, int searchScore) {
        if(!infoMap.containsKey(searchQuery)) return 0;
        
        // 쿼리 조건에 맞는 점수 배열 가져와서 이진탐색으로 기준 점수 넘는 사람의 수를 샌다.
        List<Integer> scores = infoMap.get(searchQuery);
        
        int left = 0;
        int right = scores.size();
        
        while(left < right) {
            int mid = (left + right) / 2;
            
            if (scores.get(mid) >= searchScore) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return scores.size() - left;
    }
    
}