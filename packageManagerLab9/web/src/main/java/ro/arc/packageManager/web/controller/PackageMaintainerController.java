package ro.arc.packageManager.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.arc.packageManager.core.domain.PackageMaintainer;
import ro.arc.packageManager.core.domain.exceptions.AppException;
import ro.arc.packageManager.core.service.MaintainerService;
import ro.arc.packageManager.core.service.PackageMaintainerService;
import ro.arc.packageManager.core.service.PackageService;
import ro.arc.packageManager.web.converter.PackageMaintainerConverter;
import ro.arc.packageManager.web.dto.PackageDto;
import ro.arc.packageManager.web.dto.PackageMaintainerDto;
import ro.arc.packageManager.web.dto.PackageMaintainersDto;

import java.util.Set;

@RestController
public class PackageMaintainerController {
    public static final Logger log= LoggerFactory.getLogger(ro.arc.packageManager.web.controller.PackageMaintainerController.class);

    @Autowired
    private MaintainerService maintainerService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private PackageMaintainerService packageMaintainerService;

    @Autowired
    private PackageMaintainerConverter packageMaintainerConverter;

    @RequestMapping(value = "/packageMaintainers/maintainerID={maintainerID}", method = RequestMethod.POST)
    ResponseEntity<?> addPackageMaintainer(@PathVariable Long maintainerID,
                                              @RequestBody PackageMaintainerDto packageMaintainerDto){
        log.trace("addPackageMaintainer - method entered : packageMaintainer={}", packageMaintainerDto);

        this.packageMaintainerService.addPackageMaintainer(this.packageMaintainerConverter.convertDtoToModel(packageMaintainerDto));

        log.trace("addPackageMaintainer - exit");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value="/packageMaintainers/maintainerID={maintainerID}&packageID={packageID}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePackageMaintainer(@PathVariable Long maintainerID, @PathVariable Long packageID)
    {
        log.trace("deletePackageMaintainer - method entered : maintainerID={}, packageID={}", maintainerID, packageID);

        System.out.println(maintainerID+" "+packageID);

        this.packageMaintainerService.deletePackageMaintainer(maintainerID, packageID);

        log.trace("deletePackageMaintainer - finished");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value="/packageMaintainers/maintainerID={maintainerID}", method = RequestMethod.PUT)
    ResponseEntity<?> updatePackage(@PathVariable Long maintainerID, @RequestBody PackageMaintainerDto packageMaintainerDto)
    {
        log.trace("updatePackageMaintainer -method entered : packageMaintainer={}", packageMaintainerDto);

        this.packageMaintainerService.updatePackageMaintainer(this.packageMaintainerConverter.convertDtoToModel(packageMaintainerDto));

        log.trace("updatePackage - exit");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value="/packageMaintainers/maintainerID={maintainerID}", method = RequestMethod.GET)
    Set<PackageMaintainerDto> getAllPackageMaintainers(@PathVariable Long maintainerID){
        log.trace("getAllPackageMaintainers - method entered");
        var maintainer = maintainerService.getOneMaintainer(maintainerID);
        var toReturn = packageMaintainerConverter.convertModelsToDtos(maintainer.getPackageMaintainers());
        log.trace("getAllPackageMaintainers - result={}", toReturn);
        return toReturn;
    }
}
