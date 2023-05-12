import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, Subject, tap } from "rxjs";
import { UsuarioDto } from "../base/usuario-model";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private httpClient: HttpClient) {
  }

  public findById(id: number): Observable<UsuarioDto> {
    return this.httpClient.get<UsuarioDto>(`/api/usuarios/${id}`);
  }

  public findInitialInformations(id: number):Observable<any> {
    return this.httpClient.get<any>(`/api/usuarios/initial-informations/${id}`);
  }

}
