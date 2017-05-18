import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { IngredientRecipe } from './ingredient-recipe.model';
import { IngredientRecipePopupService } from './ingredient-recipe-popup.service';
import { IngredientRecipeService } from './ingredient-recipe.service';

@Component({
    selector: 'jhi-ingredient-recipe-delete-dialog',
    templateUrl: './ingredient-recipe-delete-dialog.component.html'
})
export class IngredientRecipeDeleteDialogComponent {

    ingredientRecipe: IngredientRecipe;

    constructor(
        private ingredientRecipeService: IngredientRecipeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ingredientRecipeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ingredientRecipeListModification',
                content: 'Deleted an ingredientRecipe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ingredient-recipe-delete-popup',
    template: ''
})
export class IngredientRecipeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ingredientRecipePopupService: IngredientRecipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.ingredientRecipePopupService
                .open(IngredientRecipeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
