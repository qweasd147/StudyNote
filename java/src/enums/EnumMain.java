package enums;

/**
 * enum 파일만 잔뜩 늘려놓기도 보기싫고 Enums.Card 이런식으로 보긴 명확하게 보이지 않아서
 * 그냥 import함
 */
import enums.Enums.*;

public class EnumMain {

    public static void main(String[] args) {
        EnumMain main = new EnumMain();

        main.enumBase();          //기본 사용법
        //main.enumStatus();          //통합적 + 직관적 + 편하게 상태코드 관리

        //main.handleEnumWithLamda(); //상태 + 메소드를 함께 관리
        //main.handleEnumWithAbstract();

    }

    public void enumBase(){

        //Card.Kind.CLOVER == Card.Value.FOUR;          //ERROR 내부적으로 값이 다름. 안전하게 값 비교 가능

        if(Card.Kind.CLOVER == Card.Kind.HEART){}   //equals가 아닌 '=='를 통해 비교하여 빠르게 연산 가능

        Card.Kind val = Card.Kind.valueOf("CLOVER"); //문자열로 선언값 가져올 수 있음
        System.out.println("name : "+val.name());

        Card.Kind[] cardKinds = Card.Kind.values();     //선언된 값 배열을 물러옴

        System.out.println("CLOVER name : "+Card.Kind.CLOVER.name());
        System.out.println("CLOVER toString : "+Card.Kind.CLOVER.toString());   //toString은 어디서 override 할수 잇어서 사용을 권장안함

        //Card.Value.FOUR.val;            //필드는 private, public하게 선언 가능

        //Card.Kind data = new Card.Kind();     //ERROR!
    }

    public void enumStatus(){

        Status.ProcessStatus thirdPartyResult = null;

        //thirdPartyResult = someThirdPartyProcess();       //구현은 자유!!

        try{
            someThirdPartyProcess();                        //구현은 자유!!
            thirdPartyResult = Status.ProcessStatus.SUCCESS;
        }catch (RuntimeException e){
            thirdPartyResult = Status.ProcessStatus.FAIL;
        }

        // 공통된 enum을 반환하게 만들면 일관성 + 직관적으로 코드가 쉬워진다.
        switch (thirdPartyResult){
            case SUCCESS:
                System.out.println("처리 성공");    break;
            case WARNING:
                System.out.println("경고!");        break;
            case FAIL:
                System.out.println("처리 실패!!");  break;
        }

        /*
        각 서드파티에 맞는 데이터 관리가 쉬워진다.
        insertFile(thirdPartyResult.getNumberStatus()); //파일에는 숫자형태로 기록
        insertDB(thirdPartyResult.getStrStatus());      //DB에는 문자 형태로 기록
        */
    }

    private Status.ProcessStatus someThirdPartyProcess(){

        int result = 0;     //third pary 결과 반환값이 0이라고 가정

        switch (result){
            case 0 : return Status.ProcessStatus.SUCCESS;
            case 1 : return Status.ProcessStatus.WARNING;
            case 2 : return Status.ProcessStatus.FAIL;
            default:return Status.ProcessStatus.FAIL;
        }
        //return Status.ProcessStatus.findStatusByNumberStatus(result);
    }

    /**
     * 람다 + functional interface + enum 조합.
     * 연관된 enum(상태) + 연관된 메소드를 함께 관리할 수 있다.
     */
    public void handleEnumWithLamda(){
        double handleResult = HandleEnum.WithLamda.ADD.handleData(10.0, 5.0);
        System.out.println("add result : "+handleResult);

        handleResult = HandleEnum.WithLamda.MINUS.handleData(10.0, 5.0);
        System.out.println("minus result : "+handleResult);
    }

    /**
     * 추상 메소드 + enum 조합.
     * 연관된 enum(상태) + 연관된 메소드를 함께 관리할 수 있다.
     */
    public void handleEnumWithAbstract(){
        double handleResult = HandleEnum.WithAbstract.MULTI.handleData(10.0, 5.0);
        System.out.println("multi result : "+handleResult);

        handleResult = HandleEnum.WithAbstract.DIVIDE.handleData(10.0, 5.0);
        System.out.println("divide result : "+handleResult);
    }
}
