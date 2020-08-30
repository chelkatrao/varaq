package org.chelkatrao.varaq.repository;

import org.chelkatrao.varaq.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String system);

    Department findByCode(String DepartmentCode);

    @Modifying // o'zgartirish
    @Override
    @Query("update Department c set c.isDeleted = 1 where c.id = :companyId")
    void deleteById(@Param("companyId") Long id);

    @Modifying // o'zgartirish
    @Override
    @Query("select c from Department c where c.isDeleted = 0")
    List<Department> findAll();

}
