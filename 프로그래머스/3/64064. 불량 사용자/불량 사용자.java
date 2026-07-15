import java.io.*;
import java.util.*;

class Solution {
    /**
    <문제 요약>
    1. 문자 하나당 *으로 가림. 
    2. ban된 아이디에 해당할 수 있는 경우의 수를 구한다. 
    
    <접근 방법>
    1. 각 불량 사용자별로 가능한 아이디를 선별한다. 
        1-1. 개수가 적으니까 글자 하나씩 비교해도 될 거 같다. 
    2. 가능한 아이디를 조합하여 개수를 센다. 
        2-1. 각각 개수가 다르다..  
    */
    
    HashSet<HashSet<String>> answers = new HashSet<>();
    ArrayList<String>[] candidateList; 
    int ban_len;
    int answer = 0;
    
    public int solution(String[] user_id, String[] banned_id) {
        ban_len = banned_id.length;
        //  banned_id에 딱 맞는 문자열을 넣는다. 
        candidateList = new ArrayList[ban_len];
        for(int i=0; i<ban_len; i++) {
            candidateList[i] = new ArrayList<>();
        }
        
        // * 위치가 맞는 문자열 찾기 
        for(int bIdx=0; bIdx<ban_len; bIdx++) {
            String ban = banned_id[bIdx];
            for(String user : user_id) {
                if(ban.length() != user.length()) continue;
                
                boolean issame = true;
                
                for(int i=0; i<ban.length(); i++) {
                    if(ban.charAt(i) == '*') continue;
                    if(i >= user.length() || ban.charAt(i) != user.charAt(i)) {
                        issame = false;
                        break;
                    }
                }
                
                if(issame) {
                    candidateList[bIdx].add(user);
                }
            }
        }
        // 가능한 조합의 개수 찾기 
        countPossibleCombi(new String[ban_len], new HashSet<>(), 0);
        
        // 중복 제거된 결과의 크기를 리턴한다. 
        return answers.size();
    }
    
    /**
    1. 각 arraylist에서 하나씩 추출
    2. 추출된 값을 전부 set에도 넣는다. 
    3. set과 길이가 같으면 answer 카운트를 올린다. 
    */
    public void countPossibleCombi(String[] result, HashSet<String> resultSet, int index) {
        if(index == ban_len) {
            //System.out.println(Arrays.toString(result));
            answers.add(new HashSet<>(resultSet));
            return;
        }
        
        for(int i=0; i<candidateList[index].size(); i++) {
            result[index] = candidateList[index].get(i);
            
            if(resultSet.contains(result[index])) continue;
            
            resultSet.add(result[index]);
            countPossibleCombi(result, resultSet, index+1);
            resultSet.remove(result[index]);
        }
    }
}