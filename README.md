# 1. 서버 구성
<p align="center">
  <img src="https://i.imgur.com/UGPphCa.png">
</p>

# 2. 기술 스택
- JAVA 11
- Spring Framework
  - Spring Boot 2.7.0
  - Spring Data JPA
  - Spring Security
- MySQL 5.7
- Test Framework
  - JUnit 5
  - Mockito
- Nginx
- Thymeleaf
- OAuth2

# 3. 기능 구현
- [ ] 계정
  - [x] 로그인 기능 구현 (REST 서버는 JWT, 웹은 세션을 통한 로그인 지원)
  - [x] 회원가입 기능 구현
  - [ ] 분실 시 비밀번호 찾기 기능 구현
  - [x] 네이버나 카카오를 통해서 소셜 로그인/회원가입 할 수 있도록 지원 (OAuth 2.0을 통한 로그인 지원)
- [x] 회사
  - [x] 회사의 프로필 페이지
  - [x] 가나다 순으로 전체 회사 목록을 볼 수 있는 페이지
- [ ] 구직
  - [x] 조건 검색 구현 (페이징)
  - [ ] 검색에 정렬 기능 지원
  - [ ] 서버에 이력서를 업로드하여 구직 신청하는 기능 구현
- [x] 리뷰 기능
  - [x] 회사 프로필에 리뷰 게시
- [x] 북마크 기능
  - [x] 회사 북마크 등록/삭제
  - [x] 구직 북마크 등록/삭제

# 4. 구성원
| Backend |
| :-----: |
| <img src="https://avatars.githubusercontent.com/u/75304316?v=4" width=100px alt="LAYER6AI"/> |
| [destitutor](https://github.com/destitutor) |
