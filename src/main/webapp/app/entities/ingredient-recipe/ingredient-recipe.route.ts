import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { IngredientRecipeComponent } from './ingredient-recipe.component';
import { IngredientRecipeDetailComponent } from './ingredient-recipe-detail.component';
import { IngredientRecipePopupComponent } from './ingredient-recipe-dialog.component';
import { IngredientRecipeDeletePopupComponent } from './ingredient-recipe-delete-dialog.component';

import { Principal } from '../../shared';

export const ingredientRecipeRoute: Routes = [
    {
        path: 'ingredient-recipe',
        component: IngredientRecipeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientRecipes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ingredient-recipe/:id',
        component: IngredientRecipeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientRecipes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientRecipePopupRoute: Routes = [
    {
        path: 'ingredient-recipe-new',
        component: IngredientRecipePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientRecipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ingredient-recipe/:id/edit',
        component: IngredientRecipePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientRecipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ingredient-recipe/:id/delete',
        component: IngredientRecipeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientRecipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
