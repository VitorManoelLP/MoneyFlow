import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePageComponent } from '../home-page.component';
import { UsuarioService } from '../../../services/usuario.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GraficoModule } from '../../grafico/grafico.module';
import { of } from 'rxjs';

describe('HomePageComponent', () => {

  let component: HomePageComponent;
  let fixture: ComponentFixture<HomePageComponent>;
  let usuarioService: jasmine.SpyObj<UsuarioService>;

  beforeEach(async () => {

    const usuarioSpy = jasmine.createSpyObj('UsuarioService', ['findInitialInformations']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, GraficoModule],
      declarations: [ HomePageComponent ],
      providers: [
        { provide: UsuarioService, useValue: usuarioSpy }
      ]
    })
    .compileComponents();

    usuarioService = TestBed.inject(UsuarioService) as jasmine.SpyObj<UsuarioService>;
    usuarioService.findInitialInformations.and.callFake(() => of({ id: 1, rendimentosTotais: [] }));

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call observeUserInfo when ngOnInit is called', () => {

    component.ngOnInit();

    expect(usuarioService.findInitialInformations).toHaveBeenCalled();
    expect(component.usuario).toEqual({ id: 1, rendimentosTotais: [] });

  });

});
