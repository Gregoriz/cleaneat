import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CleaneatTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IngredientRecipeDetailComponent } from '../../../../../../main/webapp/app/entities/ingredient-recipe/ingredient-recipe-detail.component';
import { IngredientRecipeService } from '../../../../../../main/webapp/app/entities/ingredient-recipe/ingredient-recipe.service';
import { IngredientRecipe } from '../../../../../../main/webapp/app/entities/ingredient-recipe/ingredient-recipe.model';

describe('Component Tests', () => {

    describe('IngredientRecipe Management Detail Component', () => {
        let comp: IngredientRecipeDetailComponent;
        let fixture: ComponentFixture<IngredientRecipeDetailComponent>;
        let service: IngredientRecipeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CleaneatTestModule],
                declarations: [IngredientRecipeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IngredientRecipeService,
                    EventManager
                ]
            }).overrideComponent(IngredientRecipeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IngredientRecipeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientRecipeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IngredientRecipe(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ingredientRecipe).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
