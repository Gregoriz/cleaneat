import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CleaneatSharedModule } from '../../shared';
import {
    RecipeService,
    RecipePopupService,
    RecipeComponent,
    RecipeDetailComponent,
    RecipeDialogComponent,
    RecipePopupComponent,
    RecipeDeletePopupComponent,
    RecipeDeleteDialogComponent,
    recipeRoute,
    recipePopupRoute,
} from './';

const ENTITY_STATES = [
    ...recipeRoute,
    ...recipePopupRoute,
];

@NgModule({
    imports: [
        CleaneatSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RecipeComponent,
        RecipeDetailComponent,
        RecipeDialogComponent,
        RecipeDeleteDialogComponent,
        RecipePopupComponent,
        RecipeDeletePopupComponent,
    ],
    entryComponents: [
        RecipeComponent,
        RecipeDialogComponent,
        RecipePopupComponent,
        RecipeDeleteDialogComponent,
        RecipeDeletePopupComponent,
    ],
    providers: [
        RecipeService,
        RecipePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CleaneatRecipeModule {}
