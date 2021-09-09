package ro.arc.packageManager.core.service;

import ro.arc.packageManager.core.domain.PackageMaintainer;

public interface PackageMaintainerService {

    PackageMaintainer addPackageMaintainer(PackageMaintainer packageMaintainer);

    PackageMaintainer updatePackageMaintainer(PackageMaintainer packageMaintainer);

    void deletePackageMaintainer(Long maintainerID, Long packageID);
}
