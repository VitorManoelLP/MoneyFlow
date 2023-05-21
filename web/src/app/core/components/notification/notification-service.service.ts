import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Notification } from './model/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationSevice {

  private emitNotification: Subject<Notification> = new Subject();

  constructor() { }

  public lookUp(): Observable<Notification> {
    return this.emitNotification.asObservable();
  }

  public notify(notification: Notification) {
    this.emitNotification.next(notification);
  }

  public success(message: string) {
    this.notify({ message: message, title: 'Sucesso', type: 'bg-success text-white' });
  }

  public error(message: string) {
    this.notify({ message: message, title: 'Erro', type: 'bg-danger text-white' });
  }

}
