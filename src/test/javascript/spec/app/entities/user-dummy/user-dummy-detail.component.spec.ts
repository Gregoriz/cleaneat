import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CleaneatTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserDummyDetailComponent } from '../../../../../../main/webapp/app/entities/user-dummy/user-dummy-detail.component';
import { UserDummyService } from '../../../../../../main/webapp/app/entities/user-dummy/user-dummy.service';
import { UserDummy } from '../../../../../../main/webapp/app/entities/user-dummy/user-dummy.model';

describe('Component Tests', () => {

    describe('UserDummy Management Detail Component', () => {
        let comp: UserDummyDetailComponent;
        let fixture: ComponentFixture<UserDummyDetailComponent>;
        let service: UserDummyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CleaneatTestModule],
                declarations: [UserDummyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserDummyService,
                    EventManager
                ]
            }).overrideComponent(UserDummyDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserDummyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDummyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserDummy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userDummy).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
