package com.example.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class PageBean {

    private List<User> data;//当前页的数据
    private Integer firstPage;//首页
    private Integer prePage;//上一页
    private Integer nextPage;//下一页
    private Integer totalPage;//末页、总页数
    private Integer pageNum;//当前页
    private Integer totalCount;//总记录数
    private Integer pageSize;//每页显示的记录数

    private HashMap order;
    private String desc = "DESC"; //降序

    public HashMap getOrder() {
        return order;
    }

    public void setOrder(HashMap order) {
        this.order = order;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<User> getData() {
        return data;
    }
    public void setData(List<User> data) {
        this.data = data;
    }
    public Integer getFirstPage() {
        return 1;
    }
    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }
    /**
     * 计算上一页
     * @return
     */
    public Integer getPrePage() {
        return this.getPageNum()==this.getFirstPage() ? 1 : this.getPageNum()-1;
    }
    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }
    /**
     * 计算下一页
     * @return
     */
    public Integer getNextPage() {
        return this.getPageNum()==this.getTotalPage()?
                this.getTotalPage()
                : this.getPageNum()+1;
    }
    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }
    public Integer getTotalPage() {
        return this.getTotalCount()%this.getPageSize()==0 ?
                this.getTotalCount()/this.getPageSize()
                :this.getTotalCount()/this.getPageSize()+1;
    }
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
    public Integer getPageNum() {
        return (pageNum - 1)* pageSize;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
