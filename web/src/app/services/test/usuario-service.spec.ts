import { TestBed } from '@angular/core/testing';

import { UsuarioService } from '../usuario.service';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { HttpClient } from "@angular/common/http";
import { UsuarioDto } from "../../base/usuario-model";
import { of } from "rxjs";

describe('UsuarioService', () => {
  let service: UsuarioService;
  let httpClientSpy: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsuarioService]
    });
    service = TestBed.inject(UsuarioService);
    httpClientSpy = TestBed.inject(HttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call findById', () => {
    const usuario = UsuarioDto.Mock(1);

    const spy = spyOn(httpClientSpy, 'get').and.returnValue(of(usuario));

    service.findById(1).subscribe();

    expect(spy).toHaveBeenCalledWith('/api/usuarios/1');
  });

  it('should call initial-informations', () => {
    const usuario = UsuarioDto.Mock(1);

    const spy = spyOn(httpClientSpy, 'get').and.returnValue(of(usuario));

    service.findInitialInformations(1).subscribe();

    expect(spy).toHaveBeenCalledWith('/api/rendimentos/usuario-rendimento/1');
  });

});
