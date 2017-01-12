package sample.validators;

import sample.FilteringContext;
import sample.entities.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterValidator extends BaseValidator {

    private List<Validator> validators;

    public FilterValidator(Filter filter, FilteringContext context) {
        errors = new ArrayList<>();
        validators = Arrays.asList(
                new NotEmptyValueValidator("title", filter.getName()),
                new NotEmptyValueValidator("codearea", filter.getBody()),
                new JavaScriptFilterCodeValidator(filter.getName(), filter.getCode(), context.getEngine()) // need to copy here
        );
    }

    public boolean validate() {
        for(Validator validator : validators) {
            if(!validator.validate()) {
                errors.addAll(validator.getErrors());
                return false;
            }
        }
        return true;
    }
}
