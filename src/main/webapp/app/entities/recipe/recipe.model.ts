import { Tool } from '../tool';
import { IngredientRecipe } from '../ingredient-recipe';
export class Recipe {
    constructor(
        public id?: number,
        public description?: string,
        public tools?: Tool,
        public ingredients?: IngredientRecipe,
    ) {
    }
}
