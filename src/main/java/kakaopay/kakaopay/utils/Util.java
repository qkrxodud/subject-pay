package kakaopay.kakaopay.utils;

import java.text.SimpleDateFormat;

public class Util {

    //날짜 포맷팅 확인
    public static boolean isDateForMat(String date, String dateFormat) {
        try {
            //날짜 포맷
            SimpleDateFormat dateFormatParser = new SimpleDateFormat(dateFormat);
            //잘못된 값이 들어오면 Exception 리턴
            dateFormatParser.setLenient(false);
            //대상 인자 검증
            dateFormatParser.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //날짜 검증 로직
    public static boolean validationCheck(String date) {
        String dateForms[] = { "yyyy-MM-dd", "yyyy.MM.dd", "yyyyMMdd", "yyyy/MM/dd"};
        for (String dateForm : dateForms) {
            if(isDateForMat(date, dateForm)) {
                return true;
            }
        }
        return false;
    }

    //날짜포멧팅 전달
    public static String returnDateForMat(String date) {
        String dateForms[] = { "yyyy-MM-dd", "yyyy.MM.dd", "yyyyMMdd", "yyyy/MM/dd"};
        for (String dateForm : dateForms) {
            if (isDateForMat(date, dateForm)) {
                return dateForm;
            }
        }
        return date;
    }

    //년도 검증 로직
    public static boolean validationYearCheck(String date) {
        String dateForms[] = { "yyyy"};
        for (String dateForm : dateForms) {
            if(isDateForMat(date, dateForm)) {
                return true;
            }
        }
        return false;
    }

}
