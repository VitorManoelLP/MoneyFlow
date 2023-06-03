import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DetailOutlay, DetailOutlayGroup } from '../components/contas/models/detail-outlay';
import { Environment } from 'enviroments';

@Injectable()
export class RendimentosService {

  constructor(private httpClient: HttpClient) { }

  public getRendimentosByIdUsuarioRendimento(idUsuarioRendimento: number): Observable<DetailOutlay[]> {
    return this.httpClient.get<DetailOutlay[]>(`${Environment.MONEY_FLOW_API}/rendimentos/usuario-rendimento/${idUsuarioRendimento}`);
  }

  public salvar(outlay: DetailOutlayGroup): Observable<DetailOutlayGroup> {
    return this.httpClient.post<DetailOutlayGroup>(`${Environment.MONEY_FLOW_API}/rendimentos`, outlay);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete(`${Environment.MONEY_FLOW_API}/rendimentos/${id}`);
  }

  public salvarOfx(FormData: FormData): Observable<any> {
    return this.httpClient.post<any>(`${Environment.PARSE_API}/parse`, FormData);
  }

}
