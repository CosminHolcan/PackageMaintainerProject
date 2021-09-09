package ro.arc.packageManager.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.arc.packageManager.core.domain.Maintainer;
import ro.arc.packageManager.core.domain.Package;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends Repository<Package, Long>, PackageRepositoryCustom {

    @Query("select distinct p from Package p ")
    @EntityGraph(value = "maintainerWithPackageMaintainers", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Package> findAllWithPackageMaintainers();

    List<Package> findAllByDescriptionContains(String input);
    List<Package> findAllBySourceRepoContains(String input);
    List<Package> findAllByLicenseContains(String input);
    List<Package> findAllByNameContains(String input);

    boolean existsAllByName(String name);
    boolean existsAllByNameAndIdIsNot(String name, Long id);
    Optional<Package> findOneByName(String name);
}
