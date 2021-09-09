import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PackageMaintainer } from "./package-maintainers.model";

@Injectable()
export class PackageMaintainersService {
  private packageMaintainersUrl = 'http://localhost:8080/api/packageMaintainers';

  constructor(private httpClient: HttpClient) {
  }

  getPackageMaintainers(maintainerID : number): Observable<PackageMaintainer[]> {
    const url = `${this.packageMaintainersUrl}/maintainerID=${maintainerID}`;
    return this.httpClient
      .get<Array<PackageMaintainer>>(url);
  }

  save(maintainerID : number, packageMaintainer : PackageMaintainer): Observable<PackageMaintainer> {
    const url = `${this.packageMaintainersUrl}/maintainerID=${maintainerID}`;
    return this.httpClient
      .post<PackageMaintainer>(url, packageMaintainer);
  }

  delete(maintainerID : number, packageID : number) : Observable<any> {
    const url = `${this.packageMaintainersUrl}/maintainerID=${maintainerID}&packageID=${packageID}`;
    return this.httpClient
      .delete(url);
  }

  update(maintainerID : number, packageMaintainer : PackageMaintainer) : Observable<any> {
    const url = `${this.packageMaintainersUrl}/maintainerID=${maintainerID}`;
    return this.httpClient
      .put<PackageMaintainer>(url, packageMaintainer);
  }



}
