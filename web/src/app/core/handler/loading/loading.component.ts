import { Component, OnInit } from '@angular/core';
import { GlobalHandlerService } from '../global-handler.service';

@Component({
  selector: 'loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit {

  public isLoading: boolean = false;

  constructor(private globalHandlerService: GlobalHandlerService) {}

  ngOnInit(): void {
    this.loopUpRequestLoading();
  }

  private loopUpRequestLoading() {
    this.globalHandlerService.isLoading().subscribe(loading => this.isLoading = loading);
  }

}
