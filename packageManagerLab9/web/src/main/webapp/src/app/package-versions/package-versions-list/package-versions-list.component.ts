import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, Params, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { PackageVersion } from '../shared/package-versions.model';
import { PackageVersionsService } from '../shared/package-versions.service';

@Component({
  selector: 'app-package-versions-list',
  templateUrl: './package-versions-list.component.html',
  styleUrls: ['./package-versions-list.component.css']
})
export class PackageVersionsListComponent implements OnInit {
  @ViewChild('versionNumber') versionNumber: ElementRef;
  @ViewChild('startingDate') startingDate: ElementRef;

  packageVersions : Array<PackageVersion>;
  selectedPkgVersion : PackageVersion;

  @Input() packageID : number;

  constructor(private packageVersionsService : PackageVersionsService,
              private router : Router,
              private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params
      .subscribe((params: Params) => {
          this.packageID = +params["id"];
          console.log(this.packageID);
        }
      );
    this.getPackageVersions();
  }
  getPackageVersions() {
    this.packageVersionsService.getPackageVersions(this.packageID)
      .subscribe(
        packageVersions => this.packageVersions = packageVersions
      );
  }

  onSelect(packageVersions : PackageVersion): void {
    if (this.selectedPkgVersion == packageVersions)
      this.selectedPkgVersion = null;
    else
      this.selectedPkgVersion = packageVersions;
  }

  save(versionNumber : string, startingDate : string): void {
    const packageID = this.packageID;
    const packageVersion: PackageVersion = <PackageVersion>{versionNumber, startingDate, packageID};
    this.packageVersionsService.save(packageVersion)
      .subscribe(_ => this.ngOnInit(),
        error => {alert("This package version could not be added !"); });
    this.startingDate.nativeElement.value = '';
    this.versionNumber.nativeElement.value = '';
    this.ngOnInit();
  }

  delete(): void{
    this.packageVersionsService.delete(this.selectedPkgVersion.id)
      .subscribe(_ => { this.ngOnInit(); this.selectedPkgVersion = null;});
  }


}

