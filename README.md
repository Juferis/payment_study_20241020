# 결제 API 만들기
> Portone V2 모듈을 활용한 결제 시스템 개발

## 사용한 기술 스택
- JAVA 17
- SpringBoot
- JPA
- QueryDSL
- Thymeleaf(SSR)
- Docker
- 인메모리 DB(H2) -> MySQL(MariaDB) 전환
- (Portone V2)[https://developers.portone.io/opi/ko/readme?v=v2]

## 구현 항목
- [X] 테이블 설계, 엔티티 관계 정의
- [X] 클래스 정의(Order, Payment, Product, OrderProduct)
- [X] 각 클래스의 Controller, Service, Entity 및 관계 구현
- [X] 필수 검증 메소드 생성(RequestDTO 필수 값 검증, 존재하는 상품인지, 현재 주문 가능한 상품인지, 실제로 존재하는 유저인지, 결제가와 상품의 결제가와 같은지, ...)
- [X] 테스트 코드 작성
- [X] Tymeleaf를 활용한 SSR 결제창 및 장바구니 화면
- [X] 주문 관련 Controller 및 API 구현(주문대기, 주문요청, 결제대기)
- [X] Portone 결제 모듈 연결 및 결제 테스트
- [X] QueryDSL 적용
