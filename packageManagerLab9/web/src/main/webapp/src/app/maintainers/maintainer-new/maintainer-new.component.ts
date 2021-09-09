import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MaintainerService } from '../shared/maintainer.service';
import {Location} from '@angular/common';
import { Maintainer } from '../shared/maintainer.model';
import { Address } from '../shared/address.model';

@Component({
  selector: 'app-maintainer-new',
  templateUrl: './maintainer-new.component.html',
  styleUrls: ['./maintainer-new.component.css']
})
export class MaintainerNewComponent implements OnInit {

  constructor(private maintainerService : MaintainerService,
              private route : ActivatedRoute,
              private location : Location) { }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  save(userName :string, fullName : string, email : string, city : string, street : string): void {
    const address: Address = <Address>{city, street};
    const maintainer: Maintainer = <Maintainer>{userName, fullName, email, address};
    this.maintainerService.save(maintainer)
      .subscribe(_ => this.goBack(),
        error => {alert("This maintainer could not be added !"); this.ngOnInit() });
  }


}
