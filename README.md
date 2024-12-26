## 서비스 소개
![image](https://github.com/user-attachments/assets/6b4565ad-f1bf-490e-b493-9e5a2ce91774)



위버스와 같은 기존 팬덤 플랫폼은 대개 특정 기획사에 소속되어 있어 상업적인 색채가 강하며, 제한적인 서비스만을 제공해 팬들이 스스로 조직하고 실행하는 활동을 충분히 지원하지 못하고 있습니다. 이러한 기능의 부재로 팬들은 필요한 정보를 플랫폼 외부에서 개별적으로 찾아야 하거나, 서로 다른 플랫폼을 통해 비효율적으로 소통해야 하는 불편함을 겪고 있습니다.

**[차별화 요소]**

ALLREVA는 다음과 같은 기능을 제공하여 차별화를 두려고 합니다.

1. **차 대절 구인 및 관리 기능**
    
    콘서트 참여 시 필요한 교통수단을 쉽게 구인하고 관리할 수 있는 시스템

2. **팬 주최 행사 정보 통합**
    
    여러 SNS에 흩어진 팬 주최 활동 정보를 한 플랫폼에서 관리하여 팬들이 쉽게 접근할 수 있도록 제공

3. **아티스트 비공식 일정 관리**
    
    공식 일정 외에도 팬들끼리 공유하는 이벤트나 활동을 체계적으로 관리할 수 있는 공간 제공

## 기술 스택 🦾

### Languages
![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)

### Frameworks
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=json-web-tokens&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-5C7F4E?style=flat-square&logo=querydsl&logoColor=white)

### Testing
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=flat-square&logo=junit&logoColor=white)

### Databases
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)
![H2](https://img.shields.io/badge/H2-1E8CBE?style=flat-square&logo=h2database&logoColor=white)
![ElasticSearch](https://img.shields.io/badge/-ElasticSearch-005571?style=flat&logo=elasticsearch)
![AWS S3](https://img.shields.io/badge/AWS%20S3-569A31?style=flat-square&logo=amazonaws&logoColor=white)

### Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white)

### Infrastructure
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazonwebservices&logoColor=white)



## 팀원 소개 🧑‍🤝‍🧑
| 김관현 | 김도우 | 김수민 | 박상혁 |
|:---:|:---:|:---:|:---:|
| <img src="https://avatars.githubusercontent.com/u/129512238?v=4" width="150"> | <img src="https://avatars.githubusercontent.com/u/103844957?v=4" width="150"> | <img src="https://avatars.githubusercontent.com/u/83461362?s=400&u=25e719b72f905561d1f8c6cd130170815cf88c29&v=4" width="150">  | <img src="https://avatars.githubusercontent.com/u/105288887?v=4" width="150">  |
| [kwanse](https://github.com/kwanse) | [kimdw0823](https://github.com/kimdw0823) | [tnals2384](https://github.com/tnals2384) | [sangcci](https://github.com/sangcci) |
| 공연 API, 공연 캐싱, 공연 기록 API, 이벤트 발행 | 공연, 차대절, 수요조사 Elastic Search 검색 API 구현, ELK 세팅 | 공연, 공연장 Open API 파싱, 수요조사 API, 인기 검색어 API 구현 | 소셜 로그인, 차대절 API 구현 |

## 📋 ERD
[Allreva](https://www.erdcloud.com/d/5bJMq5rntrmtoCWFy)

## 프로젝트 패키지 구조
도메인 중심 설계 (Domain Driven Design)
EX) member 도메인
![image](https://github.com/user-attachments/assets/5c96bb83-857b-473e-9948-6f4a2bc557f9)


![image](https://github.com/user-attachments/assets/e4a4b9b1-8552-4c35-afbd-678129de7b19)

## 아키텍처 구조

![ALLREVA](https://github.com/user-attachments/assets/10cb7c20-ccd9-43a9-a6e5-678838015731)

## 구현 기술

### 1. 공연 Open API

개최 공연장 좌석 수가 1000석 이상인 공연장만 필터링하여, 해당 공연장에서 열리는 콘서트 상세 정보를 받아온다.

![image](https://github.com/user-attachments/assets/91b08e99-9efa-4fca-8bd3-f010bd32a9e2)


OpenFeignClient를 활용하여 응답을 받아오고, XML 파일을 dto로 파싱하여 DB에 저장.

- 112초 → 14 초 단축
  
![image](https://github.com/user-attachments/assets/53a19fc5-e79d-4dba-953e-4bb834daf1ac)

### 2. 검색 엔진으로 elasticsearch 도입 과 MySQL 과 Sync
- 효율적이고 빠른 검색 구현
- Logstash를 이용해 MySQL과 Elasticsearch sync

![스크린샷 2024-12-26 오후 3 39 57](https://github.com/user-attachments/assets/63310948-ab69-401c-bd53-edb3dc74f934)
- 공연 정보 업데이트 시간에 맞춰 Logstash를 이용해서 MySQL 과 elasticsearch를 sync
- JDBC 입력 플러그인을 사용해 MySQL 데이터를 주기적으로 폴링 및 동기화.
- 필터링과 데이터 변환을 통해 Elasticsearch에 최적화된 형태로 데이터 저장.

### 3. 공연 상세조회 캐싱

- 공연 정보를 변경하여도 실시간으로 동기화할 필요가 없다
- 이후에 멀티서버로 확장된다고 하더라도 데이터의 동기화가 크게 중요하지 않기 때문에 로컬 캐싱을 적용하였다
- 공연 정보를 하루에 한 번 Open API를 사용하여 불러오기 때문에 딱 하루에 한 번만 캐시를 갱신해주면 된다
- 사용자가 요청을 기다리지 않고 Open API로 받아오는 순간에 미리 캐싱까지 다 하여 첫 조회에도 캐시 히트하도록 구현하였다

### 4. 비동기 작업의 실행 최대한 보장

- `@TransactionalEventListener`를 이용하여 비동기 작업을 이벤트로 발행
- 이벤트가 실패하면 `LinkedBlockingQueue`에 넣어서 재발행

### 5. oauth2 소셜 로그인

- 기존 Spring Security OAuth2Client를 통해 구현 -> 리다이렉트 이슈로 인해 body에 값을 설정할 수 없음 -> 방식 변경
- authorization code를 받아오는 과정까지 프론트에서 전담. -> openfeign을 통해 직접 auth server와 통신하여 사용자 정보 흭득 -> 응답 body에 설정 가능
- 쿠키 설정 시 localhost 환경에서도 https 적용하도록 하여 cookie 인식되도록 함

### 6. test 환경 분리

- spring의 Profile 기능을 활용하여 Security Config 및 Filter 분리 -> test 시에는 DEVELOPER 권한을 만들어서 테스트 하기 쉽도록 구현
- local이나 dev 환경에서는 기존 oauth2 및 jwt 인증 과정 그대로 구현
