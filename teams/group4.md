# 안녕하세요!<br> Main Menu / Setting 파트 개발을 맡은 Team4 "599100" 입니다!

<br>
<br>

> ## 👥팀소개

| No. | Name        | Role                     |
|-----|-----------------------------------------------------------------------|--------|
| 1   | [홍진수](https://github.com/bakukun/bakukun/blob/main/README.md) | Leader |  
| 2   | [전현우](https://github.com/Jeonhyeonwoo/Jeonhyeonwoo/blob/main/README.md) | Member |  
| 3   | [안도형](https://github.com/andohyung/andohyung/blob/main/README.md) | Member |  
| 4   | [윤성빈](https://github.com/SeongBinYoon/SeongBinYoon/blob/main/README.md) | Member | 
| 5   | [한정우](https://github.com/jeongulupe/jeongulupe/blob/main/README.md) | Member | 
| 6   | [최진영](https://github.com/orca10/orca10/blob/main/README.md) | Member | 

<br>

>## 💻 Development contents
✅**화면 크기 조절 버튼 및 조절 기능 추가 완료 ⇒ 홍진수**<br></br>
   세가지 모드로 화면 조절 가능
   ⇒ 모드를 선택하면, 원하는 해상도에 맞게 게임이 재실행되며 사이즈가 변경된다
   frame 함수를 사용하여 새로운 스크린객체를 만들고,
   기존의 스크린을 dispose 하는 방법으로 화면 조절기능을 추가하였음

   #풀 모드 #와이드 모드(1280x720) #스탠다드 모드 (기본)

   ✅**소리 조절 버튼 추가 완료 ⇒ 한정우 , 전현우**<br></br>
   <del>#팀 1이랑 상의 후 결정
   ⇒ 마스터음, 효과음, 등의 세부 음악 의견을 전달 받은 후
   Volume 부분은 Master, Effect sound, Background music 등의
   세부로 나누어 각각 설정할 수 있게 기능 구현 예정</del><br>
   **⇒ TEAM 1의 PR이 딜레이 되어, 버튼만 구현하는 방향으로 노선 변경
   이후, 팀 개발에서 우리가 직접 소리기능을 추가해서 변경할 예정임**

   ✅**도움말 버튼 추가 완료 및 도움말 세부내용 작성 ⇒ 안도형**<br></br>
   게임의 전반적인 사용법을 담은 도움말 기능.
   도움말(help) 버튼을 클릭 시 도움말 창으로 들어가게끔 구성
   버튼으로 페이지 전환하여 도움말 볼 수 있도록 설정

✅**HUDscreen 버튼 추가 완료 및 화면 내 오브젝트 비율 조절완료 ⇒ 윤성빈<br></br>
   #** HUDscreen 버튼 추가완료 (Team2 작업용)
   Setting 창 내에서 HUD option 버튼을 생성

   #화면 내 오브젝트 프레임 조정 
   
⇒ 화면 사이즈 변경 시 게임이 리셋되게끔 구현되어 있는 특성상(사용자의 event 및 input update에 따라 이전 창은 뒤로 숨기고, 위에 새로 올리는 방식으로 짜여져 있음), 게임 중 단축키로 화면 사이즈를 변경하면 진행 중이던 게임이 재실행되기 때문에 필요없는 기능이라고 판단.

✅**Setting UI 설계완료 ⇒ 최진영**<br></br>
   setting screen의 기반을 다져 다른 기능들이 자리를 잡을 수 있도록 여백 및 버튼 틀을 구성

   ⇒ 여러 게임의 환경 설정 페이지의 형식을 참고하고 화면 사이즈 조절, 소리 조절, hud option 순서대로 배치했고 끝부분에 도움말과 나가기 버튼을 배치함
  
