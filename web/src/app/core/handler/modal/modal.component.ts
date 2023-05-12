import { Component, OnInit } from '@angular/core';
import { GlobalExceptionHandler } from 'src/app/core/handler/global-handler';
import { GlobalHandlerService } from '../global-handler.service';

@Component({
  selector: 'modal-handler-exception',
  templateUrl: './modal.component.html',
  styleUrls: ['modal.component.css']
})
export class ModalComponent implements OnInit {

  isOpen: boolean = false;
  message: string = '';

  constructor(private globalExceptionHandler: GlobalHandlerService) { }

  ngOnInit(): void {
    this.listenExceptions();
  }

  public openModal() {
    this.isOpen = !this.isOpen;
  }

  private listenExceptions() {
    this.globalExceptionHandler.lookUpErrors().subscribe(errorMessage => this.prepareModal(errorMessage));
  }

  private prepareModal(errorMessage: string) {
    this.isOpen = true;
    this.message = errorMessage;
  }

}
