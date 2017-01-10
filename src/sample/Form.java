package sample;


import sample.validators.Validator;

import java.util.List;

public class Form {

    private List<Validator> validators;
    private List<String> errors;

    public Form(List<Validator> validators) {
        this.validators = validators;
    }



    public List<String> getErrors() {
        return errors;
    }
}
