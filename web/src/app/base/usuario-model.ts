export class UsuarioDto {
  id: number;
  nome: string;
  email: string;
  password: string;
  imagem: ArrayBuffer;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;

  constructor(
    id: number,
    nome: string,
    email: string,
    password: string,
    imagem: ArrayBuffer,
    createdAt: Date,
    updatedAt: Date,
    deletedAt: Date
  ) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.password = password;
    this.imagem = imagem;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  static Mock(id: number): UsuarioDto {
    return new UsuarioDto(id, 'Fulano', 'Fulano@gmail', '123456', new ArrayBuffer(100), new Date(), new Date(), new Date());
  }
}
