package com.cellbeans.hspa.mstpolicestation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPoliceStationRepository extends JpaRepository<MstPoliceStation, Long> {
    Page<MstPoliceStation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstPoliceStation> findByPoliceStationNameContainsOrPoliceStationCodeContainsOrPoliceStationContactContainsOrPoliceStationCityIdCityNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String code, String contact, String cityName, Pageable page);

   /* Page<MstPoliceStation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstPoliceStation> findByPoliceStationNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, PageRequest pageRequest); */

    List<MstPoliceStation> findByPoliceStationNameContainsAndIsActiveTrueAndIsDeletedFalse(String policeStationName);

    List<MstPoliceStation> findBypoliceStationNameContains(String key);

}
