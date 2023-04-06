package com.tukorea.cogTest.dto;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.test.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TestResultForm {
    LocalDate date;

    Twohand twoHandResult;
    Pvt pvtResult;
    Crane craneResult;
    Maze mazeResult;
    Tova tovaResult;
}
