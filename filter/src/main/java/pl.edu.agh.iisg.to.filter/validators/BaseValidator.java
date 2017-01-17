package pl.edu.agh.iisg.to.filter.validators;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseValidator implements Validator {

    public BaseValidator() {
        this.errors = new ArrayList<String>();
    }

    protected List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
}
