package ro.arc.packageManager.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.arc.packageManager.core.domain.Maintainer;
import ro.arc.packageManager.core.domain.Package;
import ro.arc.packageManager.core.domain.PackageMaintainer;
import ro.arc.packageManager.core.domain.exceptions.AppException;
import ro.arc.packageManager.core.domain.validators.PackageMaintainerValidator;
import ro.arc.packageManager.core.repository.MaintainerRepository;
import ro.arc.packageManager.core.repository.PackageRepository;

@Service
public class PackageMaintainerServiceImpl implements PackageMaintainerService{

    @Autowired
    private PackageMaintainerValidator validator;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private MaintainerRepository maintainerRepository;

    @Override
    @Transactional
    public PackageMaintainer addPackageMaintainer(PackageMaintainer packageMaintainer) {
        validator.validate(packageMaintainer);

        Maintainer maintainer = maintainerRepository.findOneWithPackageMaintainersJPQL(packageMaintainer.getMaintainer().getId()).orElseThrow(() -> new AppException("Non existent maintainer"));

        Package aPackage = packageRepository.findOneByNameWithPackageMaintainersJPQL(packageMaintainer.getApackage().getName()).orElseThrow(() -> new AppException("Non existent package"));

        maintainer.addImportance(aPackage, packageMaintainer.getImportance());

        return packageMaintainer;
    }

    @Override
    @Transactional
    public PackageMaintainer updatePackageMaintainer(PackageMaintainer packageMaintainer) {
        validator.validate(packageMaintainer);

        Maintainer maintainer = maintainerRepository.findOneWithPackageMaintainersCriteria(packageMaintainer.getMaintainer().getId()).orElseThrow(() -> new AppException("Non existent maintainer"));

        Package aPackage = packageRepository.findOneByNameWithPackageMaintainersJPQL(packageMaintainer.getApackage().getName()).orElseThrow(() -> new AppException("Non existent package"));

        maintainer.updateImportance(aPackage.getId(), packageMaintainer.getImportance());
        aPackage.updateImportance(maintainer.getId(), packageMaintainer.getImportance());

        return packageMaintainer;

    }

    @Override
    @Transactional
    public void deletePackageMaintainer(Long maintainerID, Long packageID) {
        Maintainer maintainer = maintainerRepository.findOneWithPackageMaintainersJPQL(maintainerID).orElseThrow(() -> new AppException("Non existent maintainer"));

        Package aPackage = packageRepository.findOneWithPackageMaintainersJPQL(packageID).orElseThrow(() -> new AppException("Non existent package"));

        maintainer.deletePackage(aPackage.getId());
        aPackage.deleteMaintainer(maintainerID);
    }
}
