import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DetailOutlay, DetailOutlayGroup } from '../components/contas/models/detail-outlay';
import { Environment } from 'enviroments';

@Injectable()
export class RendimentosService {

  private url: string = Environment.MONEY_FLOW_API;

  constructor(private httpClient: HttpClient) { }

  public getRendimentosByIdUsuarioRendimento(idUsuarioRendimento: number): Observable<DetailOutlay[]> {
    return this.httpClient.get<DetailOutlay[]>(`${this.url}/rendimentos/usuario-rendimento/${idUsuarioRendimento}`);
  }

  public salvar(outlay: DetailOutlayGroup): Observable<DetailOutlayGroup> {
    return this.httpClient.post<DetailOutlayGroup>(`${this.url}/rendimentos`, outlay);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete(`${this.url}/rendimentos/${id}`);
  }

  public salvarOfx(FormData: FormData): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/rendimentos/ofx`, FormData);
  }

}
