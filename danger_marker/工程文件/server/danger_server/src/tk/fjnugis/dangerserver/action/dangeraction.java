package tk.fjnugis.dangerserver.action;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.model.Danger;
import tk.fjnugis.dangerserver.service.DangerService;
import tk.fjnugis.dangerserver.service.TagService;
import tk.fjnugis.dangerserver.util.Global;
import tk.fjnugis.dangerserver.util.ModelHelper;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2014/11/19.
 */
@Component
@Scope("prototype")
public class DangerAction extends ActionSupport {
    private Danger danger;
    private DangerService dangerService;
    private TagService tagService;
    private Gson gson = Global.gson;
    private String message;
    private String json;
    private int startIndex; //从第几条开始
    private int count;      //获取条数
    private String category;
    private String subCategory;
    private String tag;
    private Logger logger = Logger.getLogger(DangerAction.class);
    private double lt_longitude;
    private double lt_latitude;
    private double rb_longitude;
    private double rb_latitude;


    //上传文件
    private File[] upload;
    private String[] uploadFileName;
    private String[] uploadContentType;

    /**
     * 添加安全隐患信息
     */
    public String add() {
        if(danger==null){
            message="数据为空，上传失败";
            return "message";
        }
        try {
            Integer id=dangerService.add(this.danger);
            logger.debug("上传危险信息：" + danger.toString());
            //更新标签库
            if(danger.getTags()!=null) {
                String[] tagArr = danger.getTags().split(" ");
                for (int i = 0; i < tagArr.length; i++) {
                    if(tagService.has(tagArr[i])){
                        tagService.increase(tagArr[i]);
                    }else{
                        tagService.create(tagArr[i]);
                    }
                }
            }
            message = id+"";
        } catch (Exception e) {
            e.printStackTrace();
            message = "服务器忙，上传失败";
        }
        return "message";
    }

    /**
     * 上传安全隐患信息的图片
     */
    public String uploadPicture() throws IOException {
        //检查id是否正确
        if(danger==null||!dangerService.has(danger.getId())){
            message="id无效，上传失败";
            return "message";
        }
        //上传文件
        String savePath = ServletActionContext.getServletContext()
                .getRealPath("/WEB-INF/upload/picture");
        String picture_url_arr[]=new String[this.upload.length];
        for (int i = 0; upload == null || i < upload.length; i++) {
            String saveName = danger.getId() + "_" + (i+1)+"_"+uploadFileName[i];
            File savefile = new File(savePath, saveName);
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            FileUtils.copyFile(upload[i], savefile);
            //更新pictures字段
            picture_url_arr[i]=saveName;
        }
        String pictures= ModelHelper.getInstance().joinString(picture_url_arr," ");
        dangerService.addPicture(danger.getId(),pictures);
        message="上传成功";
        return "message";
    }

    /**
     * 获取安全隐患信息 by id
     */
    public String get() {
        danger = dangerService.get(danger.getId());
        json = gson.toJson(danger);
        return "json";
    }

    /**
     * 获取全部安全隐患信息
     */
    public String getAll() {
        List<Danger> dangerList = dangerService.getAll();
        json = gson.toJson(dangerList);
        return "json";
    }

    /**
     * 获取List
     * @return
     */
    public String getList() {
        if(count==0)
            count=10;
        //根据距离
        List<Danger> dangerList = dangerService.getList(startIndex, count, danger.getLongitude(), danger.getLatitude());
        json = gson.toJson(dangerList);
        return "json";
    }

    /**
     * 根据范围获取
     * @return
     */
    public String getByBoundary(){
        List<Danger> list=dangerService.getByBoundary(lt_longitude,lt_latitude,rb_longitude,rb_latitude);
        json=gson.toJson(list);
        return "json";
    }

    /**
     * 根据标签名获取danger集合
     */
    public String getListByTag(){
        if(count==0)
            count=10;
        List<Danger> dangerList=dangerService.getListByTag(startIndex, count, danger.getLongitude(), danger.getLatitude(),tag);
        json=gson.toJson(dangerList);
        return "json";
    }

    /**
     * 获取只包含坐标的list
     */
    public String getLngLatList(){
        List<Map<String ,Double>> list=dangerService.getLngLatList(category,subCategory,tag);
        json=gson.toJson(list);
        return "json";
    }

    //getter and setter
    public Danger getDanger() {
        return danger;
    }

    public void setDanger(Danger danger) {
        this.danger = danger;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public File[] getUpload() {
        return upload;
    }

    public void setUpload(File[] upload) {
        this.upload = upload;
    }

    public String[] getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String[] uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String[] getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String[] uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public TagService getTagService() {
        return tagService;
    }

    public DangerService getDangerService() {
        return dangerService;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getLt_longitude() {
        return lt_longitude;
    }

    public void setLt_longitude(double lt_longitude) {
        this.lt_longitude = lt_longitude;
    }

    public double getLt_latitude() {
        return lt_latitude;
    }

    public void setLt_latitude(double lt_latitude) {
        this.lt_latitude = lt_latitude;
    }

    public double getRb_longitude() {
        return rb_longitude;
    }

    public void setRb_longitude(double rb_longitude) {
        this.rb_longitude = rb_longitude;
    }

    public double getRb_latitude() {
        return rb_latitude;
    }

    public void setRb_latitude(double rb_latitude) {
        this.rb_latitude = rb_latitude;
    }

    @Resource(name = "DangerService")
    public void setDangerService(DangerService dangerService) {
        this.dangerService = dangerService;
    }

    @Resource
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
