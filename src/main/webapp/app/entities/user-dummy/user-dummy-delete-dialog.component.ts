import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { UserDummy } from './user-dummy.model';
import { UserDummyPopupService } from './user-dummy-popup.service';
import { UserDummyService } from './user-dummy.service';

@Component({
    selector: 'jhi-user-dummy-delete-dialog',
    templateUrl: './user-dummy-delete-dialog.component.html'
})
export class UserDummyDeleteDialogComponent {

    userDummy: UserDummy;

    constructor(
        private userDummyService: UserDummyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userDummyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userDummyListModification',
                content: 'Deleted an userDummy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-dummy-delete-popup',
    template: ''
})
export class UserDummyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userDummyPopupService: UserDummyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userDummyPopupService
                .open(UserDummyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
