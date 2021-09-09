package ro.arc.packageManager.core.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import ro.arc.packageManager.core.domain.*;
import ro.arc.packageManager.core.domain.Package;
import ro.arc.packageManager.core.domain.exceptions.AppException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaintainerRepositoryImpl extends CustomRepositorySupport implements MaintainerRepositoryCustom {
    @Override
    public Optional<Maintainer> findOneWithPackageMaintainersJPQL(Long ID) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select m from Maintainer m " +
                        "left join fetch m.packageMaintainers " +
                        "where m.id = "+ID.toString())
                .setMaxResults(1);
        List<Maintainer> maintainer = query.getResultList();

        if(maintainer.isEmpty())
            throw new AppException("Non existent maintainer with this id ");

        return Optional.of(maintainer.get(0));
    }

    @Override
    public List<Maintainer> findAllWithPackageMaintainersJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select m from Maintainer m " +
                        "left join fetch m.packageMaintainers ");
        List<Maintainer> maintainer = query.getResultList();

        return new ArrayList<>(maintainer);
    }

    @Override
    public Optional<Maintainer> findOneWithPackageMaintainersCriteria(Long ID) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Maintainer> query = criteriaBuilder.createQuery(Maintainer.class);
        query.distinct(Boolean.TRUE);
        Root<Maintainer> root = query.from(Maintainer.class);
        Fetch<Maintainer, PackageMaintainer> packageVersionPackageFetch = root.fetch(Maintainer_.packageMaintainers, JoinType.LEFT);

        query.where(criteriaBuilder.equal(root.get("id"), ID));
        List<Maintainer> maintainer = entityManager.createQuery(query).getResultList();

        if(maintainer.isEmpty())
            throw new AppException("Non existent maintainer with this id ");

        return Optional.of(maintainer.get(0));
    }

    @Override
    public List<Maintainer> findAllWithPackageMaintainersCriteria() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Maintainer> query = criteriaBuilder.createQuery(Maintainer.class);
        query.distinct(Boolean.TRUE);
        Root<Maintainer> root = query.from(Maintainer.class);
        Fetch<Maintainer, PackageMaintainer> packageVersionPackageFetch = root.fetch(Maintainer_.packageMaintainers, JoinType.LEFT);

        List<Maintainer> maintainer = entityManager.createQuery(query).getResultList();

        return new ArrayList<>(maintainer);
    }

    @Override
    public Optional<Maintainer> findOneWithPackageMaintainersSQL(Long ID) {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {m.*},{pm.*} " +
                "from maintainer m " +
                "left join package_maintainer pm on pm.maintainer_id = m.id "+
                "where m.id = "+ID.toString())
                .addEntity("m",Maintainer.class)
                .addJoin("pm", "m.packageMaintainers")
                .addEntity("m",Maintainer.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Maintainer> maintainer = query.getResultList();

        if(maintainer.isEmpty())
            throw new AppException("Non existent maintainer with this id ");

        return Optional.of(maintainer.get(0));
    }

    @Override
    public List<Maintainer> findAllWithPackageMaintainersSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {m.*},{pm.*} " +
                "from maintainer m " +
                "left join package_maintainer pm on pm.maintainer_id = m.id ")
                .addEntity("m",Maintainer.class)
                .addJoin("pm", "m.packageMaintainers")
                .addEntity("m",Maintainer.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Maintainer> maintainer = query.getResultList();

        return maintainer;
    }
}
