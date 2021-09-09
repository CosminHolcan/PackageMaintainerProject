import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Maintainer} from "./maintainer.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";


@Injectable()
export class MaintainerService {
  private maintainersUrl = 'http://localhost:8080/api/maintainers';

  constructor(private httpClient: HttpClient) {
  }

  getMaintainers(): Observable<Maintainer[]> {
    return this.httpClient
      .get<Array<Maintainer>>(this.maintainersUrl);
  }

  getFilteredMaintainers(type : string, input : string): Observable<Maintainer[]> {
    const url = `${this.maintainersUrl}/filter/type=${type}&input=${input}`;
    return this.httpClient
      .get<Array<Maintainer>>(url);
  }

  getMaintainer(id: number): Observable<Maintainer> {
    return this.getMaintainers()
      .pipe(
        map(maintainers => maintainers.find(maintainer => maintainer.id === id))
      );
  }

  update(maintainer): Observable<Maintainer> {
    const url = `${this.maintainersUrl}/${maintainer.id}`;
    return this.httpClient
      .put<Maintainer>(url, maintainer);
  }

  save(maintainer): Observable<Maintainer> {
    return this.httpClient
      .post<Maintainer>(this.maintainersUrl, maintainer);
  }

  delete(id) : Observable<any> {
    const url = `${this.maintainersUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }



}
