package kz.tilegenov.javaspring.FirstRestApp.util;

import kz.tilegenov.javaspring.FirstRestApp.models.Sensor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import kz.tilegenov.javaspring.FirstRestApp.services.SensorsService;
@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;
        if (sensorsService.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name", "", "Сенсор с таким именем уже существует");
    }
}
