package com.generation_p.hotel_demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "TAG")
public class Tag extends AbstractEntity implements Serializable, Cloneable {

    @Column(name = "NAME")
    private String tagName;

    public Tag() {}

    public Tag(Long id, String tagName) {
        this.setId(id);
        this.tagName = tagName;
    }

    public boolean isPersisted() {
        return getId() != null;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag [id=" + getId() + ", tagName=" + tagName + "]";
    }

    @Override
    public Tag clone() throws CloneNotSupportedException {
        return (Tag) super.clone();
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
