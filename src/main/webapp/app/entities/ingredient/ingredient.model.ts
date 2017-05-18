
const enum MeasureUnit {
    'G',
    'L',
    'PIECE'

};
export class Ingredient {
    constructor(
        public id?: number,
        public name?: string,
        public averageWeigth?: number,
        public averageWeightUnit?: MeasureUnit,
        public protein?: number,
        public lipid?: number,
        public saturatedFattyAcid?: number,
        public polyunsaturatedFattyAcids?: number,
        public saturatedFats?: number,
        public glucid?: number,
        public sugar?: number,
        public fiber?: number,
        public potassium?: number,
        public sodium?: number,
    ) {
    }
}
