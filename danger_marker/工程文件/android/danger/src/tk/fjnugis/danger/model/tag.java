package tk.fjnugis.danger.model;

import java.util.Date;

/**
 * Created by Xiangyang on 2015/1/10.
 */
public class Tag {
    private String name;
    private int count;
    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
