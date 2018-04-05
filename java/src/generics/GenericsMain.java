package generics;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class GenericsMain {

    public static void main(String[] args) {

        GenericsMain mainInstance = new GenericsMain();

        //mainInstance.safeNunSafe();     //타입이 보장되는지 여부

        //mainInstance.wildCard();       //와일드 카드 사용법
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
}