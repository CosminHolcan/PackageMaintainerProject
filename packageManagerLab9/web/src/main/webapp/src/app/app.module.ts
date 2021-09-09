import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaintainersComponent } from './maintainers/maintainers.component';
import { MaintainerListComponent } from './maintainers/maintainer-list/maintainer-list.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PackagesComponent } from './packages/packages.component';
import { PackageListComponent } from './packages/package-list/package-list.component';
import { MaintainerService } from './maintainers/shared/maintainer.service';
import { PackageService } from './packages/shared/package.service';
import { MaintainerDetailsComponent } from './maintainers/maintainer-details/maintainer-details.component';
import { MaintainerNewComponent } from './maintainers/maintainer-new/maintainer-new.component';
import { PackageDetailsComponent } from './packages/package-details/package-details.component';
import { PackageNewComponent } from './packages/package-new/package-new.component';
import { PackageVersionsComponent } from './package-versions/package-versions.component';
import { PackageVersionsListComponent } from './package-versions/package-versions-list/package-versions-list.component';
import { PackageVersionsService } from './package-versions/shared/package-versions.service';
import { PackageMaintainersComponent } from './package-maintainers/package-maintainers.component';
import { PackageMaintainersService } from './package-maintainers/shared/package-maintainers.service';
import { PackageMaintainersListComponent } from './package-maintainers/package-maintainers-list/package-maintainers-list.component';

@NgModule({
  declarations: [
    AppComponent,
    MaintainersComponent,
    MaintainerListComponent,
    PackagesComponent,
    PackageListComponent,
    MaintainerDetailsComponent,
    MaintainerNewComponent,
    PackageDetailsComponent,
    PackageNewComponent,
    PackageVersionsComponent,
    PackageVersionsListComponent,
    PackageMaintainersComponent,
    PackageMaintainersListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [MaintainerService, PackageService, PackageVersionsService, PackageMaintainersService],
  bootstrap: [AppComponent]
})
export class AppModule { }
