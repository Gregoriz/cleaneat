import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IngredientRecipe } from './ingredient-recipe.model';
import { IngredientRecipeService } from './ingredient-recipe.service';
@Injectable()
export class IngredientRecipePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ingredientRecipeService: IngredientRecipeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.ingredientRecipeService.find(id).subscribe((ingredientRecipe) => {
                this.ingredientRecipeModalRef(component, ingredientRecipe);
            });
        } else {
            return this.ingredientRecipeModalRef(component, new IngredientRecipe());
        }
    }

    ingredientRecipeModalRef(component: Component, ingredientRecipe: IngredientRecipe): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ingredientRecipe = ingredientRecipe;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
