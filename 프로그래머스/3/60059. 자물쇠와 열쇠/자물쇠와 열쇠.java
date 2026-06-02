import java.io.*;
import java.util.*;

class Solution {
    /**
    <접근 방법>
    1. 2차원 배열을 4개 이어 붙여서 크게 만든다 .
    2. 그 배열을 순회한다. 
        2-1. 순회하면서 key 값이 다를 때마다 lock 배열의 값을 1로 바꾼다. 
        2-2. 한 번 순회를 다 하고 나서 4등분 해서 각각 전부 다 1이 되는지를 확인한다. 
        2-3. 만약 전부 다 1이 되지 않았다면 key를 시계방향으로 90도 회전에서 2번을 반복한다. 
    3. 전부 다 순회했는데 lock을 다 1로 채우게 되는 경우가 없다면 false를 리턴한다. 
    */
    
    static boolean answer = true;
    static int lockSize, keySize;
    static int[][] biglock;
    static int bigLockSize;
    static int[][] tempbiglock;
    static int[][] key;

    
    public boolean solution(int[][] keyInput, int[][] lock) {
        lockSize = lock.length;
        keySize = keyInput.length;
        bigLockSize = 2*lockSize;
        
        if(!isPossible(lock)) return false;
        
        init(keyInput, lock);        
        
        // 안 될 때까지 3번 회전해서 실행 
        for(int i = 0; i < 4; i++) {
            answer = true;
            if(iterate()) {
                answer = true;
                break;
            }
            // if(check(0, lockSize, 0, lockSize)) break;
            // if(check(lockSize, bigLockSize, 0, lockSize)) break;
            // if(check(0, lockSize, lockSize, bigLockSize)) break;
            // if(check(lockSize, bigLockSize, lockSize, bigLockSize)) break;
            answer = false;
            turnKey();
        }
        
        return answer;
    }
    
    public void init(int[][] keyInput, int[][] lock) {
        biglock = new int[bigLockSize][bigLockSize];
        tempbiglock = new int[bigLockSize][bigLockSize];
        key = new int[keySize][keySize];
        for(int i = 0; i < keySize; i++ ){
            key[i] = Arrays.copyOf(keyInput[i], keySize);
        }
        
        for(int i = 0; i < bigLockSize; i++) {
            for(int j = 0; j < bigLockSize; j++) {
                int hidx = i;
                int widx = j;
                if(hidx >= lockSize) hidx -= lockSize;
                if(widx >= lockSize) widx -= lockSize;
                
                biglock[i][j] = lock[hidx][widx];
            }
        }
    }
    
    public boolean isPossible(int[][] lock) {
        int startH=0, startW=0, endH=0, endW=0;
        
        for(int i = 0; i < lockSize; i++) {
            for(int j = 0; j < lockSize; j++) {
                if(lock[i][j] == 0) {
                    startH = i;
                    startW = j;
                    break;
                }
            }
        }
        
        for(int i = lockSize-1; i >= 0; i--) {
            for(int j = lockSize-1; j >= 0; j--) {
                if(lock[i][j] == 0) {
                    endH = i;
                    endW = j;
                    break;
                }
            }
        }
        
        if(endW - startW > keySize || endH - startH > keySize) return false;
        return true;
    }
    
    public void turnKey() {
        int[][] tempKey = new int[keySize][keySize];
        for(int i = 0; i < keySize; i++) {
            for(int j = 0; j < keySize; j++) {
                tempKey[i][j] = key[keySize-j-1][i];
            }
        } 
        key = tempKey;
        // for(int i = 0; i < keySize; i++) {
        //     for(int j = 0; j < keySize; j++) {
        //         System.out.print(key[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();
    }
    
    public boolean iterate() {
        // biglock 복제
        for(int i = 0; i < bigLockSize; i++) {
            tempbiglock[i] = Arrays.copyOf(biglock[i], bigLockSize);
        }
        
        for(int bhidx = 0; bhidx < bigLockSize - keySize + 1; bhidx++) {
            for(int bwidx = 0; bwidx < bigLockSize - keySize + 1; bwidx++) {
                // key가 1이고, lock이 0인 부분은 templock을 1로 바꿔준다. 
                for(int khidx = 0; khidx < keySize; khidx++) {
                    for(int kwidx = 0; kwidx < keySize; kwidx++) {
                        if(key[khidx][kwidx]==1) {
                            if(tempbiglock[bhidx+khidx][bwidx+kwidx]==0) {
                                tempbiglock[bhidx+khidx][bwidx+kwidx] = 1;
                            } else {
                                tempbiglock[bhidx+khidx][bwidx+kwidx] = 0;
                            }   
                        }
                    }
                }
                if(check(0, lockSize, 0, lockSize)) return true;
                if(check(lockSize, bigLockSize, 0, lockSize)) return true;
                if(check(0, lockSize, lockSize, bigLockSize)) return true;
                if(check(lockSize, bigLockSize, lockSize, bigLockSize)) return true;
                
                for(int i = 0; i < bigLockSize; i++) {
                    tempbiglock[i] = Arrays.copyOf(biglock[i], bigLockSize);
                }
            }
        }
        
        return false;
        
        // for(int bhidx = 0; bhidx < bigLockSize; bhidx++) {
        //     for(int bwidx = 0; bwidx < bigLockSize; bwidx++) {
        //         System.out.print(tempbiglock[bhidx][bwidx] + " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();
    }
    
    // end는 포함 안함. (start <= now < end인 것들만 골라서 배열에 담고 전부 1인지 확인한다. )
    public boolean check(int startH, int endH, int startW, int endW) {
        boolean isAllOne = true;
        
        for(int bhidx = 0; bhidx < bigLockSize; bhidx++) {
            for(int bwidx = 0; bwidx < bigLockSize; bwidx++) {
                
                if(bhidx >= startH && bhidx < endH && bwidx >= startW && bwidx < endW) {
                    if(tempbiglock[bhidx][bwidx] == 0) {
                        isAllOne = false;
                        return isAllOne;
                    }
                }
            }
        }
        return isAllOne;
    }
}