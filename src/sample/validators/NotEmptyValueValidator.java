package sample.validators;


public class NotEmptyValueValidator extends BaseValidator {

    private static final String ERROR_MESSAGE = "This value should not be empty";

    private String value;
    private String key;

    public NotEmptyValueValidator(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean validate() {
        if(this.value.trim().equals("")) {
            errors.add(key + ": " + NotEmptyValueValidator.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
