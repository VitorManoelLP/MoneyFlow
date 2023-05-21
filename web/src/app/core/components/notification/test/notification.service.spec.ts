import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule } from "@angular/common/http/testing";
import { HttpClient } from "@angular/common/http";
import { NotificationSevice } from '../notification-service.service';

describe('RendimentosService', () => {
  let service: NotificationSevice;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NotificationSevice]
    });
    service = TestBed.inject(NotificationSevice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

});
