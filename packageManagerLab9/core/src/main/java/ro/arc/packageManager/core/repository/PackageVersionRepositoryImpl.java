package ro.arc.packageManager.core.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import ro.arc.packageManager.core.domain.Package;
import ro.arc.packageManager.core.domain.PackageVersion;
import ro.arc.packageManager.core.domain.PackageVersion_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class PackageVersionRepositoryImpl extends CustomRepositorySupport
    implements PackageVersionRepositoryCustom {
    @Override
    public List<PackageVersion> findAllWithPackageJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct pv from PackageVersion pv " +
                        "left join fetch pv.aPackage ap " );
        List<PackageVersion> packageVersions = query.getResultList();

        return packageVersions;
    }

    @Override
    public List<PackageVersion> findAllSpecifiedPackageJPQL(Package aPackage) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct pv from PackageVersion pv " +
                        "left join fetch pv.aPackage ap " +
                        "where ap.id = " + aPackage.getId());
        List<PackageVersion> packageVersions = query.getResultList();

        return packageVersions;
    }

    @Override
    public List<PackageVersion> findAllWithPackageCriteria() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PackageVersion> query = criteriaBuilder.createQuery(PackageVersion.class);
        query.distinct(Boolean.TRUE);
        Root<PackageVersion> root = query.from(PackageVersion.class);
        Fetch<PackageVersion, Package> packageVersionPackageFetch = root.fetch(PackageVersion_.aPackage, JoinType.LEFT);

        List<PackageVersion> packageVersions = entityManager.createQuery(query).getResultList();

        return packageVersions;
    }

    @Override
    public List<PackageVersion> findAllSpecifiedPackageCriteria(Package aPackage) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PackageVersion> query = criteriaBuilder.createQuery(PackageVersion.class);
        query.distinct(Boolean.TRUE);
        Root<PackageVersion> root = query.from(PackageVersion.class);
        Fetch<PackageVersion, Package> packageVersionPackageFetch = root.fetch(PackageVersion_.aPackage, JoinType.LEFT);

        query.where(criteriaBuilder.equal(root.get("aPackage").get("id"), aPackage.getId()));
        List<PackageVersion> packageVersions = entityManager.createQuery(query).getResultList();

        return packageVersions;
    }

    @Override
    public List<PackageVersion> findAllWithPackageSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {pv.*},{p.*} " +
                "from packageversion pv " +
                "left join apackage p on p.id=pv.apackage_id")
                .addEntity("pv",PackageVersion.class)
                .addJoin("p", "pv.apackage")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<PackageVersion> packageVersions = query.getResultList();


        return packageVersions;
    }

    @Override
    public List<PackageVersion> findAllSpecifiedPackageSQL(Package aPackage) {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {pv.*},{p.*} " +
                "from packageversion pv " +
                "left join apackage p on p.id=pv.apackage_id "+
                "where p.id = "+aPackage.getId().toString())
                .addEntity("pv",PackageVersion.class)
                .addJoin("p", "pv.aPackage")
                .addEntity("pv",PackageVersion.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<PackageVersion> packageVersions = query.getResultList();


        return packageVersions;
    }

}
