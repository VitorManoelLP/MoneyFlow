import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, Subject, tap } from "rxjs";
import { UsuarioDto } from "../base/usuario-model";
import { Environment } from 'enviroments';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private url: string = Environment.MONEY_FLOW_API;

  constructor(private httpClient: HttpClient) {
  }

  public findById(id: number): Observable<UsuarioDto> {
    return this.httpClient.get<UsuarioDto>(`${this.url}/usuarios/${id}`);
  }

  public findInitialInformations(id: number):Observable<any> {
    return this.httpClient.get<any>(`${this.url}/usuarios/initial-informations/${id}`);
  }

}
