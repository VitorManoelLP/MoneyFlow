import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationSevice } from './notification-service.service';
import { Notification } from './model/notification';
import { Subscription } from 'rxjs';

@Component({
  selector: 'notification',
  templateUrl: './notification.component.html'
})
export class NotificationComponent implements OnInit, OnDestroy {

  public notification: Notification;
  public show: boolean = false;

  private subscription: Subscription;

  constructor(private notificationService: NotificationSevice) {}

  ngOnDestroy(): void {
    if(this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.subscription = this.notificationService.lookUp().subscribe(notification => this.showNotification(notification));
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

  public onHide() {
    this.show = false
  }

  private showNotification(notification: Notification) {
    this.notification = notification;
    this.show = true;
  }

}
