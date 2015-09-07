package tk.fjnugis.danger.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xiangyang on 2014/11/24.
 */
public class Danger implements Serializable {
    public Integer id;
    public String name;
    public String category;    //一级类别
    public String subCategory; //二级类别
    public String tags;
    public Float rank;         //等级
    public String description;  //描述
    public String pictures;
    public String remark;      //备注
    //位置
    public Double longitude;   //经度
    public Double latitude;    //纬度
    public Float accuracy;    //精确度
    public String province;    //省
    public String city;        //市
    public String district;    //县
    public String street;      //街道
    public String addressDescritpion;//位置详细描述
    //附加信息
    public Date date;          //上传时间
    public Boolean solved;     //是否已解决
    public Integer helped;     //多少人认为有帮助
    public Integer numSolved;  //多少人认为问题已解决
    //用户
    public String username;    //上传用户名
    public String userid;      //上传用户id

    @Override
    public String toString() {
        return "Danger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", rank=" + rank +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", accuracy=" + accuracy +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", addressDescritpion='" + addressDescritpion + '\'' +
                ", date=" + date +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressDescritpion() {
        return addressDescritpion;
    }

    public void setAddressDescritpion(String addressDescritpion) {
        this.addressDescritpion = addressDescritpion;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Integer getHelped() {
        return helped;
    }

    public void setHelped(Integer helped) {
        this.helped = helped;
    }

    public Integer getNumSolved() {
        return numSolved;
    }

    public void setNumSolved(Integer numSolved) {
        this.numSolved = numSolved;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Danger)) return false;

        Danger danger = (Danger) o;

        if (accuracy != null ? !accuracy.equals(danger.accuracy) : danger.accuracy != null) return false;
        if (addressDescritpion != null ? !addressDescritpion.equals(danger.addressDescritpion) : danger.addressDescritpion != null)
            return false;
        if (category != null ? !category.equals(danger.category) : danger.category != null) return false;
        if (city != null ? !city.equals(danger.city) : danger.city != null) return false;
        if (date != null ? !date.equals(danger.date) : danger.date != null) return false;
        if (description != null ? !description.equals(danger.description) : danger.description != null) return false;
        if (district != null ? !district.equals(danger.district) : danger.district != null) return false;
        if (id != null ? !id.equals(danger.id) : danger.id != null) return false;
        if (latitude != null ? !latitude.equals(danger.latitude) : danger.latitude != null) return false;
        if (longitude != null ? !longitude.equals(danger.longitude) : danger.longitude != null) return false;
        if (name != null ? !name.equals(danger.name) : danger.name != null) return false;
        if (pictures != null ? !pictures.equals(danger.pictures) : danger.pictures != null) return false;
        if (province != null ? !province.equals(danger.province) : danger.province != null) return false;
        if (rank != null ? !rank.equals(danger.rank) : danger.rank != null) return false;
        if (remark != null ? !remark.equals(danger.remark) : danger.remark != null) return false;
        if (street != null ? !street.equals(danger.street) : danger.street != null) return false;
        if (subCategory != null ? !subCategory.equals(danger.subCategory) : danger.subCategory != null) return false;
        if (tags != null ? !tags.equals(danger.tags) : danger.tags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (subCategory != null ? subCategory.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pictures != null ? pictures.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (addressDescritpion != null ? addressDescritpion.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
