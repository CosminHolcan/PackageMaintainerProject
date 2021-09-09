import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Maintainer } from '../shared/maintainer.model';
import { MaintainerService } from '../shared/maintainer.service';

@Component({
  selector: 'app-maintainer-list',
  templateUrl: './maintainer-list.component.html',
  styleUrls: ['./maintainer-list.component.css']
})
export class MaintainerListComponent implements OnInit {
  maintainers: Array<Maintainer>;
  selectedMaintainer : Maintainer;

  constructor(private maintainerService : MaintainerService,
              private router : Router) { }

  ngOnInit(): void {
    this.getMaintainers();
  }

  getMaintainers() {
    this.maintainerService.getMaintainers()
      .subscribe(
        maintainers => this.maintainers = maintainers
      );
  }

  getFilteredMaintainers(type : string, input : string) {
    this.maintainerService.getFilteredMaintainers(type, input)
      .subscribe(
        maintainers => this.maintainers = maintainers
      );
  }

  sortMaintainers(type : string, order : string) {
    switch (type) {
      case 'email' :
        if (order == "ascending")
          this.maintainers.sort((m1, m2) => (m1.email > m2.email) ? 1 : ((m2.email > m1.email) ? -1 : 0));
        else
          this.maintainers.sort((m1, m2) => (m1.email > m2.email) ? -1 : ((m2.email > m1.email) ? 1 : 0));
        break;
      case 'userName' :
        if (order == "ascending")
          this.maintainers.sort((m1, m2) => (m1.userName > m2.userName) ? 1 : ((m2.userName > m1.userName) ? -1 : 0));
        else
          this.maintainers.sort((m1, m2) => (m1.userName > m2.userName) ? -1 : ((m2.userName > m1.userName) ? 1 : 0));
        break;
      case 'fullName' :
        if (order == "ascending")
          this.maintainers.sort((m1, m2) => (m1.fullName > m2.fullName) ? 1 : ((m2.fullName > m1.fullName) ? -1 : 0));
        else
          this.maintainers.sort((m1, m2) => (m1.fullName > m2.fullName) ? -1 : ((m2.fullName > m1.fullName) ? 1 : 0));
        break;
    }
  }

  onSelect(maintainer : Maintainer): void {
    if (this.selectedMaintainer == maintainer)
      this.selectedMaintainer = null;
    else
      this.selectedMaintainer = maintainer;
  }

  gotoDetail(): void {
    this.router.navigate(['/maintainer/detail', this.selectedMaintainer.id]);
  }

  delete(): void{
    this.maintainerService.delete(this.selectedMaintainer.id)
      .subscribe(_ => { this.ngOnInit(); this.selectedMaintainer = null;});
  }

  goToAdd(): void{
    this.router.navigate(['/maintainer/add']);
  }

}
