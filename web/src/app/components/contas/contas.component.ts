import { Component, OnInit } from '@angular/core';

import { MesUtil } from '../../core/util/mes';
import { TipoRendimentoUtil } from 'src/app/base/tipo-rendimento';

import { UsuarioService } from 'src/app/services/usuario.service';
import { RendimentosService } from 'src/app/services/rendimentos.service';

import { Outlay } from './models/outlay';
import { DetailOutlay } from './models/detail-outlay';
import { Totalizadores } from './models/totalizadores';
import { UserInfo } from './models/user-info';
import { NotificationSevice } from 'src/app/core/components/notification/notification-service.service';

@Component({
  selector: 'app-contas',
  templateUrl: './contas.component.html'
})
export class ContasComponent implements OnInit {

  constructor(
    private usuarioService: UsuarioService,
    private rendimentosService: RendimentosService,
    private notificationService: NotificationSevice) { }

  private formData = new FormData();

  outlay: Outlay[];
  detailOutlay: DetailOutlay[] = [];

  totalizadores: Totalizadores = {
    totalDespesas: 0,
    totalReceitas: 0
  };

  searchFields = ['mes', 'nome', 'tipoRendimento'];

  fieldsWithHandler = new Map();

  ngOnInit(): void {

    this.fieldsWithHandler.set('tipoRendimento', (obj: string) => TipoRendimentoUtil.getFieldByTipoRendimento(obj));
    this.fieldsWithHandler.set('mes', (obj: number) => MesUtil.getMesField(obj));

    this.findUserOutlay();
  }

  public salvarOfx(event: any): void {
    this.formData.append('file', <File>event.target.files[0]);
  }

  public importarOfx(): void {
    this.rendimentosService.salvarOfx(this.formData).subscribe(() => {
      this.notificationService.success('Rendimentos importados com sucesso!');
      document?.getElementById('closeImportacao')?.click();
      this.findUserOutlay();
    });
  }

  public removeDetailOutlay(detail: DetailOutlay) {
    this.rendimentosService.delete(detail.id).subscribe(() => {
      this.detailOutlay = this.detailOutlay.filter((detailOutlay: DetailOutlay) => detailOutlay.id !== detail.id);
      this.notificationService.success('Rendimento removido com sucesso!');
    });
  }

  public getFieldTipoRendimento(tipoRendimento: string): string {
    return TipoRendimentoUtil.getFieldByTipoRendimento(tipoRendimento);
  }

  public receiveRefresh(): void {
    this.findUserOutlay();
  }

  public getMesField(mes: number): string {
    return MesUtil.getMesField(mes);
  }

  public openDetail(entity: Outlay): void {
    entity.expand = !entity.expand;
    if(!this.alreadyContainsRendimentosById(entity.idUsuarioRendimento)) {
      this.rendimentosService.getRendimentosByIdUsuarioRendimento(entity.idUsuarioRendimento)
        .subscribe((rendimento: DetailOutlay[]) => this.detailOutlay.push(...rendimento));
    }
  }

  public getSpecificOutlay(idUsuarioRendimento: number): DetailOutlay[] {
    return this.detailOutlay.filter((detail: DetailOutlay) => detail.idUsuarioRendimento === idUsuarioRendimento);
  }

  private findUserOutlay(): void {
    this.usuarioService.findInitialInformations(1).subscribe((userInfo: UserInfo) => this.buildObjectOutlay(userInfo));
  }

  private alreadyContainsRendimentosById(idUsuarioRendimento: number): boolean {
    return this.detailOutlay.some((detail: DetailOutlay) => detail.idUsuarioRendimento === idUsuarioRendimento);
  }

  private buildObjectOutlay(userInfo: UserInfo): void {
    this.outlay = userInfo.rendimentosTotais;
    this.totalizadores = {
      totalDespesas: userInfo.valorTotalDespesas,
      totalReceitas: userInfo.valorTotalReceitas
    };
  }

}
