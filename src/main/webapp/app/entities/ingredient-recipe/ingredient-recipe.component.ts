import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { IngredientRecipe } from './ingredient-recipe.model';
import { IngredientRecipeService } from './ingredient-recipe.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ingredient-recipe',
    templateUrl: './ingredient-recipe.component.html'
})
export class IngredientRecipeComponent implements OnInit, OnDestroy {
ingredientRecipes: IngredientRecipe[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private ingredientRecipeService: IngredientRecipeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.ingredientRecipeService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.ingredientRecipes = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.ingredientRecipeService.query().subscribe(
            (res: Response) => {
                this.ingredientRecipes = res.json();
                this.currentSearch = '';
            },
            (res: Response) => this.onError(res.json())
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIngredientRecipes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IngredientRecipe) {
        return item.id;
    }
    registerChangeInIngredientRecipes() {
        this.eventSubscriber = this.eventManager.subscribe('ingredientRecipeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
