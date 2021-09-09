package ro.arc.packageManager.core.repository;

import ro.arc.packageManager.core.domain.Maintainer;

import java.util.List;
import java.util.Optional;

public interface MaintainerRepositoryCustom {
    Optional<Maintainer> findOneWithPackageMaintainersJPQL(Long ID);
    List<Maintainer> findAllWithPackageMaintainersJPQL();

    Optional<Maintainer> findOneWithPackageMaintainersCriteria(Long ID);
    List<Maintainer> findAllWithPackageMaintainersCriteria();

    Optional<Maintainer> findOneWithPackageMaintainersSQL(Long ID);
    List<Maintainer> findAllWithPackageMaintainersSQL();
}
