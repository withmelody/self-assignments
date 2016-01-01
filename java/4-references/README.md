# 4 References in java

[참고문헌](http://d2.naver.com/helloworld/329631)

초기 Java는 사용자가 GC에 관여하지 못하게 만들어져 있었다.  
그러나 Java 1.2버전에서  몇개의 레퍼런스 타입들이 생겨나며 사용자가 관여 가능하게 됨

총 4가지의 타입이 존재한다.

1. Strong Reference
2. Weak Reference
3. Soft Reference
4. Phantom Reference

Strong을 제외한 Weak, Soft, Phantom은 java.lang.ref 라이브러리에 포함되어 잇다.

## Strong Reference
- 일반적으로 사용하는 레퍼런스

```java
String sample = "This is a one of strong reference";
```

## Weak Reference


## Soft Reference


## Phantom Reference


