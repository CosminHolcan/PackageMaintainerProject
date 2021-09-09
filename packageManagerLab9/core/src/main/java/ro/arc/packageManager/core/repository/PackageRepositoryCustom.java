package ro.arc.packageManager.core.repository;

import ro.arc.packageManager.core.domain.Package;

import java.util.List;
import java.util.Optional;

public interface PackageRepositoryCustom {
    Optional<Package> findOneWithPackageMaintainersJPQL(Long ID);
    List<Package> findAllWithPackageMaintainersJPQL();

    Optional<Package> findOneByNameWithPackageMaintainersJPQL(String name);

    Optional<Package> findOneWithPackageMaintainersCriteria(Long ID);
    List<Package> findAllWithPackageMaintainersCriteria();

    Optional<Package> findOneWithPackageMaintainersSQL(Long ID);
    List<Package> findAllWithPackageMaintainersSQL();
}
