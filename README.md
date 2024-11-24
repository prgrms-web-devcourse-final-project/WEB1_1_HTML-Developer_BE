# WEB1_1_HTML-Developer_BE

## GIT 컨벤션 !
1. 이슈 템플릿에 맞춰 **이슈 생성**한다. <br>
    `기능구현(feat)` | `오류해결(fix)` | `환경설정(chore)` | `리팩토링(refactor)`
    
2. 브랜치의 이름은 **태그이름/이슈이름**로 한다. <br>
   `chore/project-setting`
   
3. commit message는 [티켓번호] **태그이름: 내용**으로 커밋한다. <br>
   `[ARV-01] feat: 회원가입 API 구현`

4. PR 템플릿에 맞춰 PR을 날린다. <br>
    4-1. 실행결과 스크린샷을 첨부해서 내용만 봐도 알 수 있도록 작성한다.  <br>
    4-2. `close #{이슈넘버}` 로 merge 시 자동으로 이슈가 close 되도록 한다. 
5. merge할 때는 반드시 PR을 날려 최소 한 명의 팀원에게 **리뷰**받은 후 스스로 merge한다.
6. main브랜치에 merge가 된 브랜치는 삭제한다. 


## PR 규칙
💡 ISSUE 이름과 같게 제목 설정해주세요!!! <br>
💡 PR Templete에 따라 착착 작성해주세요!!!!

## Commit Message
💡 commit message는 [티켓번호] 태그이름: 내용으로 커밋 <br>
```java
[ARV-01] feat : 회원가입 API 구현
```
💡 태그이름은 모두 소문자로 쓰고 아래 9개 중 하나로 <br>

| 태그 | 설명 |
| --- | --- |
| feat | 새로운 기능 추가한 경우  |
| fix | 오류를 해결한 경우 |
| design | CSS등 UI변경한 경우 |
| style | 코드 포맷 변경, 세미콜론 누락, 코드 수정이 없는 경우 |
| refactor | 코드의 리팩토링  |
| docs | 문서와 관련하여 수정한 경우 |
| test | test를 추가하거나 수정했을 경우  |
| chore | build와 관련된 부분, 패키지 매니저 설정 등. 초기 설정도 여기에 포함 |
| rename | 폴더 이름, 경로 변경한 경우 |
| merge | 충돌 해결시 작성하는 커밋 메시지 |


### 스쿼시 앤 머지

```java
[ARV-01] feat: 차대절 신청 폼 작성 API 구현
```

### 이슈 등록할 때 쓰는 브랜치 이름 (feat, fix, chore, refactor)

```java
chore/project-setting
feat/reservation
```
