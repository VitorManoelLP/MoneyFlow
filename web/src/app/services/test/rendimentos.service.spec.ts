import { TestBed } from '@angular/core/testing';

import { UsuarioService } from '../usuario.service';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { HttpClient } from "@angular/common/http";
import { UsuarioDto } from "../../base/usuario-model";
import { of } from "rxjs";
import { RendimentosService } from '../rendimentos.service';

describe('RendimentosService', () => {
  let service: RendimentosService;
  let httpClientSpy: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RendimentosService]
    });
    service = TestBed.inject(RendimentosService);
    httpClientSpy = TestBed.inject(HttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call findById', () => {

    const spy = spyOn(httpClientSpy, 'get').and.returnValue(of({ id: 1 }));

    service.getRendimentosByIdUsuarioRendimento(1).subscribe();

    expect(spy).toHaveBeenCalledWith('/api/usuarios/1');
  });

});
