import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CleaneatSharedModule } from '../../shared';
import {
    ToolService,
    ToolPopupService,
    ToolComponent,
    ToolDetailComponent,
    ToolDialogComponent,
    ToolPopupComponent,
    ToolDeletePopupComponent,
    ToolDeleteDialogComponent,
    toolRoute,
    toolPopupRoute,
} from './';

const ENTITY_STATES = [
    ...toolRoute,
    ...toolPopupRoute,
];

@NgModule({
    imports: [
        CleaneatSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ToolComponent,
        ToolDetailComponent,
        ToolDialogComponent,
        ToolDeleteDialogComponent,
        ToolPopupComponent,
        ToolDeletePopupComponent,
    ],
    entryComponents: [
        ToolComponent,
        ToolDialogComponent,
        ToolPopupComponent,
        ToolDeleteDialogComponent,
        ToolDeletePopupComponent,
    ],
    providers: [
        ToolService,
        ToolPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CleaneatToolModule {}
