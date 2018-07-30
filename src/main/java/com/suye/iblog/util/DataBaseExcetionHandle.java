package com.suye.iblog.util;





import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseExcetionHandle {
    public static String getMessage(ConstraintViolationException e){
        List<String> list=new ArrayList<>();
        for(ConstraintViolation<?> constraintViolation:e.getConstraintViolations()){
            list.add(constraintViolation.getMessage());
        }
        String messages= null;
                //StringUtils.join(list.toArray(),";");
        return messages;
    }

}
