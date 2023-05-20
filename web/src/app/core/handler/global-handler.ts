import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpStatusCode } from "@angular/common/http";
import { Observable, catchError, finalize, throwError } from "rxjs";
import { GlobalHandlerService } from "./global-handler.service";
import { Injectable } from "@angular/core";

@Injectable()
export class GlobalExceptionHandler implements HttpInterceptor {

  private severityErrors = [
    HttpStatusCode.InternalServerError,
    HttpStatusCode.NotImplemented,
    HttpStatusCode.BadGateway,
    HttpStatusCode.ServiceUnavailable,
    HttpStatusCode.GatewayTimeout,
    HttpStatusCode.HttpVersionNotSupported,
    HttpStatusCode.VariantAlsoNegotiates,
    HttpStatusCode.InsufficientStorage,
    HttpStatusCode.LoopDetected,
    HttpStatusCode.NotExtended,
    HttpStatusCode.NetworkAuthenticationRequired
  ]

  constructor(private globalHandler: GlobalHandlerService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let isFinalized = false;
    const timerLoading = 500;

    setTimeout(() => {
      if(!isFinalized) {
        this.globalHandler.show();
      }
    }, timerLoading);

    return next.handle(req).pipe(
      finalize(() => {
        isFinalized = true;
        this.globalHandler.hide();
      }),
      catchError((httpError: HttpErrorResponse) => this.captureErrorMessage(httpError))
    )
  }


  private captureErrorMessage(httpError: HttpErrorResponse) {

    let error = this.severityErrors.includes(httpError.status) ? 'Infelizmente, uma falha inesperada ocorreu em nossos servidores. \n Por favor, tente novamente mais tarde ou entre em contato com o suporte técnico para obter assistência.' : httpError.error.message;

    this.globalHandler.emitError(error);
    return throwError(() => new Error(httpError.error.message));
  }
}
