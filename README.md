# 일루미나리안 코딩 테스트 과제

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

리뷰, 리뷰댓글, 리뷰 포토 관계구성
```text
User
^
|
|
1|N
|
v
Review 1-----------1 Review Photo
^
|
|
1|N
|
v
Review Reply
```


