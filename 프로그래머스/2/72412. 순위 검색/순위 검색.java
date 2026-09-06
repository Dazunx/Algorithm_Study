import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 조건이 주어지고, 지원서 작성 항목 4가지에 맞는 지원자의 수를 구한다. 
    
    <문제 전략>
    1. 입력을 정리한다. 
        1-1. info를 지원자 클래스로 저장한다.
        1-2. query를 and로 나누어서 저장한다. 
    2. 각 
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

        for (String lang : options[0]) {
            for (String job : options[1]) {
                for (String career : options[2]) {
                    for (String food : options[3]) {
                        String key = lang + job + career + food;

                        if (!infoMap.containsKey(key)) {
                            infoMap.put(key, new ArrayList<>());
                        }
                        infoMap.get(key).add(score);
                    }
                }
            }
        }
    }
    
    public int search(String searchQuery, int searchScore) {
        if(!infoMap.containsKey(searchQuery)) return 0;
        
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