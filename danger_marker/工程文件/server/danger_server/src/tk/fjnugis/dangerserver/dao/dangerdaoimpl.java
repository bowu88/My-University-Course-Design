package tk.fjnugis.dangerserver.dao;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import tk.fjnugis.dangerserver.model.Danger;
import tk.fjnugis.dangerserver.util.Global;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2015/1/8.
 */
@Component("dangerDao")
public class DangerDaoImpl implements DangerDao {
    private SessionFactory sessionFactory;

    @Override
    public Integer add(Danger danger) {
        Point location= Global.geometryFactory.createPoint(new Coordinate(danger.getLongitude(),danger.getLatitude()));
        danger.setLocation(location);
        Session session=sessionFactory.getCurrentSession();
        session.save(danger);
        return danger.getId();
    }

    @Override
    public Danger get(int id) {
        Danger danger;
        Session session=sessionFactory.getCurrentSession();
        danger = (Danger) session.get(Danger.class,id);
        return danger;
    }

    @Override
    public List<Danger> getAll() {
        List<Danger> list;
        Session session=sessionFactory.getCurrentSession();
        String hql="from Danger";
        Query query=session.createQuery(hql);
        list=query.list();
        return list;
    }

    @Override
    public List<Danger> getList(int startIndex, int count, Double longitude, Double latitude) {
        List<Danger> list;
        Session session=sessionFactory.getCurrentSession();
        String hql="from Danger as d order by (d.longitude - :longitude)*(d.longitude - :longitude)+(d.latitude-:latitude)*(d.latitude-:latitude)";
        Query query=session.createQuery(hql);
        query.setDouble("longitude",longitude);
        query.setDouble("latitude",latitude);
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        list=query.list();
        return list;
    }

    @Override
    public List<Map<String,Double>> getLngLatList(){
        String sql="select longitude,latitude FROM danger;";
        Query query=getSession().createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<String,Double>> getLngLatList(String category,String subCategory,String tag){
        String sql="select longitude,latitude from danger where ";
        if(category!=null&&!category.trim().equals("")){
            sql+="category=:category and ";
        }
        if(subCategory!=null&&!subCategory.trim().equals("")){
            sql+="subCategory=:subCategory and ";
        }
        if(tag!=null&&!tag.trim().equals("")){
            sql+="tags like :tag and";
        }
        sql=sql.substring(0,sql.length()-4);
        Query query=getSession().createSQLQuery(sql);
        if(category!=null&&!category.trim().equals("")){
            query.setString("category",category);
        }
        if(subCategory!=null&&!subCategory.trim().equals("")){
            query.setString("subCategory",subCategory);
        }
        if(tag!=null&&!tag.trim().equals("")){
            query.setString("tag","%"+tag+"%");
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public void update(Danger danger) {
        getSession().update(danger);
    }

    @Override
    public List<Danger> getListByTag(int startIndex, int count, Double longitude, Double latitude,String tag) {
        List<Danger> list;
        Session session=sessionFactory.getCurrentSession();
        String hql="from Danger as d where tags like :tag order by (d.longitude - :longitude)*(d.longitude - :longitude)+(d.latitude-:latitude)*(d.latitude-:latitude)";
        Query query=session.createQuery(hql);
        query.setDouble("longitude",longitude);
        query.setDouble("latitude",latitude);
        query.setString("tag","%"+tag+"%");
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        list=query.list();
        return list;
    }

    @Override
    public List<Danger> getByBoundary(double lt_longitude, double lt_latitude, double rb_longitude, double rb_latitude) {
        String wkt="Polygon(("+lt_longitude+' '+lt_latitude+','+lt_longitude+' '+rb_latitude+','+rb_longitude+' '+rb_latitude+','+rb_longitude+' '+lt_latitude+','+lt_longitude+' '+lt_latitude+"))";
//        System.out.println("------------"+wkt+"\n"+wktToGeometry(wkt).toString());
        List<Danger> list;
        Session session=sessionFactory.getCurrentSession();
        String hql="from Danger as d where MBRContains(GeomFromText('"+wkt+"'),d.location)=true ";
//        String hql="from Danger d where MBRContains(:geometry,d.location)=true";
        Query query=session.createQuery(hql);
//        query.setParameter("geometry",wktToGeometry(wkt));
        list=query.list();
        return list;
    }

    /**
     * 将wktz换为Geometry对象
     * @param wkt
     * @return
     */
    private Geometry wktToGeometry(String wkt){
        WKTReader fromText=new WKTReader();
        Geometry geometry=null;
        try{
            geometry=fromText.read(wkt);
        }catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("WKT 格式不合法");
        }
        return geometry;
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    //IoC

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
