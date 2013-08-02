package com.devicehive.dao;

import com.devicehive.configuration.Constants;
import com.devicehive.exceptions.dao.HivePersistingException;
import com.devicehive.model.DeviceClass;
import com.devicehive.model.Equipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Stateless
public class EquipmentDAO {

    private static final Logger logger = LoggerFactory.getLogger(DeviceClassDAO.class);
    @PersistenceContext(unitName = Constants.PERSISTENCE_UNIT)
    private EntityManager em;

    /**
     * Inserts new record
     *
     * @param equipment Equipment instance to save
     * @Return managed instance of Equipment
     */
    public Equipment create(@NotNull Equipment equipment) {
        em.persist(equipment);
        return equipment;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Equipment> getByDeviceClass(DeviceClass deviceClass) {
        TypedQuery<Equipment> query = em.createNamedQuery("Equipment.getByDeviceClass", Equipment.class);
        query.setParameter("deviceClass", deviceClass);
        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Equipment getByDeviceClass(@NotNull Long deviceClassId, @NotNull Long equipmentId) {
        Equipment e = em.find(Equipment.class, equipmentId);
        if (e == null || !e.getDeviceClass().getId().equals(deviceClassId)) {
            return null;
        }
        return e;

    }

    public boolean update (@NotNull Equipment equipment, @NotNull Long equipmentId){
        Query query = em.createNamedQuery("Equipment.updateProperties");
        query.setParameter("id", equipmentId);
        query.setParameter("name", equipment.getName());
        query.setParameter("code", equipment.getCode());
        query.setParameter("type", equipment.getType());
        query.setParameter("data", equipment.getData());
        return query.executeUpdate() != 0;
    }

    public boolean update (@NotNull Equipment equipment, @NotNull Long equipmentId, @NotNull Long deviceClassId){
        Query query = em.createNamedQuery("Equipment.updatePropertiesUsingDeviceClass");
        query.setParameter("equipmentId", equipmentId);
        query.setParameter("deviceClassId", deviceClassId);
        query.setParameter("name", equipment.getName());
        query.setParameter("code", equipment.getCode());
        query.setParameter("type", equipment.getType());
        query.setParameter("data", equipment.getData());
        return query.executeUpdate() != 0;
    }

    public boolean delete(@NotNull Long equipmentId){
        Query query =  em.createNamedQuery("Equipment.deleteById");
        query.setParameter("id", equipmentId);
        return query.executeUpdate() != 0;
    }

    public boolean delete(@NotNull Long equipmentId, @NotNull Long deviceClassId){
        Query query =  em.createNamedQuery("Equipment.deleteByIdAndDeviceClass");
        query.setParameter("id", equipmentId);
        query.setParameter("deviceClassId", deviceClassId);
        return query.executeUpdate() != 0;
    }

    /**
     * @param id Equipment Id
     * @returns Equipment
     */
    public Equipment get(@NotNull Long id) {
        return em.find(Equipment.class, id);
    }

    /**
     * @param equipments equipments to remove
     * @return
     */
    public int delete(Collection<Equipment> equipments) {
        Query query = em.createNamedQuery("Equipment.deleteByEquipmentList");
        query.setParameter("equipmentList", equipments);
        return query.executeUpdate();
    }
}