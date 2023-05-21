import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NotificationComponent } from '../notification.component';
import { NotificationSevice } from '../notification-service.service';
import { of } from 'rxjs';


describe('NotificationComponent', () => {
  let component: NotificationComponent;
  let fixture: ComponentFixture<NotificationComponent>;
  let notificationService: jasmine.SpyObj<NotificationSevice>;

  beforeEach(async () => {

    const spy = jasmine.createSpyObj('NotificationSevice', ['lookUp']);

    await TestBed.configureTestingModule({
      declarations: [ NotificationComponent ],
      providers: [
        { provide: NotificationSevice, useValue: spy }
      ]
    })
    .compileComponents();

    notificationService = TestBed.inject(NotificationSevice) as jasmine.SpyObj<NotificationSevice>;
    notificationService.lookUp.and.callFake(() => of(mockNotification()));

    fixture = TestBed.createComponent(NotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call lookUp onInit', () => {
    expect(notificationService.lookUp).toHaveBeenCalled();
    expect(component.notification).toEqual(mockNotification());
  });

  it('should call translateType', () => {
    expect(component.translateType('success')).toEqual('bg-success text-light');
    expect(component.translateType('danger')).toEqual('bg-danger text-light');
    expect(component.translateType('')).toEqual('');
  });

});

function mockNotification(): { message: string; title: string; type: "success"; } {
  return { message: 'Teste', title: 'Teste', type: 'success' };
}

