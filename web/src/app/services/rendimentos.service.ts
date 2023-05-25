import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DetailOutlay, DetailOutlayGroup } from '../components/contas/models/detail-outlay';

@Injectable()
export class RendimentosService {

  constructor(private httpClient: HttpClient) { }

  public getRendimentosByIdUsuarioRendimento(idUsuarioRendimento: number): Observable<DetailOutlay[]> {
    return this.httpClient.get<DetailOutlay[]>(`/api/rendimentos/usuario-rendimento/${idUsuarioRendimento}`);
  }

  public salvar(outlay: DetailOutlayGroup): Observable<DetailOutlayGroup> {
    return this.httpClient.post<DetailOutlayGroup>('/api/rendimentos', outlay);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete(`/api/rendimentos/${id}`);
  }

  public salvarOfx(FormData: FormData): Observable<any> {
    return this.httpClient.post<any>('/api/rendimentos/ofx', FormData);
  }

}
