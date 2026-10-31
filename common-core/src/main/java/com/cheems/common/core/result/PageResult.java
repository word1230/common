package com.cheems.common.core.result;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页查询结果。
 *
 * @param <T> 单条记录的类型
 */
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long total;

    private long pageNum;

    private long pageSize;

    private List<T> records;

    private PageResult(long total, long pageNum, long pageSize, List<T> records) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.records = records;
    }

    public static <T> PageResult<T> of(long total, long pageNum, long pageSize, List<T> records) {
        List<T> safeRecords = records == null ? Collections.emptyList() : records;
        return new PageResult<>(total, pageNum, pageSize, safeRecords);
    }

    public static <T> PageResult<T> empty(long pageNum, long pageSize) {
        return of(0, pageNum, pageSize, Collections.emptyList());
    }

    public long getTotal() {
        return total;
    }

    public long getPageNum() {
        return pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public List<T> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return "PageResult{total=" + total + ", pageNum=" + pageNum
                + ", pageSize=" + pageSize + ", recordCount=" + records.size() + '}';
    }
}
