import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { IngredientRecipe } from './ingredient-recipe.model';
import { IngredientRecipePopupService } from './ingredient-recipe-popup.service';
import { IngredientRecipeService } from './ingredient-recipe.service';
import { Ingredient, IngredientService } from '../ingredient';
import { Recipe, RecipeService } from '../recipe';

@Component({
    selector: 'jhi-ingredient-recipe-dialog',
    templateUrl: './ingredient-recipe-dialog.component.html'
})
export class IngredientRecipeDialogComponent implements OnInit {

    ingredientRecipe: IngredientRecipe;
    authorities: any[];
    isSaving: boolean;

    ingredients: Ingredient[];

    recipes: Recipe[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private ingredientRecipeService: IngredientRecipeService,
        private ingredientService: IngredientService,
        private recipeService: RecipeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.ingredientService.query().subscribe(
            (res: Response) => { this.ingredients = res.json(); }, (res: Response) => this.onError(res.json()));
        this.recipeService.query().subscribe(
            (res: Response) => { this.recipes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ingredientRecipe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ingredientRecipeService.update(this.ingredientRecipe));
        } else {
            this.subscribeToSaveResponse(
                this.ingredientRecipeService.create(this.ingredientRecipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<IngredientRecipe>) {
        result.subscribe((res: IngredientRecipe) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: IngredientRecipe) {
        this.eventManager.broadcast({ name: 'ingredientRecipeListModification', content: 'OK'});
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

    trackIngredientById(index: number, item: Ingredient) {
        return item.id;
    }

    trackRecipeById(index: number, item: Recipe) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ingredient-recipe-popup',
    template: ''
})
export class IngredientRecipePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ingredientRecipePopupService: IngredientRecipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.ingredientRecipePopupService
                    .open(IngredientRecipeDialogComponent, params['id']);
            } else {
                this.modalRef = this.ingredientRecipePopupService
                    .open(IngredientRecipeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
