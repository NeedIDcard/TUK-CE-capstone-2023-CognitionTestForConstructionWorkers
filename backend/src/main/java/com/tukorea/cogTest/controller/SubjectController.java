package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;
import com.tukorea.cogTest.paging.Page;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.SubjectService;
import com.tukorea.cogTest.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final TestResultService testResultService;

    @GetMapping("/{id}/test-result")
    public ResponseEntity<Map<String, Object>> lookupSubjectTestResult(
            @PathVariable Long id,
            @RequestParam int curPageNum,
            @RequestParam int contentPerPage
    ) {
        try {
            List<TestResultDTO> testResult = subjectService.findTestResult(id);

            Page page = Page.getPage(curPageNum, contentPerPage, "testResult", testResult);

            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("page", page);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subject " + id + "'s result success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    @PostMapping("/{id}/test-result")
    public ResponseEntity<Map<String, Object>> saveSubjectTestResult(
            @PathVariable Long id,
            @RequestBody TestResultForm testResult) {
        try{
            TestResultDTO saved = testResultService.save(testResult, id);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("testResult", saved);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Save subject " + id + "'s result success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }
}
