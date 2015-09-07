package tk.fjnugis.dangerserver.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Component;
import tk.fjnugis.dangerserver.model.Tag;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Xiangyang on 2015/1/10.
 */
@Component("tagDao")
public class TagDaoImpl implements TagDao{
    private SessionFactory sessionFactory;
    @Override
    public void add(Tag tag) {
        getSession().save(tag);
    }

    @Override
    public void delete(String name) {
        getSession().delete(name, Tag.class);
    }

    @Override
    public void update(Tag tag) {
        getSession().update(tag);
    }

    @Override
    public Tag get(String name) {
        Tag tag= (Tag) getSession().get(Tag.class,name);
        return tag;
    }

    @Override
    public List<Tag> getList(int startIndex, int count, String orderBy,String ascOrDesc) {
        String hql="from Tag order by :orderby";
        if(orderBy==null||orderBy.equals(""))
            orderBy="name";
        if(ascOrDesc!=null&&ascOrDesc.equalsIgnoreCase("desc"))
            hql+="desc";
        Query query=getSession().createQuery(hql);
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        query.setString("orderby", orderBy);
        List<Tag> list=query.list();
        return list;
    }

    @Override
    public List<Tag> getListByName(String name, int count) {
        String hql="from Tag as tag where tag.name like :name order by tag.count";
        Query query=getSession().createQuery(hql);
        query.setString("name","%"+name+"%");
        query.setMaxResults(count);
        List<Tag> list=query.list();
        return list;
    }

    @Override
    public void updateAll() {
//        String sql="update ";
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    //--------- IoC -------------
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
