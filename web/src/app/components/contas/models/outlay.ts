import { TipoRendimento } from "src/app/base/tipo-rendimento";

export interface Outlay {
  idUsuarioRendimento: number;
  nome: string;
  mes: number;
  tipoRendimento: TipoRendimento;
  valorTotal: number;
  expand?: boolean;
}
