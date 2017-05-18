import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { UserDummy } from './user-dummy.model';
import { UserDummyService } from './user-dummy.service';

@Component({
    selector: 'jhi-user-dummy-detail',
    templateUrl: './user-dummy-detail.component.html'
})
export class UserDummyDetailComponent implements OnInit, OnDestroy {

    userDummy: UserDummy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private userDummyService: UserDummyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserDummies();
    }

    load(id) {
        this.userDummyService.find(id).subscribe((userDummy) => {
            this.userDummy = userDummy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserDummies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userDummyListModification',
            (response) => this.load(this.userDummy.id)
        );
    }
}
