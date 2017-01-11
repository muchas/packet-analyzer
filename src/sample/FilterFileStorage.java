package sample;

import sample.entities.Filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FilterFileStorage implements FilterStorage {

    private static String DEFAULT_DIRECTORY = "resources/filters/";

    private String directory;

    public FilterFileStorage() {
        this.directory = DEFAULT_DIRECTORY;
    }

    @Override
    public boolean save(Filter filter) {
        try(  PrintWriter out = new PrintWriter(directory + filter.getName() + ".js" )  ){
            out.println(filter.getCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Filter> loadAll() {
        File folder = new File(directory);
        ArrayList<Filter> filters = new ArrayList<>();

        for (final File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();
            String extension = getFileExtension(fileName);

            if(extension.equals("js")) {
                try {
                    String code = FileUtils.readFile(fileEntry.getAbsolutePath(), StandardCharsets.UTF_8);
                    filters.add(new Filter(code));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filters;
    }

    private String getFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i+1);
        }

        return extension;
    }
}
