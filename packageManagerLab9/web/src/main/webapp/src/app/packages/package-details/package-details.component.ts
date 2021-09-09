import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { PackageModel } from '../shared/package.model';
import { PackageService } from '../shared/package.service';
import {Location} from '@angular/common';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-package-details',
  templateUrl: './package-details.component.html',
  styleUrls: ['./package-details.component.css']
})
export class PackageDetailsComponent implements OnInit {

  @Input() package: PackageModel;

  constructor(private packageService : PackageService,
              private route : ActivatedRoute,
              private location : Location) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.packageService.getPackage(+params['id'])))
      .subscribe(packagem => this.package = packagem);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.packageService.update(this.package)
      .subscribe(_ => this.goBack(),
        error => {alert("Package couldn't be modified !"); this.ngOnInit() });
  }

}
