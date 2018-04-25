package generics;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class GenericsMain {

    public static void main(String[] args) {

        GenericsMain mainInstance = new GenericsMain();

        //mainInstance.safeNunSafe();     //타입이 보장되는지 여부

        //mainInstance.wildCard();       //와일드 카드 사용법

        mainInstance.genericsMethod();   //제너릭 메소드
    }

    /**
     * 제너릭 장점. 생성 시 타입을 보장 할 수가 있음. 따로 타입 검사 안해봐도 됨.
     */
	public void safeNunSafe(){
        SubGenerics unsafeSub = new SubGenerics();

        unsafeSub.setItem("unsafe");

        String unSafeitem = (String)unsafeSub.getItem();    // warning. String 이라는 보장이 안됨

        SubGenerics<String> safeSub = new SubGenerics<String>();

        String safeItem = safeSub.getItem();                //String 이라는게 보장이 됨

        SubGenerics<String> safeSub2 = new SubGenerics<>(); //컴파일 시, String 라는걸 유추(추론) 가능하여서 생략가능.
    }

    public void wildCard(){
    	
    	List<SubSomeThingClass> subSomeThingList = new ArrayList<>();

    	List<SuperSomeThingClass> superSomeThingList = new ArrayList<>();

        subSomeThingList.add(new SubSomeThingClass());
        subSomeThingList.add(new SubSomeThingClass());

        superSomeThingList.add(new SuperSomeThingClass());
        superSomeThingList.add(new SuperSomeThingClass());

        handleSubWildCard(subSomeThingList);
        handleSubWildCard(superSomeThingList);
        
        //handleSuperWildCard(subSomeThingList);	ERROR!
        handleSuperWildCard(superSomeThingList);
    }
    
    public void handleSubWildCard(List<? extends SuperSomeThingClass> someThingList) {
    	
    	//TODO some thing...

        //someThingList.add(new SuperSomeThingClass());     ERROR!
    }
    
    public void handleSuperWildCard(List<? super SuperSomeThingClass> someThingList) {
    	
    	//TODO some thing...

        //someThingList.add(new SuperSomeThingClass());     ERROR!
    }

    public void genericsMethod(){

        SubSomeThingClass sub1 = new SubSomeThingClass();
        SubSomeThingClass sub2 = new SubSomeThingClass();

        SubGenerics<SubSomeThingClass> subGen1 = new SubGenerics<>();
        SubGenerics<SubSomeThingClass> subGen2 = new SubGenerics<>();


        boolean equalsResult1 = isEquals1(sub1, sub2);

        //isEquals2, 3은 같은 로직의 메소드
        boolean equalsResult2 = isEquals2(subGen1, subGen2);
        boolean equalsResult3 = isEquals3(subGen1, subGen1);

        System.out.println("result 1 : "+equalsResult1);
        System.out.println("result 2 : "+equalsResult2);
        System.out.println("result 3 : "+equalsResult3);

    }

    private static <T extends SubSomeThingClass> boolean isEquals1(T s1, T s2){

        int s1Code = s1.hashCode();
        int s2Code = s2.hashCode();

	    return s1Code == s2Code;
    }

    private static <T extends SubSomeThingClass> boolean isEquals2(SubGenerics<T> s1, SubGenerics<T> s2){

        int s1Code = s1.hashCode();
        int s2Code = s2.hashCode();

        return s1Code == s2Code;
    }

    private static boolean isEquals3(
            SubGenerics<? extends SubSomeThingClass> s1
            , SubGenerics<? extends SubSomeThingClass> s2){

        int s1Code = s1.hashCode();
        int s2Code = s2.hashCode();

        return s1Code == s2Code;
    }
}