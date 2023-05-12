import { ErrorHandler, Injectable } from '@angular/core';
import { GlobalHandlerService } from './global-handler.service';

@Injectable()
export class AngularExceptionHandler implements ErrorHandler {

  constructor(private globalHandlerService: GlobalHandlerService) {}

  handleError(error: any): void {
    this.globalHandlerService.emitError(error);
  }
}
