package sample.validators;


import java.util.List;

public abstract class BaseValidator implements Validator {

    protected List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
}
