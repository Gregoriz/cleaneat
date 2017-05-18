import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Recipe } from './recipe.model';
import { RecipePopupService } from './recipe-popup.service';
import { RecipeService } from './recipe.service';
import { Tool, ToolService } from '../tool';

@Component({
    selector: 'jhi-recipe-dialog',
    templateUrl: './recipe-dialog.component.html'
})
export class RecipeDialogComponent implements OnInit {

    recipe: Recipe;
    authorities: any[];
    isSaving: boolean;

    tools: Tool[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private recipeService: RecipeService,
        private toolService: ToolService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.toolService.query().subscribe(
            (res: Response) => { this.tools = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.recipe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.recipeService.update(this.recipe));
        } else {
            this.subscribeToSaveResponse(
                this.recipeService.create(this.recipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<Recipe>) {
        result.subscribe((res: Recipe) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Recipe) {
        this.eventManager.broadcast({ name: 'recipeListModification', content: 'OK'});
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

    trackToolById(index: number, item: Tool) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-recipe-popup',
    template: ''
})
export class RecipePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recipePopupService: RecipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.recipePopupService
                    .open(RecipeDialogComponent, params['id']);
            } else {
                this.modalRef = this.recipePopupService
                    .open(RecipeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
