package by.epam.special_task;

import by.epam.special_task.service.SearchService;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(new File("appliances_db.txt"));

        SearchService searchService = new SearchService(inputStream);

        System.out.println(searchService.searchDevices("refrigerator", "height", "200"));
    }
}
