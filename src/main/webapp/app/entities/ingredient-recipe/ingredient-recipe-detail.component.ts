import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { IngredientRecipe } from './ingredient-recipe.model';
import { IngredientRecipeService } from './ingredient-recipe.service';

@Component({
    selector: 'jhi-ingredient-recipe-detail',
    templateUrl: './ingredient-recipe-detail.component.html'
})
export class IngredientRecipeDetailComponent implements OnInit, OnDestroy {

    ingredientRecipe: IngredientRecipe;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private ingredientRecipeService: IngredientRecipeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIngredientRecipes();
    }

    load(id) {
        this.ingredientRecipeService.find(id).subscribe((ingredientRecipe) => {
            this.ingredientRecipe = ingredientRecipe;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIngredientRecipes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ingredientRecipeListModification',
            (response) => this.load(this.ingredientRecipe.id)
        );
    }
}
