package Logic.Validators;

public abstract class ValidatorUtil {

    public static boolean isNameValid(String name){
        return name.matches("[A-Za-z]{3,20}"); // at least 3 letters
    }

    public static boolean isPasswordValid(String password){
        return password.matches("\\S{3,20}"); //at least 5 non-whitespace characters
    }

    public static boolean isNumberValid(String number){
        return number.matches("\\d+"); // at least a digit
    }

    public static boolean isDateValid(String date){
        return date.matches("\\d{2}/\\d{2}/\\d{4}"); // "dd/mm/yyyy" format
    }

    public static boolean isListNameValid(String name){
        return name.matches("\\S{3,}"); // at least 3 non-whitespace characters
    }

}
