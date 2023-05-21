import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationComponent } from './notification.component';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    NotificationComponent
  ],
  exports: [NotificationComponent],
  imports: [
    CommonModule,
    NgbToastModule
  ]
})
export class NotificationModule { }
