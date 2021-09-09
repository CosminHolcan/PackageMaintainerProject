import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";


import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import { PackageModel } from './package.model';


@Injectable()
export class PackageService {
  private packagesUrl = 'http://localhost:8080/api/packages';

  constructor(private httpClient: HttpClient) {
  }

  getPackages(): Observable<PackageModel[]> {
    return this.httpClient
      .get<Array<PackageModel>>(this.packagesUrl);
  }

  getPackage(id: number): Observable<PackageModel> {
    return this.getPackages()
      .pipe(
        map(packages => packages.find(pkg => pkg.id === id))
      );
  }

  update(pkg): Observable<PackageModel> {
    const url = `${this.packagesUrl}/${pkg.id}`;
    return this.httpClient
      .put<PackageModel>(url, pkg);
  }

  save(pkg): Observable<PackageModel> {
    return this.httpClient
      .post<PackageModel>(this.packagesUrl, pkg);
  }

  delete(id) : Observable<any> {
    const url = `${this.packagesUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

}
