package tk.fjnugis.dangerserver.model;

import com.google.gson.annotations.Expose;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DANGER")
public class Danger {
    private Integer id;
    private String name;
    private String category;    //一级类别
    private String subCategory; //二级类别
    private String tags;        //标签，使用空格分隔
    private Float rank;         //等级
    private String description;  //描述
    private String pictures;    //图片url地址，使用空格分隔
    private String remark;      //备注
    //位置
    private transient Point location;     //空间数据模型
    private Double longitude;   //经度
    private Double latitude;    //纬度
    private Float accuracy;    //精确度
    private String province;    //省
    private String city;        //市
    private String district;    //县
    private String street;      //街道
    private String addressDescritpion;//位置详细描述
    //附加信息
    private Date date;          //上传时间
    private Boolean solved;     //是否已解决
    private Integer helped;     //多少人认为有帮助
    private Integer numSolved;  //多少人认为问题已解决
    //用户
    @Expose
    private String username;    //上传用户名
    @Expose
    private String userid;      //上传用户id

    @Override
    public String toString() {
        return "Danger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", tags='" + tags + '\'' +
                ", rank=" + rank +
                ", description='" + description + '\'' +
                ", pictures='" + pictures + '\'' +
                ", remark='" + remark + '\'' +
                ", location=" + location +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", accuracy=" + accuracy +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", addressDescritpion='" + addressDescritpion + '\'' +
                ", date=" + date +
                ", solved=" + solved +
                ", helped=" + helped +
                ", numSolved=" + numSolved +
                ", username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public Float getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public String getRemark() {
        return remark;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getSolved() {
        return solved;
    }

    public Integer getHelped() {
        return helped;
    }

    public Integer getNumSolved() {
        return numSolved;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getTags() {
        return tags;
    }

    public String getPictures() {
        return pictures;
    }

    @Type(type = "org.hibernatespatial.GeometryUserType")
    public Point getLocation() {
        return location;
    }

    @Transient
    public String getUsername() {
        return username;
    }

    @Transient
    public String getUserid() {
        return userid;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddressDescritpion() {
        return addressDescritpion;
    }

    public void setAddressDescritpion(String addressDescritpion) {
        this.addressDescritpion = addressDescritpion;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public void setHelped(Integer helped) {
        this.helped = helped;
    }

    public void setNumSolved(Integer numSolved) {
        this.numSolved = numSolved;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
