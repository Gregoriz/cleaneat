import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ToolComponent } from './tool.component';
import { ToolDetailComponent } from './tool-detail.component';
import { ToolPopupComponent } from './tool-dialog.component';
import { ToolDeletePopupComponent } from './tool-delete-dialog.component';

import { Principal } from '../../shared';

export const toolRoute: Routes = [
    {
        path: 'tool',
        component: ToolComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tools'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tool/:id',
        component: ToolDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tools'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const toolPopupRoute: Routes = [
    {
        path: 'tool-new',
        component: ToolPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tool/:id/edit',
        component: ToolPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tool/:id/delete',
        component: ToolDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tools'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
