import java.io.*;
import java.util.*;

class Solution {
    /**
    1. board를 순회하면서 우, 하, 우하 를 같은 알파벳인지 확인한다. 
    2. 만약 모두 같다면 해당 좌표를 모두 X로 변경한다. 
    3. 그리고 해당 좌표의 위쪽 부분만 전부 그 위치로 내린다. (Deque 사용)
    */
    
    int[] dr = {0, 1, 1}; // 우, 하, 우하
    int[] dc = {1, 0, 1};
    
    HashSet<Integer> downColumn = new HashSet<>();
    char[][] board;
    
    class Coor {
        int r, c;
        
        Coor(int r, int c){
            this.r = r;
            this.c = c;
        }
        
        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coor coor = (Coor) o;
            
            return r == coor.r && c == coor.c;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
    
    public int solution(int h, int w, String[] originBoard) {
        int answer = 0;
        board = new char[h][w];
        
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                board[i][j] = originBoard[i].charAt(j);
            }
        }
        
        while(true){
            int addCount = findDeletingBlock(h, w);
            if(addCount == 0) break;
            
            answer += addCount;
            // 내리는 함수 호출 
            for(int columnIdx = 0; columnIdx < w; columnIdx++){
                //if(!downColumn.contains(columnIdx)) continue;
                down(columnIdx, h);
            }
            downColumn.clear();
        }
        
        return answer;
    }
    
    public int findDeletingBlock(int h, int w) {
        HashSet<Coor> deletedCoor = new HashSet<>();
        
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                if(board[i][j]=='X') continue;
                
                // 현재 위치 추가 
                List<Coor> tempCoor = new ArrayList<>();
                tempCoor.add(new Coor(i, j));
                int count = 1;
                
                for(int d=0; d<3; d++){
                    int nr = i + dr[d];
                    int nc = j + dc[d];
                    
                    // 범위를 넘어가거나 빈칸이면 멈춘다. 
                    if(nr<0 || nr>=h || nc<0 || nc>=w) break;
                    if(board[i][j] != board[nr][nc]) break;
                    
                    // 만약 현재 위치랑 같은 알파벳이면 임시 리스트에 추가한다.
                    tempCoor.add(new Coor(nr, nc));
                    count++;
                }
                // 우, 하, 우하를 다 보았는데 모두 현재랑 같은 알파벳이면, 
                if(count==4) {
                    // 해당 위치를 X로 바꿔준다. 
                    for(Coor coor : tempCoor){
                        deletedCoor.add(new Coor(coor.r, coor.c));
                    }
                    // 해당 컬럼을 내려줘야할 컬럼 set에 추가한다. 
                    //downColumn.add();
                    //downColumn.add(i+1);
                }
            }
        }
        deleteSameThings(deletedCoor);
        
        return deletedCoor.size();
    }
    
    public void deleteSameThings(HashSet<Coor> deletedCoor){
        for(Coor coor : deletedCoor) {
            board[coor.r][coor.c] = 'X';
        }
    }
    
    
    /**
    1. 값이 있는 것만 다 디큐에 넣는다.
    2. 디큐에서 마지막부터 빼서 마지막 줄부터 채운다. 
    3. 나머지는 전부 X로 바꾼다.  
    */
    Deque<Character> dq = new ArrayDeque<>(); 
    
    public void down(int columnIdx, int h) {
        for(int i=0; i<h; i++){
            if(board[i][columnIdx] != 'X') dq.add(board[i][columnIdx]);
        }
        for(int putIdx=h-1; putIdx >= 0; putIdx--){
            if(!dq.isEmpty()) board[putIdx][columnIdx] = dq.removeLast();
            else board[putIdx][columnIdx] = 'X';
        }
    }
}