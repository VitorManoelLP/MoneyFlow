import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UsuarioService } from "./services/usuario.service";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { ModalModule } from './core/handler/modal/modal.module';
import { GlobalExceptionHandler } from './core/handler/global-handler';
import { LoadingModule } from './core/handler/loading/loading.module';
import { AngularExceptionHandler } from './core/handler/angular-exception-handler';

@NgModule({
    declarations: [
        AppComponent
    ],
    providers: [
      { provide: HTTP_INTERCEPTORS, useClass: GlobalExceptionHandler, multi: true },
      { provide: ErrorHandler, useClass: AngularExceptionHandler },
      UsuarioService
    ],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ModalModule,
        LoadingModule
    ]
})
export class AppModule {
}
