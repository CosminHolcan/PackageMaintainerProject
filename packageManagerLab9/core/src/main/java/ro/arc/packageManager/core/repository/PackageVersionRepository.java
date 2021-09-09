package ro.arc.packageManager.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.arc.packageManager.core.domain.Package;
import ro.arc.packageManager.core.domain.PackageVersion;

import java.util.List;

public interface PackageVersionRepository extends Repository<PackageVersion, Long>, PackageVersionRepositoryCustom {

    @Query("select distinct pv from PackageVersion pv")
    @EntityGraph(value = "packageVersionWithPackage", type =
            EntityGraph.EntityGraphType.LOAD)
    List<PackageVersion> findAllWithPackage();
    List<PackageVersion> findAllByAPackage(Package aPackage);
    List<PackageVersion> findAllByVersionNumberContains(String input);
    boolean existsAllByVersionNumberAndIdIsNot(String versionNumber, Long id);
    boolean existsAllByVersionNumber(String versionNumber);
    boolean existsAllByVersionNumberAndAPackageIs(String versionNumber, Package aPackage);
}