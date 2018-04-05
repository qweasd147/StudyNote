# Generics.

# 1. Generics ? 
    메소드 또는 collection class에 컴파일 시 타입 체크를 해주는 기능. 컴파일 시 체크하기 때문에
    타입 안정성을 높이고 형변환 번거로움을 줄임.
    
# 2. 장점

### 2.1 형변환을 줄임. 안정성을 높임
```java
public void safeNunSafe(){
    SubGenerics unsafeSub = new SubGenerics();

    unsafeSub.setItem("unsafe");

    String unSafeitem = (String)unsafeSub.getItem();    // warning. String 이라는 보장이 안됨

    SubGenerics<String> safeSub = new SubGenerics<String>();

    String safeItem = safeSub.getItem();                //String 이라는게 보장이 됨

    SubGenerics<String> safeSub2 = new SubGenerics<>(); //컴파일 시, String 라는걸 유추(추론) 가능하여서 생략가능.
}
```
이런식으로 가져올떄 형변환을 할 필요가 없다(객체 타입이 보장됨).

### 2.2 와일드 카드를 사용함으로써 코드 확장성을 자유롭게 함
    귀찮아서 소스 코드 생략. 첨부된 소스 코드를 보자(mainInstance.wildCard 실행)

### 2.3 제너릭 메소드의 사용
    조금 더 공부 해야겠음.. 뭔가 장점이 와닿지가 않음

# 3. 주요 사용법
기타 알기 쉬운내용은 pass. 물론 기준은 나
### 3.1 와일드 카드 확장
```java
public void handleSubWildCard(List<? extends SuperSomeThingClass> someThingList) {
    //some thing...
}

public void handleSuperWildCard(List<? super SuperSomeThingClass> someThingList) {
    //some thing...
}
```
? extends A -> A와 A를 확장한 class만 허용(child class)

? super A -> A와 A의 super class만 허용

? -> 아무거나. 사실상 ? extends Object

### 3.2 타입 추론이 가능함
```java
    SubGenerics<String> safeSub2 = new SubGenerics<>();    
    SubGenerics<String> safeSub2 = new SubGenerics<String>();
```
위의 두 줄은 서로 같다. 컴파일 시, String 라는걸 유추 가능함(생략 가능). jdk 1.7 이상만 가능
### 3.3 제너릭 메소드
    일단 보류

# 4. 주의할점
```java
    List<? extends SuperSomeThingClass> list = new ArrayList<>();
    
    list.add(new SuperSomeThingClass());   //ERROR!
```
참고 사항으로 이런 방식으로는 안된다. 될꺼 같지만 list에 각 아이템 요소가 무엇으로 처리해야 할지 보장이 안된다.

쫌더 자세히 설명하면 만약 저게 가능하다고 가정하면
```java
    List<? extends SuperSomeThingClass> list = new ArrayList<>();
    
    list.add(new SuperSomeThingClass());
    list.add(new SubSomeThingClass());

    ??? someThing= list.get(0);
```
이런식으로 someThing의 클래스가 어떤것인지 몰라서 내부적으로 문제가 발생한다고 한다. 클래스의 안정성을 위해서라도 이런방식은 막아두었다고 한다.


참고 https://stackoverflow.com/questions/24861758/difference-for-super-extends-string-in-method-and-variable-declaration?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
