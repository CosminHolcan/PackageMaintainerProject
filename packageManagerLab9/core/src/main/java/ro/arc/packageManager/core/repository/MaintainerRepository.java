package ro.arc.packageManager.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.arc.packageManager.core.domain.Maintainer;
import ro.arc.packageManager.core.domain.PackageVersion;

import java.util.List;

public interface MaintainerRepository extends Repository<Maintainer, Long>, MaintainerRepositoryCustom {

    @Query("select distinct m from Maintainer m ")
    @EntityGraph(value = "maintainerWithPackageMaintainers", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Maintainer> findAllWithPackageMaintainers();

    List<Maintainer> findAllByEmailContains(String input);
    List<Maintainer> findAllByUserNameContains(String input);
    List<Maintainer> findAllByFullNameContains(String input);
    boolean existsAllByUserNameOrEmail(String userName, String email);
    boolean existsAllByUserNameAndIdIsNot(String userName, Long id);
    boolean existsAllByEmailAndIdIsNot(String email, Long id);
}
