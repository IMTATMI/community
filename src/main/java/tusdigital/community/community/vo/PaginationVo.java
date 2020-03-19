package tusdigital.community.community.vo;


import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装 date 对应的页面 可作用于泛型
 */

public class PaginationVo<T> {
    private List<T> data;
    //向前按钮
    private boolean showPrevious;
    private boolean showFirstPage;

    //向后按钮
    private boolean showNext;
    private boolean showEndPage;

    //当前页
    private Integer page;

    //页面列 如 1 2 3 4 5 6 >  >>       < 2 3 4 5 6 >
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        //该怎么显示 pages  显示3个    第5页 显示  < 456 >...  第一页显示 123> 计算！！！
        // 首先 pages 是空的 先把当前页加入
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            //如果前面还有  插队到集合第一个
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            //没到最后 贪TM的
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //  如果第一页就不展示 第一页前的箭头
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        // 如果最后一页就不展示 最后的箭头
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页  就是页面的 <
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否展示最后一页  就是页面的>
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }

    //getter  setter  以下不用看

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PaginationVo{" +
                "data=" + data +
                ", showPrevious=" + showPrevious +
                ", showFirstPage=" + showFirstPage +
                ", showNext=" + showNext +
                ", showEndPage=" + showEndPage +
                ", page=" + page +
                ", pages=" + pages +
                ", totalPage=" + totalPage +
                '}';
    }
}
