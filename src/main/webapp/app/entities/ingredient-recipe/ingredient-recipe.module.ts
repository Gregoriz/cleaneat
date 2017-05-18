import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CleaneatSharedModule } from '../../shared';
import {
    IngredientRecipeService,
    IngredientRecipePopupService,
    IngredientRecipeComponent,
    IngredientRecipeDetailComponent,
    IngredientRecipeDialogComponent,
    IngredientRecipePopupComponent,
    IngredientRecipeDeletePopupComponent,
    IngredientRecipeDeleteDialogComponent,
    ingredientRecipeRoute,
    ingredientRecipePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ingredientRecipeRoute,
    ...ingredientRecipePopupRoute,
];

@NgModule({
    imports: [
        CleaneatSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IngredientRecipeComponent,
        IngredientRecipeDetailComponent,
        IngredientRecipeDialogComponent,
        IngredientRecipeDeleteDialogComponent,
        IngredientRecipePopupComponent,
        IngredientRecipeDeletePopupComponent,
    ],
    entryComponents: [
        IngredientRecipeComponent,
        IngredientRecipeDialogComponent,
        IngredientRecipePopupComponent,
        IngredientRecipeDeleteDialogComponent,
        IngredientRecipeDeletePopupComponent,
    ],
    providers: [
        IngredientRecipeService,
        IngredientRecipePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CleaneatIngredientRecipeModule {}
