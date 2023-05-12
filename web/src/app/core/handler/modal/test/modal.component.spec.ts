import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalComponent } from '../modal.component';
import { GlobalExceptionHandler } from '../../global-handler';
import { of } from 'rxjs';
import { GlobalHandlerService } from '../../global-handler.service';

describe('ModalComponent', () => {
  let component: ModalComponent;
  let fixture: ComponentFixture<ModalComponent>;
  let globalExceptionHandler: jasmine.SpyObj<GlobalHandlerService>;

  beforeEach(async () => {

    const globalSpy = jasmine.createSpyObj('GlobalExceptionHandler', ['lookUpErrors']);

    await TestBed.configureTestingModule({
      declarations: [ ModalComponent ],
      providers: [
        { provide: GlobalExceptionHandler, useValue: globalSpy }
      ]
    })
    .compileComponents();

    globalExceptionHandler = TestBed.inject(GlobalHandlerService) as jasmine.SpyObj<GlobalHandlerService>;
    globalExceptionHandler.lookUpErrors.and.callFake(() => of('error message test'));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set isOpen true and message when listenEvent is called', () => {

    component.ngOnInit();

    expect(globalExceptionHandler.lookUpErrors).toHaveBeenCalled();
    expect(component.isOpen).toBeTrue();
    expect(component.message).toEqual('error message test');

  });

});
