import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Subject, tap } from 'rxjs';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html'
})
export class HomePageComponent implements OnInit {

  usuario: any;
  usuarioLoaded: Subject<any> = new Subject();

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.observeUserInfo();
  }

  public observeUserInfo() {
    this.usuarioService.findInitialInformations(1)
      .pipe(tap(usuario => this.usuarioLoaded.next(usuario)))
      .subscribe(usuario => this.usuario = usuario);
  }

}
