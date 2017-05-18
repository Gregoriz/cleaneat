import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { UserDummy } from './user-dummy.model';
import { UserDummyPopupService } from './user-dummy-popup.service';
import { UserDummyService } from './user-dummy.service';

@Component({
    selector: 'jhi-user-dummy-dialog',
    templateUrl: './user-dummy-dialog.component.html'
})
export class UserDummyDialogComponent implements OnInit {

    userDummy: UserDummy;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private userDummyService: UserDummyService,
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
        if (this.userDummy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userDummyService.update(this.userDummy));
        } else {
            this.subscribeToSaveResponse(
                this.userDummyService.create(this.userDummy));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserDummy>) {
        result.subscribe((res: UserDummy) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserDummy) {
        this.eventManager.broadcast({ name: 'userDummyListModification', content: 'OK'});
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
    selector: 'jhi-user-dummy-popup',
    template: ''
})
export class UserDummyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userDummyPopupService: UserDummyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.userDummyPopupService
                    .open(UserDummyDialogComponent, params['id']);
            } else {
                this.modalRef = this.userDummyPopupService
                    .open(UserDummyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
