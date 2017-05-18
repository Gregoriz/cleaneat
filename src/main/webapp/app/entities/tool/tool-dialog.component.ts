import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Tool } from './tool.model';
import { ToolPopupService } from './tool-popup.service';
import { ToolService } from './tool.service';

@Component({
    selector: 'jhi-tool-dialog',
    templateUrl: './tool-dialog.component.html'
})
export class ToolDialogComponent implements OnInit {

    tool: Tool;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private toolService: ToolService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tool.id !== undefined) {
            this.subscribeToSaveResponse(
                this.toolService.update(this.tool));
        } else {
            this.subscribeToSaveResponse(
                this.toolService.create(this.tool));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tool>) {
        result.subscribe((res: Tool) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Tool) {
        this.eventManager.broadcast({ name: 'toolListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-tool-popup',
    template: ''
})
export class ToolPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private toolPopupService: ToolPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.toolPopupService
                    .open(ToolDialogComponent, params['id']);
            } else {
                this.modalRef = this.toolPopupService
                    .open(ToolDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
