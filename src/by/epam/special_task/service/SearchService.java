package by.epam.special_task.service;

import by.epam.special_task.device.Device;
import by.epam.special_task.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    private InputStream inputStream;

    public SearchService(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Device> searchDevices(String deviceName, String parameterName, String parameterValue) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<Device> result = reader.lines()
                .filter(line -> line.toLowerCase().contains(deviceName.toLowerCase())
                        && line.toLowerCase().contains((parameterName + "=" + parameterValue).toLowerCase()))
                .map(line -> {
                    try {
                        return Parser.parseDeviceFromString(line);
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        reader.close();

        return result;
    }
}
