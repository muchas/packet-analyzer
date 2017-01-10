package sample.validators;


import java.util.List;


public interface Validator {
    public abstract boolean validate();
    public List<String> getErrors();
}
