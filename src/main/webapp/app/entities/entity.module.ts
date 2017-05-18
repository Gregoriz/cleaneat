import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CleaneatToolModule } from './tool/tool.module';
import { CleaneatRecipeModule } from './recipe/recipe.module';
import { CleaneatIngredientModule } from './ingredient/ingredient.module';
import { CleaneatIngredientRecipeModule } from './ingredient-recipe/ingredient-recipe.module';
import { CleaneatUserDummyModule } from './user-dummy/user-dummy.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CleaneatToolModule,
        CleaneatRecipeModule,
        CleaneatIngredientModule,
        CleaneatIngredientRecipeModule,
        CleaneatUserDummyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CleaneatEntityModule {}
