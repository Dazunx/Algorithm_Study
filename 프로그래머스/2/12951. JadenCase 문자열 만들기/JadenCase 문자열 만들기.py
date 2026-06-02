def solution(s):
    answer = ''
    answers = s.split(' ')
    
    for word in answers:
        word = word.capitalize() + ' '
        answer += word
    answer = answer[:-1]

    return answer