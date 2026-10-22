def dfs(num,numbers,target,answer,i):
    if i==len(numbers):
        if num==target:
            return 1
        else:
            return 0
    
    answer += dfs(num+numbers[i],numbers,target,0,i+1)
    answer += dfs(num-numbers[i],numbers,target,0,i+1)  
    
    return answer

def solution(numbers, target):
    return dfs(0,numbers, target, 0, 0)