package dao;

import org.springframework.orm.hibernate5.HibernateTemplate;
import pojo.po.News;

import java.util.List;

public class NewsDao {

    private HibernateTemplate template;

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public void saveNews(News e) {
        template.save(e);
    }

    public void updateNews(News e) {
        template.update(e);
    }

    public void deleteNews(News e) {
        template.delete(e);
    }

    public News getById(int id) {
        News e = template.get(News.class, id);
        return e;
    }

    public List<News> getEmployees() {
        List<News> list = template.loadAll(News.class);
        return list;
    }
}
