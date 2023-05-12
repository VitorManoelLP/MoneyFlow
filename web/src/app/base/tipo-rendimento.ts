export enum TipoRendimento {
  RECEITA = "RECEITA",
  DESPESA = "DESPESA"
}

export class TipoRendimentoUtil {

  public static getFieldByTipoRendimento(tipoRendimento: string): string {
    return tipoRendimento ? tipoRendimento.charAt(0).concat(tipoRendimento.toLowerCase().substring(1)) : '';
  }

}
