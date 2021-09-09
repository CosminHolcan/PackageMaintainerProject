import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PackageService } from '../shared/package.service';
import {Location} from '@angular/common';
import { PackageModel } from '../shared/package.model';

@Component({
  selector: 'app-package-new',
  templateUrl: './package-new.component.html',
  styleUrls: ['./package-new.component.css']
})
export class PackageNewComponent implements OnInit {

  constructor(private packageService : PackageService,
              private route : ActivatedRoute,
              private location : Location) { }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  save(name : string, description : string, sourceRepo : string, license : string): void {
    const pkg: PackageModel = <PackageModel>{name, description, sourceRepo, license};
    this.packageService.save(pkg)
      .subscribe(_ => this.goBack(),
        error => {alert("This package could not be added !"); this.ngOnInit() });
  }

}
