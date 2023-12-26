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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CarApi extends BusinessApi{
    @GetMapping
    List<Car> list();

    @GetMapping("/{carId}")
    Car findById(@Parameter(description = "Id of the Car which is not null positive integer") @PathVariable("carId") String carId);

    @Operation(summary = "Create Car", description = "This API will create new car object and will assign a unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Car Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "5xx", description = "Internal Server Errors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})
    })
    @PostMapping
    Car create(@RequestBody Car car);

    @PutMapping("/{carId}")
    Car update(@PathVariable("carId") String carId, @RequestBody Car car);

    @PatchMapping("/{carId}")
    Car partialUpdate(@PathVariable("carId") String carId, @RequestBody JsonPatch patch);

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{carId}")
    BooleanResponse delete(@PathVariable("carId") String carId);
}
