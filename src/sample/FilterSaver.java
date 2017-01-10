package sample;

import sample.entities.Filter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class FilterSaver {

    public static boolean save(Filter filter) throws FileNotFoundException {

        try(  PrintWriter out = new PrintWriter("resources/filters/" + filter.getName() + ".js" )  ){
            out.println(filter.getCode());
        }

        return true;
    }
}
