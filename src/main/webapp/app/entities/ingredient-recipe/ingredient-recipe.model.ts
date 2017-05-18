
const enum MeasureUnit {
    'G',
    'L',
    'PIECE'

};
import { Ingredient } from '../ingredient';
import { Recipe } from '../recipe';
export class IngredientRecipe {
    constructor(
        public id?: number,
        public quantity?: number,
        public measureUnit?: MeasureUnit,
        public ingredient?: Ingredient,
        public recipe?: Recipe,
    ) {
    }
}
