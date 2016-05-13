/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.jdt2016.hackathon.hr.model.Employee;

/**
 * 
 * @author hiroshi.hayakawa@oracle.com
 *
 */
@RestController
@RequestMapping("/hr")
public class HrResourceController {

    /**
     * EMPLOYEEテーブルの全てのエントリーのデータを取得します。
     *
     * @return EMPLOYEEテーブルの全てのエントリー
     */
    @RequestMapping(path = "/employees",
                    method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        EntityManager em = EntityManagerUtils.getEntityManager();
        @SuppressWarnings("unchecked")
        List<Employee> entities =
                em.createNamedQuery("Employee.findAll").getResultList();
        return entities;
    }

    /**
     * DEPARTMENTSテーブルの全てのエントリーのデータを取得します。
     *
     * @return DEPARTMENTSテーブルの全てのエントリー
     */
    @RequestMapping(path = "/departments",
                    method = RequestMethod.GET)
    public List<Employee> getDepartments() {
        EntityManager em = EntityManagerUtils.getEntityManager();
        @SuppressWarnings("unchecked")
        List<Employee> entities =
                em.createNamedQuery("Department.findAll").getResultList();
        return entities;
    }

}
