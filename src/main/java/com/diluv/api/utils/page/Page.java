package com.diluv.api.utils.page;

public class Page {
    private final int page;
    private final int offset;
    private final int perPage;
    private final int totalPageCount;

    public Page(int page, int offset, int perPage, int totalPageCount) {
        this.page = page;
        this.offset = offset;
        this.perPage = perPage;
        this.totalPageCount = totalPageCount;
    }

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return offset;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
