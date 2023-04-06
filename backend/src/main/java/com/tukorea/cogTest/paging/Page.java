package com.tukorea.cogTest.paging;


import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Page {

    private boolean prev;
    private boolean next;

    private int contentPerPage;
    private int startPageNum;
    private int endPageNum;
    private int curPageNum;
    private int allPageNum;

    private final Map<String, Object> contents = new ConcurrentHashMap<>();

    @Builder
    public Page(boolean prev, boolean next, int contentPerPage, int startPageNum, int endPageNum, int curPageNum, int allPageNum) {
        this.prev = prev;
        this.next = next;
        this.contentPerPage = contentPerPage;
        this.startPageNum = startPageNum;
        this.endPageNum = endPageNum;
        this.curPageNum = curPageNum;
        this.allPageNum = allPageNum;
    }

    /**
     * 페이지 인스턴스를 만들어 준다.
     * @param curPageNum : 요청한 현재 페이지 번호
     * @param contentPerPage : 페이지당 컨텐츠 숫자
     * @param contents : 응답에 포함된 컨텐츠
     * @return page 객체를 리턴한다.
     * @param <T> page의 컨텐츠 DTO
     */
    public static <T> Page getPage(int curPageNum, int contentPerPage, String contentType, List<T> contents) {
        int allPageNum = contents.size()/ contentPerPage;
        if(contents.size()% contentPerPage != 0){
            allPageNum+=1;
        }
        int startPageNum = curPageNum / contentPerPage * contentPerPage;
        if(startPageNum == 0) startPageNum =1;
        int endPageNum = startPageNum+ contentPerPage -1;
        if(endPageNum > contents.size()) endPageNum = contents.size();

        boolean prev = startPageNum != 1;
        boolean next = endPageNum != contents.size();

        Page page = Page.builder()
                .curPageNum(curPageNum)
                .allPageNum(allPageNum)
                .startPageNum(startPageNum)
                .endPageNum(endPageNum)
                .contentPerPage(contentPerPage)
                .prev(prev)
                .next(next)
                .build();

        page.getContents().put(contentType, contents);
        return page;
    }
}
