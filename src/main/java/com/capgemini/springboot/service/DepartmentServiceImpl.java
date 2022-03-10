package com.capgemini.springboot.service;

import com.capgemini.springboot.controller.DepartmentController;
import com.capgemini.springboot.entity.Department;
import com.capgemini.springboot.error.DepartmentNotFoundException;
import com.capgemini.springboot.repository.DepartmentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;
    
    private final Logger LOGGER =
            LoggerFactory.getLogger(DepartmentService.class);

    @Override
    public Department saveDepartment(Department department) {
    	LOGGER.info("Inside saveDepartment of DepartmentService");
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> fetchDepartmentList() {
    	LOGGER.info("Inside fetchDepartmentList of DepartmentService");
        return departmentRepository.findAll();
    }

    @Override
    public Department fetchDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        Optional<Department> department =
                departmentRepository.findById(departmentId);

        if(!department.isPresent()) {
            throw new DepartmentNotFoundException("Department Not Available");
        }

        return  department.get();
    }

    @Override
    public void deleteDepartmentById(Long departmentId) {
    	LOGGER.info("Inside deleteDepartment of DepartmentService");
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) {
    	LOGGER.info("Inside updateDepartment of DepartmentService");
        Department depDB = departmentRepository.findById(departmentId).get();

        if(Objects.nonNull(department.getDepartmentName()) &&
        !"".equalsIgnoreCase(department.getDepartmentName())) {
            depDB.setDepartmentName(department.getDepartmentName());
        }

        if(Objects.nonNull(department.getDepartmentCode()) &&
                !"".equalsIgnoreCase(department.getDepartmentCode())) {
            depDB.setDepartmentCode(department.getDepartmentCode());
        }

        if(Objects.nonNull(department.getDepartmentAddress()) &&
                !"".equalsIgnoreCase(department.getDepartmentAddress())) {
            depDB.setDepartmentAddress(department.getDepartmentAddress());
        }

        return departmentRepository.save(depDB);
    }

    @Override
    public Department fetchDepartmentByName(String departmentName) {
    	LOGGER.info("Inside fetchDepartment of DepartmentService");
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName);
    }
}