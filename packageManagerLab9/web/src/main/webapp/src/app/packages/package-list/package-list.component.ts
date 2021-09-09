import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PackageModel } from '../shared/package.model';
import { PackageService } from '../shared/package.service';

@Component({
  selector: 'app-package-list',
  templateUrl: './package-list.component.html',
  styleUrls: ['./package-list.component.css']
})
export class PackageListComponent implements OnInit {
  packages : PackageModel[];
  selectedPackage : PackageModel;

  constructor(private packageService : PackageService,
              private router : Router) { }

  ngOnInit(): void {
    this.getPackages();
  }

  getPackages() {
    this.packageService.getPackages()
      .subscribe(
        packages => this.packages = packages
      );
  }

  getFilteredPackages(type : string, input : string) {
    switch (type) {
      case 'name' :
        this.packages = this.packages.filter(pkg => pkg.name.includes(input));
        break;
      case 'description' :
        this.packages = this.packages.filter(pkg => pkg.description.includes(input));
        break;
      case 'sourceRepo' :
        this.packages = this.packages.filter(pkg => pkg.sourceRepo.includes(input));
        break;
      case 'license' :
        this.packages = this.packages.filter(pkg => pkg.license.includes(input));
        break;
    }
  }

  sortPackages(type : string, order : string) {
    switch (type) {
      case 'name' :
        if (order == "ascending")
          this.packages.sort((p1,p2) => (p1.name > p2.name) ? 1 : ((p2.name > p1.name) ? -1 : 0));
        else
          this.packages.sort((p1,p2) => (p1.name > p2.name) ? -1 : ((p2.name > p1.name) ? 1 : 0));
        break;
      case 'description' :
        if (order == "ascending")
          this.packages.sort((p1,p2) => (p1.description > p2.description) ? 1 : ((p2.description > p1.description) ? -1 : 0));
        else
          this.packages.sort((p1,p2) => (p1.description > p2.description) ? -1 : ((p2.description > p1.description) ? 1 : 0));
        break;
      case 'sourceRepo' :
        if (order == "ascending")
          this.packages.sort((p1,p2) => (p1.sourceRepo > p2.sourceRepo) ? 1 : ((p2.sourceRepo > p1.sourceRepo) ? -1 : 0));
        else
          this.packages.sort((p1,p2) => (p1.sourceRepo > p2.sourceRepo) ? -1 : ((p2.sourceRepo > p1.sourceRepo) ? 1 : 0));
        break;
      case 'license' :
        if (order == "ascending")
          this.packages.sort((p1,p2) => (p1.license > p2.license) ? 1 : ((p2.license > p1.license) ? -1 : 0));
        else
          this.packages.sort((p1,p2) => (p1.license > p2.license) ? -1 : ((p2.license > p1.license) ? 1 : 0));
        break;
    }
  }

  onSelect(packagem : PackageModel): void {
    if (this.selectedPackage == packagem)
      this.selectedPackage = null;
    else
      this.selectedPackage = packagem;
  }

  gotoDetail(): void {
    this.router.navigate(['/package/detail', this.selectedPackage.id]);
  }

  delete(): void{
    this.packageService.delete(this.selectedPackage.id)
      .subscribe(_ => { this.ngOnInit(); this.selectedPackage = null;});
  }

  goToAdd(): void{
    this.router.navigate(['/package/add']);
  }

}
