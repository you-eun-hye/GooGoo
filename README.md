# GooGoo
구글 스토어 어플 내의 구독 서비스를 관리하는 서비스입니다.

# 사용자 페이지
1. 회원가입
    1. 닉네임 + 이메일 + 비밀번호
        1. 이메일 인증
        2. 이메일 & 닉네임 중복 체크
    2. 소셜 로그인 → 구글
2. 로그인
    1. 이메일 + 비밀번호
        1. 로그인 실패 핸들러
        2. 아이디 찾기
        3. 임시 비밀번호 메일 전송
    2. 마이페이지
        1. 내정보 수정
            1. 이메일 → 이메일 인증
            2. 닉네임
            3. 비밀번호
        2. 로그아웃
        3. 회원탈퇴 
3. Q&A(게시판) 
    1. 게시물 작성
        1. 이미지 단일 파일 첨부
    2. 내가 작성한 게시물 + 공지 조회
        1. 페이징 처리
    3. 게시물 수정 → 답변 완료된 것은 수정 불가
    4. 게시물 삭제
4. 구글 크롤링
    1. 결제 내역 TOP3 조회
    2. 결제 내역 전체 조회
        1. 페이징 처리
    3. 추천
        1. 구글 플레이스토어 TOP100 크롤링

# 관리자 페이지
1. 유저
    1. 조회
    2. 수정
    3. 삭제
2. 관리자
    1. 등록
    2. 수정
    3. 삭제
3. 공지
    1. 생성
    2. 수정
    3. 삭제
4. 문의
    1. 조회
    2. 답변
5. 로그아웃
