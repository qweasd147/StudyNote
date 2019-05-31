package com.example.demo;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestUtils {

    public static String GETTER_PREFIX = "get";

    //parameter로 처리 가능한 클래스 목록
    private static final List<Class> PARAMETER_CLASS_LIST = Arrays.asList(String.class, List.class, Set.class);

    /**
     * Object 필드에 해당 값을 주입한다.
     * @param target
     * @param fieldName
     * @param value
     */
    public static void injectValue(Object target, String fieldName, Object value){

        Field[] targetFields = target.getClass().getDeclaredFields();

        Field searchFiled = Arrays.stream(targetFields)
                .filter(field -> fieldName.equals(field.getName()))
                .findAny()
                .orElseThrow(()->
                        new IllegalStateException(
                            String.format("not found %s field. in %s class"
                                , fieldName, target.getClass().getName())));

        boolean isAccessible = searchFiled.isAccessible();

        if(!isAccessible)
            searchFiled.setAccessible(true);

        ReflectionUtils.setField(searchFiled, target, value);

        if(!isAccessible)
            searchFiled.setAccessible(false);
    }

    /**
     * mock request body에 dto 값을 넣는다.
     * @param requestBuilder
     * @param dto
     * @param ignoreField
     * @return
     */
    public static MockHttpServletRequestBuilder addDtoParamsInRequestBody(
            MockHttpServletRequestBuilder requestBuilder, Object dto, List<String> ignoreField) {

        Class<?> target = dto.getClass();

        Predicate<Field> isEnableField = makeFieldChecker(dto, ignoreField);

        Arrays.stream(target.getDeclaredFields())
                .filter(isEnableField)                   //handling 가능한 필드인지 체크
                .map(field -> {
                    String[] fieldValue = convertToArrayStr(dto, field);
                    return Pair.of(field.getName(), fieldValue);
                })
                .forEach((pair) -> requestBuilder.param(pair.getKey(), pair.getValue()));

        return requestBuilder;
    }

    public static MockHttpServletRequestBuilder addDtoParamsInRequestBody(
            MockHttpServletRequestBuilder requestBuilder, Object dto) {

        return addDtoParamsInRequestBody(requestBuilder, dto, Collections.EMPTY_LIST);
    }

    /**
     * dto를 Query String 형태로 만든다
     * @param dto
     * @param ignoreField
     * @return
     */
    public static String dtoToQueryStr(Object dto, List<String> ignoreField){
        Class<?> target = dto.getClass();

        Predicate<Field> isEnableField = makeFieldChecker(dto, ignoreField);

        String queryStr = Arrays.stream(target.getDeclaredFields())
                .filter(isEnableField)                     //handling 가능한 필드인지 체크
                .map(field -> {
                    Object fieldValue = ReflectionTestUtils.invokeGetterMethod(dto, field.getName());
                    return Pair.of(field.getName(), fieldValue.toString());
                })
                .filter(pair -> Objects.nonNull(pair.getValue()))
                .map(pair -> pair.getKey() + "=" + pair.getValue())
                .collect(Collectors.joining("&"));

        return queryStr;
    }

    public static String dtoToQueryStr(Object dto){
        return dtoToQueryStr(dto, Collections.EMPTY_LIST);
    }

    /**
     * 무시 목록(ignoreField)에 필드명이 있는지, 필드 타입이 활용 가능한(String, List 등) 타입인지
     * , getter method가 존재하는지, value 값이 null값이 아닌지 체크
     * @param object
     * @param ignoreField
     * @return
     */
    private static Predicate<Field> makeFieldChecker(Object object, List<String> ignoreField){

        Class<?> target = object.getClass();

        Predicate<Field> containsIgnoreList = field -> ignoreField.contains(field.getName());

        Predicate<Field> isAssignType = field -> {
            Class<?> fieldType = field.getType();

            //assign class list
            return PARAMETER_CLASS_LIST.stream()
                    .anyMatch(clazz -> fieldType.isAssignableFrom(clazz));
        };

        Predicate<Field> hasGetter = field -> {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(field.getName());
            return ReflectionUtils.findMethod(target, getterMethodName) != null;
        };

        Predicate<Field> hasValue = field -> {

            Object value = ReflectionTestUtils.invokeGetterMethod(object, field.getName());
            return Objects.nonNull(value);
        };

        return containsIgnoreList.negate()
                .and(isAssignType)
                .and(hasGetter)
                .and(hasValue);
    }

    private static String[] convertToArrayStr(Object object, Field field){
        Object fieldValue = ReflectionTestUtils.invokeGetterMethod(object, field.getName());

        //TODO : 파라미터가 array, set, 배열일때 일관되게 배열로 내보내게 만들어야됨
        //TODO : generic 타입이 String이 아닐때 ......
        if(Collection.class.isAssignableFrom(field.getType())){
            Collection<String> fieldValues = (Collection<String>) fieldValue;
            return fieldValues.toArray(new String[fieldValues.size()]);
        }else if(field.getType().isArray()){
            return (String[]) fieldValue;
        }else{
            return new String[]{fieldValue.toString()};
        }
    }
}
