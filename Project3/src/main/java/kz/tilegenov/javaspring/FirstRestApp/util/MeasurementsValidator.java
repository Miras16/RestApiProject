package kz.tilegenov.javaspring.FirstRestApp.util;

import kz.tilegenov.javaspring.FirstRestApp.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import kz.tilegenov.javaspring.FirstRestApp.services.SensorsService;

@Component
public class MeasurementsValidator implements Validator {

    private final SensorsService sensorService;

    @Autowired
    public MeasurementsValidator(SensorsService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;

        if (measurement.getSensor() == null) {
            return;
        }

        if (sensorService.findByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "Нет зарегистрированного сенсора с таким именем!");
    }
}