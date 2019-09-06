package stream;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WithCheckedEx {

    public static String encodeWithEx(String str) throws UnsupportedEncodingException {

        return URLEncoder.encode(str, "utf-8");
    }

    public static String encodeWithoutEx(String str){

        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<String> strs = Arrays.asList("첫번째", "두번째", "세번째");

        /*
        strs.stream()
                .map(WithCheckedEx::encodeWithEx)   //ERROR!
                .collect(Collectors.toList());
         */

        strs.stream()
                .map(WithCheckedEx::encodeWithoutEx)
                .collect(Collectors.toList());
    }
}
