package com.generation_p.hotel_demo.services;

import com.generation_p.hotel_demo.entity.HotelCategory;
import com.generation_p.hotel_demo.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TagService {

    @PersistenceContext(unitName = "hotel_demo", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(TagService.class.getName());

    public TagService() {}

    public List<Tag> findAll() {
        return entityManager.createQuery("select t from Tag t", Tag.class).getResultList();
    }

    public List<Tag> findAll(String filter, int start, int maxResults) {
        return entityManager.createQuery("select t from Tag t", Tag.class).setFirstResult(start)
                .setMaxResults(maxResults).getResultList();
    }

    @Transactional
    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    public long count() {
        return findAll().size();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Tag tag) {
        Tag rem = entityManager.find(Tag.class, tag.getId());
        entityManager.remove(rem);
        entityManager.flush();
    }

    @Transactional
    public void save(Tag entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE, "Tag is null.");
            return;
        }
        Tag tag = entityManager.merge(entry);
        entityManager.persist(tag);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void fillTestData() {
        if (findAll().isEmpty()) {
            final String[] data = new String[] { "sometag1", "sometag2", "sometag3"};

            for (String tag : data) {
                Tag t = new Tag();
                t.setTagName(tag);
                save(t);
            }
            entityManager.flush();
        }
    }

}
