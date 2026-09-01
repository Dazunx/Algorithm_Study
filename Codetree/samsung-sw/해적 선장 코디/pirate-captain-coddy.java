import java.io.*;
import java.util.*;

public class Main {

    /**
    <문제 요약>
    1. 코디가 T개의 명령을 내림. 
    2. 명령은 1시간 단위로 실행된다. 
    3. 명령이 주어질 때 -> 총 피해량, 사격에 참여한 선박 수, 사격 우선순위에 따른 사격 선박들의 id를 출력한다. 

        [명령의 종류]
            1. 공격 준비 (100)
                1-1. 코디가 N척의 선박에 사격 준비를 지시한다 .
                    (고유 선박 번호 id_i, 공격력 p_i, 재장전 시간 r_i)
                1-2. 원래 사격 대기 상태임. 
            2. 지원 요청 (200)
                2-1. 새로운 선박 합류 
                    2-1-1. 사격 대기 상태 (선박 번호 id, 공격력 p, 재장전 시간 r)
            3. 함포 교체 (300)
                3-1. id번 선박의 함포를 교체한다.
                3-2. 교체된 선박의 공격력이 pw가 된다. 
            4. 공격 명령 (400)
                4-1. 사격 대기 상태의 선박 중 공격력이 가장 높은 선박 최대 5척이 사격한다. 
                    4-1-1. 공격력이 같으면 선박 번호 id가 작은 선박을 선택. 
                    4-1-2. 이후, 총 피해가 최대가 되도록 선박을 고른다.
                4-2. 사격에 참여한 선박의 공격력 합 만큼 피해를 줄 수 있음.
                4-3. 사격한 선박들은 즉시 재장전 (이 시간 동안은 공격 불가능)
                    4-3-1. 사격 시점을 포함해 r시간이 경과하면 다시 사격 대기 상태로 전환한다. 
            
    <문제 전략>
    1. 명령에 맞는 번호로 함수를 불러온다. 
    2. 100 : 공격 대기 상태인 선박들 리스트에 추가한다. 
    3. 200 : 지원요청을 받으면 공격 대기 선박 리스트에 추가한다.
    4. 300 : id 번의 선박의 공격력을 pw로 갱신한다. 
    5. 400 : 우선순위 큐에서 가장 공격력이 높은 선박 5척 공격
        5-1. 사격 선박들의 공격력 재장전 .  (재장전 시간 이후에 장전된다.)
    */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    static HashMap<Integer, Tank> tankList = new HashMap<>();  // 그냥 모든 탱크 리스트 (id로 찾을 용도)
    static ArrayList<Tank> reloadList = new ArrayList<>();  // 재장전해야하는 탱크 리스트 
    static PriorityQueue<Tank> attackPQ = new PriorityQueue<>();  // 공격 나가는 탱크 리스트 

    static class Tank implements Comparable<Tank> {
        int id, p, r; // id, 현재 공격력, 재장전 시간 
        int pw; // 채워질 장전량 
        int remainingTimeToReload; // 재장전까지 남은 시간  

        Tank(int id, int p, int r, int pw){
            this.id = id;
            this.p = p;
            this.r = r;
            this.pw = pw;
        }

        @Override
        public int compareTo(Tank o){
            // 만약 공격력이 같다면 선박번호 오름차순 
            if(this.p == o.p){
                return Integer.compare(this.id, o.id);
            }
            // 그렇지 않으면 공격력 오름차순
            return Integer.compare(o.p, this.p);
        }
    }

    public static void main(String[] args) throws IOException {
        solve();
        System.out.print(sb);
    }

    public static void solve() throws IOException {
        int orderCount = Integer.parseInt(br.readLine());

        for(int orderIdx=1; orderIdx<=orderCount; orderIdx++){
            // 명령 수만큼 함수를 실행한다. 
            StringTokenizer st = new StringTokenizer(br.readLine());
            int action = Integer.parseInt(st.nextToken());

            switch(action) {
                case 100:
                    order1(st);
                    break;
                case 200:
                    order2(st);
                    break;
                case 300:
                    order3(st);
                    break;
                case 400:
                    order4();
                    break;
            }
            // 시간이 지날 때마다 재장전까지 걸리는 시간을 줄인다. 
            reload();
        }
    }

    public static void reload(){
        for(int loadIdx=reloadList.size()-1; loadIdx>=0; loadIdx--){
            Tank loadTank = reloadList.get(loadIdx);

            loadTank.remainingTimeToReload--;
            if(loadTank.remainingTimeToReload==0){
                loadTank.p = loadTank.pw;
                attackPQ.add(loadTank);
                reloadList.remove(loadTank);
            }
        }
    }

    public static void order1(StringTokenizer st) {
        int count = Integer.parseInt(st.nextToken());

        for(int i=0; i<count; i++){
            int id = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            Tank tank = new Tank(id, p, r, p);
            attackPQ.add(tank);
            tankList.put(id, tank); 
        }
    }

    public static void order2(StringTokenizer st) {
        // 2-1. 새로운 선박 합류 
        int id = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        // 2-1-1. 사격 대기 상태 (선박 번호 id, 공격력 p, 재장전 시간 r)
        Tank tank = new Tank(id, p, r, p);
        attackPQ.add(tank);
        tankList.put(id, tank);  
    }

    public static void order3(StringTokenizer st) {
        int id = Integer.parseInt(st.nextToken());
        int pw = Integer.parseInt(st.nextToken());

        Tank tank = tankList.get(id);

        // 3-1. id번 선박의 함포를 교체한다.
        // 3-2. 교체된 선박의 공격력이 pw가 된다. 
        if (attackPQ.contains(tank)) {
            attackPQ.remove(tank);
            tank.p = pw;
            tank.pw = pw;
            attackPQ.add(tank);
        } else {
            // 재장전 중이거나 다른 상태라면 값만 변경 (나중에 reload될 때 반영됨)
            tank.p = pw;
            tank.pw = pw;
        }
    }

    public static void order4() {
        int pw_total = 0;
        int attackCount = 0; 

        List<Integer> shooterIds = new ArrayList<>();
        
        // 사격 대기 상태의 선박 중 공격력이 가장 높은 선박 최대 5척이 사격한다. 
        while(!attackPQ.isEmpty()){
            Tank attackTank = attackPQ.remove();
            pw_total += attackTank.p;
            attackTank.p = 0;
            attackTank.remainingTimeToReload = attackTank.r;

            shooterIds.add(attackTank.id);
            reloadList.add(attackTank);

            attackCount++;
            if(attackCount==5) break;
        }
        if(attackCount>0) {
            sb.append(pw_total).append(" ").append(attackCount).append(" ");
            for(int id : shooterIds){ 
                sb.append(id).append(" ");
            }
            sb.append("\n");
        }
    }

}
