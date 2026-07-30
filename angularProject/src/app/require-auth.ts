import { Directive, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appRequireAuth]',
  standalone: true,
})
export class RequireAuth implements OnInit {
  private isMockUserLoggedIn: boolean = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
  ) {}

  ngOnInit() {
    this.checkAccess();
  }
  private checkAccess() {
    if (this.isMockUserLoggedIn) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
