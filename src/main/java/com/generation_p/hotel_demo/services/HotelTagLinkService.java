package com.generation_p.hotel_demo.services;

import com.generation_p.hotel_demo.entity.HotelTagLink;
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
public class HotelTagLinkService {

    @PersistenceContext(unitName = "hotel_demo", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;


    public HotelTagLinkService() {}



    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(HotelTagLink hotelTagLink) {
        HotelTagLink rem = entityManager.find(HotelTagLink.class, hotelTagLink.getId());
        entityManager.remove(rem);
        entityManager.flush();
    }

    @Transactional
    public void save(HotelTagLink entry) {
        if (entry == null) {
            //LOGGER.log(Level.SEVERE, "Tag is null.");
            return;
        }
        HotelTagLink htl = entityManager.merge(entry);
        entityManager.persist(htl);
    }



}
