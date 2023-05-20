export interface DetailOutlay {
  idUsuarioRendimento: number;
  descricao: string;
  data: Date;
  valor: number;
}

export interface DetailOutlayGroup {
  mes: number,
  nome: string,
  tipoRendimento: string,
  idUsuarioRendimento: number,
  rendimentos: DetailOutlayToSave[]
};

export interface DetailOutlayToSave {
  descricao: string;
  data: Date;
  valor: number;
}
