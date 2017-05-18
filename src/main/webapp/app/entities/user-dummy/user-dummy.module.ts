import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CleaneatSharedModule } from '../../shared';
import {
    UserDummyService,
    UserDummyPopupService,
    UserDummyComponent,
    UserDummyDetailComponent,
    UserDummyDialogComponent,
    UserDummyPopupComponent,
    UserDummyDeletePopupComponent,
    UserDummyDeleteDialogComponent,
    userDummyRoute,
    userDummyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userDummyRoute,
    ...userDummyPopupRoute,
];

@NgModule({
    imports: [
        CleaneatSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserDummyComponent,
        UserDummyDetailComponent,
        UserDummyDialogComponent,
        UserDummyDeleteDialogComponent,
        UserDummyPopupComponent,
        UserDummyDeletePopupComponent,
    ],
    entryComponents: [
        UserDummyComponent,
        UserDummyDialogComponent,
        UserDummyPopupComponent,
        UserDummyDeleteDialogComponent,
        UserDummyDeletePopupComponent,
    ],
    providers: [
        UserDummyService,
        UserDummyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CleaneatUserDummyModule {}
