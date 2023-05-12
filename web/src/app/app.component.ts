import { Component, OnInit } from '@angular/core';
import { UsuarioService } from "./services/usuario.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  usuario: any;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.getUsuario();
  }

  private getUsuario() {
    return this.usuarioService.findById(1)
      .subscribe(usuario => this.usuario = usuario);
  }

}
