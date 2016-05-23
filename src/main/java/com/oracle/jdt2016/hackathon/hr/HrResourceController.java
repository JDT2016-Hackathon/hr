/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.jdt2016.hackathon.hr.model.Employee;
import com.oracle.jdt2016.hackathon.hr.model.Job;

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
        /*
         * 以下のコードのコメントアウトを解除すると、返り値に新しい
         * エントリーが追加されるようになります。
         * これにより、画面に表示されるグラフの形状が変わることを確認
         * してください。
         */
        Employee rookie = new Employee();
        rookie.setEmployeeId(999);
        rookie.setFirstName("Duke");
        rookie.setLastName("Java");
        rookie.setSalary(BigDecimal.valueOf(99999999));
        @SuppressWarnings("unchecked")
        List<Job> jobs =
            em.createNamedQuery("Job.findAll").getResultList();
        rookie.setJob(jobs.get(0));
        entities.add(rookie);
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
