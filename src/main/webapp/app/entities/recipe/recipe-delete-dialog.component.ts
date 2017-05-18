import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Recipe } from './recipe.model';
import { RecipePopupService } from './recipe-popup.service';
import { RecipeService } from './recipe.service';

@Component({
    selector: 'jhi-recipe-delete-dialog',
    templateUrl: './recipe-delete-dialog.component.html'
})
export class RecipeDeleteDialogComponent {

    recipe: Recipe;

    constructor(
        private recipeService: RecipeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'recipeListModification',
                content: 'Deleted an recipe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recipe-delete-popup',
    template: ''
})
export class RecipeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recipePopupService: RecipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.recipePopupService
                .open(RecipeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
