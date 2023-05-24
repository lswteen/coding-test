# 일루미나리안 코딩 테스트 과제

## API

모든 API는 `http://localhost:8080/api/`를 기본 URL로 사용합니다.

### 주문 API

**주문 목록 조회**
- 엔드포인트: `GET /orders/order`
- 파라미터: 사용자 ID (`Long userId`)

**주문 상세 조회**
- 엔드포인트: `GET /orders/detail/{orderId}`
- 경로 변수: 주문 ID (`Long orderId`)

**주문 취소**
- 엔드포인트: `GET /orders/cancel/{orderId}`
- 경로 변수: 주문 ID (`Long orderId`)

### 리뷰 API

API는 `ReviewRepository.java` 인터페이스를 참조하세요. 이 인터페이스는 리뷰 관련 동작을 정의합니다.

## 데이터베이스

애플리케이션은 H2 인메모리 데이터베이스를 사용합니다. 데이터베이스는 애플리케이션을 실행할 때마다 초기화되며, `schema.sql` 파일에 정의된 DDL로 생성됩니다.

## 보안

이 프로젝트는 JWT를 사용하여 보안을 관리합니다. JWT 관련 설정은 `application.yml` 파일에서 확인할 수 있습니다.

## 개발 환경

- JDK 1.8
- Spring Boot 2.5.3
- Gradle 7.0
- H2 Database

## 프로젝트의 의도
Lombok, jpa, ibatis, 멀티라인 을 사용하지 않고 개발을 함으로써
어떻게 라이브러리에서 코드가 자동으로 만들어지는지 개발자에게 알고 써야한다 라는 개념을 알려주는것 같습니다.
저역시 항상 주니어 개발자에게 메뉴얼을 잘 파악하고 이해한만큼 사용하는것을 권고하고있습니다.  
오랜만에 jdbcTemplate를 사용해서 작성하다보니 다시금 오픈소스 라이브러리를 이용해서 개발자관점만 비지니스적으로 집중할수 있는것이 
개발시간을 단축시켜주는지 느낄수있는 경험이지만 ...   
하다보니 처음에는 재미가 있었는데 점점 귀찮은걸 이겨내는것이 쉽지가 않네요 ㅎㅎ

## 프로젝트 요구사항
1. 제공된 프로젝트 zip 파일을 사용하여 과제를 진행한다.
2. dependency 는 추가 하지 않고 설정 된 것만을 사용한다.
3. 만약에 dependency 추가가 꼭 필요한 상황이라면 과제관에게 해당 내용에 대해서
   공지하도록 한다.
4. 문제 풀이는 코드작업 + 간단한 설명을 작성 하도록 하고 설명은 편한 방법으로
   자유롭게 작성 한다.
5. 주어진 수행 과제 외에 과제 프로젝트 내에 개선하고 싶은 코드나 추가하고 싶은
   공통 기능이 있다면 자유롭게 작성하고 해당 내용에 대해 간단한 설명을 추가하도록
   한다.
6. 테스트 코드 작성은 필수 사항입니다. 반드시 작성 해주세요

# 요구사항 중 공통 기능에 대한 개발자의 견해
요구사항 대로 수행예정입니다.
**공통 기능** 에 대해서는 많은 개발자들과 예기하며 느끼는 부분을 공유 해보겠습니다.

저역시 공통적인 기능들을 개발도 많이 해보고 다양한 라이브러리도 사용했는데
공통 기능을 개발하는것도 중요하지만 보편적으로 오픈된 라이브러리를 알고있다면 되도록이면 공통 라이브러리 사용하는것을 권고하는 편입니다.

내가만든 공통라이브러리가 특정 회사, 프로젝트에 필수적이라면 만들어서 사용하는것이 좋지만 만들어진 공통라이브러리보다 더좋은
기능이 업데이트 되었을때 내가사용하는 Legacy공통 라이브러리에서 너무오래된 하위 버전을 사용하는부분이 core에 사용중인 상태로
되어있다면 변경되는 곳에 너무많은 리소스가 사용될 수 있습니다.

