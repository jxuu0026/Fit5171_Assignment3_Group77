package rockets.check;

import java.util.regex.Pattern;


public class Validator {

    public void checkForNullEmpty(Object obj) {
        if (null == obj) {
            throw new IllegalArgumentException("variables cannot be null.");
        }
        if (obj.toString().trim().equals("")) {
            throw new IllegalArgumentException("variables cannot be empty.");
        }
    }

    public void checkEmailFormat(String email) {
        String pattern = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+";
        boolean matches = Pattern.matches(pattern, email);
        if (matches == false) {
            throw new IllegalArgumentException("A user needs to have a valid email address");
        }
    }

    public void checkWeightIsLessThanZero(int weight) {
        if(weight < 0) {
            throw new IllegalArgumentException("weight cannot be less than 0");
        }
    }

    public void checkLoadingWeightIsLessThanZero(int loadingWeight) {
        if(loadingWeight < 0) {
            throw new IllegalArgumentException("loading weight cannot be less than 0");
        }
    }
}
