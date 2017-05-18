import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Tool } from './tool.model';
import { ToolPopupService } from './tool-popup.service';
import { ToolService } from './tool.service';

@Component({
    selector: 'jhi-tool-delete-dialog',
    templateUrl: './tool-delete-dialog.component.html'
})
export class ToolDeleteDialogComponent {

    tool: Tool;

    constructor(
        private toolService: ToolService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.toolService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'toolListModification',
                content: 'Deleted an tool'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tool-delete-popup',
    template: ''
})
export class ToolDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private toolPopupService: ToolPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.toolPopupService
                .open(ToolDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
