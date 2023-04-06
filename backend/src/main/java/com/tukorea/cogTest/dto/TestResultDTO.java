package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.test.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TestResultDTO {
    Long id;

    Subject target;

    LocalDate date;

    Twohand twoHandResult;
    Pvt pvtResult;
    Crane craneResult;
    Maze mazeResult;
    Tova tovaResult;
}
