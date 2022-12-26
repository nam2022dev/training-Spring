package com.codede.spring.dao;

import com.codede.spring.GenericDAO;
import com.codede.spring.entity.Department;
import com.codede.spring.sql.QuerySql;
import com.opengamma.elsql.ElSql;
import com.opengamma.elsql.ElSqlConfig;
import org.springframework.orm.jpa.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DepartmentDAO implements GenericDAO<Department, Long>  {
    protected EntityManager getEntityManager() {
        return EntityManagerHolder.get();
    }
    ElSql bundle = ElSql.of(ElSqlConfig.POSTGRES, QuerySql.class);

    public void save(Object o) {
        try {
            Query query = getEntityManager().createNativeQuery(
                    bundle.getSql(QuerySql.SAVE_DEPARTMENT), SocPollVoteEntity.class);
            query.setParameter(POLL_ID, pollId);
            query.setParameter(USER_ID, userId);
        }
    }
}
