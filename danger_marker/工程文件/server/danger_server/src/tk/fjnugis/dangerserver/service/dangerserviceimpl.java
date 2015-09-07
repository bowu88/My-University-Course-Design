package tk.fjnugis.dangerserver.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.dao.DangerDao;
import tk.fjnugis.dangerserver.model.Danger;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2014/11/20.
 */
@Component("DangerService")
public class DangerServiceImpl implements DangerService{
    private DangerDao dangerDao;
    private Logger logger=Logger.getLogger(DangerService.class);

    @Transactional
    @Override
    public Integer add(Danger danger) {
        logger.info("服务:添加记录：\n" + danger.toString());
        return dangerDao.add(danger);
    }

    @Transactional
    @Override
    public Danger get(int id){
        Danger danger=dangerDao.get(id);
        logger.debug("调用 get(String id),获取到:\n"+danger.toString());
        return danger;
    }

    @Transactional
    @Override
    public boolean has(int id) {
        Danger danger=dangerDao.get(id);
        if(danger!=null)
            return true;
        return false;
    }

    @Transactional
    @Override
    public List<Danger> getAll() {
        logger.debug("调用getAll()");
        List<Danger> list=dangerDao.getAll();
        return list;
    }

    @Transactional
    @Override
    public List<Danger> getList(int startIndex, int count, Double longitude, Double latitude ) {
        List<Danger> list=dangerDao.getList(startIndex,count,longitude,latitude);
        logger.debug("调用 getList,\n起始索引："+startIndex+"\n获取个数："+count+"\n坐标："+longitude+","+latitude);
        return list;
    }

    @Transactional
    @Override
    public List<Map<String, Double>> getLngLatList() {
        return dangerDao.getLngLatList();
    }

    @Transactional
    @Override
    public List<Map<String, Double>> getLngLatList(String category, String subCategory, String tag) {
        return dangerDao.getLngLatList(category,subCategory,tag);
    }

    @Transactional
    @Override
    public void addPicture(Integer id, String pictures) {
        Danger danger=dangerDao.get(id);
        danger.setPictures(pictures);
        dangerDao.update(danger);
    }
    @Transactional
    @Override
    public List<Danger> getListByTag(int startIndex, int count, Double longitude, Double latitude,String tag) {
         return dangerDao.getListByTag(startIndex,count,longitude,latitude,tag);
    }

    @Transactional
    @Override
    public List<Danger> getByBoundary(double lt_longitude, double lt_latitude, double rb_longitude, double rb_latitude) {
        return dangerDao.getByBoundary(lt_longitude,lt_latitude,rb_longitude,rb_latitude);
    }

    //-------getter & setter-----
    @Resource
    public void setDangerDao(DangerDao dangerDao) {
        this.dangerDao = dangerDao;
    }
}
