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

public class PackageRepositoryImpl extends CustomRepositorySupport implements PackageRepositoryCustom{
    @Override
    public Optional<Package> findOneWithPackageMaintainersJPQL(Long ID) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select p from Package p " +
                        "left join fetch p.packageMaintainers " +
                        "where p.id = "+ID.toString())
                .setMaxResults(1);
        List<Package> packages = query.getResultList();

        if(packages.isEmpty())
            throw new AppException("Non existent package with this id ");

        return Optional.of(packages.get(0));
    }

    @Override
    public List<Package> findAllWithPackageMaintainersJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select p from Package p " +
                        "left join fetch p.packageMaintainers ");
        List<Package> packages = query.getResultList();

        return new ArrayList<>(packages);
    }

    @Override
    public Optional<Package> findOneByNameWithPackageMaintainersJPQL(String name) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select p from Package p " +
                        "left join fetch p.packageMaintainers " +
                        "where p.name = '"+name+"'")
                .setMaxResults(1);
        List<Package> packages = query.getResultList();

        if(packages.isEmpty())
            throw new AppException("Non existent package with this id ");

        return Optional.of(packages.get(0));
    }

    @Override
    public Optional<Package> findOneWithPackageMaintainersCriteria(Long ID) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Package> query = criteriaBuilder.createQuery(Package.class);
        query.distinct(Boolean.TRUE);
        Root<Package> root = query.from(Package.class);
        Fetch<Package, PackageMaintainer> packagePackageMaintainerFetch = root.fetch(Package_.packageMaintainers, JoinType.LEFT);

        query.where(criteriaBuilder.equal(root.get("id"), ID));
        List<Package> packages = entityManager.createQuery(query).getResultList();

        if(packages.isEmpty())
            throw new AppException("Non existent package with this id ");

        return Optional.of(packages.get(0));
    }

    @Override
    public List<Package> findAllWithPackageMaintainersCriteria() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Package> query = criteriaBuilder.createQuery(Package.class);
        query.distinct(Boolean.TRUE);
        Root<Package> root = query.from(Package.class);
        Fetch<Package, PackageMaintainer> packagePackageMaintainerFetch = root.fetch(Package_.packageMaintainers, JoinType.LEFT);

        List<Package> packages = entityManager.createQuery(query).getResultList();

        return new ArrayList<>(packages);
    }

    @Override
    public Optional<Package> findOneWithPackageMaintainersSQL(Long ID) {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {p.*},{pm.*} " +
                "from apackage p " +
                "left join package_maintainer pm on pm.package_id = p.id "+
                "where p.id = "+ID.toString())
                .addEntity("p",Package.class)
                .addJoin("pm", "p.packageMaintainers")
                .addEntity("p",Package.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Package> packages = query.getResultList();

        if(packages.isEmpty())
            throw new AppException("Non existent package with this id ");

        return Optional.of(packages.get(0));
    }

    @Override
    public List<Package> findAllWithPackageMaintainersSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {p.*},{pm.*} " +
                "from apackage p " +
                "left join package_maintainer pm on pm.package_id = p.id ")
                .addEntity("p",Package.class)
                .addJoin("pm", "p.packageMaintainers")
                .addEntity("p",Package.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Package> packages = query.getResultList();


        return new ArrayList<>(packages);
    }
}
