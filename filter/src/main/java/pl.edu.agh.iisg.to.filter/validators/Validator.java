package pl.edu.agh.iisg.to.filter.validators;


import java.util.List;


public interface Validator {
    public abstract boolean validate();
    public List<String> getErrors();
}
