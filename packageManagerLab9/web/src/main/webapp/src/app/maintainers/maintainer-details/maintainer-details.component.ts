import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { Maintainer } from '../shared/maintainer.model';
import { MaintainerService } from '../shared/maintainer.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-maintainer-details',
  templateUrl: './maintainer-details.component.html',
  styleUrls: ['./maintainer-details.component.css']
})
export class MaintainerDetailsComponent implements OnInit {

  @Input() maintainer: Maintainer;

  constructor(private maintainerService : MaintainerService,
              private route : ActivatedRoute,
              private location : Location) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.maintainerService.getMaintainer(+params['id'])))
      .subscribe(maintainer => this.maintainer = maintainer);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.maintainerService.update(this.maintainer)
      .subscribe(_ => this.goBack(),
        error => {alert("Maintainer couldn't be modified !"); this.ngOnInit() });
  }

}
