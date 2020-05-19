package tusdigital.community.community.vo;

public class QuestionQueryVo {
    private String search;
    private Integer page;
    private Integer size;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "QuestionQueryVo{" +
                "search='" + search + '\'' +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
