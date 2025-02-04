![scheduler](https://github.com/user-attachments/assets/130b28e8-43c3-49c1-ae8f-5c42acde64a9)

![image](https://github.com/user-attachments/assets/5c97c8ea-32cc-4f53-adce-83b188ac1f3b)

Lv.0 API 명세 및 ERD 작성 <br/>
https://coral-bonsai-6fd.notion.site/Scheduler-project-17afe68bada98057ac28fb78d1b13446?pvs=4

Lv.1 일정 생성 및 조회 <br/>
해당 레벨에서 일정은 할일, 작성자명, 비밀번호, 작성/수정일을 포함하나, 공통조건에서 비밀 번호는 일정 정보에서 제외된다고 명시되었었다.
<br/>
그래서 처음부터 user 객체를 고려하여 구현되었기에, 처음 sql은 위의 erd에 만족하는 생성문을 작성하였으나, 임시로 user 대신 writer라는 필드를 만들어 작성자 명을 대체하였다.

Lv2. 일정 수정 및 삭제 <br/>
우선 비밀 번호 없이 구현후, 연관 관계 설정 이후에 비밀번호를 적용하고자 하여, 단순 수정, 삭제를 우선 구현하였다.

Lv3. 연관 관계 설정<br/>
SchedulerRepsoitoryImpl에서 sql 문을 수행할 때, user 테이블과 scheduler 테이블을 join하는 방식으로 작성자와 일정을 연결하였다.
<br/><br/>

동작 화면
![image](https://github.com/user-attachments/assets/ca8ecae2-4467-4a53-8766-8ff4ff06b3ab)

![image](https://github.com/user-attachments/assets/609e2ec9-d293-41be-8caf-7fa4a2e35d81)

![image](https://github.com/user-attachments/assets/5d3c20be-9611-4afb-b5a3-86adfde96bcd)

![image](https://github.com/user-attachments/assets/ef8554ab-407a-468b-ad86-245104c31d10)
