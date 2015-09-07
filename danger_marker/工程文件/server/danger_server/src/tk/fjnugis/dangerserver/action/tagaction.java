package tk.fjnugis.dangerserver.action;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.fjnugis.dangerserver.model.Tag;
import tk.fjnugis.dangerserver.service.TagService;
import tk.fjnugis.dangerserver.util.Global;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Xiangyang on 2015/1/11.
 */
@Component
@Scope("prototype")
public class TagAction extends ActionSupport {
    private String message;
    private String json;
    private String tag;
    private int begin;
    private int count;
    private String orderBy;
    private String ascOrDesc;
    private Gson gson= Global.gson;

    private TagService tagService;

    public String getList(){
        //todo 获取标签集合
        if(count==0)
            count=50;
        List<Tag> list=tagService.getList(begin,count,orderBy,ascOrDesc);
        json=gson.toJson(list);
        return "json";
    }

    public String update(){

        return "json";
    }

    //------------- setter & getter -------------
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public TagService getTagService() {
        return tagService;
    }

    @Resource
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
