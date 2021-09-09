package ro.arc.packageManager.core.repository;

import ro.arc.packageManager.core.domain.PackageVersion;
import ro.arc.packageManager.core.domain.Package;

import java.util.List;

public interface PackageVersionRepositoryCustom {
    List<PackageVersion> findAllWithPackageJPQL();
    List<PackageVersion> findAllSpecifiedPackageJPQL(Package aPackage);

    List<PackageVersion> findAllWithPackageCriteria();
    List<PackageVersion> findAllSpecifiedPackageCriteria(Package aPackage);

    List<PackageVersion> findAllWithPackageSQL();
    List<PackageVersion> findAllSpecifiedPackageSQL(Package aPackage);
}
