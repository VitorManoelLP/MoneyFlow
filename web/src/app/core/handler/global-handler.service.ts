import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GlobalHandlerService {

  private emitErrorEvent: Subject<string> = new Subject<string>();
  private loading: Subject<boolean> = new Subject<boolean>();

  constructor() { }

  show(): void {
    this.loading.next(true);
  }

  hide(): void {
    this.loading.next(false);
  }

  isLoading(): Observable<boolean> {
    return this.loading.asObservable();
  }

  emitError(errorMessage: string) {
    this.emitErrorEvent.next(errorMessage);
  }

  lookUpErrors(): Observable<string> {
    return this.emitErrorEvent.asObservable();
  }

}
