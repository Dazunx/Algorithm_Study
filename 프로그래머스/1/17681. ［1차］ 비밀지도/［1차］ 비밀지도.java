import java.io.*;
import java.util.*;

class Solution {
    /**
    <조건>
    1. 한변의 길이 n , 각 칸은 공백(" ", 0)이나 벽(#, 1)이다.
    2. 지도 1과 지도 2 중 하나라도 벽이면 벽이고, 둘 다 공백이면 공백이다. 
    3. 각 가로줄을 2진수로 바꿔서 변환한 암호화된 배열 두 개가 n개 주어진다. 
    
    <문제 전략>
    1. 우선 각각 벽의 위치를 구한다. 십진수를 이진수로 변환해야 한다. 
    */
    
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        
        // 각 배열을 십진수를 이진수로 변환한다. 
        int[][] newArr1 = convertToBinary(n, arr1);
        System.out.println();
        int[][] newArr2 = convertToBinary(n, arr2);
        
        // 둘의 지도를 맞춰보면서 전체 지도를 구해낸다. 
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++) {
            sb.setLength(0);
            for(int j=0; j<n; j++) {
                if(newArr1[i][j]==1 || newArr2[i][j]==1) {
                    sb.append('#');
                } else {
                    sb.append(' ');
                }
            }
            answer[i] = sb.toString();
        }
        
        return answer;
    }
    
    public int[][] convertToBinary(int n, int[] arr) {
        int[][] binaryArr = new int[n][n];
        
        for(int rowIdx=0; rowIdx<n; rowIdx++) {
            int row = arr[rowIdx];
            
            int idx = n-1;
            while(row > 0) {
                binaryArr[rowIdx][idx--] = row%2;
                row /= 2;
            }
            
            System.out.println(Arrays.toString(binaryArr[rowIdx]));
        }
        
        return binaryArr;
    }
}