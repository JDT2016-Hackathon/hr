/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JPAのエンティティマネージャを操作するためのユーティリティ
 *
 * @author hiroshi.hayakawa@oracle.com
 *
 */
public class EntityManagerUtils {
    /**
     * persistence.xmlのpersistence-unitの名前
     */
    private static final String PERSISTENCE_UNIT_NAME = "HR";
    /**
     * このアプリケーションで唯一tのEntiretyManagerFactoryの参照
     *
     * このアプリケーションでは、このユーティリティクラスからEntityManagerを取得するので、この参照に直接
     * アクセスする実装を行わないこと
     */
    private static EntityManagerFactory emf = null;
    /**
     * このアプリケーションで唯一tのEntiretyManagerの参照
     *
     * このアプリケーションでは、このユーティリティクラスからEntityManagerを取得する
     */
    private static EntityManager em = null;

    /**
     * EntityManagerFactory、EntityManagerを初期化します。
     *
     * 初期化パラーメータは、環境変数に設定されたパラメータが存在する場合、それを読み込んでデフォルト値
     * を上書きします。
     */
    public static synchronized void initialize() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    /**
     * EntityMangerオブジェクトを取得します。<br>
     * EntityMangerが初期化されていない場合、初期化処理を行ったうえでオブジェクトを返します。
     *
     * @return EntityManager
     */
    public static synchronized EntityManager getEntityManager() {
        if (em == null) {
            initialize();
        }
        return em;
    }

    /**
     * EntityManagerFactory、EntityManagerをクローズします。
     */
    public static synchronized void closeAll() {
        if (em != null) {
            em.close(); em = null;
        }
        if (emf != null) {
            emf.close(); emf = null;
        }
    }

}