기능을 만들려고하는 목적과 일치한다면 공통 라이브러리는 오픈소스 사용 권장합니다.

# Database 관계형 구조 분석
```text
 User 1-----N Review 1-----1 Order
  ^                        /
  |                       /
  |                      /
 1|N                   1|N
  |                    /
  |                   /
  v                  v
 Order          Product

```
User와 Review는 1:N 관계를 가지고 있습니다. 하나의 사용자(User)는 여러 개의 리뷰(Review)를 작성할 수 있습니다.  
Review와 Order는 1:1 관계를 가지고 있습니다. 하나의 리뷰는 하나의 주문(Order)에만 연결될 수 있습니다.  
User와 Order는 1:N 관계를 가지고 있습니다. 하나의 사용자는 여러 개의 주문(Order)을 가질 수 있습니다.  
Order와 Product는 1:N 관계를 가지고 있습니다. 하나의 주문(Order)는 하나의 상품(Product)에만 연결될 수 있지만, 하나의 상품은 여러 주문에 속할 수 있습니다.  

## 과제 요구사항
a. 리뷰는 공개 여부를 설정 할 수 있어야 할것 같아요.  
b. 리뷰 작성시 사용자는 리워드로 일정금액의 마일리지를 받도록 해주세요.  
c. 리뷰에 좋아요 기능을 추가하고 답글을 달 수 있도록 해주세요  
d. 리뷰의 사진을 첨부 할 수 있도록 해주세요. 사진은 여러장 등록이
가능하도록 해주시구요

![스크린샷 2023-05-24 오후 9 51 18](https://github.com/lswteen/coding-test/assets/3292892/6d7c0cd8-7fe5-4857-9fbf-629da987aab1)

리뷰, 리뷰댓글, 리뷰 포토 관계구성
```text
User
^
|
|
1|N
|
v
Review 1-----------N Review Photo
^
|
|
1|N
|
v
Review Reply
```
## 마일리지 
주문후 구매완료 배송받은 상품에 리뷰를 텍스트 또는 이미지로 추가하게되면 
마케팅 전략으로 상품에 퀄리티 보장과 마케팅을 함으로써 재구매율이 올라가게됩니다.

텍스트보다 이미지가 재구매율을 높이는데 효과적인 정보를 제공하기때문에 텍스트 100원 이미지 350원 정도 제공하는방향으로
실제 오픈마켓도 텍스트 이미지 리뷰 댓글에 차등해서 제공합니다.

```text
  ┌──────────┐    ┌──────────┐     ┌───────────┐    ┌───────────┐
  │          │    │          │     │           │    │           │
  │  USER    │    │  ORDER   │     │  REVIEW   │    │  MILEAGE  │
  │          │    │          │     │           │    │           │
  │  id      │◀──▶│user_id   │     │user_id    │◀──▶│user_id    │
  │  name    │    │product_id│    │product_id │    │order_id   │◀──▶│order_id │
  │  email   │    │review_id │◀──▶│id         │    │review_id  │
  │  password│    │state     │    │content    │    │amount     │
  │  login_  │    │request_  │    │state      │    │create_date│
  │  count   │    │msg       │    │likes      │    └───────────┘
  │  last_   │    │reject_   │    │create_date│
  │  login_  │    │msg       │    └───────────┘
  │  date    │    │complete_ │
  │  create_ │    │date      │
  │  date    │    │rejected_ │
  │  modified│    │date      │
  │  date    │    │create_date│
  └──────────┘    └─────────┘

```

요구사항 테이블은 schema.sql에 추가 하였습니다.

보통은 entity, DTO를 분리하는데 과제 요구사항에 맞춰진행하면서 별도 분리를 진행하진 않았습니다.
JPA를 사용하게되면 Querydsl, 퍼시스던트 컨텍스트 트랜잭션 범위가 중요하기 때문에 필수지만 이번 개발에서는 간략화 하였습니다.


