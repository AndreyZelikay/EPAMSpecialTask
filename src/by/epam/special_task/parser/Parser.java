package by.epam.special_task.parser;

import by.epam.special_task.device.Device;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Parser {

    public static Device parseDeviceFromString(String input) throws ReflectiveOperationException {
        String[] specifications = input.replaceAll("[:,]","").split(" ");

        Class<?> deviceClass = Class.forName("by.epam.special_task.device." + specifications[0]);

        Device device = (Device) deviceClass.getConstructor().newInstance();

        for(Field field: deviceClass.getDeclaredFields()) {
            field.setAccessible(true);

            String value = Arrays.stream(specifications)
                    .filter(specification -> specification.replace("_", "")
                            .toLowerCase()
                            .contains(field.getName().toLowerCase()))
                    .findFirst().orElse("").replaceAll("^.+?=","");

            switch (field.getType().getSimpleName()) {
                case "String" -> field.set(device, value);
                case "int" -> field.set(device, Integer.parseInt(value));
                case "double" -> field.set(device, Double.parseDouble(value));
            }
        }

        return device;
    }
}
