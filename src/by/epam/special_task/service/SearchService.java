package by.epam.special_task.service;

import by.epam.special_task.device.Device;
import by.epam.special_task.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    private final InputStream inputStream;
    private static final String DEVICE_BY_NAME_AND_PARAM = "^(?i)%s.+?(?i)%s=(?i)%s[,$].*$";

    public SearchService(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Device> searchDevices(String deviceName, String parameterName, String parameterValue) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines().filter(line -> line.matches(String.format(DEVICE_BY_NAME_AND_PARAM, deviceName, parameterName, parameterValue)))
                .map(line -> {
                    try {
                        return Parser.parseDeviceFromString(line);
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
    }
}
