import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MaintainerDetailsComponent } from './maintainers/maintainer-details/maintainer-details.component';
import { MaintainerNewComponent } from './maintainers/maintainer-new/maintainer-new.component';
import { MaintainersComponent } from './maintainers/maintainers.component';
import { PackageDetailsComponent } from './packages/package-details/package-details.component';
import { PackageNewComponent } from './packages/package-new/package-new.component';
import { PackagesComponent } from './packages/packages.component';

const routes: Routes = [

  {path: 'maintainers', component : MaintainersComponent},
  {path: 'maintainer/detail/:id', component: MaintainerDetailsComponent},
  {path: 'maintainer/add', component: MaintainerNewComponent},

  {path: 'packages', component : PackagesComponent},
  {path: 'package/detail/:id', component: PackageDetailsComponent},
  {path: 'package/add', component: PackageNewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
