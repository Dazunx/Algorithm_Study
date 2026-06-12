def solution(genres, plays):
    music = {}   # {genre: [(play, index), ...]}
    genre_play_sum = {}  # {genre: 총 재생수}

    for i in range(len(plays)):
        g, p = genres[i], plays[i]
        if g not in music:
            music[g] = []
            genre_play_sum[g] = 0
        music[g].append((p, i))      # (재생수, 인덱스) 저장
        genre_play_sum[g] += p       # 총합 업데이트
    
    # 장르별 총합으로 정렬
    sorted_genres = sorted(genre_play_sum.items(), key=lambda x: x[1], reverse=True)

    answer = []
    for g, _ in sorted_genres:
        # 장르 내 노래 정렬: 재생수 내림차순, 같으면 인덱스 오름차순
        songs = sorted(music[g], key=lambda x: (-x[0], x[1]))
        answer.extend([idx for _, idx in songs[:2]])  # 상위 2개 인덱스
    
    return answer