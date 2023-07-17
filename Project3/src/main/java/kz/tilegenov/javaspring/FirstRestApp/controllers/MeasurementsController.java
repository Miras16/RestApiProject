package kz.tilegenov.javaspring.FirstRestApp.controllers;


import kz.tilegenov.javaspring.FirstRestApp.dto.MeasurementDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import kz.tilegenov.javaspring.FirstRestApp.dto.MeasurementsResponse;
import kz.tilegenov.javaspring.FirstRestApp.models.Measurement;
import kz.tilegenov.javaspring.FirstRestApp.services.MeasurementsService;
import kz.tilegenov.javaspring.FirstRestApp.services.SensorsService;
import kz.tilegenov.javaspring.FirstRestApp.util.MeasurementException;
import kz.tilegenov.javaspring.FirstRestApp.util.MeasurementsErrorResponse;
import kz.tilegenov.javaspring.FirstRestApp.util.MeasurementsValidator;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static kz.tilegenov.javaspring.FirstRestApp.util.ErrorsUtil.returnErrorsToClient;

@RequestMapping("/measurements")
@RestController
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final MeasurementsValidator measurementsValidator;

    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService, MeasurementsValidator measurementsValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementsValidator = measurementsValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementsValidator.validate(measurementToAdd,bindingResult);
        if(bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }
        measurementsService.addMeasurement(measurementToAdd);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements() {
        // Обычно список из элементов оборачивается в один объект для пересылки
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {

        return modelMapper.map(measurementDTO, Measurement.class);

    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    @ExceptionHandler
    private ResponseEntity<MeasurementsErrorResponse> handleException(MeasurementException e) {
        MeasurementsErrorResponse response = new MeasurementsErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
