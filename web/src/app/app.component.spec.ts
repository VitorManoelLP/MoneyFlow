import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ModalModule } from './core/handler/modal/modal.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UsuarioService } from './services/usuario.service';
import { of } from 'rxjs';
import { UsuarioDto } from './base/usuario-model';

describe('AppComponent', () => {

  let usuarioService: jasmine.SpyObj<UsuarioService>;

  beforeEach(async () => {

    const usuarioSpy = jasmine.createSpyObj('UsuarioService', ['findById']);

    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        ModalModule,
        NgbModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: UsuarioService, useValue: usuarioSpy }
      ]
    }).compileComponents();

    usuarioService = TestBed.inject(UsuarioService) as jasmine.SpyObj<UsuarioService>;
    usuarioService.findById.and.callFake(() => of(mockUser()));

  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should call usuarioService.findById when init component', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    app.ngOnInit();
    expect(usuarioService.findById).toHaveBeenCalled();
    expect(app.usuario).toEqual(mockUser());
  });

});

function mockUser() {
  return new UsuarioDto(1, 'nome', 'email', '12345', new ArrayBuffer(0), new Date(), new Date(), new Date());
}
