package com.generation_p.hotel_demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "HOTEL_ID_TAG_ID")
public class HotelTagLink extends AbstractEntity implements Serializable, Cloneable {

    @Column(name = "HOTEL_ID")
    private Integer hotelId;

    @Column(name = "TAG_ID")
    private Integer tagId;

    public HotelTagLink() {}


    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Tag))
            return false;

        Tag that = (Tag) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
