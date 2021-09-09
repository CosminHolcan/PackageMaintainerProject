import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, Params, Router } from '@angular/router';
import { PackageMaintainer } from '../shared/package-maintainers.model';
import { PackageMaintainersService } from '../shared/package-maintainers.service';

@Component({
  selector: 'app-package-maintainers-list',
  templateUrl: './package-maintainers-list.component.html',
  styleUrls: ['./package-maintainers-list.component.css']
})
export class PackageMaintainersListComponent implements OnInit {
  @ViewChild('packageName') packageName: ElementRef;
  @ViewChild('importance') importance: ElementRef;

  packageMaintainers : Array<PackageMaintainer>;
  selectedPkgMaintainer : PackageMaintainer;

  @Input() maintainerID : number;

  constructor(private packageMaintainersService : PackageMaintainersService,
              private router : Router,
              private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params
      .subscribe((params: Params) => {
          this.maintainerID = +params["id"];
          console.log(this.maintainerID);
        }
      );
    this.getPackageMaintainers();
  }
  getPackageMaintainers() {
    this.packageMaintainersService.getPackageMaintainers(this.maintainerID)
      .subscribe(
        packageMaintainers => this.packageMaintainers = packageMaintainers
      );
  }

  onSelect(packageMaintainer : PackageMaintainer): void {
    if (this.selectedPkgMaintainer == packageMaintainer)
      this.selectedPkgMaintainer = null;
    else
      this.selectedPkgMaintainer = packageMaintainer;
  }

  save(packageName : string, importanceString : string): void {
    var importance : number = +importanceString;
    const maintainerID = this.maintainerID;
    const packageMaintainer: PackageMaintainer = <PackageMaintainer>{packageName, importance, maintainerID};
    this.packageMaintainersService.save(this.maintainerID, packageMaintainer)
      .subscribe(_ => this.ngOnInit(),
        error => alert("This package maintainer couldn't be added "));
    this.packageName.nativeElement.value = '';
    this.importance.nativeElement.value = '';
    this.ngOnInit();
  }

  delete(): void{
    this.packageMaintainersService.delete(this.maintainerID, this.selectedPkgMaintainer.packageID)
      .subscribe(_ => { this.ngOnInit(); this.selectedPkgMaintainer = null;});
  }

  update(newImportance : string) : void{
    const maintainerID = this.maintainerID;
    const packageName = this.selectedPkgMaintainer.packageName;
    const packageID = this.selectedPkgMaintainer.packageID;
    var importance : number = +newImportance;
    const packageMaintainer: PackageMaintainer = <PackageMaintainer>{packageName, importance, maintainerID, packageID};
    this.packageMaintainersService.update(maintainerID, packageMaintainer)
      .subscribe(_ => { this.ngOnInit(); this.selectedPkgMaintainer = null;},
        error => alert("New importance should be between 1 and 10"));
  }

}
