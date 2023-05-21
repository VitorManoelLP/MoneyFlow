import { Component, OnDestroy, OnInit } from '@angular/core';
import * as bootstrap from 'bootstrap';
import { NotificationSevice } from './notification-service.service';
import { Notification } from './model/notification';
import { Subscription } from 'rxjs';

@Component({
  selector: 'notification',
  templateUrl: './notification.component.html'
})
export class NotificationComponent implements OnInit, OnDestroy {

  public notification: Notification;
  private subscription: Subscription;

  constructor(private notificationService: NotificationSevice) {}

  ngOnInit(): void {
    this.subscription = this.notificationService.lookUp().subscribe(notification => this.showNotification(notification));
  }

  ngOnDestroy(): void {
    if(this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  public translateType(type: string): string {
    switch(type) {
      case 'success':
        return 'bg-success text-light';
      case 'danger':
        return 'bg-danger text-light';
      default:
        return '';
    }
  }

  private showNotification(notification: Notification) {
    this.notification = notification;
    new bootstrap.Toast('.toast').show();
  }

}
