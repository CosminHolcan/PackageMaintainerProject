import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PackageVersion } from "./package-versions.model";

@Injectable()
export class PackageVersionsService {
  private pkgVersionsUrl = 'http://localhost:8080/api/packageVersions';

  constructor(private httpClient: HttpClient) {
  }

  getPackageVersions(packageID : number) : Observable<PackageVersion[]> {
    const url = `${this.pkgVersionsUrl}/filter/packageID=${packageID}`;
    return this.httpClient
      .get<Array<PackageVersion>>(url);
  }

  save(packageVersion): Observable<PackageVersion> {
    return this.httpClient
      .post<PackageVersion>(this.pkgVersionsUrl, packageVersion);
  }

  delete(id) : Observable<any> {
    const url = `${this.pkgVersionsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }
}
