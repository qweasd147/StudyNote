package enums;

import java.util.Arrays;
import java.util.function.BiFunction;

public class Enums {

    public static class Card{
        public enum Kind{
            CLOVER, HEART, DIAMOND, SPADE;

            Kind(){}
        }

        public enum Value {
            TWO(2), THREE(3), FOUR(4);

            final int val;

            Value(int val) {
                this.val = val;
            }

            public int getValue(){
                return val;
            }
        }
    }

    public static class Status{
        public enum FlagStatus{
            Y(1, true), N(0, false);

            private final int numberStatus;
            private final boolean booleanStatus;

            FlagStatus(int numberStatus, boolean booleanStatus) {
                this.numberStatus = numberStatus;
                this.booleanStatus = booleanStatus;
            }

            public int getNumberStatus() {
                return numberStatus;
            }

            public boolean isBooleanStatus() {
                return booleanStatus;
            }
        }

        public enum ProcessStatus{

            SUCCESS(0,"success","s"), WARNING(1,"warning", "w"), FAIL(2, "fail", "f"), NOT_FOUND(-999,"notFound", "notFound");

            private final int numberStatus;
            private final String strStatus;
            private final String shortStrStatus;

            ProcessStatus(int numberStatus, String strStatus, String shortStrStatus) {
                this.numberStatus = numberStatus;
                this.strStatus = strStatus;
                this.shortStrStatus = shortStrStatus;
            }

            public int getNumberStatus() {
                return numberStatus;
            }

            public String getStrStatus() {
                return strStatus;
            }

            public String getShortStrStatus() {
                return shortStrStatus;
            }

            public static ProcessStatus findStatusByNumberStatus(int findValue) {
                return Arrays.stream(values())
                        .filter(processStatus -> processStatus.getNumberStatus() == findValue)
                        .findFirst()
                        .orElse(NOT_FOUND);
            }
        }
    }

    public static class Table{
        public enum BoardTableColumn{
            BOARD_IDX("boardIdx", "BOARD_IDX")
            , BOARD_TITLE("boardTitle","BOARD_TITLE")
            , BOARD_CONTENTS("boardContents","BOARD_CONTENTS");

            private final String camelCase;
            private final String snakeCase;

            BoardTableColumn(String camelCase, String snakeCase) {
                this.camelCase = camelCase;
                this.snakeCase = snakeCase;
            }

            public String getCamelCase() {
                return camelCase;
            }

            public String getSnakeCase() {
                return snakeCase;
            }
        }
    }

    public static class HandleEnum {
        public enum WithLamda {

            ADD((aDouble1, aDouble2) -> aDouble1 + aDouble2)
            , MINUS((aDouble1, aDouble2) -> aDouble1 - aDouble2)
            , MULTI((aDouble1, aDouble2) -> aDouble1 * aDouble2)
            , DIVIDE((aDouble1, aDouble2) -> aDouble1 / aDouble2);

            private final BiFunction<Double, Double, Double> expression;

            WithLamda(BiFunction<Double, Double, Double> expression) {
                this.expression = expression;
            }

            public double handleData(Double aDouble1, Double aDouble2){
                return expression.apply(aDouble1, aDouble2);
            }
        }

        public enum WithAbstract{

            ADD {
                @Override
                public double handleData(double aDouble1, double aDouble2) {
                    return aDouble1 + aDouble2;
                }
            }
            , MINUS {
                @Override
                public double handleData(double aDouble1, double aDouble2) {
                    return aDouble1 - aDouble2;
                }
            }
            , MULTI {
                @Override
                public double handleData(double aDouble1, double aDouble2) {
                    return aDouble1 * aDouble2;
                }
            }
            , DIVIDE {
                @Override
                public double handleData(double aDouble1, double aDouble2) {
                    return aDouble1 / aDouble2;
                }
            };

            public abstract double handleData(double aDouble1, double aDouble2);
        }
    }
}
