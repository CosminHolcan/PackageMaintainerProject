package ro.arc.packageManager.core.domain.validators;

import org.springframework.stereotype.Component;
import ro.arc.packageManager.core.domain.PackageMaintainer;

@Component
public class PackageMaintainerValidator implements Validator<PackageMaintainer> {
    @Override
    public void validate(PackageMaintainer entity) throws ValidatorException {
        Validate.notNull(entity.getImportance(), "importance");

        if (entity.getImportance() < 1 || entity.getImportance() > 10)
            throw new ValidatorException("Importance should be between 1 and 10");
    }
}