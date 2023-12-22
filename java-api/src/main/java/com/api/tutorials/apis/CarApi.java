package com.api.tutorials.apis;

import com.api.tutorials.dtos.BooleanResponse;
import com.api.tutorials.dtos.Car;
import com.api.tutorials.dtos.ExceptionResponse;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CarApi {
    @GetMapping
    List<Car> list();

    @Operation(summary = "Find car by ID", description = "This API lookup pre-existing Cars in the database if its found it returns the Car object otherwise an exception is thrown")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Car By Id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Car Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @GetMapping("/{carId}")
    Car findById(@Parameter(description = "Id of the Car which is not null positive integer") @PathVariable("carId") String carId);

    @PostMapping
    Car create(@RequestBody Car car);

    @PutMapping("/{carId}")
    Car update(@PathVariable("carId") String carId, @RequestBody Car car);

    @PatchMapping("/{carId}")
    Car partialUpdate(@PathVariable("carId") String carId, @RequestBody JsonPatch patch);

    @DeleteMapping("/{carId}")
    BooleanResponse delete(@PathVariable("carId") String carId);
}
